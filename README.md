# Presto Med API

# Sobre o projeto

Presto Med API é uma API REST desenvolvida para o gerenciamento da agenda e cadastro de consultas de uma clínica médica, nele o usuário pode criar uma conta, listar todos os médicos da clínica ou procura-los baseados em suas especialidades e por fim agendar uma consulta no horário em que preferir (obedecendo as regras de negócio estabelecidas).

- O horário de agendamento deve obedecer o horário de funcionamento da clínica
- Um médico não pode ter mais de uma consulta no mesmo horário
- Um usuário não pode ter mais de uma consulta no mesmo horário
- A consulta deve ser marcada ou reagendada com 24 horas de antecedência

# Tecnologias utilizadas
- Java 17
- Spring Boot
- Spring Secutiry / JWT
- JPA / Hibernate
- Maven
- MySQL
- JUnit / Mockito
- Swagger

# Como executar o projeto

```bash
# clonar repositório
git clone https://github.com/lucascamposdev/prestomed-API.git

# executar o projeto
mvn spring-boot:run
```

# Autor

Lucas Campos

https://www.linkedin.com/in/lucascamposdev/

