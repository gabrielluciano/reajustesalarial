package com.gabrielluciano.reajustesalarial.services;

import com.gabrielluciano.reajustesalarial.dto.FuncionarioRequest;
import com.gabrielluciano.reajustesalarial.dto.ReajusteRequest;
import com.gabrielluciano.reajustesalarial.dto.ReajusteResponse;
import com.gabrielluciano.reajustesalarial.exceptions.DuplicatedCpfException;
import com.gabrielluciano.reajustesalarial.exceptions.FuncionarioNotFoundException;
import com.gabrielluciano.reajustesalarial.exceptions.SalarioAlreadyReajustadoException;
import com.gabrielluciano.reajustesalarial.models.Funcionario;
import com.gabrielluciano.reajustesalarial.repositories.FuncionarioRepository;
import com.gabrielluciano.reajustesalarial.strategies.ReajusteSalarialProcessor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        funcionario.setSalario(funcionario.getSalario().setScale(2, RoundingMode.FLOOR));
        return funcionarioRepository.save(funcionario).getId();
    }

    @Override
    public ReajusteResponse calcularReajuste(ReajusteRequest reajusteRequest) {
        Funcionario funcionario = funcionarioRepository.findByCpf(reajusteRequest.getCpf())
                .orElseThrow(() -> new FuncionarioNotFoundException(reajusteRequest.getCpf()));

        if (funcionario.isSalarioReajustado())
            throw new SalarioAlreadyReajustadoException(funcionario.getCpf());

        ReajusteSalarialProcessor processor = ReajusteSalarialProcessor.fromSalario(funcionario.getSalario());

        BigDecimal salarioAtual = funcionario.getSalario();
        BigDecimal novoSalario = processor.getNovoSalario(salarioAtual);

        funcionario.setSalario(novoSalario);
        funcionario.setSalarioReajustado(true);
        funcionarioRepository.save(funcionario);

        ReajusteResponse reajusteResponse = new ReajusteResponse();
        reajusteResponse.setCpf(funcionario.getCpf());
        reajusteResponse.setNovoSalario(novoSalario);
        reajusteResponse.setValorReajuste(processor.getValorReajuste(salarioAtual));
        reajusteResponse.setPercentualReajuste(processor.getPercentualReajuste());
        return reajusteResponse;
    }
}
