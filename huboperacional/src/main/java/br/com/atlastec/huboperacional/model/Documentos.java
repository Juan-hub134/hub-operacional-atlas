package br.com.atlastec.huboperacional.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Documentos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String titulo;
    @Column(name = "tipo")
    String tipoDocumento;
    byte[] documento;
    LocalDateTime data;
    public Documentos() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public byte[] getDocumento() {
        return documento;
    }

    public void setDocumento(byte[] documento) {
        this.documento = documento;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
