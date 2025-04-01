package com.proyecto.Proyecto.Controller;

import com.proyecto.Proyecto.Entities.Usuario;
import com.proyecto.Proyecto.Service.Interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Proyecto/usuario")
public class UsuarioController {
    @Autowired
    @Qualifier("UsuarioService")
    IUsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> agregarUsuario(@RequestBody @Validated Usuario usuario){
        return usuarioService.guardar(usuario);
    }

    @PutMapping
    public ResponseEntity<?> editarUsuario(@RequestBody @Validated Usuario usuario){
        return usuarioService.actualizar(usuario);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable("id") int id){
        return usuarioService.eliminar(id);
    }
    @GetMapping
    public List<Usuario> listaUsuario(Pageable pageable){
        return usuarioService.consultarUsuario(pageable);
    }
    @GetMapping("/id/{id}")
    public Usuario findById(@PathVariable ("id") int id){
        return usuarioService.findById(id);
    }
}
