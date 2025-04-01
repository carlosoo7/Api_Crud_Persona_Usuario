package com.proyecto.Proyecto.Controller;

import com.proyecto.Proyecto.Entities.Persona;
import com.proyecto.Proyecto.Service.Interfaces.IPersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Proyecto/persona")
public class PersonaController {

    @Autowired
    @Qualifier("PersonaService")
    IPersonaService personaService;

    @PostMapping
    public ResponseEntity<?> agregarPersona(@RequestBody @Validated Persona persona){
        return personaService.guardar(persona);
    }
    @PutMapping
    public ResponseEntity<?> editarPersona(@RequestBody @Validated Persona persona){
        return personaService.actualizar(persona);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPersona(@PathVariable("id") int id){
        return personaService.eliminar(id);
    }
    @GetMapping
    public ResponseEntity<Page<Persona>> listaPersona (Pageable pageable){
        return personaService.consultarPersona(pageable);
    }
    @GetMapping("/id/{id}")
    public Persona getById(@PathVariable("id")int id){
        return personaService.findById(id);
    }

    @GetMapping("/pnombre/{pnombre}")
    public ResponseEntity<Page<Persona>> getByPNombre(@PathVariable("pnombre") String pnombre,Pageable pageable){
        return personaService.findByNombre(pnombre,pageable);
    }
    @GetMapping("/edad/{edad}")
    public ResponseEntity<Page<Persona>> getByEdad(@PathVariable("edad") int edad,Pageable pageable){
        return personaService.findByEdad(edad, pageable);
    }
}
