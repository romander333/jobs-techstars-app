---

# `README.md`

```md
# Jobs Techstars Application

Jobs Techstars Application is a Spring Boot application that downloads job data from the Techstars jobs API and stores it in a PostgreSQL database.

The application supports automatic job synchronization, database migration with Liquibase, and paginated job search with filtering and sorting.

## Tech Stack

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Liquibase
- WebClient
- Project Reactor
- Docker
- Docker Compose
- Maven
- Swagger / OpenAPI

## Main Features

- Downloads jobs from the external Techstars API
- Stores jobs in PostgreSQL
- Automatically synchronizes jobs every hour
- Removes jobs that are no longer present in the external API response
- Supports pagination
- Supports sorting
- Supports filtering by:
- title
- company name
- location
- seniority
- Uses Liquibase for database schema migrations
- Runs fully in Docker

## Project Structure

```text
src/main/java
└── application source code

src/main/resources
└── db/changelog
└── Liquibase changelogs

Dockerfile
docker-compose.yml
README.md
INSTALL.md