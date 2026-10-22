# 🛒 E-commerce Product API

API desenvolvida em **Spring Boot** para gerenciar o catálogo de produtos de um sistema de e-commerce, focando em arquitetura limpa, separação de responsabilidades em camadas, validações robustas e tratamento global de exceções.

---

## 🚀 Tecnologias Utilizadas

- **Java 17 / JDK 20**
- **Spring Boot 3**
- **Spring Data JPA / Hibernate**
- **Spring Web**
- **Bean Validation (Jakarta Validation)**
- **PostgreSQL**
- **Docker & Docker Compose**

---

## 📁 Arquitetura do Projeto

O projeto segue o padrão de **Arquitetura em Camadas** e **Tratamento Centralizado**:
- **Controller (`/controller`):** Porta de entrada REST, responsável por receber requisições HTTP, disparar o Bean Validation (`@Valid`) e retornar respostas padronizadas.
- **Service (`/services`):** Concentra as regras de negócio (como validação de duplicidade com `existsByNome`) e o fluxo de processamento da aplicação.
- **Repository (`/repository`):** Interface de persistência estendendo `JpaRepository` para comunicação direta com o banco relacional.
- **Entity (`/entities`):** Modelos de dados mapeados para o banco utilizando JPA/Hibernate.
- **Exceptions & DTOs (`/exceptions` e `/dto`):** Tratamento global de erros (`@RestControllerAdvice`) devolvendo payloads padronizados em `StandardErrorDTO`.

---

## 🐳 Subindo o Ambiente de Banco de Dados

Para rodar o banco de dados PostgreSQL localmente utilizando o Docker, execute o comando abaixo na raiz do projeto onde está o arquivo `docker-compose.yml`:

```bash
docker-compose up -d
```

O banco subirá na porta `5436` com as seguintes credenciais configuradas no `application.properties`:
- **Database:** `ecommerce_db`
- **User:** `postgres`
- **Password:** `postgres`

---

## ⚙️ Como Executar a Aplicação

1. Certifique-se de que o Docker com o PostgreSQL está rodando.
2. Abra o projeto na sua IDE de preferência (IntelliJ IDEA).
3. Execute a classe principal `EcommerceProductApplication`.
4. A API estará acessível em `http://localhost:8080`.

---

## 📡 Endpoints, Validações e Tratamento de Erros

### 1. Cadastrar Produto
- **Rota:** `POST /api/produtos`
- **Regras de Validação & Negócio:** 
  - O nome e a descrição são obrigatórios (`@NotBlank`).
  - O preço e a quantidade em estoque devem ser positivos (`@NotNull`, `@Positive`).
  - O sistema valida se já existe um produto cadastrado com o mesmo nome via `existsByNome`, lançando exceção de negócio caso ocorra duplicidade (`IllegalArgumentException`).
- **Exemplo de Body (JSON) Válido:**
```json
{
  "nome": "Carregador Turbo",
  "descricao": "Carregador turbo bivolt para Android e iPhone.",
  "preco": 149.99,
  "quantidadeEstoque": 10
}
```

### 2. Listar Produtos
- **Rota:** `GET /api/produtos`
- **Retorno:** Lista em JSON contendo todos os produtos cadastrados.

---

## ⚠️ Tratamento de Erros Padronizado (`GlobalExceptionHandler`)

Caso ocorra uma falha de validação de campos ou de regra de negócio, a API intercepta a exceção e retorna um JSON estruturado (`StandardErrorDTO`) com status `400 Bad Request`:

```json
{
  "timestamp": "2026-10-22T18:00:00Z",
  "status": 400,
  "error": "Regra de negócio Violada",
  "message": "Esse produto já existe. Favor cadastrar um produto novo.",
  "path": "/api/produtos"
}
```