# API de Cálculo de Reajuste Salarial e Imposto de Renda para funcionários

## Sobre o Projeto

API para cálculo de Reajuste Salarial e Imposto de renda para funcionários de uma empresa fictícia criada para teste técnico de processo seletivo.

### Construído Com

- Java
- Spring Boot
- Postgresql
- Docker

### Features

- Endpoint para cadastro de funcionários
- Endpoint para computar reajuste salarial dos funcionários
- Endpoint para retornar o valor de imposto de renda devido pelo funcionário
- Testes unitários e de integração
- Utilização de padrões de projetos como Strategy, Chain of Responsibility e DTO

## Getting Started

Veja abaixo as instruções para configurar e iniciar o ambiente de desenvolvimento do projeto

### Pré-requisitos

Para executar o projeto, você deve ter o Java JDK na versão 8, o gerenciador de dependências Maven, o GIT e o banco de dados PostgreSQL instalado em sua máquina. Opcionalmente, você pode utilizar o Docker para o banco de dados, como será mostrado na sequência.

- Verificando a versão do Java

  ```sh
  java -version
  javac -version
  ```

- Verificando a versão do Maven
  ```sh
  mvn -v
  ```

### Setup

1. Clone o repositório
   ```sh
   git clone https://github.com/gabrielluciano/reajustesalarial.git
   ```
2. Acesse o diretório
   ```sh
   cd reajustesalarial
   ```
3. Inicie o banco de dados utilizando docker compose
   ```sh
   docker compose up -d
   ```
4. Inicie a aplicação em modo de desenvolvimento.
   ```sh
   mvn spring-boot:run
   ```

## Utilização

- Executar testes unitários 

  ```sh
  mvn test
  ```

- Executar testes de integração

  ```sh
  mvn test -Pintegration-tests
  ```

- Criando o arquivo jar do projeto

  ```sh
  mvn clean package -DskipTests
  ```

## Endpoints

```
POST /api/reajustesalarial

    Cria um novo funcionário e retorna o id do funcionário criado

    Status de retorno:
        201 (CREATED) - Funcionário criado com sucesso
        400 (BAD_REQUEST) - Request mal formatada
        409 (CONFLICT) - Funcionário com o CPF já existe

    Exemplo de corpo da requisição:

    {
      "nome": "John Doe",
      "cpf": "640.934.940-88",
      "telefone": "+1 555-123-4567",
      "dataNascimento": "1990-05-15",
      "salario": 800.14,
      "endereco": {
        "pais": "Brazil",
        "estado": "São Paulo",
        "cidade": "São Paulo",
        "logradouro": "123 Main Street",
        "numero": "456",
        "cep": "12345-678",
        "complemento": "Apt 101"
      }
    }
```

```
PUT /api/reajustesalarial

    Calcula reajuste do salário para o funcionário

    Status de retorno:
        200 (OK) - Salário reajustado com sucesso
        400 (BAD_REQUEST) - Request mal formatada
        404 (NOT_FOUND) - Funcionário com o CPF não encontrado 
        422 (UNPROCESSABLE_ENTITY) - Salário do funcionário já foi reajustado 

    Exemplo de corpo da requisição:

    {
      "cpf": "640.934.940-88"
    }

    Exemplo de corpo da resposta:

    {
      "cpf": "640.934.940-88",
      "novoSalario": 880.15,
      "valorReajuste": 80.01,
      "percentualReajuste": "10%"
    }
```

```
GET /api/reajustesalarial/{cpf}

    Obtém o imposto de renda para o funcionário

    Return status:
        200 (OK) - Imposto de renda retornado com sucesso
        400 (BAD_REQUEST) - CPF com formato incorreto 
        404 (NOT_FOUND) - Funcionário com o CPF não encontrado 
        422 (UNPROCESSABLE_ENTITY) - Salário do funcionário ainda não foi reajustado para calcular o imposto 

    Exemplos de corpo da resposta:

    {
      "cpf": "640.934.940-88",
      "imposto": "Isento"
    }

    {
      "cpf": "707.203.970-81",
      "imposto": "Imposto: R$ 193.37"
    }
```
