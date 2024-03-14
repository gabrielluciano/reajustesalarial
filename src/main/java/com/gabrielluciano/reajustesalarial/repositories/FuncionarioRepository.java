package com.gabrielluciano.reajustesalarial.repositories;

import com.gabrielluciano.reajustesalarial.models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    @Query("FROM Funcionario f WHERE f.cpf = :cpf")
    Optional<Funcionario> findByCpf(String cpf);
}
