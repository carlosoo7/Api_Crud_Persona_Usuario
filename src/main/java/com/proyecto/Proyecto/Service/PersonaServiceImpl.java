package com.proyecto.Proyecto.Service;

import com.proyecto.Proyecto.Entities.Persona;
import com.proyecto.Proyecto.Entities.Usuario;
import com.proyecto.Proyecto.Repository.PersonaRepository;
import com.proyecto.Proyecto.Repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;


@Service("PersonaService")
public class PersonaServiceImpl implements IPersonaService{

    @Autowired
    @Qualifier("IPersonaRepo")
    private PersonaRepository IPersonaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger logger = org.apache.logging.log4j.LogManager.getLogger(PersonaServiceImpl.class);

    @Override
    public ResponseEntity<?> guardar(Persona persona) {
        try{
            if (persona == null){
                logger.error("Error Porfavor Agregar Persona: El Campo Esta Vacio!");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error Porfavor Agregar Persona: El Campo Esta Vacio!");
            } else{
                persona.setUsuario(userActivation(persona));
                Persona guardado = IPersonaRepository.save(persona);
                Usuario user = guardado.getUsuario();
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/id/{id}")
                        .buildAndExpand(guardado.getId())
                        .toUri();
                return ResponseEntity.created(location).body((user));
            }
        }catch(Exception e) {
            logger.error("Error No_Agregado: La Persona No Se Agrego!" , e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error No_Agregado: La Persona No Se Agrego!");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> actualizar(Persona persona) {
        try{
            if((persona == null)||(persona.getId() == 0)||!(IPersonaRepository.existsById(persona.getId()))){
                logger.error("Error La persona con el id " + persona.getId() + " no existe o es null!");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error La persona con el id " + persona.getId() + " No existe o es null!");
            } else{

                    Persona personaActualizada = IPersonaRepository.save(persona);

                entityManager.flush();
                entityManager.refresh(personaActualizada);
                Optional<Persona> actualizado = IPersonaRepository.findById(personaActualizada.getId());
                return ResponseEntity.ok(actualizado.get());
            }
        }catch(Exception e) {
            logger.error("Error No_Editado: La Persona No Se Ha Editado!", e);
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
        String password= UUID.randomUUID().toString().substring(0,10);
        boolean exists;
        do {
            username = persona.getPNombre().substring(0, Math.min(persona.getPNombre().length(), 3)) +
                    persona.getPApellido().substring(0, Math.min(persona.getPApellido().length(), 3)) +
                    (1000 + random.nextInt(9000));
            Optional<Usuario> userName = Optional.ofNullable(usuarioRepository.findByUsername(username));
            apiKey = UUID.randomUUID().toString().substring(0,35) + UUID.randomUUID().toString().substring(0,5);
            Optional<Usuario> userApi = Optional.ofNullable(usuarioRepository.findByApikey(apiKey));
            exists = userName.isPresent() || userApi.isPresent();
        } while (exists);
        Usuario guardar = new Usuario(persona.getId(), username, password, apiKey, persona);
        return guardar;
    }
}
