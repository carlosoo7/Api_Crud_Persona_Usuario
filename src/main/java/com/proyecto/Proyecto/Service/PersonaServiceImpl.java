package com.proyecto.Proyecto.Service;

import com.proyecto.Proyecto.Entities.Persona;
import com.proyecto.Proyecto.Repository.PersonaRepository;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;


@Service("PersonaService")
public class PersonaServiceImpl implements IPersonaService{

    @Autowired
    @Qualifier("IPersonaRepo")
    private PersonaRepository IPersonaRepository;

    private static final Logger logger = org.apache.logging.log4j.LogManager.getLogger(PersonaServiceImpl.class);

    @Override
    public ResponseEntity<?> guardar(Persona persona) {
        try{
            if (persona == null){
                logger.error("Error Porfavor Agregar Persona: El Campo Esta Vacio!");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Error Porfavor Agregar Persona: El Campo Esta Vacio!");
            } else{
                Persona guardado = IPersonaRepository.save(persona);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/id/{id}")
                        .buildAndExpand(guardado.getId())
                        .toUri();
                return ResponseEntity.created(location).build();
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
}
