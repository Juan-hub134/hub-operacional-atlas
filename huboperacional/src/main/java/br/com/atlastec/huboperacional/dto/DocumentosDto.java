package br.com.atlastec.huboperacional.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.nimbusds.jose.shaded.gson.annotations.SerializedName;

public record DocumentosDto(String titulo,
                            String tipo) {
}
