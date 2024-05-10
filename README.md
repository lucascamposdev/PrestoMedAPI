# Presto Med API

# Sobre o projeto

Presto Med é uma API REST desenvolvida para o gerenciamento da agenda e cadastro de consultas de uma clínica médica, nele o usuário pode criar uma conta e agendar sua consulta com o médico da especialidade que deseja no horário em que preferir (obedecendo as regras de negócio abaixo).

# Regras
- O horário de agendamento deve obedecer o horário de funcionamento da clínica.
- Um médico não pode ter mais de uma consulta no mesmo horário.
- Um usuário não pode ter mais de uma consulta no mesmo horário.
- A consulta deve ser marcada ou reagendada com 24 horas de antecedência.

# Tecnologias utilizadas
- Java 17
- Spring Boot 3.2.5
- Spring Secutiry / JWT
- JPA / Hibernate
- Maven
- MySQL
- JUnit / Mockito
- Swagger

A API poderá ser acessada em http://localhost:8080

# Como executar o projeto

```bash
# clonar repositório
git clone https://github.com/lucascamposdev/prestomed-API.git

# executar o projeto
mvn spring-boot:run
```

## API Endpoints

A maior parte das funcionalidades exige que o usuário esteja logado (utilizando token JWT)

O Swagger contendo todos os endpoints e como utiliza-los poderá ser acessado em: http://localhost:8080/swagger-ui.html

# Autor

Lucas Campos

https://www.linkedin.com/in/lucascamposdev/

