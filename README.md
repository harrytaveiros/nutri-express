# Nutri-Express API 🥗

API REST para um aplicativo de delivery de comida saudável, desenvolvida em Spring Boot com arquitetura em camadas (Controller, Service e Repository).

## 🚀 Como executar o projeto

1. Certifique-se de ter o Java 17+ e o Maven instalados.
2. Tenha uma instância do PostgreSQL rodando localmente na porta 5432.
3. Configure as credenciais do banco no arquivo `src/main/resources/application.properties`.
4. Execute a aplicação pela classe `DemoApplication.java` ou via terminal com o comando `mvn spring-boot:run`.

## 📍 Endpoints Implementados

**Rotas Base:**
* `GET /pratos` - Lista todos os pratos (Status 200)
* `GET /pratos/{id}` - Busca um prato pelo ID (Status 200 ou 404)
* `GET /pratos?categoria=nome` - Filtra pratos pela categoria (Status 200)
* `POST /pratos` - Cria um novo prato (Status 201)
* `PUT /pratos/{id}` - Atualiza todos os dados de um prato (Status 200 ou 404)
* `DELETE /pratos/{id}` - Remove um prato (Status 204 ou 404)

**Desafios Extras:**
* `GET /pratos/calorias?max=500` - Filtra pratos com até X calorias.
* `PATCH /pratos/{id}/valor` - Atualiza somente o preço do prato.