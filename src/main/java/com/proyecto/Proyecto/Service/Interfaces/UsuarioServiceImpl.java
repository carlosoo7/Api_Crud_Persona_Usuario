package com.proyecto.Proyecto.Service.Interfaces;

import com.proyecto.Proyecto.Entities.Usuario;
import com.proyecto.Proyecto.Repository.UsuarioRepository;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service("UsuarioService")
public class UsuarioServiceImpl implements IUsuarioService{

    @Autowired
    @Qualifier("IUsuarioRepo")
    private UsuarioRepository IUsuarioRepository;

    private static final Logger logger = org.apache.logging.log4j.LogManager.getLogger(UsuarioServiceImpl.class);

    @Override
    public ResponseEntity<?> guardar(Usuario usuario) {
        try {
            if (usuario == null) {
                logger.error("Error Porfavo Agregar Usuario:El Campo Esta Vacio!");
                return ResponseEntity.noContent().build();
            } else {
                Usuario guardado = IUsuarioRepository.save(usuario);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/id/{id}")
                        .buildAndExpand(guardado.getId())
                        .toUri();
                return ResponseEntity.created(location).build();
            }
        } catch (Exception e) {
            logger.error("Error Agregar_Usuario: El Usuario No Se Ha Agregado!");
            return ResponseEntity.internalServerError().body("Error Agregar_Usuario: El Usuario No Se Ha Agregado!");
        }
    }

    @Override
    public ResponseEntity<?> actualizar(Usuario usuario) {
        try{
          if(usuario == null){
              logger.error("Error El Usuario Tiene Un Valor Nulo O El Id Es 0!");
              return ResponseEntity.badRequest().body("Error El Usuario Tiene Un Valor Nulo O El Id Es 0!");
          }else{
             Usuario guardado = IUsuarioRepository.save(usuario);
              return ResponseEntity.ok(guardado);
          }
        } catch(Exception e){
            logger.error("Error No_Editado: El Usuario No Se Ha Editado!");
            return ResponseEntity.internalServerError().body("Error No_Editado: El Usuario No Se Ha Editado!");
        }
    }

    @Override
    public ResponseEntity<?> eliminar(int id) {
        try{
            if(id == 0){
                logger.error("Error Al Eliminar Usuario: El Id Del Uusario Es 0!");
                return ResponseEntity.badRequest().body("Error El Usuario Tiene Un Valor Nulo O El Id Es 0!");
            }else{
                Usuario usuario= IUsuarioRepository.findById(id);
                IUsuarioRepository.delete(usuario);
                return ResponseEntity.ok().body("El Usuario Eliminado Exitosamente");
            }
        }catch(Exception e){
            logger.error("Error No_Eliminado: El Usuario No Se Ha Eliminado");
            return ResponseEntity.internalServerError().body("Error No_Eliminado: El Usuario No Se Ha Eliminado");
        }
    }

    @Override
    public List<Usuario> consultarUsuario(Pageable pageable) {
        return IUsuarioRepository.findAll(pageable).getContent();
    }

    @Override
    public Usuario findById(int id) {
        return IUsuarioRepository.findById(id);
    }
}
