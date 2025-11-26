# API REST - Sistema de Consertos de Veículos

## Sobre o Projeto

A aplicação é uma API RESTful que permite gerenciar o cadastro de consertos de veículos, incluindo informações sobre o veículo, mecânico responsável e datas de entrada e saída. O sistema implementa operações CRUD completas com validações, paginação e exclusão lógica de registros.

---

## Tecnologias 

- **Java 21**
- **Spring Boot 3.5.5**
- **Spring Data JPA**
- **Spring Validation**
- **H2 Database**
- **Flyway**
- **Lombok**
- **Maven**

---

## Endpoints da API

### POST - Cadastrar Conserto
```http
POST /repairs
```
```http
{
  "entryDate": "YYYY-MM-DD",
  "exitDate": "YYYY-MM-DD",
  "mechanic": {
    "mechanicName": "string",
    "yearsOfExperience": 0
  },
  "vehicle": {
    "vehicleMake": "string",
    "vehicleModel": "string",
    "vehicleYear": "YYYY",
    "vehicleColor": "string"
  }
}
```
**Resposta**: `201 Created` com dados completos do conserto

---

### GET - Listar Todos (com paginação)
```http
GET /repairs?page=0&size=10
```
**Resposta**: `200 OK` com página de consertos

---

### GET - Listar Parcial (apenas ativos, sem paginação)
```http
GET /repairs/active
```
**Resposta**: `200 OK` com lista resumida (ID, datas, nome mecânico, marca e modelo)

---

### GET - Buscar por ID
```http
GET /repairs/{id}
```
**Resposta**: `200 OK` com detalhes completos ou `404 Not Found`

---

### PUT - Atualizar Conserto
```http
PUT /repairs
```
```http
{
  "id": 0,
  "exitDate": "YYYY-MM-DD",
  "mechanicName": "string",
  "yearsOfExperience": 0
}
```
**Resposta**: `200 OK` com dados atualizados

---

### DELETE - Exclusão Lógica
```http
DELETE /repairs/{id}
```
**Resposta**: `204 No Content`

## Migrations (Flyway)

O projeto utiliza Flyway para versionamento do banco de dados:

- **V1**: Criação da tabela `repairs` com campos básicos
- **V2**: Adição do campo `vehicle_color` na tabela
- **V3**: Adição do campo `active` para exclusão lógica
- **V4**: População inicial da tabela `repairs` com dados de exemplo

As migrations são executadas automaticamente na inicialização da aplicação.

## Créditos

Projeto desenvolvido para a disciplina **PRW3 (Programação Web 3)**