package br.com.atlastec.huboperacional.service;

import br.com.atlastec.huboperacional.dto.MovimentacaoFinanceiraDto;
import br.com.atlastec.huboperacional.dto.MovimentacoesFinanceirasRepostaDto;
import br.com.atlastec.huboperacional.exception.idNaoEncontradoException;
import br.com.atlastec.huboperacional.model.MovimentacoesFinanceiras;
import br.com.atlastec.huboperacional.repository.MovimentacoesFinanceirasRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service
public class MovimentacoesFinanceirasService {
    private final MovimentacoesFinanceirasRepository movimentacoesFinanceirasRepository;
    public MovimentacoesFinanceirasService(MovimentacoesFinanceirasRepository movimentacoesFinanceirasRepository) {
        this.movimentacoesFinanceirasRepository = movimentacoesFinanceirasRepository;
    }
    public void registrarMovimentacoesFinanceiras(MovimentacaoFinanceiraDto movimentacaoFinanceiraDto) {
        MovimentacoesFinanceiras movimentacoesFinanceiras = new MovimentacoesFinanceiras();
        LocalDateTime data = LocalDateTime.now();
        movimentacoesFinanceiras.setTipo(movimentacaoFinanceiraDto.tipo());
        movimentacoesFinanceiras.setValor(movimentacaoFinanceiraDto.valor());
        movimentacoesFinanceiras.setDescricao(movimentacaoFinanceiraDto.descricao());
        movimentacoesFinanceiras.setCategoria(movimentacaoFinanceiraDto.categoria());
        movimentacoesFinanceiras.setStatus(movimentacaoFinanceiraDto.status());
        movimentacoesFinanceiras.setData(data);
        movimentacoesFinanceirasRepository.save(movimentacoesFinanceiras);
    }
    public List<MovimentacoesFinanceirasRepostaDto> listarMovimentacoesFinanceirasRepostas() {
        List<MovimentacoesFinanceiras> lista = movimentacoesFinanceirasRepository.findAll();
        List<MovimentacoesFinanceirasRepostaDto> listaDto = new ArrayList<>();
        for(MovimentacoesFinanceiras f: lista){
            MovimentacoesFinanceirasRepostaDto dto = new MovimentacoesFinanceirasRepostaDto(f.getId(),
                    f.getTipo(),
                    f.getValor(),
                    f.getDescricao(),
                    f.getCategoria(),
                    f.getStatus()
            );
            listaDto.add(dto);
        }
        return listaDto;
    }
    public void atualizarAlgumaMovimentacoesFinanceira(int id, MovimentacaoFinanceiraDto movimentacaoFinanceiraDto) {
        Optional<MovimentacoesFinanceiras> movimentacaoFinanceiras = movimentacoesFinanceirasRepository.findById(id);
        if(movimentacaoFinanceiras.isPresent()){
            LocalDateTime data = LocalDateTime.now();
            movimentacaoFinanceiras.get().setTipo(movimentacaoFinanceiraDto.tipo());
            movimentacaoFinanceiras.get().setValor(movimentacaoFinanceiraDto.valor());
            movimentacaoFinanceiras.get().setDescricao(movimentacaoFinanceiraDto.descricao());
            movimentacaoFinanceiras.get().setCategoria(movimentacaoFinanceiraDto.categoria());
            movimentacaoFinanceiras.get().setStatus(movimentacaoFinanceiraDto.status());
            movimentacaoFinanceiras.get().setData(data);
            movimentacoesFinanceirasRepository.save(movimentacaoFinanceiras.get());
        }else {
        throw new idNaoEncontradoException("id não encontrado");
    }
    }
    public void excluirMovimentacaoFinanceiras(int id){
        Optional<MovimentacoesFinanceiras> movimentacoesFinanceiras = movimentacoesFinanceirasRepository.findById(id);
        if(movimentacoesFinanceiras.isPresent()){
            movimentacoesFinanceirasRepository.deleteById(id);
        }else {
        throw new idNaoEncontradoException("id não encontrado");
        }
    }
    public MovimentacaoFinanceiraDto buscarEspecificamentePorAlgumaMovimentacaoFinanceira(int id){
        Optional<MovimentacoesFinanceiras> movimentacoesFinanceiras = movimentacoesFinanceirasRepository.findById(id);
        if(movimentacoesFinanceiras.isPresent()){
            MovimentacaoFinanceiraDto movimentacaoFinanceiraDto = new MovimentacaoFinanceiraDto(
                    movimentacoesFinanceiras.get().getTipo(),
                    movimentacoesFinanceiras.get().getValor(),
                    movimentacoesFinanceiras.get().getDescricao(),
                    movimentacoesFinanceiras.get().getCategoria(),
                    movimentacoesFinanceiras.get().getStatus());
            return movimentacaoFinanceiraDto;
        }else {
        throw new idNaoEncontradoException("id não encontrado");
        }
    }
    //vai calcular todos os registro da tabela receita com o tipo receita e  status pago
    public BigDecimal somarEntradas(){
//        List<MovimentacoesFinanceiras> lista = movimentacoesFinanceirasRepository.findAll();
//        BigDecimal totalEntradas = BigDecimal.ZERO;
//
//        for (MovimentacoesFinanceiras m : lista) {
//            if ("receita".equalsIgnoreCase(m.getTipo()) && "pago".equalsIgnoreCase(m.getStatus())) {
//                totalEntradas = totalEntradas.add(m.getValor()); // "add" já faz o trabalho e somar é como se fosse totalEntradas += m.getValor()
//            }
//        }
//
//        return totalEntradas; ANOTACAO = melhor usar o @query direto no repository fica melhor que usar a memória do java para calcular
        BigDecimal receita = movimentacoesFinanceirasRepository.somarPorTipoEStatus("receita","pago");
        if(receita == null){
            return  BigDecimal.ZERO;
        }
        return receita;
    }
    //soma as despesas do atributo valor com o tipo despesas e status pago
    public BigDecimal somarSaidas (){
        BigDecimal despesas = movimentacoesFinanceirasRepository.somarPorTipoEStatus("despesas", "pago");
        if(despesas == null){
            return BigDecimal.ZERO;
        }
        return despesas;
    }
    // soma da receita  - soma da despesas
    public BigDecimal saldoDoPeriodo(){
        BigDecimal lucro = somarEntradas();
        BigDecimal despesas = somarSaidas();
        BigDecimal saldoDoPeriodo = lucro.subtract(despesas);
        return saldoDoPeriodo;
    }
    //soma valores com o saldo pendente
    public BigDecimal saldoPendente(){
        BigDecimal saldoPendente = movimentacoesFinanceirasRepository.saldoPendenteLiquido();
        if (saldoPendente == null){
            return  BigDecimal.ZERO;
        }
        return saldoPendente;
    }
}
