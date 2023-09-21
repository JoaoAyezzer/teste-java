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
- **Obter uma person por ID**: `GET /pessoas/{id}`
- **Criar uma nova person com pelo menos um contact**: `POST /pessoas`
- **Atualizar uma person existente**: `PUT /pessoas`
- **Excluir uma person**: `DELETE /pessoas/{id}`

### Contatos
- **Excluir um contact de uma person**: `DELETE /contatos/{id}`

## Estrutura das Entidades

### Pessoa

- **id**: Identificador único da person (UUID).
- **nome**: Nome da person.
- **cpf**: CPF da person.
- **dataNascimento**: Data de nascimento da person.
- **contatos**: Lista de contatos da person (relação OneToMany).

### Contato

- **id**: Identificador único do contact (UUID).
- **nome**: Nome do contact.
- **telefone**: Número de telefone do contact.
- **email**: Endereço de e-mail do contact.

## Exemplo de Requisições e Respostas

Aqui estão alguns exemplos de requisições e respostas:

### Criar uma nova person com pelo menos um contact

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