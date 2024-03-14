package com.gabrielluciano.reajustesalarial.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "funcionarios")
@SequenceGenerator(
        name = Funcionario.SEQUENCE_NAME,
        sequenceName = Funcionario.SEQUENCE_NAME,
        allocationSize = 50
)
public class Funcionario {

    public static final String SEQUENCE_NAME = "funcionarios_id_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = SEQUENCE_NAME)
    private Long id;

    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true, length = 14)
    private String cpf;
    @Column(length = 20)
    private String telefone;
    @Column(nullable = false)
    private LocalDate dataNascimento;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salario;
    @Column(nullable = false)
    private boolean salarioReajustado;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public boolean isSalarioReajustado() {
        return salarioReajustado;
    }

    public void setSalarioReajustado(boolean salarioReajustado) {
        this.salarioReajustado = salarioReajustado;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Funcionario that = (Funcionario) object;
        return Objects.equals(cpf, that.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }
}
