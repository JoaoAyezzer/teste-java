# API REST Spring Boot para Gerenciamento de Pessoas e Contatos

Esta é uma API REST construída com Spring Boot 3.1.3 que permite o gerenciamento de pessoas e seus contatos. A aplicação utiliza Java 17 e a biblioteca Lombok para simplificar o código. O banco de dados relacional PostgreSQL é usado para armazenar os dados.

## Requisitos

Antes de executar a aplicação, certifique-se de ter as seguintes ferramentas instaladas:

- Java 17
- PostgreSQL
- Maven

## Configuração do Banco de Dados

1. Crie um banco de dados PostgreSQL chamado `crud_pessoa` (ou o nome de sua escolha).
   2. Abra o arquivo `src/main/resources/application.properties` e configure as propriedades do banco de dados:

   ```properties
    spring:
        datasource:
            url: jdbc:postgresql://localhost:5434/teste-java
            username: elotech
            password: 'testejava'
   ```

   Substitua `seu_usuario` e `sua_senha` pelas credenciais do seu banco de dados.

## Executando a Aplicação

Para executar a aplicação, siga os passos abaixo:

1. Clone este repositório:

   ```shell
   git clone https://github.com/JoaoAyezzer/teste-java.git
   ```

2. Navegue até o diretório do projeto:

   ```shell
   cd teste-java
   ```

3. Compile o projeto usando o Maven:

   ```shell
   mvn clean install
   ```

4. Inicie a aplicação:

   ```shell
   java -jar target/teste-java.jar
   ```

A API estará disponível em `http://localhost:8080`.

## Endpoints da API

A API oferece os seguintes endpoints:

### Pessoas

- **Listar todas as pessoas**: `GET /pessoas`
- **Obter uma pessoa por ID**: `GET /pessoas/{id}`
- **Criar uma nova pessoa com pelo menos um contato**: `POST /pessoas`
- **Atualizar uma pessoa existente**: `PUT /pessoas`
- **Excluir uma pessoa**: `DELETE /pessoas/{id}`

### Contatos
- **Excluir um contato de uma pessoa**: `DELETE /contatos/{id}`

## Estrutura das Entidades

### Pessoa

- **id**: Identificador único da pessoa (UUID).
- **nome**: Nome da pessoa.
- **cpf**: CPF da pessoa.
- **dataNascimento**: Data de nascimento da pessoa.
- **contatos**: Lista de contatos da pessoa (relação OneToMany).

### Contato

- **id**: Identificador único do contato (UUID).
- **nome**: Nome do contato.
- **telefone**: Número de telefone do contato.
- **email**: Endereço de e-mail do contato.

## Exemplo de Requisições e Respostas

Aqui estão alguns exemplos de requisições e respostas:

### Criar uma nova pessoa com pelo menos um contato

- **Requisição**: POST /api/pessoas

- **Corpo da Requisição**:

  ```json
  {
    "nome": "João Silva",
    "cpf": "12345678901",
    "dataNascimento": "1980-01-15",
    "contatos": [
      {
        "nome": "Contato 1",
        "telefone": "(11) 1234-5678",
        "email": "contato1@example.com"
      }
    ]
  }
  ```