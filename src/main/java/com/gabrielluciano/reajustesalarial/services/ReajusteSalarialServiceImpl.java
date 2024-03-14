package com.gabrielluciano.reajustesalarial.services;

import com.gabrielluciano.reajustesalarial.dto.FuncionarioRequest;
import com.gabrielluciano.reajustesalarial.exceptions.DuplicatedCpfException;
import com.gabrielluciano.reajustesalarial.models.Funcionario;
import com.gabrielluciano.reajustesalarial.repositories.FuncionarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReajusteSalarialServiceImpl implements ReajusteSalarialService {

    private final FuncionarioRepository funcionarioRepository;
    private final ModelMapper modelMapper;

    public ReajusteSalarialServiceImpl(FuncionarioRepository funcionarioRepository, ModelMapper modelMapper) {
        this.funcionarioRepository = funcionarioRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public long cadastrarFuncionario(FuncionarioRequest funcionarioRequest) {
        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findByCpf(funcionarioRequest.getCpf());
        if (optionalFuncionario.isPresent())
            throw new DuplicatedCpfException(funcionarioRequest.getCpf());

        Funcionario funcionario = modelMapper.map(funcionarioRequest, Funcionario.class);
        return funcionarioRepository.save(funcionario).getId();
    }
}
