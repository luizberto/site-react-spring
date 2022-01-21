package com.luizberto.minhasfinancas.repository;


import com.luizberto.minhasfinancas.modelentity.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void deveVerificarExistenciaDoEmail(){
        //condição
        Usuario usuario = Usuario.builder().nome("usuario").email("usuario@gmail.com").build();
        entityManager.persist(usuario);

        //ação
        boolean result = repository.existsByEmail("usuario@gmail.com");

        //resultado
        Assertions.assertTrue(result);
    }

    @Test
    public void deveRetornarFalsoQuandoUsuarioCadastradoComOEmail(){
        //CENARIO
        //ação
        boolean result = repository.existsByEmail("usuario@gmail.com");
        //verificacao
        Assertions.assertFalse(result);
    }

    @Test
    public void devePersistirUmUsuarioNaBaseDeDados(){
        //cenario
        Usuario usuario = Usuario.builder()
                .nome("usuario").email("usuario@gmail").senha("senha").build();

        //acao
        Usuario usuarioSalvo = repository.save(usuario);

        //verificacao
        Assertions.assertNotNull(
                usuarioSalvo.getId()
        );
    }

    @Test
    public void deveBuscarUmUsuarioPorEmail(){
        //cenario
        Usuario usuario = Usuario.builder()
                .nome("usuario").email("usuario@gmail").senha("senha").build();
        entityManager.persist(usuario);

        //acao
        Optional<Usuario> result =  repository.findByEmail("usuario@gmail");

        //atraves desse comando se verifica se é verdadeiro a condição do teste
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    public void deveRetornarVazioQuandoNãoHouverUmUsuarioPorEmailNaBaseDeDados(){
        //cenario

        //acao
        Optional<Usuario> result =  repository.findByEmail("usuario@gmail");

        //atraves desse comando se verifica se é falsa a condição do teste
        Assertions.assertFalse(result.isPresent());
    }


}
