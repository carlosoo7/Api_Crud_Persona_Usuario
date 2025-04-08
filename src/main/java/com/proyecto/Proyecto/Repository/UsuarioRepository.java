package com.proyecto.Proyecto.Repository;


import com.proyecto.Proyecto.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("IUsuarioRepo")
public interface UsuarioRepository extends JpaRepository<Usuario, Serializable>, ListPagingAndSortingRepository<Usuario, Serializable> {
    public abstract Usuario findById(int id);
    public abstract Usuario findByLog(String user);
    public abstract Usuario findByaPikey(String apikey);
}
