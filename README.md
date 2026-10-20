# 🛒 E-commerce Product API

API desenvolvida em **Spring Boot** para gerenciar o catálogo de produtos de um sistema de e-commerce, focando em arquitetura limpa, boas práticas de desenvolvimento backend e evolução contínua.

---

## 🚀 Tecnologias Utilizadas

- **Java 17 / JDK 20**
- **Spring Boot 3**
- **Spring Data JPA / Hibernate**
- **Spring Web**
- **Bean Validation**
- **PostgreSQL**
- **Docker & Docker Compose**

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

## 📡 Endpoints da API

### 1. Cadastrar Produto
- **Rota:** `POST /api/produtos`
- **Exemplo de Body (JSON):**
```json
{
  "nome": "Iphone 17 Pro Max",
  "descricao": "Celular Iphone 17 Pro Max, lançamento 2025.",
  "preco": 7999.99
}
```

### 2. Listar Produtos
- **Rota:** `GET /api/produtos`
- **Retorno:** Lista em JSON com todos os produtos cadastrados no banco de dados.

---
