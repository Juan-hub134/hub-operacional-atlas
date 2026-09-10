package br.com.atlastec.huboperacional.repository;

import br.com.atlastec.huboperacional.model.Registro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Integer> {
    Optional<Registro> findByToken(String token);
}
