# API REST - Sistema de Consertos de Veículos

Este repositório traz um projeto desenvolvido para a disciplina **Programação Web 3**, focado na criação de uma API REST completa para gerenciar consertos de veículos em uma oficina mecânica.

## Sobre o Projeto

A aplicação é uma API RESTful que permite gerenciar o cadastro de consertos de veículos, incluindo informações sobre o veículo, mecânico responsável e datas de entrada e saída. O sistema implementa operações CRUD completas com validações, paginação e exclusão lógica de registros.

### Funcionalidades

- **Cadastrar Conserto**: Registra um novo conserto com dados do veículo, mecânico e datas.
- **Listar Todos os Consertos**: Exibe todos os consertos com paginação.
- **Listar Consertos Ativos**: Retorna apenas consertos ativos com dados resumidos (sem paginação).
- **Buscar Conserto por ID**: Consulta detalhes de um conserto específico.
- **Atualizar Conserto**: Permite alterar data de saída e dados do mecânico.
- **Excluir Conserto**: Realiza exclusão lógica (soft delete) marcando o conserto como inativo.
- **Validações Automáticas**: Campos obrigatórios e formato de datas validados.

## Tecnologias

- **Java 21**
- **Spring Boot 3.5.5**
- **Spring Data JPA**
- **Spring Validation**
- **H2 Database** (banco em arquivo)
- **Flyway** (migrations)
- **Lombok** (redução de boilerplate)
- **Maven**

## Estrutura do Projeto

```
java-api-restful-project/
├── src/
│   └── main/
│       ├── java/
│       │   └── br/
│       │       └── edu/
│       │           └── ifsp/
│       │               └── leo/
│       │                   ├── ApiConsertosApplication.java
│       │                   ├── controller/
│       │                   │   └── ConsertoController.java
│       │                   ├── conserto/
│       │                   │   ├── Conserto.java
│       │                   │   ├── ConsertoRepository.java
│       │                   │   ├── DadosCadastroConserto.java
│       │                   │   ├── DadosAtualizacaoConserto.java
│       │                   │   ├── DadosDetalhamentoConserto.java
│       │                   │   └── DadosListagemConserto.java
│       │                   ├── mecanico/
│       │                   │   └── Mecanico.java
│       │                   └── veiculo/
│       │                       └── Veiculo.java
│       └── resources/
│           ├── application.properties
│           └── db/
│               └── migration/
│                   ├── V1__create-table-consertos.sql
│                   ├── V2__alter-table-consertos-add-cor-veiculo.sql
│                   └── V3__alter-table-consertos-add-ativo.sql
│                   └── V4__insert-initial-data.sql
├── pom.xml
├── LICENSE
└── README.md
```

## Modelo de Dados

### Entidade Principal: Conserto

```java
- id: Long (auto-increment)
- dataEntrada: String (formato: dd/MM/yyyy)
- dataSaida: String (formato: dd/MM/yyyy)
- ativo: Boolean (para exclusão lógica)
```

### Classes

**Mecanico**
```java
- nome: String (obrigatório)
- anosExperiencia: int
```

**Veiculo**
```java
- marca: String (obrigatório)
- modelo: String (obrigatório)
- ano: String (obrigatório, formato: yyyy)
- cor: String (opcional)
```

## Endpoints da API

### POST - Cadastrar Conserto
```http
POST /consertos
```
```http
{
  "dataEntrada": "YYYY-MM-DD",
  "dataSaida": "YYYY-MM-DD",
  "mecanico": {
    "nome": "string",
    "anosExperiencia": 0
  },
  "veiculo": {
    "marca": "string",
    "modelo": "string",
    "ano": "YYYY",
    "cor": "string"
  }
}
```
**Resposta**: `201 Created` com dados completos do conserto

---

### GET - Listar Todos (com paginação)
```http
GET /consertos?page=0&size=10
```
**Resposta**: `200 OK` com página de consertos

---

### GET - Listar Parcial (apenas ativos, sem paginação)
```http
GET /consertos/parcial
```
**Resposta**: `200 OK` com lista resumida (ID, datas, nome mecânico, marca e modelo)

---

### GET - Buscar por ID
```http
GET /consertos/{id}
```
**Resposta**: `200 OK` com detalhes completos ou `404 Not Found`

---

### PUT - Atualizar Conserto
```http
PUT /consertos
```
```http
{
  "id": 0,
  "dataSaida": "YYYY-MM-DD",
  "nomeMecanico": "string",
  "anosExperiencia": 0
}
```
**Resposta**: `200 OK` com dados atualizados

---

### DELETE - Exclusão Lógica
```http
DELETE /consertos/{id}
```
**Resposta**: `204 No Content`

## Migrations (Flyway)

O projeto utiliza Flyway para versionamento do banco de dados:

- **V1**: Criação da tabela `consertos` com campos básicos
- **V2**: Adição do campo `cor` na tabela
- **V3**: Adição do campo `ativo` para exclusão lógica
- **V4**: População inicial com dados de exemplo

As migrations são executadas automaticamente na inicialização da aplicação.

## Créditos

Projeto desenvolvido para a disciplina **PRW3 (Programação Web 3)**