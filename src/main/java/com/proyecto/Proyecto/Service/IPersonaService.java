package com.proyecto.Proyecto.Service;

import com.proyecto.Proyecto.Entities.Persona;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPersonaService {

        public ResponseEntity<?> guardar (Persona persona);
        public ResponseEntity<?> actualizar (Persona persona);
        public ResponseEntity<?> eliminar(int id);
        public ResponseEntity<List<Persona>> consultarPersona(Pageable pageable);


        public Persona findById(int id);
        public ResponseEntity<List<Persona>> findByNombre(String pnombre, Pageable pageable);
        public ResponseEntity<List<Persona>> findByEdad(int edad, Pageable pageable);



}
