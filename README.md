# API REST de Gerenciamento de Produtos

## Descrição do Projeto

Este projeto é uma API RESTful desenvolvida com **Spring Boot** para gerenciar produtos. A aplicação segue uma arquitetura em camadas (`Controller`, `Service`, `Repository`) e utiliza boas práticas como DTOs (Data Transfer Objects), tratamento de exceções global e validação de dados. A API foi projetada para ser robusta, escalável e de fácil manutenção.

---

## Funcionalidades

A API oferece os seguintes endpoints para a gestão de produtos:

-   **`POST /api/products/new`**: Cria um novo produto.
-   **`GET /api/products`**: Lista todos os produtos registrados.
-   **`GET /api/products/{id}`**: Busca um produto específico pelo seu ID.
-   **`PUT /api/products/{id}`**: Atualiza as informações de um produto existente.
-   **`DELETE /api/products/{id}`**: Deleta um produto pelo seu ID.

---

## Tecnologias Utilizadas

-   **Java 17**: Linguagem de programação.
-   **Spring Boot 3.3.1**: Framework para a criação da aplicação.
-   **Spring Data JPA**: Para a camada de persistência e interação com o banco de dados.
-   **MySQL**: Banco de dados relacional.
-   **Maven**: Gerenciador de dependências.
-   **Lombok**: Para reduzir o boilerplate code (getters, setters, etc.).
-   **Swagger/OpenAPI (Springdoc)**: Para documentação e teste interativo dos endpoints.

---

## Arquitetura do Projeto

O projeto segue uma arquitetura em camadas para separar as responsabilidades:

-   **`controller`**: Camada de entrada da API, responsável por receber as requisições HTTP e delegar as tarefas para a camada de serviço. Utiliza DTOs para comunicação e lida com respostas HTTP.
-   **`service`**: Camada de lógica de negócio, onde as regras e validações são aplicadas. Orquestra a interação com a camada de repositório.
-   **`repository`**: Camada de acesso a dados. Utiliza o Spring Data JPA para interagir com o banco de dados sem a necessidade de código SQL manual.
-   **`model`**: Contém a entidade `Product`, que representa a tabela no banco de dados.
-   **`dto`**: Contém os Data Transfer Objects, utilizados para definir o formato de entrada e saída dos dados da API, garantindo segurança e flexibilidade.

---

## Como Rodar o Projeto

### Pré-requisitos
-   JDK 17 ou superior.
-   Maven.
-   MySQL instalado e rodando.

### Configuração do Banco de Dados
1.  Crie um banco de dados chamado `api_crud` no seu servidor MySQL.
2.  No arquivo `src/main/resources/application.properties`, configure as credenciais do seu banco de dados:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/api_crud
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update

---

## Executando a Aplicação

1. Clone este repositório.
2. Navegue até a pasta raiz do projeto.
3. Execute o comando para rodar a aplicação:
    - mvn spring-boot:run

A aplicação estará disponível em (http://localhost:8080).

---

## Documentação da API (Swagger/OpenAPI)

Após iniciar a aplicação, a documentação interativa da API estará disponível em:
- [Swagger] (http://localhost:8080/swagger-ui.html)

Nesta interface, você poderá visualizar todos os endpoints, seus modelos de dados e testá-los diretamente.