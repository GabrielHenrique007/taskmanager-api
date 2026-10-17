# Task Manager API

API REST para gerenciamento de tarefas, desenvolvida com Java e Spring Boot, utilizando PostgreSQL para persistência dos dados.

O projeto foi desenvolvido com o objetivo de praticar conceitos de desenvolvimento backend, arquitetura em camadas, operações CRUD, validação de dados e testes automatizados.

## Tecnologias utilizadas

- Java 17 (versão de compilação configurada no projeto)
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- PostgreSQL
- Bean Validation
- Maven
- JUnit e Mockito
- MockMvc
- Swagger / OpenAPI

## Funcionalidades

- Criar tarefas.
- Listar todas as tarefas.
- Filtrar tarefas por status.
- Editar tarefas existentes.
- Excluir tarefas.
- Validar dados de entrada.
- Tratar erros de validação e tarefas não encontradas.
- Documentar e testar endpoints pela interface Swagger.
- Executar testes automatizados de serviços e controladores.

## Endpoints

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/tarefas` | Lista as tarefas |
| GET | `/tarefas?status=PENDENTE` | Filtra tarefas por status |
| POST | `/tarefas` | Cria uma tarefa |
| PUT | `/tarefas/{id}` | Edita uma tarefa |
| DELETE | `/tarefas/{id}` | Exclui uma tarefa |

## Exemplo de criação de tarefa

Requisição `POST /tarefas`:

```json
{
  "titulo": "Estudar Spring Boot",
  "status": "PENDENTE"
}
```

Os status disponíveis são:

- `PENDENTE`
- `EM_ANDAMENTO`
- `CONCLUIDA`

## Documentação da API

Com a aplicação em execução, acesse a interface Swagger:

http://localhost:8080/swagger-ui/index.html

Por meio dela, é possível visualizar e testar os endpoints da API.

## Banco de dados

O projeto utiliza PostgreSQL com Spring Data JPA.

Para executar a aplicação, é necessário ter um banco de dados PostgreSQL configurado e ajustar as propriedades de conexão no arquivo `src/main/resources/application.properties`.

Não publique senhas ou outras credenciais do banco de dados no repositório.

## Como executar o projeto

1. Instale um JDK compatível com a versão de Java configurada no projeto.
2. Configure o PostgreSQL e as propriedades de conexão.
3. Na raiz do projeto, execute:

```powershell
.\mvnw.cmd spring-boot:run
```

Para executar os testes automatizados:

```powershell
.\mvnw.cmd test
```

## Testes automatizados

O projeto possui testes para verificar o comportamento dos serviços, dos endpoints HTTP e o carregamento do contexto da aplicação.

Na última execução da suíte completa, os 16 testes passaram sem falhas ou erros.

## Objetivo

Este projeto faz parte dos meus estudos em desenvolvimento backend com Java e Spring Boot, com foco na construção de APIs REST e na preparação para oportunidades de estágio em desenvolvimento de software.