package com.luizberto.minhasfinancas.repository;

import com.luizberto.minhasfinancas.enums.StatusLancamento;
import com.luizberto.minhasfinancas.enums.TipoLancamento;
import com.luizberto.minhasfinancas.modelentity.Lancamento;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LancamentoRepositoryTest {
    @Autowired
    LancamentoRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void deveSalvarLancamento(){
        Lancamento lancamento = criarLancamento();

        lancamento = repository.save(lancamento);

        Assertions.assertNotNull(lancamento.getId());
    }

    @Test
    public void deveDeletarLancamento(){
        Lancamento lancamento = criarEPersistirLancamento();

        lancamento = entityManager.find(Lancamento.class, lancamento.getId());
        repository.delete(lancamento);

        Lancamento lancamentoInexistente = entityManager.find(Lancamento.class, lancamento.getId());
        Assertions.assertNull(lancamentoInexistente);
    }

    @Test
    public void deveAtualizarLancamento(){
        Lancamento lancamento = criarEPersistirLancamento();

        lancamento.setAno(2018);
        lancamento.setDescricao("Teste Atualizar");
        lancamento.setStatus(StatusLancamento.CANCELADO);

        repository.save(lancamento);

        Lancamento lancamentoAtualizado = entityManager.find(Lancamento.class, lancamento.getId());

        Assertions.assertEquals(lancamentoAtualizado.getAno(), 2018);
        Assertions.assertEquals(lancamentoAtualizado.getDescricao(), "Teste Atualizar");
        Assertions.assertEquals(lancamentoAtualizado.getStatus(), StatusLancamento.CANCELADO);

    }

    @Test
    public void deveBuscarUmLancamentoPorId(){
        Lancamento lancamento = criarEPersistirLancamento();

        Optional<Lancamento> lancamentoEncontrado = repository.findById(lancamento.getId());

        Assertions.assertTrue(lancamentoEncontrado.isPresent());
    }


    private Lancamento criarEPersistirLancamento(){
        Lancamento lancamento = criarLancamento();
        entityManager.persist(lancamento);
        return lancamento;
    }

    public static Lancamento criarLancamento(){
        return Lancamento.builder()
                .ano(2019)
                .mes(1)
                .descricao("lançamento qualquer")
                .valor(BigDecimal.valueOf(10))
                .tipo(TipoLancamento.RECEITA)
                .status(StatusLancamento.PENDENTE)
                .dataCadastro(LocalDate.now())
                .build();
    }
}


