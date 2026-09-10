package br.com.atlastec.huboperacional.service;

import br.com.atlastec.huboperacional.repository.TreinamentoRepository;

public class TreinamentoService {
    private TreinamentoRepository treinamentoRepository;
    public TreinamentoService(TreinamentoRepository treinamentoRepository){
        this.treinamentoRepository = treinamentoRepository;
    }
}
