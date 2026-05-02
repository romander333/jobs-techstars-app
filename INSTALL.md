# Installation Guide

This guide describes how to run the Jobs Techstars Application locally using Docker.

## Requirements

Before running the application, make sure you have installed:

- Docker
- Docker Compose
- Git

## Clone the Repository

Clone the project from the repository:

```bash
git clone https://github.com/romander333/jobs-techstars-app.git
```

## Environment Variables

The application can be configured using environment variables.

Recommended `.env` example:

```env
JOBS_DB_NAME=jobs
JOBS_USERNAME=postgres
JOBS_PASSWORD=postgres
POSTGRES_DB_PORT=5433
BASE_JOBS_TECH_STARS_URL=https://api.getro.com/api/v2/collections/89/search/jobs
```

If the `.env` file is not provided, Docker Compose will use default values from `docker-compose.yml`.

## Docker Compose Configuration

The application contains two main services:

- `postgres` — PostgreSQL database
- `app` — Spring Boot application

PostgreSQL runs inside Docker on port `5432`.

The local PostgreSQL port is configured by `POSTGRES_DB_PORT`.

Recommended port mapping:

```yaml
ports:
  - "${POSTGRES_DB_PORT:-5433}:5432"
```

This means:

```text
localhost:5433 -> postgres container:5432
```

## Run the Application

Build and start all services:

```bash
docker compose up --build
```

The application will be available at:

```text
http://localhost:8080
```

## PostgreSQL Connection

You can connect to the database locally using the following settings:

```text
Host: localhost
Port: 5433
Database: jobs
Username: postgres
Password: postgres
```

If you changed `POSTGRES_DB_PORT` in `.env`, use that port instead of `5433`.

## Database Migrations

Database schema is managed by Liquibase.

Liquibase migrations are executed automatically when the application starts.

After successful startup, the following Liquibase tables should be created:

```text
databasechangelog
databasechangeloglock
```

The main application table should also be created:

```text
jobs
```

## Check Created Tables

To check tables inside PostgreSQL, run:

```bash
docker exec -it jobs_postgres psql -U postgres -d jobs -c "\dt"
```

Expected result should include:

```text
databasechangelog
databasechangeloglock
jobs
```

## Check Saved Jobs

To check how many jobs were saved:

```bash
docker exec -it jobs_postgres psql -U postgres -d jobs -c "SELECT COUNT(*) FROM jobs;"
```

To view several saved jobs:

```bash
docker exec -it jobs_postgres psql -U postgres -d jobs -c "SELECT * FROM jobs LIMIT 5;"
```

## API Documentation

Swagger UI is available at:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI documentation is available at:

```text
http://localhost:8080/v3/api-docs
```

## Main API Endpoint

The main endpoint for receiving jobs is:

```http
GET /jobs
```

Example request:

```text
http://localhost:8080/jobs?page=0&size=20
```

Example request with filters:

```text
http://localhost:8080/jobs?title=Sales&companyName=DataCamp&location=Argentina&seniority=ASSOCIATE&page=0&size=20&sort=title,asc
```

## Job Synchronization

The application synchronizes jobs automatically.

Synchronization runs every hour.

During synchronization, the application:

1. Downloads jobs from the external Techstars jobs API.
2. Saves actual jobs into PostgreSQL.
3. Removes jobs that are no longer present in the external API response.

## Create Data Dump

After running the application and saving data into the database, create a SQL dump:

```bash
mkdir -p data
docker exec -t jobs_postgres pg_dump -U postgres -d jobs > data/jobs_dump.sql
```

The generated dump will be saved here:

```text
data/jobs_dump.sql
```

This file can be submitted together with the project as proof that the application works and data was saved successfully.

## Restore Data Dump

To restore the dump manually, run:

```bash
docker exec -i jobs_postgres psql -U postgres -d jobs < data/jobs_dump.sql
```

## Stop the Application

To stop running containers:

```bash
docker compose down
```

## Restart the Application

To start the application again:

```bash
docker compose up --build
```

## Rebuild from Scratch

To remove containers and rebuild the application:

```bash
docker compose down
docker compose up --build
```

## Reset Database and Rebuild from Scratch

To remove containers, database volume, and all saved PostgreSQL data:

```bash
docker compose down -v
docker compose up --build
```

Warning: this command removes all saved database data.

## Check Logs

Application logs:

```bash
docker compose logs -f app
```

PostgreSQL logs:

```bash
docker compose logs -f postgres
```

All services logs:

```bash
docker compose logs -f
```

## Troubleshooting

### PostgreSQL connection refused

Check that the PostgreSQL port mapping is correct.

Correct example:

```yaml
ports:
  - "${POSTGRES_DB_PORT:-5433}:5432"
```

The right side must be `5432`, because PostgreSQL inside the container listens on port `5432`.

### Liquibase tables were created, but the jobs table is not visible

Make sure you are connected to the correct database:

```text
Database: jobs
Schema: public
```

Also check tables directly inside the container:

```bash
docker exec -it jobs_postgres psql -U postgres -d jobs -c "\dt"
```

### Application cannot connect to PostgreSQL

Inside Docker Compose, the application should connect to PostgreSQL using the service name:

```text
jdbc:postgresql://postgres:5432/jobs
```

Do not use `localhost` inside the application container, because `localhost` would refer to the application container itself, not to the PostgreSQL container.

## Notes

- The application is fully dockerized.
- PostgreSQL data is stored in a Docker volume.
- Liquibase automatically creates the database schema.
- Jobs are synchronized automatically every hour.
- A SQL dump can be generated and submitted with the project.