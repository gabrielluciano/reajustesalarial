package com.gabrielluciano.reajustesalarial.repositories;

import com.gabrielluciano.reajustesalarial.models.Funcionario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import com.gabrielluciano.reajustesalarial.util.FuncionarioCreator;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class FuncionarioRepositoryTest {

    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Autowired
    TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        entityManager.clear();
    }

    @Test
    void givenCpf_whenFindByCpf_ThenReturnsFuncionario() {
        Funcionario funcionario = FuncionarioCreator.createValidFuncionario();
        funcionario.setId(null);
        funcionario.getEndereco().setId(null);

        entityManager.persistAndFlush(funcionario);

        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findByCpf(funcionario.getCpf());

        assertThat(optionalFuncionario).isNotEmpty();
        assertThat(optionalFuncionario.get().getCpf()).isEqualTo(funcionario.getCpf());
    }

    @Test
    void givenCpf_whenFindByCpf_ThenReturnsEmptyOptional() {
        Funcionario funcionario = FuncionarioCreator.createValidFuncionario();
        funcionario.setId(null);
        funcionario.getEndereco().setId(null);

        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findByCpf(funcionario.getCpf());

        assertThat(optionalFuncionario).isEmpty();
    }
}
