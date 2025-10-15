# DSCommerce - Sistema de E-commerce

## 📋 Sobre o Projeto

O DSCommerce é uma API REST desenvolvida em Spring Boot para um sistema de e-commerce. O projeto implementa funcionalidades completas de gerenciamento de produtos, categorias, usuários e pedidos, com autenticação e autorização baseada em OAuth2 e JWT.

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.1.2**
- **Spring Security** com OAuth2 Authorization Server
- **Spring Data JPA**
- **H2 Database** (banco em memória para testes)
- **Maven** (gerenciamento de dependências)
- **JUnit 5** (testes unitários e de integração)
- **MockMvc** (testes de integração)
- **Mockito** (mocking em testes unitários)
- **Jacoco** (cobertura de código)

## 🏗️ Arquitetura do Projeto

### Estrutura de Pacotes
```
src/main/java/com/devsuperior/dscommerce/
├── config/              # Configurações de segurança e OAuth2
├── controllers/         # Controladores REST
├── dto/                # Data Transfer Objects
├── entities/           # Entidades JPA
├── projections/        # Projeções para consultas customizadas
├── repositories/       # Repositórios JPA
├── services/          # Lógica de negócio
└── util/              # Utilitários
```

### Modelo de Dados

#### Entidades Principais:
- **User**: Usuários do sistema (clientes e administradores)
- **Product**: Produtos do catálogo
- **Category**: Categorias de produtos
- **Order**: Pedidos realizados
- **OrderItem**: Itens dos pedidos
- **Payment**: Pagamentos dos pedidos
- **Role**: Papéis de usuário (CLIENT, ADMIN)

#### Relacionamentos:
- User ↔ Order (1:N)
- Order ↔ OrderItem (1:N)
- Product ↔ OrderItem (1:N)
- Product ↔ Category (N:N)
- User ↔ Role (N:N)
- Order ↔ Payment (1:1)

## 🔐 Sistema de Autenticação

O projeto utiliza **OAuth2 Authorization Server** com as seguintes funcionalidades:

### Tipos de Usuário:
- **ROLE_CLIENT**: Cliente comum (pode fazer pedidos e consultar seus dados)
- **ROLE_ADMIN**: Administrador (pode gerenciar produtos, categorias e pedidos)

### Endpoints de Autenticação:
- **POST** `/oauth2/token` - Obter token de acesso
- **GET** `/oauth2/jwks` - Chaves públicas para validação JWT

### Credenciais de Teste:
```
Cliente:
- Email: maria@gmail.com
- Senha: ******

Administrador:
- Email: alex@gmail.com
- Senha: ******
```

## 📚 Endpoints da API

### 🔑 Autenticação
Para acessar endpoints protegidos, inclua o header:
```
Authorization: Bearer {seu_token_jwt}
```

### 📦 Produtos

#### Listar Produtos (Público)
```http
GET /products?name={nome}&page={page}&size={size}&sort={campo,direção}
```

#### Buscar Produto por ID (Público)
```http
GET /products/{id}
```

#### Criar Produto (Admin)
```http
POST /products
Authorization: Bearer {admin_token}
Content-Type: application/json

{
  "name": "Nome do Produto",
  "description": "Descrição detalhada",
  "price": 99.99,
  "imgUrl": "https://exemplo.com/imagem.jpg",
  "categories": [{"id": 1}]
}
```

#### Atualizar Produto (Admin)
```http
PUT /products/{id}
Authorization: Bearer {admin_token}
Content-Type: application/json

{
  "name": "Nome Atualizado",
  "description": "Nova descrição",
  "price": 149.99,
  "imgUrl": "https://exemplo.com/nova-imagem.jpg",
  "categories": [{"id": 1}, {"id": 2}]
}
```

#### Deletar Produto (Admin)
```http
DELETE /products/{id}
Authorization: Bearer {admin_token}
```

### 📂 Categorias

#### Listar Categorias (Público)
```http
GET /categories
```

### 👤 Usuários

#### Obter Dados do Usuário Logado
```http
GET /users/me
Authorization: Bearer {token}
```

### 🛒 Pedidos

#### Buscar Pedido por ID
```http
GET /orders/{id}
Authorization: Bearer {token}
```

#### Criar Pedido (Cliente)
```http
POST /orders
Authorization: Bearer {client_token}
Content-Type: application/json

{
  "items": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 3,
      "quantity": 1
    }
  ]
}
```

## 🧪 Testes

### Estrutura de Testes

O projeto possui uma cobertura abrangente de testes:

#### Testes Unitários (`src/test/java/com/devsuperior/dscommerce/services/`)
- **ProductServiceTests**: Testa lógica de negócio dos produtos
- **UserServiceTests**: Testa funcionalidades de usuário
- **OrderServiceTests**: Testa lógica de pedidos
- **CategoryServiceTests**: Testa operações de categorias
- **AuthServiceTests**: Testa autenticação

#### Testes de Integração (`src/test/java/com/devsuperior/dscommerce/it/`)
- **ProductControllerIT**: Testa endpoints de produtos
- **UserControllerIT**: Testa endpoints de usuários
- **OrderControllerIT**: Testa endpoints de pedidos
- **CategoryControllerIT**: Testa endpoints de categorias

### Executando os Testes

```bash
# Executar todos os testes
mvn test

# Executar testes com relatório de cobertura
mvn clean test jacoco:report

# Executar apenas testes unitários
mvn test -Dtest="*ServiceTests"

# Executar apenas testes de integração
mvn test -Dtest="*IT"
```

### Cobertura de Código

O projeto utiliza **Jacoco** para análise de cobertura. O relatório é gerado em:
```
target/jacoco-report/index.html
```

## 🚀 Como Executar

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6 ou superior

### Passos para Execução

1. **Clone o repositório**
```bash
git clone <url-do-repositorio>
cd ProjetoDSCommerce
```

2. **Execute a aplicação**
```bash
mvn spring-boot:run
```

3. **Acesse a aplicação**
- API: `http://localhost:8080`
- Console H2: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: (vazio)

## 📋 Coleção Postman

### Configuração Inicial

1. **Obter Token de Acesso**
```http
POST http://localhost:8080/oauth2/token
Content-Type: application/x-www-form-urlencoded

grant_type=password&username=alex@gmail.com&password=123456&client_id=myclientid&client_secret=myclientsecret
```

2. **Configurar Variável de Ambiente**
- Crie uma variável `token` no Postman
- Cole o valor do `access_token` retornado

### Requisições CRUD Completas

#### 🔐 Autenticação

**Obter Token (Cliente)**
```http
POST {{base_url}}/oauth2/token
Content-Type: application/x-www-form-urlencoded

grant_type=password&username=maria@gmail.com&password=123456&client_id=myclientid&client_secret=myclientsecret
```

**Obter Token (Admin)**
```http
POST {{base_url}}/oauth2/token
Content-Type: application/x-www-form-urlencoded

grant_type=password&username=alex@gmail.com&password=123456&client_id=myclientid&client_secret=myclientsecret
```

#### 📦 Produtos

**1. Listar Todos os Produtos**
```http
GET {{base_url}}/products
```

**2. Buscar Produto por ID**
```http
GET {{base_url}}/products/1
```

**3. Buscar Produtos por Nome**
```http
GET {{base_url}}/products?name=Macbook
```

**4. Criar Produto (Admin)**
```http
POST {{base_url}}/products
Authorization: Bearer {{admin_token}}
Content-Type: application/json

{
  "name": "iPhone 15 Pro",
  "description": "O mais avançado iPhone com chip A17 Pro",
  "price": 8999.99,
  "imgUrl": "https://exemplo.com/iphone15pro.jpg",
  "categories": [{"id": 2}]
}
```

**5. Atualizar Produto (Admin)**
```http
PUT {{base_url}}/products/1
Authorization: Bearer {{admin_token}}
Content-Type: application/json

{
  "name": "The Lord of the Rings - Edição Especial",
  "description": "Edição especial com capa dura e ilustrações",
  "price": 120.50,
  "imgUrl": "https://exemplo.com/lotr-especial.jpg",
  "categories": [{"id": 1}]
}
```

**6. Deletar Produto (Admin)**
```http
DELETE {{base_url}}/products/1
Authorization: Bearer {{admin_token}}
```

#### 📂 Categorias

**Listar Todas as Categorias**
```http
GET {{base_url}}/categories
```

#### 👤 Usuários

**Obter Dados do Usuário Logado**
```http
GET {{base_url}}/users/me
Authorization: Bearer {{token}}
```

#### 🛒 Pedidos

**1. Buscar Pedido por ID**
```http
GET {{base_url}}/orders/1
Authorization: Bearer {{token}}
```

**2. Criar Pedido (Cliente)**
```http
POST {{base_url}}/orders
Authorization: Bearer {{client_token}}
Content-Type: application/json

{
  "items": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 3,
      "quantity": 1
    }
  ]
}
```

### Variáveis de Ambiente Postman

Configure as seguintes variáveis no Postman:

```json
{
  "base_url": "http://localhost:8080",
  "client_token": "{{access_token_do_cliente}}",
  "admin_token": "{{access_token_do_admin}}",
  "token": "{{access_token_atual}}"
}
```

## 📊 Dados de Teste

O projeto inclui dados pré-carregados no arquivo `import.sql`:

### Usuários:
- **Maria Brown** (Cliente): maria@gmail.com / 123456
- **Alex Green** (Admin): alex@gmail.com / 123456
- **Ana Blue** (Admin): ana@gmail.com / 123456

### Produtos:
- 25 produtos pré-cadastrados
- Categorias: Livros, Eletrônicos, Computadores
- Pedidos de exemplo com itens

### Categorias:
- Livros (ID: 1)
- Eletrônicos (ID: 2)
- Computadores (ID: 3)

## 🔧 Configurações

### Propriedades da Aplicação

```properties
# Perfil ativo
spring.profiles.active=test

# Configurações de segurança
security.client-id=myclientid
security.client-secret=myclientsecret
security.jwt.duration=86400

# CORS
cors.origins=http://localhost:3000,http://localhost:5173

# JPA
spring.jpa.open-in-view=false
```

### Banco de Dados H2

```properties
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=

# Console H2
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# SQL Log
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

## 📈 Métricas e Monitoramento

### Cobertura de Testes
- **Jacoco** configurado para gerar relatórios de cobertura
- Exclusões configuradas para classes de configuração e DTOs
- Relatório disponível em: `target/jacoco-report/index.html`

### Logs
- SQL queries são logadas no console (modo desenvolvimento)
- Logs formatados para melhor legibilidade

## 🚨 Tratamento de Erros

O projeto implementa tratamento centralizado de erros:

- **ResourceNotFoundException**: Recurso não encontrado (404)
- **DatabaseException**: Violação de integridade (400)
- **ValidationException**: Dados inválidos (422)
- **ForbiddenException**: Acesso negado (403)
- **UnauthorizedException**: Não autenticado (401)

## 🔒 Segurança

### Implementações de Segurança:
- **OAuth2 Authorization Server**
- **JWT Tokens**
- **Method-level Security** com `@PreAuthorize`
- **CORS** configurado
- **Validação de dados** com Bean Validation
- **Senhas criptografadas** com BCrypt

### Níveis de Acesso:
- **Público**: Listagem de produtos e categorias
- **Cliente**: Consulta de dados pessoais, criação de pedidos
- **Admin**: CRUD completo de produtos, consulta de pedidos

## 📝 Validações

### Produtos:
- Nome: mínimo 3 caracteres
- Descrição: mínimo 3 caracteres
- Preço: deve ser positivo
- Categorias: pelo menos uma categoria obrigatória

### Pedidos:
- Pelo menos um item obrigatório
- Quantidade deve ser positiva
- Produto deve existir

## 🎯 Funcionalidades Implementadas

### ✅ Completas:
- [x] CRUD de Produtos
- [x] CRUD de Categorias
- [x] Gerenciamento de Usuários
- [x] Sistema de Pedidos
- [x] Autenticação OAuth2
- [x] Autorização por Roles
- [x] Testes Unitários
- [x] Testes de Integração
- [x] Validação de Dados
- [x] Tratamento de Erros
- [x] Paginação
- [x] Busca por nome
- [x] Cobertura de Código

### 🔄 Possíveis Melhorias:
- [ ] Upload de imagens
- [ ] Cache com Redis
- [ ] Notificações por email
- [ ] Relatórios de vendas
- [ ] API de pagamento
- [ ] Logs estruturados
- [ ] Métricas com Actuator
- [ ] Documentação com Swagger

