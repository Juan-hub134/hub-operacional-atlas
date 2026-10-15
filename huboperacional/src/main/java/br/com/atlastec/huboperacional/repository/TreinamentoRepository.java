package br.com.atlastec.huboperacional.repository;

import br.com.atlastec.huboperacional.model.Treinamento;
import br.com.atlastec.huboperacional.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface TreinamentoRepository extends JpaRepository<Treinamento, Integer> {
    Optional<Treinamento> findByIdUsuarioAndAreaAndDescricao(Usuario idUsuario, String area, String descricao);
    List<Treinamento> findAllByIdUsuario(Usuario idUsuario);
}
