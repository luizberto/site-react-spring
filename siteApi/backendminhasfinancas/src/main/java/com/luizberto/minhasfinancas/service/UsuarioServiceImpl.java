package com.luizberto.minhasfinancas.service;

import com.luizberto.minhasfinancas.exceptions.ErroAutenticacao;
import com.luizberto.minhasfinancas.exceptions.RegraNegocioExeption;
import com.luizberto.minhasfinancas.modelentity.Usuario;
import com.luizberto.minhasfinancas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {


    private UsuarioRepository repository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> usuario = repository.findByEmail(email);

        if(!usuario.isPresent()){
            throw new ErroAutenticacao("email nao encontrado");
        }

        if(!usuario.get().getSenha().equals(senha)){
            throw new ErroAutenticacao("senha invalida!");
        }

        return usuario.get();

    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        validarEmail(usuario.getEmail());
        return repository.save(usuario);
    }

    @Override
    public void validarEmail(String email) {
        boolean existe = repository.existsByEmail(email);

        if(existe){
            throw new RegraNegocioExeption("usuario ja existe");
        }
    }

    @Override
    public Optional<Usuario> obterPorId(Long id) {
        return repository.findById(id);
    }
}
