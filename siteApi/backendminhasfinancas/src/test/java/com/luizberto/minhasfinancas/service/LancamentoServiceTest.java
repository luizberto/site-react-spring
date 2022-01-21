package com.luizberto.minhasfinancas.service;

import com.luizberto.minhasfinancas.enums.StatusLancamento;
import com.luizberto.minhasfinancas.exceptions.RegraNegocioExeption;
import com.luizberto.minhasfinancas.modelentity.Lancamento;
import com.luizberto.minhasfinancas.repository.LancamentoRepository;
import com.luizberto.minhasfinancas.repository.LancamentoRepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class LancamentoServiceTest {
    @SpyBean
    LancamentoServiceImpl service;

    @MockBean
    LancamentoRepository repository;


    @Test
    public void deveSalvarUmLancamento(){
        Lancamento lancamentoSalvar = LancamentoRepositoryTest.criarLancamento();
        Mockito.doNothing().when(service).validar(lancamentoSalvar);

        Lancamento lancamentoSalvo = LancamentoRepositoryTest.criarLancamento();
        lancamentoSalvo.setId(1l);
        lancamentoSalvo.setStatus(StatusLancamento.PENDENTE);
        Mockito.when(repository.save(lancamentoSalvar)).thenReturn(lancamentoSalvo);

        Lancamento lancamento = service.salvar(lancamentoSalvar);

        Assertions.assertEquals(lancamento.getId(), lancamentoSalvo.getId());
        Assertions.assertEquals(lancamento.getStatus(), StatusLancamento.PENDENTE);
    }

    @Test
    public void naoDeixeSalvarUmLancamentoQuandoHouverErroDeValidacao(){
        Assertions.assertThrows(RegraNegocioExeption.class, () -> {
            Lancamento lancamentoSalvar = LancamentoRepositoryTest.criarLancamento();
            Mockito.doThrow(RegraNegocioExeption.class).when(service).validar(lancamentoSalvar);

            service.salvar(lancamentoSalvar);
            Mockito.verify(repository, Mockito.never()).save(lancamentoSalvar);

        });

    }

    @Test
    public void deveAtualizarUmLancamento(){

        //cenario
        Lancamento lancamentoSalvo = LancamentoRepositoryTest.criarLancamento();
        lancamentoSalvo.setId(1l);
        lancamentoSalvo.setStatus(StatusLancamento.PENDENTE);
        Mockito.doNothing().when(service).validar(lancamentoSalvo);
        Mockito.when(repository.save(lancamentoSalvo)).thenReturn(lancamentoSalvo);

        //execução
        service.atualizar(lancamentoSalvo);

        //verificação
        Mockito.verify(repository, Mockito.times(1)).save(lancamentoSalvo);
    }

    @Test
    public void deveLancarUmErroAotentarAtualizarUmLancamentoQueAindaNaoFoiSalvo(){

        Assertions.assertThrows(NullPointerException.class, () -> {
            //cenario
            Lancamento lancamentoSalvar = LancamentoRepositoryTest.criarLancamento();

            //execução e verificação
           service.atualizar(lancamentoSalvar);
            Mockito.verify(repository, Mockito.never()).save(lancamentoSalvar);
        });
    }

    @Test
    public void deveDeletarUmLancamento(){
        //cenario
        Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
        lancamento.setId(1l);

        //execução
        service.deletar(lancamento);

        //vericação
        Mockito.verify(repository).delete(lancamento);
    }

    @Test
    public void deneLancarErroAoTentarDeletarUmLancamentoQueAindaNaoFoiSalvo(){
        Assertions.assertThrows(NullPointerException.class, () -> {
            //cenario
            Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();

            //execução
            service.deletar(lancamento);

            //verificação
            Mockito.verify(repository, Mockito.never()).delete(lancamento);
        });
    }

    @Test
    public void deveFiltrarLancamento(){
        //cenario
        Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
        lancamento.setId(1l);
        List<Lancamento> lista = Arrays.asList(lancamento);
        Mockito.when(repository.findAll(Mockito.any(Example.class))).thenReturn(lista);

        //execução
        List<Lancamento> resultado = service.buscar(lancamento);

    }

    @Test
    public void deveAtualizarOStatusDeUmLancamento(){
        //cenario
        Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
        lancamento.setId(1l);
        lancamento.setStatus(StatusLancamento.PENDENTE);

        StatusLancamento novoStatus = StatusLancamento.EFETIVADO;
        Mockito.doReturn(lancamento).when(service).atualizar(lancamento);

        //execucao
        service.atualizarStatus(lancamento, novoStatus);

        //verificações
        Assertions.assertEquals(lancamento.getStatus(), novoStatus);
        Mockito.verify(service).atualizar(lancamento);
    }

    @Test
    public void deveObterUmLancamentoPorId(){
        //cenario
        Long id = 1l;

        Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
        lancamento.setId(id);

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(lancamento));

        //execução
        Optional<Lancamento> resultado = service.obterPorId(id);

        Assertions.assertTrue(resultado.isPresent());
    }

    @Test
    public void deveRetornarVazioQuandoLancamentoMaoExiste(){
        //cenario
        Long id = 1l;

        Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
        lancamento.setId(id);

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        //execução
        Optional<Lancamento> resultado = service.obterPorId(id);

        Assertions.assertFalse(resultado.isPresent());
    }

}
