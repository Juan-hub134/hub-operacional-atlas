package br.com.atlastec.huboperacional.dto;

import java.math.BigDecimal;

public record MovimentacaoFinanceiraDto(String tipo, BigDecimal valor, String descricao, String categoria, String status) {
}
