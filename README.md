# 🛒 E-commerce Product API

API desenvolvida em **Spring Boot** para gerenciar o catálogo de produtos de um sistema de e-commerce, focando em arquitetura limpa, separação de responsabilidades em camadas, validações robustas e tratamento global de exceções.

---

## 🚀 Tecnologias Utilizadas

* **Java 17 / JDK 20**
* **Spring Boot 3**
* **Spring Data JPA / Hibernate**
* **Spring Web**
* **Bean Validation (Jakarta Validation)**
* **PostgreSQL**
* **Docker & Docker Compose**

---

## 📁 Arquitetura do Projeto

O projeto segue o padrão de **Arquitetura em Camadas** e **Tratamento Centralizado**:
* **Controller (`/controller`):** Porta de entrada REST, responsável por receber requisições HTTP, disparar o Bean Validation (`@Valid`) e retornar respostas padronizadas.
* **Service (`/services`):** Concentra as regras de negócio (como validação de duplicidade com `existsByNome` e busca por ID via `Optional<Produto>`) e o fluxo de processamento da aplicação.
* **Repository (`/repository`):** Interface de persistência estendendo `JpaRepository` para comunicação direta com o banco relacional.
* **Entity (`/entities`):** Modelos de dados mapeados para o banco utilizando JPA/Hibernate.
* **Exceptions & DTOs (`/exceptions` e `/dto`):** Tratamento global de erros (`@RestControllerAdvice`) devolvendo payloads padronizados em `StandardErrorDTO`.

---

## 🐳 Subindo o Ambiente de Banco de Dados

Para rodar o banco de dados PostgreSQL localmente utilizando o Docker, execute o comando abaixo na raiz do projeto onde está o arquivo `docker-compose.yml`:

```bash
docker-compose up -d
```

O banco subirá na porta `5436` com as seguintes credenciais configuradas no `application.properties`:
* **Database:** `ecommerce_db`
* **User:** `postgres`
* **Password:** `postgres`

---

## ⚙️ Como Executar a Aplicação

1. Certifique-se de que o Docker com o PostgreSQL está rodando.
2. Abra o projeto na sua IDE de preferência (IntelliJ IDEA).
3. Execute a classe principal `EcommerceProductApplication`.
4. A API estará acessível em `http://localhost:8080`.

---

## 📡 Endpoints e Regras de Negócio (CRUD Completo)

| Método | Rota | Descrição | Status Sucesso |
| :--- | :--- | :--- | :--- |
| **POST** | `/api/produtos` | Cadastra um novo produto | `200 OK` / `201 Created` |
| **GET** | `/api/produtos` | Lista todos os produtos | `200 OK` |
| **GET** | `/api/produtos/{id}` | Busca um produto específico pelo ID | `200 OK` |
| **PUT** | `/api/produtos/{id}` | Atualiza os dados de um produto existente | `200 OK` |
| **DELETE** | `/api/produtos/{id}` | Remove um produto pelo ID | `200 OK` / `204 No Content` |

### 1. Cadastrar Produto (`POST /api/produtos`)
* **Regras de Validação & Negócio:**
  * O nome e a descrição são obrigatórios (`@NotBlank`).
  * O preço e a quantidade em estoque devem ser positivos (`@NotNull`, `@Positive`).
  * O sistema valida se já existe um produto cadastrado com o mesmo nome via `existsByNome`, lançando exceção de negócio caso ocorra duplicidade (`IllegalArgumentException`).
* **Exemplo de Body (JSON) Válido:**
```json
{
  "nome": "Carregador Turbo",
  "descricao": "Carregador turbo bivolt para Android e iPhone.",
  "preco": 149.99,
  "quantidadeEstoque": 10
}
```

### 2. Listar Produtos (`GET /api/produtos`)
* Retorna uma lista em JSON contendo todos os produtos cadastrados no banco de dados.

### 3. Buscar Produto por ID (`GET /api/produtos/{id}`)
* Utiliza o método herdado `findById(Long id)` do `ProdutoRepository` com retorno `Optional<Produto>`, garantindo busca segura contra `NullPointerException`.
* Se o ID não for encontrado, lança a exceção customizada `ResourceNotFoundException` devolvendo status `404 Not Found`.

### 4. Atualizar Produto (`PUT /api/produtos/{id}`)
* Recebe o ID via URL e o JSON com os novos dados anotados com `@Valid`.
* Realiza a busca segura via `Optional`. Se o produto existir, atualiza os campos e persiste as alterações; caso contrário, lança `ResourceNotFoundException` (404).

### 5. Deletar Produto (`DELETE /api/produtos/{id}`)
* Verifica a existência do produto pelo ID antes de efetuar a remoção. Caso o registro não exista, lança `ResourceNotFoundException` (404).

---

## ⚠️ Tratamento de Erros Padronizado (`GlobalExceptionHandler`)

A API intercepta exceções centralizadamente via `@RestControllerAdvice` e retorna um payload padronizado (`StandardErrorDTO`):

### Exemplo 1: Regra de Negócio Violada / Parâmetro Inválido (`400 Bad Request`)
```json
{
  "timestamp": "2026-10-26T16:30:00Z",
  "status": 400,
  "error": "Regra de negócio Violada",
  "message": "Esse produto já existe. Favor cadastrar um produto novo.",
  "path": "/api/produtos"
}
```

### Exemplo 2: Recurso Não Encontrado (`404 Not Found`)
```json
{
  "timestamp": "2026-10-26T16:30:00Z",
  "status": 404,
  "error": "Recurso Não Encontrado",
  "message": "Produto não encontrado para o ID informado.",
  "path": "/api/produtos/99"
}
```
