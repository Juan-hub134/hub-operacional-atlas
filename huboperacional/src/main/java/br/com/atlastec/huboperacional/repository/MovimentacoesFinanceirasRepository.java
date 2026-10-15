package br.com.atlastec.huboperacional.repository;

import br.com.atlastec.huboperacional.model.MovimentacoesFinanceiras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface MovimentacoesFinanceirasRepository extends JpaRepository<MovimentacoesFinanceiras, Integer> {
    @Query("SELECT SUM(m.valor) FROM MovimentacoesFinanceiras m WHERE m.tipo = :tipo AND m.status = :status")
    BigDecimal somarPorTipoEStatus(@Param("tipo") String tipo, @Param("status") String status);
    @Query("SELECT SUM(m.valor) FROM MovimentacoesFinanceiras m WHERE m.status = :status")
    BigDecimal somaSaldoPedente(@Param("status") String status);

}
