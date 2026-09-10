package br.com.atlastec.huboperacional.repository;

import br.com.atlastec.huboperacional.model.Treinamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreinamentoRepository extends JpaRepository<Treinamento, Integer> {
}
