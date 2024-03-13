package com.gabrielluciano.reajustesalarial.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "enderecos")
@SequenceGenerator(
        name = Endereco.SEQUENCE_NAME,
        sequenceName = Endereco.SEQUENCE_NAME,
        allocationSize = 50
)
public class Endereco {

    public static final String SEQUENCE_NAME = "enderecos_id_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    private Long id;
    @Column(length = 100, nullable = false)
    private String pais;
    @Column(length = 100, nullable = false)
    private String estado;
    @Column(length = 100, nullable = false)
    private String cidade;
    @Column(length = 200, nullable = false)
    private String logradouro;
    @Column(length = 20, nullable = false)
    private String numero;
    @Column(length = 20, nullable = false)
    private String cep;
    @Column(length = 255, nullable = true)
    private String complemento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Endereco endereco = (Endereco) object;
        return Objects.equals(pais, endereco.pais) && Objects.equals(estado, endereco.estado)
                && Objects.equals(cidade, endereco.cidade) && Objects.equals(logradouro, endereco.logradouro)
                && Objects.equals(numero, endereco.numero) && Objects.equals(cep, endereco.cep);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pais, estado, cidade, logradouro, numero, cep);
    }
}
