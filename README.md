# NotaFlow

API REST em Java + Spring Boot para gestão de notas fiscais, com integração
planejada a um pipeline de extração automática de dados via visão computacional.

Projeto de estudo para consolidar Spring Boot, JPA, PostgreSQL e boas práticas
de backend.

## Stack

- Java 21+ / Spring Boot
- PostgreSQL + Flyway (migrations)
- Maven

## Como rodar

Pré-requisitos: Docker e JDK 21+.

\`\`\`bash
docker compose up -d
./mvnw spring-boot:run
\`\`\`

A aplicação sobe em `http://localhost:8080`. Verifique a saúde em:
`http://localhost:8080/actuator/health`

> Nota: o PostgreSQL do projeto roda na porta **5433** (não a padrão 5432),
> para não conflitar com uma instalação local do Postgres.

## Status

Em desenvolvimento. 

**Fase 0 — SETUP** ✅ concluída.\
**Fase 1 — CRUD com regras reais** ✅ concluída \
**Fase 2 — Processamento assíncrono** 🔜 próxima