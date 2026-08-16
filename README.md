# 💊 Estoque Farmácia — API REST

API REST para controle de estoque de medicamentos em farmácias. Desenvolvida com Spring Boot, oferece gerenciamento de medicamentos e fabricantes com autenticação JWT, controle de acesso por perfil e proteção contra brute force.

---

## 🛠️ Tecnologias

| Tecnologia | Versão | Finalidade |
|---|---|---|
| Java | 21 | Linguagem |
| Spring Boot | 4.0.6 | Framework principal |
| Spring Security | — | Autenticação e autorização |
| Spring Data JPA | — | Persistência de dados |
| PostgreSQL | 16 | Banco de dados relacional |
| Auth0 Java JWT | 4.4.0 | Geração e validação de tokens JWT |
| Bucket4j | 8.10.1 | Rate limiting por IP |
| SpringDoc OpenAPI | 3.0.0 | Documentação Swagger |
| Lombok | — | Redução de boilerplate |
| Docker | — | Containerização |

---

## 📐 Arquitetura

```
┌─────────────────────────────────────────────────┐
│              Docker Compose Network              │
│                                                 │
│  ┌──────────────────┐    ┌──────────────────┐   │
│  │   backend        │───▶│   postgres       │   │
│  │   (porta 8080)   │    │   (porta 5432)   │   │
│  │   Spring Boot    │    │   PostgreSQL 16  │   │
│  └──────────────────┘    └──────────────────┘   │
└─────────────────────────────────────────────────┘
```

---

## 🔐 Autenticação e Segurança

- **JWT (Bearer Token):** todas as rotas, exceto `/api/auth/login`, exigem autenticação
- **Perfis de acesso:**
  - `ADMIN` — acesso total (leitura, escrita e cadastro de usuários)
  - `OPERADOR` — somente leitura de medicamentos e fabricantes
- **Rate Limiting:** proteção contra brute force no login por IP via Bucket4j — retorna `429 Too Many Requests` ao exceder o limite
- **Senhas:** armazenadas com hash BCrypt, nunca em texto puro
- **Validação de e-mail:** regex estrito exige domínio com TLD (`usuario@dominio.com`)

---

## 📡 Endpoints da API

> Todas as rotas autenticadas exigem o header:
> ```
> Authorization: Bearer {seu_token_jwt}
> ```

---

### 🔑 Autenticação — `/api/auth`

| Método | Rota | Descrição | Auth |
|---|---|---|---|
| `POST` | `/api/auth/login` | Realiza login e retorna o token JWT | ❌ Pública |
| `POST` | `/api/auth/cadastrar` | Cadastra um novo usuário | ✅ Apenas ADMIN |

#### `POST /api/auth/login`
```json
// Request
{
  "login": "admin@farma.com",
  "senha": "admin123"
}

// Response 200 OK
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

#### `POST /api/auth/cadastrar`
```json
// Request
{
  "login": "novo@farma.com",
  "senha": "senha123",
  "role": "OPERADOR"
}

// Response 201 Created
{
  "id": 3,
  "login": "novo@farma.com",
  "role": "OPERADOR"
}
```

**Roles disponíveis:** `ADMIN` · `OPERADOR`

**Erros possíveis:**
| Status | Situação |
|---|---|
| `400 Bad Request` | Campos inválidos (e-mail sem TLD, senha curta, etc.) |
| `401 Unauthorized` | Token ausente ou inválido |
| `403 Forbidden` | Usuário autenticado não é ADMIN |
| `409 Conflict` | Login já cadastrado no sistema |

---

### 🏭 Fabricantes — `/api/fabricantes`

> Todas as rotas exigem autenticação. `POST`, `PUT` e `DELETE` exigem perfil `ADMIN`.

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/api/fabricantes` | Lista todos (paginado, ordenado por nome) |
| `GET` | `/api/fabricantes/{id}` | Busca fabricante por ID |
| `POST` | `/api/fabricantes` | Cadastra um novo fabricante |
| `PUT` | `/api/fabricantes/{id}` | Atualiza dados do fabricante |
| `DELETE` | `/api/fabricantes/{id}` | Remove um fabricante |

---

### 💊 Medicamentos — `/api/medicamentos`

> Todas as rotas exigem autenticação. `POST`, `PUT` e `DELETE` exigem perfil `ADMIN`.

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/api/medicamentos` | Lista todos (paginado, ordenado por nome) |
| `GET` | `/api/medicamentos/{id}` | Busca medicamento por ID |
| `POST` | `/api/medicamentos` | Cadastra um novo medicamento |
| `PUT` | `/api/medicamentos/{id}` | Atualiza dados do medicamento |
| `DELETE` | `/api/medicamentos/{id}` | Remove um medicamento |

**Tarjas disponíveis:** `LIVRE` · `AMARELA` · `VERMELHA` · `PRETA`

---

## 🐳 Rodando com Docker (recomendado)

### Pré-requisitos
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) instalado

### 1. Configurar as variáveis de ambiente

Copie o arquivo de exemplo e preencha com seus valores:

```bash
cp .env.example .env
```

Conteúdo do `.env`:
```env
DB_HOST=localhost
DB_PORT=5432
DB_USERNAME=postgres
DB_PASSWORD=sua_senha_aqui
JWT_SECRET=seu_secret_aqui
```

### 2. Subir os containers

```bash
docker compose up --build
```

> Na primeira execução o Docker irá baixar as images e compilar o projeto (~3-5 min).
> Nas próximas execuções o cache torna o processo muito mais rápido.

### 3. Acessar

| Serviço | URL |
|---|---|
| API | http://localhost:8080 |
| Swagger UI | http://localhost:8080/swagger-ui/index.html |
| Banco de dados | `localhost:5432` — banco: `estoque_farma` |

---

## 💻 Rodando localmente (sem Docker)

### Pré-requisitos
- Java 21
- Maven 3.9+
- PostgreSQL rodando localmente

### 1. Configure o `.env` na raiz do projeto

```env
DB_HOST=localhost
DB_PORT=5432
DB_USERNAME=postgres
DB_PASSWORD=sua_senha
JWT_SECRET=seu_secret
```

### 2. Crie o banco de dados no PostgreSQL

```sql
CREATE DATABASE estoque_farma;
```

### 3. Execute o projeto

```bash
./mvnw spring-boot:run
```

---

## 🗄️ Modelos de dados

### Fabricante
| Campo | Tipo | Obrigatório | Único |
|---|---|---|---|
| `id` | Long | — | ✅ |
| `nome` | String | ✅ | ❌ |
| `cnpj` | String | ✅ | ✅ |
| `telefone` | String | ✅ | ❌ |
| `email` | String | ✅ | ❌ |

### Medicamento
| Campo | Tipo | Obrigatório |
|---|---|---|
| `id` | Long | — |
| `codigoBarras` | String (13) | ✅ |
| `nome` | String | ✅ |
| `principioAtivo` | String | ✅ |
| `tarja` | Enum | ✅ |
| `dosagem` | String | ✅ |
| `preco` | Double | ✅ |
| `quantidadeEstoque` | Integer | ✅ |
| `lote` | String | ✅ |
| `dataValidade` | LocalDate | ✅ |
| `fabricante` | Fabricante | ✅ |

### Usuário
| Campo | Tipo | Valores |
|---|---|---|
| `login` | String (e-mail válido) | — |
| `senha` | String (BCrypt) | — |
| `role` | Enum | `ADMIN`, `OPERADOR` |

---

## ⚙️ Perfis de configuração

| Perfil | Ativação | Características |
|---|---|---|
| `dev` | padrão | SQL no console, `ddl-auto=update`, Swagger ativo |
| `prod` | `SPRING_PROFILES_ACTIVE=prod` | Sem SQL no console, `ddl-auto=validate`, Swagger desativado |

---

## 🧰 Comandos Docker úteis

```bash
# Subir os containers em background
docker compose up -d

# Ver logs em tempo real
docker compose logs -f backend

# Parar os containers (dados preservados)
docker compose down

# Parar e apagar os dados do banco
docker compose down -v

# Rebuildar após mudanças no código
docker compose up --build
```

---

## 📁 Estrutura do projeto

```
src/
└── main/
    ├── java/com/farma/estoque/
    │   ├── controller/     # Endpoints REST
    │   ├── service/        # Regras de negócio
    │   ├── repository/     # Acesso ao banco (Spring Data JPA)
    │   ├── model/          # Entidades JPA
    │   ├── dto/            # Objetos de transferência de dados
    │   ├── security/       # JWT, filtros, rate limiting
    │   └── exception/      # Tratamento global de erros
    └── resources/
        ├── application.properties
        ├── application-dev.properties
        └── application-prod.properties
```