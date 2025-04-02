package com.proyecto.Proyecto.Service;

import com.proyecto.Proyecto.Entities.Persona;
import com.proyecto.Proyecto.Entities.Usuario;
import com.proyecto.Proyecto.Repository.PersonaRepository;
import com.proyecto.Proyecto.Repository.UsuarioRepository;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


@Service("PersonaService")
public class PersonaServiceImpl implements IPersonaService{

    @Autowired
    @Qualifier("IPersonaRepo")
    private PersonaRepository IPersonaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    private static final Logger logger = org.apache.logging.log4j.LogManager.getLogger(PersonaServiceImpl.class);

    @Override
    public ResponseEntity<?> guardar(Persona persona) {
        try{
            if (persona == null){
                logger.error("Error Porfavor Agregar Persona: El Campo Esta Vacio!");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Error Porfavor Agregar Persona: El Campo Esta Vacio!");
            } else{
                Persona guardado = IPersonaRepository.save(persona);
                Usuario user = userActivation(guardado);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/id/{id}")
                        .buildAndExpand(guardado.getId())
                        .toUri();
                return ResponseEntity.created(location).body((user));
            }
        }catch(Exception e) {
            logger.error("Error No_Agregado: La Persona No Se Agrego!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error No_Agregado: La Persona No Se Agrego!");
        }
    }

    @Override
    public ResponseEntity<?> actualizar(Persona persona) {
        try{
            if((persona == null)||(persona.getId() == 0)){
                logger.error("Error La Persona Tiene Un Valor Nulo O El Id Es 0!");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Error La Persona Tiene Un Valor Nulo O El Id Es 0!");
            } else{
                IPersonaRepository.save(persona);
                return ResponseEntity.ok(persona);
            }
        }catch(Exception e) {
            logger.error("Error No_Editado: La Persona No Se Ha Editado!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error No_Editado: La Persona No Se Ha Editado!");
        }
    }

    @Override
    public ResponseEntity<?> eliminar(int id) {
        try{
            if(id == 0){
                logger.error("Error Al Eliminar Persona: El Id De La Persona Es 0!");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Error Al Eliminar Persona: El Id De La Persona Es 0!");
            }else{
                Persona persona = IPersonaRepository.findById(id);
                IPersonaRepository.delete(persona);
                return ResponseEntity.ok().body("La Persona Ha Sido Eliminada");
            }
        }catch(Exception e) {
            logger.error("Error No_Eliminado: La Persona No Se Elimino!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error No_Eliminado: La Persona No Se Elimino!");
        }
    }

    @Override
    public ResponseEntity<List<Persona>> consultarPersona(
            @PageableDefault(size=10, sort="id", direction = Sort.Direction.ASC)Pageable pageable) {
        return ResponseEntity.ok(IPersonaRepository.findAll(pageable).getContent());
    }

    @Override
    public Persona findById(int id) {
        return IPersonaRepository.findById(id);
    }

    @Override
    public ResponseEntity<List<Persona>> findByNombre(
            String pnombre,@PageableDefault(size=10, sort="id", direction = Sort.Direction.ASC) Pageable pageable) {
        List<Persona> personas = IPersonaRepository.findBypNombre(pnombre, pageable).getContent();

        if (personas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(personas);
    }

    @Override
    public ResponseEntity<List<Persona>> findByEdad(
            int edad, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        List<Persona> personas = IPersonaRepository.findByEdad(edad, pageable).getContent();

        if (personas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(personas);
    }
    private Usuario userActivation(Persona persona) {
        Random random = new Random();
        String username;
        String apiKey;
        boolean exists;
        do {
            username = persona.getPNombre().substring(0, Math.min(persona.getPNombre().length(), 3)) +
                    persona.getSNombre().substring(0, Math.min(persona.getSNombre().length(), 3)) +
                    (1000 + random.nextInt(9000));
            Optional<Usuario> userName = Optional.ofNullable(usuarioRepository.findByUsuario(username));
            String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 20; i++) {
                int index = ThreadLocalRandom.current().nextInt(caracteres.length());
                sb.append(caracteres.charAt(index));
            }
            apiKey = sb.toString();
            Optional<Usuario> userApi = Optional.ofNullable(usuarioRepository.findByaPikey(apiKey));
            exists = userName.isPresent() || userApi.isPresent();
        } while (exists);
        Usuario guardar = new Usuario(persona.getId(), username, apiKey);
        usuarioRepository.save(guardar);
        return guardar;
    }


}
