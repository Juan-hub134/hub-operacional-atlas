package br.com.atlastec.huboperacional.model;

import jakarta.persistence.*;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer idUsuario;
    @OneToOne //cardinialidade 1:1, olha o diagrama quando for revisitar o código.
    @MapsId // referencia o id que vem da tabela registro
    @JoinColumn(name = "id_registro")//local da tabela,vai caçar fk
    private Registro idRegistro;
    private String nome;
    private String email;
    private String senha;
    private String tipo;
    public Usuario() {}
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Registro getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Registro idRegistro) {
        this.idRegistro = idRegistro;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
