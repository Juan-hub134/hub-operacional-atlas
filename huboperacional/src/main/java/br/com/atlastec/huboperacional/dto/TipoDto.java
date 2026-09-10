package br.com.atlastec.huboperacional.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record TipoDto(
        @JsonAlias("tipo")
        String tipoRegistro
) {
}
