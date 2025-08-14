# To-Do List API

![Java](https://img.shields.io/badge/Java-17%2B-blue?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green?style=for-the-badge&logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14%2B-blue?style=for-the-badge&logo=postgresql)

## 📝 Descrição

Esta é uma API RESTful completa para um sistema de gerenciamento de tarefas (To-Do List), desenvolvida com Spring Boot. A aplicação permite o cadastro e autenticação de usuários, criação de quadros de tarefas (Task Boards) e o gerenciamento completo de tarefas individuais, incluindo funcionalidades de filtro e paginação. A segurança é garantida por autenticação baseada em tokens JWT.

---

## ✨ Funcionalidades

* **Autenticação de Usuários:** Sistema de registro e login com geração de tokens JWT.
* **Gerenciamento de Quadros de Tarefas:** CRUD completo para criar, listar, atualizar e deletar quadros de tarefas (`TaskBoard`).
* **Gerenciamento de Tarefas:** CRUD completo para criar, listar, atualizar e deletar tarefas (`Task`).
* **Filtragem Avançada:** A listagem de tarefas permite filtros dinâmicos por status, prioridade e data de vencimento.
* **Paginação:** Todas as listagens (`/all`) são paginadas para melhor performance.
* **Segurança:** Endpoints protegidos usando Spring Security e JWT.

---

## 🛠️ Tecnologias Utilizadas

* **Java 17+**
* **Spring Boot 3.x**
* **Spring Security 6.x** (para autenticação e autorização)
* **Spring Data JPA** (para persistência de dados)
* **JWT (JSON Web Token)** (para autenticação stateless)
* **PostgreSQL** (como banco de dados relacional)
* **Lombok** (para redução de código boilerplate)
* **Maven** (para gerenciamento de dependências e build)

---

## 🚀 Como Executar

Siga os passos abaixo para configurar e executar o projeto em seu ambiente local.

### Pré-requisitos

* **JDK 17** ou superior instalado.
* **Maven 3.8** ou superior instalado.
* Uma instância do **PostgreSQL** rodando.

### Instalação

1.  **Clone o repositório:**
    ```bash
    git clone https://seu-repositorio-git/To-Do_List.git
    cd To-Do_List
    ```

2.  **Configure o Banco de Dados:**
    * Abra o arquivo `src/main/resources/application.properties` (ou `.yml`).
    * Altere as propriedades `spring.datasource.url`, `spring.datasource.username` e `spring.datasource.password` com as credenciais do seu banco de dados PostgreSQL.
    * Crie um banco de dados com o nome que você especificou.

3.  **Configure o JWT Secret:**
    * Ainda no arquivo de propriedades, configure a sua chave secreta para a geração de tokens JWT.

4.  **Execute a aplicação:**
    ```bash
    mvn spring-boot:run
    ```

A API estará disponível em `http://localhost:8080`.

---

## 📖 API Endpoints

A seguir está a documentação detalhada de todos os endpoints disponíveis na API.

### 👤 Autenticação (`/user`)

#### `POST /user/register`
Registra um novo usuário no sistema.

* **Request Body:**
    ```json
    {
      "username": "seu_usuario",
      "email": "usuario@email.com",
      "password": "sua_senha"
    }
    ```

#### `POST /user/login`
Autentica um usuário e retorna um token JWT.

* **Request Body:**
    ```json
    {
      "email": "usuario@email.com",
      "password": "sua_senha"
    }
    ```

* **Success Response (200 OK):**
    ```json
    {
      "token": "seu.jwt.token.aqui"
    }
    ```

---

### 📋 Quadros de Tarefas (`/task-board`)

*Todos os endpoints abaixo requerem um token JWT no cabeçalho `Authorization`.*
`Authorization: Bearer <seu_token_jwt>`

#### `POST /task-board/create`
Cria um novo quadro de tarefas.

* **Request Body:**
    ```json
    {
      "title": "Projeto da API",
      "status": "NOT_STARTED",
      "tasks": [
        "uuid-da-tarefa-1",
        "uuid-da-tarefa-2"
      ]
    }
    ```

#### `GET /task-board/all`
Lista todos os quadros de tarefas de forma paginada.

* **Query Parameters (Paginação):**
    * `page`: Número da página (ex: `0`).
    * `size`: Quantidade de itens por página (ex: `10`).
    * `sort`: Campo para ordenação (ex: `sort=title,asc`).

#### `PUT /task-board/update/{id}`
Atualiza um quadro de tarefas existente.

* **Path Variable:** `id` (UUID do quadro de tarefas).
* **Request Body:** Similar ao de criação.

OBS 1: Se a Tarefa já estiver em um Quadro de Tarefas a Tarefa não pode ser adicionada ao Quadro de Tarefas

OBS 2: O Quadro de Tarefas só pode ser concluído se suas Tarefas estiverem completas ou canceladas


#### `DELETE /task-board/delete/{id}`
Deleta um quadro de tarefas.

* **Path Variable:** `id` (UUID do quadro de tarefas).

---

### ✅ Tarefas (`/task`)

*Todos os endpoints abaixo requerem um token JWT.*

#### `POST /task/create`
Cria uma nova tarefa.

* **Request Body:**
    ```json
    {
      "title": "Configurar o banco de dados",
      "description": "Criar as tabelas e os tipos ENUM no PostgreSQL.",
      "deadline": "2025-12-31T23:59:00",
      "status": "NOT_STARTED",
      "priority": 1,
      "users": ["uuid-do-usuario-1"]
    }
    ```

#### `GET /task/all`
Lista todas as tarefas com filtros e paginação.

* **Query Parameters (Filtros Opcionais):**
    * `status`: Filtra por status (ex: `IN_PROGRESS`).
    * `priority`: Filtra por prioridade (ex: `1`).
    * `deadline`: Filtra por data (formato `YYYY-MM-DD`, ex: `2025-10-15`).
* **Query Parameters (Paginação):**
    * `page`, `size`, `sort`.

#### `PUT /task/update/{id}`
Atualiza uma tarefa existente.

* **Path Variable:** `id` (UUID da tarefa).
* **Request Body:** Similar ao de criação.

#### `DELETE /task/delete/{id}`
Deleta uma tarefa.

* **Path Variable:** `id` (UUID da tarefa).