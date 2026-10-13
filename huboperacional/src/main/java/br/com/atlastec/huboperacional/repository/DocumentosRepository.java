package br.com.atlastec.huboperacional.repository;

import br.com.atlastec.huboperacional.model.Documentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentosRepository extends JpaRepository<Documentos, Integer> {
}
