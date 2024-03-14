CREATE SEQUENCE IF NOT EXISTS enderecos_id_seq
    START WITH 1
    INCREMENT BY 50
    MINVALUE 1
    NO MAXVALUE;

CREATE SEQUENCE IF NOT EXISTS funcionarios_id_seq
    START WITH 1
    INCREMENT BY 50
    MINVALUE 1
    NO MAXVALUE;

CREATE TABLE IF NOT EXISTS enderecos (
    id BIGINT PRIMARY KEY,
    cep VARCHAR(20) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    complemento VARCHAR(255),
    estado VARCHAR(100) NOT NULL,
    logradouro VARCHAR(200) NOT NULL,
    numero VARCHAR(20) NOT NULL,
    pais VARCHAR(100) NOT NULL
);

CREATE TABLE funcionarios (
    id BIGINT PRIMARY KEY,
    cpf VARCHAR(14) NOT NULL,
    data_nascimento DATE NOT NULL,
    nome VARCHAR(255) NOT NULL,
    salario NUMERIC(10,2) NOT NULL,
    salario_reajustado BOOLEAN NOT NULL,
    telefone VARCHAR(20),
    endereco_id BIGINT REFERENCES enderecos(id)
);

ALTER TABLE enderecos OWNER TO spring;
ALTER TABLE funcionarios OWNER TO spring;

ALTER SEQUENCE enderecos_id_seq OWNER TO spring;
ALTER SEQUENCE funcionarios_id_seq OWNER TO spring;
