package com.proyecto.Proyecto.Service.Interfaces;

import com.proyecto.Proyecto.Entities.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
public interface IPersonaService {

        public ResponseEntity<?> guardar (Persona persona);
        public ResponseEntity<?> actualizar (Persona persona);
        public ResponseEntity<?> eliminar(int id);
        public ResponseEntity<Page<Persona>> consultarPersona(Pageable pageable);


        public Persona findById(int id);
        public ResponseEntity<Page<Persona>> findByNombre(String pnombre, Pageable pageable);
        public ResponseEntity<Page<Persona>> findByEdad(int edad, Pageable pageable);



}
