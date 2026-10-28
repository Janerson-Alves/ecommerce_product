# 🛒 E-commerce Product API

API desenvolvida em **Spring Boot** para gerenciar o catálogo de produtos de um sistema de e-commerce, focando em arquitetura limpa, separação de responsabilidades em camadas via DTOs (Request/Response), validações robustas, paginação, filtros e tratamento global de exceções.

---

## 🚀 Tecnologias Utilizadas

* **Java 17 / JDK 20**
* **Spring Boot 3**
* **Spring Data JPA / Hibernate**
* **Spring Web**
* **Bean Validation (Jakarta Validation)**
* **SpringDoc OpenAPI (Swagger UI Starter)**
* **PostgreSQL**
* **Docker & Docker Compose**

---

## 📁 Arquitetura do Projeto

O projeto segue o padrão de **Arquitetura em Camadas** com **Desacoplamento por DTOs**:
* **Controller (`/controller`):** Recebe as requisições HTTP, valida os DTOs de entrada (`@Valid @RequestBody ProdutoRequestDTO`), aceita parâmetros de paginação e retorna `ProdutoResponseDTO`.
* **Service (`/services`):** Concentra a lógica de negócio, converte os DTOs para Entidade na escrita e mapeia a Entidade para `ProdutoResponseDTO` no retorno.
* **DTO (`/dto`):**
  * `ProdutoRequestDTO`: Objeto para entrada de dados com Bean Validation.
  * `ProdutoResponseDTO`: Objeto para saída/exposição de dados da API.
* **Repository (`/repository`):** Persistência JPA com métodos derivados (`findByNomeContainingIgnoreCaseAndPrecoBetween`).
* **Entity (`/entities`):** Mapeamento O/R do banco de dados relacional.
* **Exceptions (`/exceptions`):** Tratamento global com `@RestControllerAdvice` e `StandardErrorDTO`.

---

## 🐳 Subindo o Ambiente de Banco de Dados

Para rodar o banco de dados PostgreSQL localmente via Docker, execute na raiz do projeto:

```bash
docker-compose up -d
```

Configurações (`application.properties`):
* **Porta:** `5436`
* **Database:** `ecommerce_db`
* **User:** `postgres`
* **Password:** `postgres`

---

## ⚙️ Como Executar a Aplicação

1. Certifique-se de que o contêiner do PostgreSQL está ativo.
2. Execute a classe principal `EcommerceProductApplication` na IDE.
3. A API estará disponível em `http://localhost:8080`.

---

## 📡 Endpoints REST e Contratos DTO

| Método | Rota | Body Entrada | Resposta | Status |
| :--- | :--- | :--- | :--- | :--- |
| **POST** | `/api/produtos` | `ProdutoRequestDTO` | `ProdutoResponseDTO` | `200 OK` / `201 Created` |
| **GET** | `/api/produtos` | N/A (Params query) | `Page<ProdutoResponseDTO>` | `200 OK` |
| **GET** | `/api/produtos/{id}` | N/A | `ProdutoResponseDTO` | `200 OK` |
| **PUT** | `/api/produtos/{id}` | `ProdutoRequestDTO` | `ProdutoResponseDTO` | `200 OK` |
| **DELETE** | `/api/produtos/{id}` | N/A | Mensagem / No Content | `200 OK` / `204 No Content` |

---

### 1. Cadastrar Produto (`POST /api/produtos`)

* **Exemplo de Request Body (`ProdutoRequestDTO`):**
```json
{
  "nome": "Carregador Turbo 30W",
  "descricao": "Carregador rápido bivolt com saída USB-C.",
  "preco": 129.90,
  "quantidadeEstoque": 15
}
```

* **Exemplo de Response Body (`ProdutoResponseDTO`):**
```json
{
  "id": 1,
  "nome": "Carregador Turbo 30W",
  "descricao": "Carregador rápido bivolt com saída USB-C.",
  "preco": 129.90,
  "quantidadeEstoque": 15
}
```

---

### 2. Listar Produtos Pagina e Filtrado (`GET /api/produtos`)

* **Parâmetros de Query:**
  * `nome` (String): Busca parcial case-insensitive.
  * `valorInicio` (BigDecimal): Preço mínimo.
  * `valorFinal` (BigDecimal): Preço máximo.
  * `page`, `size`, `sort`: Parâmetros do Spring Data `Pageable`.

---

## ⚠️ Tratamento de Erros Centralizado (`GlobalExceptionHandler`)

* `400 Bad Request`: Falha em regras de negócio (ex: nome duplicado) ou erro de validação do DTO.
* `404 Not Found`: Recurso não localizado para o ID fornecido.