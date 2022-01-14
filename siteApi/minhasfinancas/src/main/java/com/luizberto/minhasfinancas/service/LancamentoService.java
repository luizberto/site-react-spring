package com.luizberto.minhasfinancas.service;

import com.luizberto.minhasfinancas.enums.StatusLancamento;
import com.luizberto.minhasfinancas.modelentity.Lancamento;

import java.util.List;

public interface LancamentoService {
    Lancamento salvar(Lancamento lancamento);

    Lancamento atualizar(Lancamento lancamento);

    void deletar(Lancamento lancamento);

    List<Lancamento> buscar(Lancamento lancamentoFiltro);

    void atualizarStatus(Lancamento lancamento, StatusLancamento status);

    void validar (Lancamento lancamento);
}
