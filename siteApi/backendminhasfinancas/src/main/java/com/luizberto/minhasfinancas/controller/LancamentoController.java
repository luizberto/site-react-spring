package com.luizberto.minhasfinancas.controller;

import com.luizberto.minhasfinancas.dto.AtualizaStatusDTO;
import com.luizberto.minhasfinancas.dto.LancamentoDTO;
import com.luizberto.minhasfinancas.enums.StatusLancamento;
import com.luizberto.minhasfinancas.enums.TipoLancamento;
import com.luizberto.minhasfinancas.exceptions.RegraNegocioExeption;
import com.luizberto.minhasfinancas.modelentity.Lancamento;
import com.luizberto.minhasfinancas.modelentity.Usuario;
import com.luizberto.minhasfinancas.service.LancamentoService;
import com.luizberto.minhasfinancas.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lancamentos")
@RequiredArgsConstructor
public class LancamentoController {
    private final LancamentoService service;
    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity buscar(
            @RequestParam (value = "descricao", required = false) String descricao,
            @RequestParam (value = "mes", required = false) Integer mes,
            @RequestParam (value = "ano", required = false) Integer ano,
            @RequestParam ("usuario") Long idUsuario
    ){
        Lancamento lancamentoFiltro = new Lancamento();
        lancamentoFiltro.setDescricao(descricao);
        lancamentoFiltro.setMes(mes);
        lancamentoFiltro.setAno(ano);

        Optional<Usuario> usuario = usuarioService.obterPorId(idUsuario);

        if(!usuario.isPresent()){
            return ResponseEntity.status(400).body("Não foi possivel encontrar usuario");
        }else {
            lancamentoFiltro.setUsuario(usuario.get());
        }

        List<Lancamento> lancamentos = service.buscar(lancamentoFiltro);
        return ResponseEntity.status(200).body(lancamentos);

    }

    @GetMapping("{id}")
    public ResponseEntity obterLancamento(@PathVariable ("id") Long id){
        return  service.obterPorId(id)
                .map( lancamento -> new ResponseEntity(converter(lancamento), HttpStatus.OK))
                .orElseGet( () ->  new ResponseEntity(HttpStatus.NOT_FOUND) );
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody LancamentoDTO dto){
        try{
            Lancamento entidade = converter(dto);
            entidade = service.salvar(entidade);
            return ResponseEntity.status(200).body(entidade);
        }catch (RegraNegocioExeption e){
            return ResponseEntity.status(400).body(e.getMessage());
        }

    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody LancamentoDTO dto){
        return service.obterPorId(id).map(entity -> {
           try{
               Lancamento lancamento = converter(dto);
               lancamento.setId(entity.getId());
               service.atualizar(lancamento);
               return ResponseEntity.status(200).body(lancamento);
           }catch (RegraNegocioExeption e){
               return ResponseEntity.status(400).body(e.getMessage());
           }
        }).orElseGet(() -> ResponseEntity.status(400).body("nao encontrado na base de dados"));
    }

    @PutMapping("{id}/atualiza-status")
    public ResponseEntity atualizarStatus(@PathVariable Long id, @RequestBody AtualizaStatusDTO dto){
        return service.obterPorId(id).map(entity ->{
            StatusLancamento statusSelecionado = StatusLancamento.valueOf(dto.getStatus());

            if (statusSelecionado == null){
                return ResponseEntity.status(400).body("Status nao disponivel");
            }

            try{
                entity.setStatus(statusSelecionado);
                service.atualizar(entity);
                return ResponseEntity.status(200).body(entity);
            }catch (RegraNegocioExeption e){
                return ResponseEntity.status(400).body(e.getMessage());
            }
        }).orElseGet(() ->
                ResponseEntity.status(400).body("nao encontrado na base de dados"));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable Long id){
            return service.obterPorId(id).map(entidade -> {
            service.deletar(entidade);
            return ResponseEntity.status(204).build();
        }).orElseGet(() ->
                    ResponseEntity.status(400).body("lançamento nao encontrado"));
    }

    private LancamentoDTO converter(Lancamento lancamento){
        return LancamentoDTO.builder()
                .id(lancamento.getId())
                .descricao(lancamento.getDescricao())
                .valor(lancamento.getValor())
                .mes(lancamento.getMes())
                .ano(lancamento.getAno())
                .status(lancamento.getStatus().name())
                .tipo(lancamento.getTipo().name())
                .usuario(lancamento.getUsuario().getId())
                .build();
    }

    private Lancamento converter(LancamentoDTO dto ){
        Lancamento lancamento = new Lancamento();
        lancamento.setId(dto.getId());
        lancamento.setDescricao(dto.getDescricao());
        lancamento.setAno(dto.getAno());
        lancamento.setMes(dto.getMes());
        lancamento.setValor(dto.getValor());

        Usuario usuario = usuarioService
                .obterPorId(dto.getUsuario())
                .orElseThrow(() -> new RegraNegocioExeption("Usuario nao encontrado"));

        lancamento.setUsuario(usuario);
        if(dto.getTipo() != null){
            lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
        }

        if (dto.getStatus() != null){
            lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
        }
        return lancamento;
    }
}
