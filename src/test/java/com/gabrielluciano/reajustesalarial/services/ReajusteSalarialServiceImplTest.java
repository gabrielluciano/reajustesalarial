package com.gabrielluciano.reajustesalarial.services;

import com.gabrielluciano.reajustesalarial.dto.FuncionarioRequest;
import com.gabrielluciano.reajustesalarial.dto.ReajusteRequest;
import com.gabrielluciano.reajustesalarial.dto.ReajusteResponse;
import com.gabrielluciano.reajustesalarial.exceptions.DuplicatedCpfException;
import com.gabrielluciano.reajustesalarial.exceptions.FuncionarioNotFoundException;
import com.gabrielluciano.reajustesalarial.exceptions.SalarioAlreadyReajustadoException;
import com.gabrielluciano.reajustesalarial.models.Funcionario;
import com.gabrielluciano.reajustesalarial.repositories.FuncionarioRepository;
import com.gabrielluciano.reajustesalarial.util.FuncionarioCreator;
import com.gabrielluciano.reajustesalarial.util.FuncionarioRequestCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ReajusteSalarialServiceImplTest {

    ReajusteSalarialServiceImpl reajusteSalarialService;

    @Mock
    FuncionarioRepository funcionarioRepository;

    @BeforeEach
    void setUp() {
        reajusteSalarialService = new ReajusteSalarialServiceImpl(funcionarioRepository, new ModelMapper());
    }

    @Test
    void givenFuncionarioRequest_whenCadastrarFuncionario_ThenReturnId() {
        Funcionario funcionario = FuncionarioCreator.createValidFuncionario();
        FuncionarioRequest funcionarioRequest = FuncionarioRequestCreator.createFromFuncionario(funcionario);
        when(funcionarioRepository.findByCpf(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());
        when(funcionarioRepository.save(ArgumentMatchers.any()))
                .thenReturn(funcionario);

        long id = reajusteSalarialService.cadastrarFuncionario(funcionarioRequest);

        assertEquals(funcionario.getId(), id);
    }

    @Test
    void givenFuncionarioRequestWithDuplicatedCpf_whenCadastrarFuncionario_thenThrowsException() {
        Funcionario funcionario = FuncionarioCreator.createValidFuncionario();
        FuncionarioRequest funcionarioRequest = FuncionarioRequestCreator.createFromFuncionario(funcionario);
        when(funcionarioRepository.findByCpf(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(funcionario));

        assertThrows(DuplicatedCpfException.class, () -> reajusteSalarialService
                .cadastrarFuncionario(funcionarioRequest));
    }

    @Test
    void givenFuncionarioRequestWithNotFoundCpf_whenCalcularReajuste_thenThrowsException() {
        Funcionario funcionario = FuncionarioCreator.createValidFuncionario();
        when(funcionarioRepository.findByCpf(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());

        assertThrows(FuncionarioNotFoundException.class, () -> reajusteSalarialService
                .calcularReajuste(new ReajusteRequest(funcionario.getCpf())));
    }

    @Test
    void givenFuncionarioRequestWithSalarioReajustado_whenCalcularReajuste_thenThrowsException() {
        Funcionario funcionario = FuncionarioCreator.createValidFuncionario();
        funcionario.setSalarioReajustado(true);
        when(funcionarioRepository.findByCpf(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(funcionario));

        assertThrows(SalarioAlreadyReajustadoException.class, () -> reajusteSalarialService
                .calcularReajuste(new ReajusteRequest(funcionario.getCpf())));
    }

    @Test
    void givenFuncionarioWithSalarioBetween0And400dot00_whenCalcularReajuste_thenExpect15percent() {
        BigDecimal salarioAtual = new BigDecimal("200.15");
        BigDecimal salarioReajustado = new BigDecimal("230.17");
        BigDecimal valorReajuste = new BigDecimal("30.02");
        String percentualReajuste = "15%";

        testCalcularReajusteAndExpect(salarioAtual, salarioReajustado, valorReajuste, percentualReajuste);

        salarioAtual = new BigDecimal("0.00");
        salarioReajustado = new BigDecimal("0.00");
        valorReajuste = new BigDecimal("0.00");
        percentualReajuste = "15%";

        testCalcularReajusteAndExpect(salarioAtual, salarioReajustado, valorReajuste, percentualReajuste);

        salarioAtual = new BigDecimal("400.00");
        salarioReajustado = new BigDecimal("460.00");
        valorReajuste = new BigDecimal("60.00");
        percentualReajuste = "15%";

        testCalcularReajusteAndExpect(salarioAtual, salarioReajustado, valorReajuste, percentualReajuste);
    }

    @Test
    void givenFuncionarioWithSalarioBetween400dot01And800dot00_whenCalcularReajuste_thenExpect12percent() {
        BigDecimal salarioAtual = new BigDecimal("457.31");
        BigDecimal salarioReajustado = new BigDecimal("512.18");
        BigDecimal valorReajuste = new BigDecimal("54.87");
        String percentualReajuste = "12%";

        testCalcularReajusteAndExpect(salarioAtual, salarioReajustado, valorReajuste, percentualReajuste);

        salarioAtual = new BigDecimal("400.01");
        salarioReajustado = new BigDecimal("448.01");
        valorReajuste = new BigDecimal("48.00");
        percentualReajuste = "12%";

        testCalcularReajusteAndExpect(salarioAtual, salarioReajustado, valorReajuste, percentualReajuste);

        salarioAtual = new BigDecimal("800.00");
        salarioReajustado = new BigDecimal("896.00");
        valorReajuste = new BigDecimal("96.00");
        percentualReajuste = "12%";

        testCalcularReajusteAndExpect(salarioAtual, salarioReajustado, valorReajuste, percentualReajuste);
    }

    @Test
    void givenFuncionarioWithSalarioBetween800dot01And1200dot00_whenCalcularReajuste_thenExpect10percent() {
        BigDecimal salarioAtual = new BigDecimal("939.17");
        BigDecimal salarioReajustado = new BigDecimal("1033.08");
        BigDecimal valorReajuste = new BigDecimal("93.91");
        String percentualReajuste = "10%";

        testCalcularReajusteAndExpect(salarioAtual, salarioReajustado, valorReajuste, percentualReajuste);

        salarioAtual = new BigDecimal("800.01");
        salarioReajustado = new BigDecimal("880.01");
        valorReajuste = new BigDecimal("80.00");
        percentualReajuste = "10%";

        testCalcularReajusteAndExpect(salarioAtual, salarioReajustado, valorReajuste, percentualReajuste);

        salarioAtual = new BigDecimal("1200.00");
        salarioReajustado = new BigDecimal("1320.00");
        valorReajuste = new BigDecimal("120.00");
        percentualReajuste = "10%";

        testCalcularReajusteAndExpect(salarioAtual, salarioReajustado, valorReajuste, percentualReajuste);
    }

    @Test
    void givenFuncionarioWithSalarioBetween1200dot01And2000dot00_whenCalcularReajuste_thenExpect7percent() {
        BigDecimal salarioAtual = new BigDecimal("1542.91");
        BigDecimal salarioReajustado = new BigDecimal("1650.91");
        BigDecimal valorReajuste = new BigDecimal("108.00");
        String percentualReajuste = "7%";

        testCalcularReajusteAndExpect(salarioAtual, salarioReajustado, valorReajuste, percentualReajuste);

        salarioAtual = new BigDecimal("1200.01");
        salarioReajustado = new BigDecimal("1284.01");
        valorReajuste = new BigDecimal("84.00");
        percentualReajuste = "7%";

        testCalcularReajusteAndExpect(salarioAtual, salarioReajustado, valorReajuste, percentualReajuste);

        salarioAtual = new BigDecimal("2000.00");
        salarioReajustado = new BigDecimal("2140.00");
        valorReajuste = new BigDecimal("140.00");
        percentualReajuste = "7%";

        testCalcularReajusteAndExpect(salarioAtual, salarioReajustado, valorReajuste, percentualReajuste);
    }

    @Test
    void givenFuncionarioWithSalarioHigherThan2000dot00_whenCalcularReajuste_thenExpect4percent() {
        BigDecimal salarioAtual = new BigDecimal("3671.12");
        BigDecimal salarioReajustado = new BigDecimal("3817.96");
        BigDecimal valorReajuste = new BigDecimal("146.84");
        String percentualReajuste = "4%";

        testCalcularReajusteAndExpect(salarioAtual, salarioReajustado, valorReajuste, percentualReajuste);

        salarioAtual = new BigDecimal("2000.01");
        salarioReajustado = new BigDecimal("2080.01");
        valorReajuste = new BigDecimal("80.00");
        percentualReajuste = "4%";

        testCalcularReajusteAndExpect(salarioAtual, salarioReajustado, valorReajuste, percentualReajuste);
    }

    private void testCalcularReajusteAndExpect(BigDecimal salarioAtual, BigDecimal salarioReajustado,
                                               BigDecimal valorReajuste, String percentualReajuste) {
        Funcionario funcionario = FuncionarioCreator.createValidFuncionario();
        funcionario.setSalarioReajustado(false);
        funcionario.setSalario(salarioAtual);
        when(funcionarioRepository.findByCpf(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(funcionario));

        ReajusteResponse reajusteResponse = reajusteSalarialService
                .calcularReajuste(new ReajusteRequest(funcionario.getCpf()));

        assertEquals(salarioReajustado, reajusteResponse.getNovoSalario());
        assertEquals(valorReajuste, reajusteResponse.getValorReajuste());
        assertEquals(percentualReajuste, reajusteResponse.getPercentualReajuste());
        assertEquals(funcionario.getCpf(), reajusteResponse.getCpf());
    }
}
