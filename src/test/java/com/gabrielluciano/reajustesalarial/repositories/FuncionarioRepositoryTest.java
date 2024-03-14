package com.gabrielluciano.reajustesalarial.repositories;

import com.gabrielluciano.reajustesalarial.models.Endereco;
import com.gabrielluciano.reajustesalarial.models.Funcionario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class FuncionarioRepositoryTest {

    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Autowired
    TestEntityManager entityManager;

    Endereco endereco;
    Funcionario funcionario;

    @BeforeEach
    void setUp() {
        funcionario = getFuncionario();
        endereco = getEndereco();
        funcionario.setEndereco(endereco);

        entityManager.clear();
    }

    @Test
    void givenCpf_whenFindByCpf_ThenReturnsFuncionario() {
        entityManager.persistAndFlush(funcionario);

        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findByCpf(funcionario.getCpf());

        assertThat(optionalFuncionario).isNotEmpty();
        assertThat(optionalFuncionario.get().getCpf()).isEqualTo(funcionario.getCpf());
    }

    @Test
    void givenCpf_whenFindByCpf_ThenReturnsEmptyOptional() {
        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findByCpf(funcionario.getCpf());

        assertThat(optionalFuncionario).isEmpty();
    }

    private Endereco getEndereco() {
        Endereco e = new Endereco();
        e.setCep("12345678912");
        e.setPais("Brasil");
        e.setEstado("Rio de Janeiro");
        e.setCidade("Rio de Janeiro");
        e.setLogradouro("Rua A, bairro B");
        e.setNumero("12A");
        e.setComplemento("Complemento");
        return e;
    }

    private Funcionario getFuncionario() {
        Funcionario f = new Funcionario();
        f.setCpf("12345678912");
        f.setNome("Nome Funcionario");
        f.setSalario(new BigDecimal("3000.01"));
        f.setTelefone("5510999999999");
        f.setDataNascimento(LocalDate.parse("1996-10-21"));
        return f;
    }
}
