# ProductProject

API RESTful para gerenciamento de um catálogo de produtos, desenvolvida com Spring Boot como projeto de estudo de **Spring Security** (autenticação HTTP Basic + autorização por *role*, com usuários persistidos em banco de dados).

## Tecnologias

- Java 21
- Spring Boot 4.1.1
- Spring Web (MVC)
- Spring Security
- Spring Data JPA
- H2 Database (em memória)
- Bean Validation
- Lombok
- SpringDoc OpenAPI (Swagger UI)

## Segurança

A API usa **HTTP Basic Authentication**. As regras de acesso são definidas por método HTTP:

| Método | Rota        | Acesso                  |
|--------|-------------|--------------------------|
| GET    | `/**`       | Público (sem autenticação) |
| POST   | `/**`       | Requer `ROLE_ADMIN`      |
| PUT    | `/**`       | Requer `ROLE_ADMIN`      |
| DELETE | `/**`       | Requer `ROLE_ADMIN`      |
| —      | `/h2-console/**` | Público |

Os usuários são entidades JPA (`Usuario`) que implementam `UserDetails`, carregadas via `UserDetailsService` (`AutenticacaoService`), e as senhas são armazenadas com hash `BCrypt` (nunca em texto puro).

Um usuário é criado automaticamente na primeira execução (veja `CargaDadosInicial`):

| Login | Senha | Role |
|-------|-------|------|
| `user` | `12345` | `ADMIN` |

> ⚠️ Essas credenciais são apenas para fins didáticos. Não devem ser usadas em ambiente de produção.

## Endpoints

Base path: `/produtos`

| Método | Rota              | Descrição                                      |
|--------|-------------------|-------------------------------------------------|
| GET    | `/produtos`       | Lista todos os produtos cadastrados             |
| GET    | `/produtos/{id}`  | Busca um produto pelo ID                        |
| GET    | `/produtos?nome=` | Busca produtos ativos cujo nome contenha o termo informado |
| POST   | `/produtos`       | Cadastra um novo produto (requer `ROLE_ADMIN`)  |
| PUT    | `/produtos/{id}`  | Atualiza um produto existente (requer `ROLE_ADMIN`) |

A documentação interativa completa (schemas de request/response, códigos de erro) fica disponível no Swagger UI.

## Executando o projeto

Pré-requisitos: JDK 21 e Maven (ou use o wrapper incluso no projeto).

```bash
./mvnw spring-boot:run
```

A aplicação sobe em `http://localhost:8081`.

### Testando com autenticação

```bash
# Rota pública — não precisa de credenciais
curl http://localhost:8081/produtos

# Rota protegida — precisa de ROLE_ADMIN
curl -u user:12345 -X POST http://localhost:8081/produtos \
  -H "Content-Type: application/json" \
  -d '{"nome": "Webcam Full HD", "preco": 250.00}'
```

## Banco de dados

O projeto usa H2 em memória, populado automaticamente na inicialização (produtos de exemplo + usuário admin).

- Console H2: `http://localhost:8081/h2-console`
- JDBC URL: `jdbc:h2:mem:produtosdb`
- Usuário: `sa`
- Senha: *(em branco)*

> Os dados são reiniciados a cada execução (`ddl-auto=create-drop`).

## Documentação da API (Swagger)

- Swagger UI: `http://localhost:8081/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8081/api-docs`

## Estrutura do projeto

```
src/main/java/io/github/danieldapper/api_produtos/
├── config/          # Segurança, OpenAPI e carga inicial de dados
├── controller/       # Endpoints REST
├── dto/               # Objetos de requisição/resposta
├── entity/            # Entidades JPA (Produto, Usuario, Role)
├── exception/         # Tratamento global de exceções
├── mapper/            # Conversão entre entidades e DTOs
├── repository/        # Interfaces Spring Data JPA
└── service/           # Regras de negócio e autenticação
```

## Autor

Projeto desenvolvido como exercício da disciplina de Desenvolvimento de Sistemas — CentroWEG.
