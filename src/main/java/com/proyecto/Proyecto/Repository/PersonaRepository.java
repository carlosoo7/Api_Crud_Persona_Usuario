package com.proyecto.Proyecto.Repository;

import com.proyecto.Proyecto.Entities.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository("IPersonaRepo")
public interface PersonaRepository extends JpaRepository<Persona, Serializable>, PagingAndSortingRepository<Persona, Serializable> {
public abstract Persona findById (int id);
public abstract Page<Persona> findByEdad(int edad,Pageable pageable);
public abstract Page<Persona> findBypNombre(String pnombre,Pageable pageable);
public abstract Page<Persona> findBypApellido(String papellido, Pageable pageable);
public abstract Page<Persona> findAll(Pageable pageable);
}
