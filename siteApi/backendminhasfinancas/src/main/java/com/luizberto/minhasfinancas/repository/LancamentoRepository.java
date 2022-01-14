package com.luizberto.minhasfinancas.repository;

import com.luizberto.minhasfinancas.modelentity.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
