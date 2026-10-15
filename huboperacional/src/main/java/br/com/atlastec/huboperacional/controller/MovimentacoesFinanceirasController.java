package br.com.atlastec.huboperacional.controller;

import br.com.atlastec.huboperacional.dto.MovimentacaoFinanceiraDto;
import br.com.atlastec.huboperacional.dto.MovimentacoesFinanceirasRepostaDto;
import br.com.atlastec.huboperacional.exception.idNaoEncontradoException;
import br.com.atlastec.huboperacional.service.MovimentacoesFinanceirasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atlashub")
public class MovimentacoesFinanceirasController {
    private final MovimentacoesFinanceirasService movimentacoesFinanceirasService;
    public MovimentacoesFinanceirasController(MovimentacoesFinanceirasService movimentacoesFinanceirasService) {
        this.movimentacoesFinanceirasService = movimentacoesFinanceirasService;
    }
    @PostMapping("api/admin/registrar/movimentacao/financeira")
    public ResponseEntity<String> registrarMovimentacaoFinanceira(@RequestBody MovimentacaoFinanceiraDto movimentacaoFinanceiraDto){
        movimentacoesFinanceirasService.registrarMovimentacoesFinanceiras(movimentacaoFinanceiraDto);
        return ResponseEntity.ok("registrado com sucesso");
    }
    @GetMapping("/api/admin/listar/movimentacao/financeira")
    public ResponseEntity<List<MovimentacoesFinanceirasRepostaDto>> listarMovimentacoesFinanceira(){
        return ResponseEntity.ok(movimentacoesFinanceirasService.listarMovimentacoesFinanceirasRepostas());
    }
    @PostMapping("/api/admin/{id}/atualizar/movimentacao/financeira")
    public ResponseEntity<String> atualizarMovimentacaoFinanceira(@PathVariable Integer id,@RequestBody MovimentacaoFinanceiraDto movimentacaoFinanceiraDto){
        try {
            movimentacoesFinanceirasService.atualizarAlgumaMovimentacoesFinanceira(id,movimentacaoFinanceiraDto);
            return ResponseEntity.ok("atualizado com sucesso");
        }catch (idNaoEncontradoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping("/api/admin/{id}/excluir/movimentacao/financeira")
    public ResponseEntity<String> atualizarMovimentacaoFinanceira(@PathVariable Integer id){
        try {
            movimentacoesFinanceirasService.excluirMovimentacaoFinanceiras(id);
            return ResponseEntity.ok("excluido com sucesso");
        }catch (idNaoEncontradoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/api/admin/{id}/buscar/movimentacao/financeira")
    public ResponseEntity<?> buscarEspecificamentePorAlgumaMovimentacaoFinanceira(@PathVariable Integer id){
        try {
            return ResponseEntity.ok(movimentacoesFinanceirasService.buscarEspecificamentePorAlgumaMovimentacaoFinanceira(id));
        }catch (idNaoEncontradoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/api/admin/soma/entradas/financeiras")
    public ResponseEntity<?> somarEntrada(){
        try {
            return ResponseEntity.ok(movimentacoesFinanceirasService.somarEntradas());
        }catch (idNaoEncontradoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/api/admin/soma/saidas/financeiras")
    public ResponseEntity<?> somarSaidas(){
        try {
            return ResponseEntity.ok(movimentacoesFinanceirasService.somarSaidas());
        }catch (idNaoEncontradoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/api/admin/saldo/periodo")
    public ResponseEntity<?> saldoPeriodo(){
        try {
            return ResponseEntity.ok(movimentacoesFinanceirasService.saldoDoPeriodo());
        }catch (idNaoEncontradoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/api/admin/soma/saldo/pendente")
    public ResponseEntity<?> saldoPendente(){
        try {
            return ResponseEntity.ok(movimentacoesFinanceirasService.saldoPendente());
        }catch (idNaoEncontradoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
