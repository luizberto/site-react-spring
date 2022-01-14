package com.luizberto.minhasfinancas.controller;

import com.luizberto.minhasfinancas.dto.UsuarioDTO;
import com.luizberto.minhasfinancas.exceptions.ErroAutenticacao;
import com.luizberto.minhasfinancas.exceptions.RegraNegocioExeption;
import com.luizberto.minhasfinancas.modelentity.Usuario;
import com.luizberto.minhasfinancas.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private UsuarioService service;

    public UsuarioController(UsuarioService service){
        this.service = service;
    }

     @PostMapping
    public ResponseEntity salvar(@RequestBody UsuarioDTO dto){

        Usuario usuario = Usuario.builder().nome(dto.getNome()).email(dto.getEmail())
                .senha(dto.getSenha()).build();

        try{
            Usuario usuarioSalvo = service.salvarUsuario(usuario);
            return ResponseEntity.status(201).build();
        }catch (RegraNegocioExeption e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
     }

     @PostMapping("/autenticar")
    public ResponseEntity autenticar(@RequestBody UsuarioDTO dto){
        try {
            Usuario usuarioAutenticado = service.autenticar(dto.getEmail(), dto.getSenha());
            return ResponseEntity.status(200).body(usuarioAutenticado);
        }catch (ErroAutenticacao e){
            return ResponseEntity.status(403).body(e.getMessage());
        }
     }
}
