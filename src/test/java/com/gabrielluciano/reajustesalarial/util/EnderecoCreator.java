package com.gabrielluciano.reajustesalarial.util;

import com.gabrielluciano.reajustesalarial.models.Endereco;

public class EnderecoCreator {

    public static Endereco createValidEndereco() {
        Endereco endereco = new Endereco();
        endereco.setId(1L);
        endereco.setCep("12345678912");
        endereco.setPais("Brasil");
        endereco.setEstado("Rio de Janeiro");
        endereco.setCidade("Rio de Janeiro");
        endereco.setLogradouro("Rua A, bairro B");
        endereco.setNumero("12A");
        endereco.setComplemento("Complemento");
        return endereco;
    }
}
