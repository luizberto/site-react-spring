package com.luizberto.minhasfinancas.service;


import com.luizberto.minhasfinancas.exceptions.ErroAutenticacao;
import com.luizberto.minhasfinancas.exceptions.RegraNegocioExeption;
import com.luizberto.minhasfinancas.modelentity.Usuario;
import com.luizberto.minhasfinancas.repository.UsuarioRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @SpyBean
    UsuarioService service;

    @MockBean
    UsuarioRepository repository;

    @Test
    public void deveAutenticarUmUsuarioComSucesso(){
        Assertions.assertDoesNotThrow( () -> {
            //cenario
            String email = "usuario4@gmail";
            String senha = "12345jj";

            Usuario usuario = Usuario.builder().email(email).senha(senha).id(2l).build();

            Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));

            //acao
            Usuario result = service.autenticar(email, senha);
            Assertions.assertNotNull(result);

        });
    }

    @Test
    public void deveLancarErroQuandoNaoEncontrarEmailCadastradoPeloUsuario(){
        Assertions.assertThrows(ErroAutenticacao.class, () -> {
           Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

           service.autenticar("email@email", "senha");
        });
    }

    @Test
    public void deveLancarErroQuandoASenhaNaoCorresponderComACadastradaPeloUsuario(){
        Assertions.assertThrows(ErroAutenticacao.class, () -> {
            String email = "email@gmail";
            Usuario usuario = Usuario.builder().email(email).senha("1234").build();
            Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));

            service.autenticar(email, "senha");
        });
    }


    @Test
    public void deveValidarEmail(){
        Assertions.assertDoesNotThrow( () -> {
            //cenario
            Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);

            //acao
            service.validarEmail("usuario@gmail.com");
        }

            );

    }

    @Test
    public void deveLancarErroCasoOEmailJaEstejaCadastrado(){
        Assertions.assertThrows(RegraNegocioExeption.class, ()->{
            //cenario
            Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);

            //acao
            service.validarEmail("usuario@gmail.com");
        });
    }

    @Test
    public void deveSalvarUmUsuario(){
        Assertions.assertDoesNotThrow(()->{
            //cenario
            Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
            Usuario usuario = Usuario.builder().nome("nome").email("email@email")
                    .senha("senha").id(1l).build();

            Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

            //acao
          Usuario usuarioSalvo = service.salvarUsuario(new Usuario());

          //verificacao
            Assertions.assertNotNull(usuarioSalvo);
            Assertions.assertEquals(usuarioSalvo.getNome(), usuarioSalvo.getNome());

        });
    }

    @Test
    public void naoDeveSalvarUmUsuarioComEmailCadastrado(){
        Assertions.assertThrows(RegraNegocioExeption.class, () -> {
           //cenario
           String email = "email@email";
           Usuario usuario = Usuario.builder().email(email).build();
           Mockito.doThrow(RegraNegocioExeption.class).when(service).validarEmail(email);

           //acao
            service.salvarUsuario(usuario);

            //verificacao
            Mockito.verify(repository, Mockito.never()).save(usuario);
        });
    }




}
