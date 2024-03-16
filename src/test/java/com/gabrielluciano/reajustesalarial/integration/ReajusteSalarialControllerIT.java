package com.gabrielluciano.reajustesalarial.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gabrielluciano.reajustesalarial.dto.FuncionarioRequest;
import com.gabrielluciano.reajustesalarial.dto.ReajusteRequest;
import com.gabrielluciano.reajustesalarial.models.Funcionario;
import com.gabrielluciano.reajustesalarial.repositories.FuncionarioRepository;
import com.gabrielluciano.reajustesalarial.util.FuncionarioCreator;
import com.gabrielluciano.reajustesalarial.util.FuncionarioRequestCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class ReajusteSalarialControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @BeforeEach
    void setUp() {
        funcionarioRepository.deleteAll();
    }

    @Test
    void givenFuncionarioRequest_WhenCadastrarFuncionario_thenReturn201AndId() throws Exception {
        FuncionarioRequest funcionarioRequest = FuncionarioRequestCreator.createValidFuncionarioRequest();

        mockMvc.perform(post("/api/reajustesalarial").
                        content(asJsonString(funcionarioRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(matchesPattern("\\d+")));
    }

    @Test
    void givenFuncionarioRequestWithDuplicatedCpf_WhenCadastrarFuncionario_thenReturn409() throws Exception {
        FuncionarioRequest funcionarioRequest = FuncionarioRequestCreator.createValidFuncionarioRequest();

        mockMvc.perform(post("/api/reajustesalarial").
                content(asJsonString(funcionarioRequest))
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(post("/api/reajustesalarial").
                        content(asJsonString(funcionarioRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void givenFuncionarioRequestWithInvalidCpf_WhenCadastrarFuncionario_thenReturn400() throws Exception {
        testCadastrarFuncionarioWithCpfAndExpectBadRequest("000.000.000-00");
        testCadastrarFuncionarioWithCpfAndExpectBadRequest("00000000000");
        testCadastrarFuncionarioWithCpfAndExpectBadRequest("123.456.789-00");
        testCadastrarFuncionarioWithCpfAndExpectBadRequest("55158324000");
    }

    private void testCadastrarFuncionarioWithCpfAndExpectBadRequest(String cpf) throws Exception {
        FuncionarioRequest funcionarioRequest = FuncionarioRequestCreator.createValidFuncionarioRequest();
        funcionarioRequest.setCpf(cpf);

        mockMvc.perform(post("/api/reajustesalarial")
                        .content(asJsonString(funcionarioRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenReajusteRequest_WhenCalcularReajuste_thenReturn200AndReajusteResponse() throws Exception {
        BigDecimal salarioAtual = new BigDecimal("200.15");
        BigDecimal salarioReajustado = new BigDecimal("230.17");
        BigDecimal valorReajuste = new BigDecimal("30.02");
        String percentualReajuste = "15%";

        Funcionario funcionario = FuncionarioCreator.createValidFuncionario();
        funcionario.setSalarioReajustado(false);
        funcionario.setSalario(salarioAtual);
        funcionario.setId(null);
        funcionario.getEndereco().setId(null);
        funcionarioRepository.save(funcionario);

        mockMvc.perform(put("/api/reajustesalarial")
                        .content(asJsonString(new ReajusteRequest(funcionario.getCpf())))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.novoSalario").value(salarioReajustado))
                .andExpect(jsonPath("$.valorReajuste").value(valorReajuste))
                .andExpect(jsonPath("$.percentualReajuste").value(percentualReajuste))
                .andExpect(jsonPath("$.cpf").value(funcionario.getCpf()));
    }

    @Test
    void givenReajusteRequestWithNotFoundCpf_WhenCalcularReajuste_thenReturn404() throws Exception {
        Funcionario funcionario = FuncionarioCreator.createValidFuncionario();
        mockMvc.perform(put("/api/reajustesalarial")
                        .content(asJsonString(new ReajusteRequest(funcionario.getCpf())))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void givenReajusteRequestWithSalarioReajustado_WhenCalcularReajuste_thenReturn422() throws Exception {
        Funcionario funcionario = FuncionarioCreator.createValidFuncionario();

        funcionario.setSalarioReajustado(true);
        funcionario.setId(null);
        funcionario.getEndereco().setId(null);
        funcionarioRepository.save(funcionario);

        mockMvc.perform(put("/api/reajustesalarial")
                        .content(asJsonString(new ReajusteRequest(funcionario.getCpf())))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void givenReajusteRequestWithInvalidCpf_WhenCalcularReajuste_thenReturn400() throws Exception {
        testCalcularReajusteWithCpfAndExpectBadRequest("000.000.000-00");
        testCalcularReajusteWithCpfAndExpectBadRequest("00000000000");
        testCalcularReajusteWithCpfAndExpectBadRequest("123.456.789-00");
        testCalcularReajusteWithCpfAndExpectBadRequest("55158324000");
    }

    private void testCalcularReajusteWithCpfAndExpectBadRequest(String cpf) throws Exception {
        ReajusteRequest reajusteRequest = new ReajusteRequest(cpf);

        mockMvc.perform(put("/api/reajustesalarial")
                        .content(asJsonString(reajusteRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    static String asJsonString(final Object object) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(object);
    }
}
