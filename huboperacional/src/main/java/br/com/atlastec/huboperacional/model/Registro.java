package br.com.atlastec.huboperacional.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "registro")
public class Registro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String token;
    private LocalDateTime dataExpiracao;
    @Column(name = "tipo")
    private String tipoRegistro;
    public Registro() {
    }

    public Registro(String token, LocalDateTime dataExpiracao, String tipoRegistro) {
        this.token = token;
        this.dataExpiracao = dataExpiracao;
        this.tipoRegistro = tipoRegistro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(LocalDateTime dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }
}
