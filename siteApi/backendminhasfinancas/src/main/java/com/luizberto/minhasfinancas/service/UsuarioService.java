package com.luizberto.minhasfinancas.service;

import com.luizberto.minhasfinancas.modelentity.Usuario;

public interface UsuarioService {
    Usuario autenticar (String email, String senha);
    Usuario salvarUsuario(Usuario usuario);
    void validarEmail(String email);
}
