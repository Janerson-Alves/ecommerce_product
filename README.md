# 🛒 E-commerce Product API

API desenvolvida em **Spring Boot** para gerenciar o catálogo de produtos de um sistema de e-commerce, focando em arquitetura limpa, separação de responsabilidades em camadas e validações de integridade de dados.

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

O projeto segue o padrão de **Arquitetura em Camadas**:
- **Controller (`/controller`):** Porta de entrada REST, responsável por receber requisições HTTP, disparar o Bean Validation (`@Valid`) e retornar respostas padronizadas.
- **Service (`/services`):** Concentra as regras de negócio e o fluxo de processamento da aplicação, isolando o controller do acesso direto ao banco.
- **Repository (`/repository`):** Interface de persistência estendendo `JpaRepository` para comunicação direta com o banco de dados.
- **Entity (`/entities`):** Modelos de dados mapeados para o banco relacional utilizando JPA/Hibernate.

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

## 📡 Endpoints da API e Validações

### 1. Cadastrar Produto
- **Rota:** `POST /api/produtos`
- **Regras de Validação:** 
  - O nome e a descrição são obrigatórios e não podem estar em branco (`@NotBlank`).
  - O preço e a quantidade em estoque não podem ser nulos e devem ser valores estritamente positivos (`@NotNull`, `@Positive`).
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
- **Retorno:** Lista em JSON contendo todos os produtos cadastrados e persistidos no banco de dados.

---

## 🖥️ Comandos Git para Atualizar o Repositório

Abra o terminal da sua IDE na pasta raiz do projeto e execute os comandos abaixo para enviar as alterações para o GitHub:

```bash
git add .
git commit -m "docs: atualiza readme e codigo com implementacao das tasks 02 (validacoes) e 03 (service)"
git push origin main
```

---

Feito com ☕ por **Janerson Alves**.