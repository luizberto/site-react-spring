package com.luizberto.minhasfinancas.controller;

import com.luizberto.minhasfinancas.dto.UsuarioDTO;
import com.luizberto.minhasfinancas.exceptions.ErroAutenticacao;
import com.luizberto.minhasfinancas.exceptions.RegraNegocioExeption;
import com.luizberto.minhasfinancas.modelentity.Usuario;
import com.luizberto.minhasfinancas.service.LancamentoService;
import com.luizberto.minhasfinancas.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final LancamentoService lancamentoService;



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

     @GetMapping("{id}/saldo")
    public ResponseEntity obterSaldo(@PathVariable Long id){
         Optional<Usuario> usuario = service.obterPorId(id);

         if (!usuario.isPresent()){
             return ResponseEntity.status(404).build();
         }
         BigDecimal saldo = lancamentoService.obterSaldoPorUsuario(id);
         return ResponseEntity.status(200).body(saldo);
     }
}
