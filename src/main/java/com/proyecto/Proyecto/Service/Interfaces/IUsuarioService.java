package com.proyecto.Proyecto.Service.Interfaces;

import com.proyecto.Proyecto.Entities.Usuario;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUsuarioService {

    ResponseEntity<?> guardar (Usuario usuario);
    ResponseEntity<?> actualizar (Usuario usuario);
    ResponseEntity<?> eliminar (int id);
    List<Usuario> consultarUsuario(Pageable pageable);
    Usuario findById(int id);
}
