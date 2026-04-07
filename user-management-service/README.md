# user-management-service

A Spring Boot REST service for user registration. Used as the shared Java repository for both GitHub Copilot hackathon exercises.

## Purpose

| Exercise | How this repo is used |
|----------|----------------------|
| Exercise 1 (Standards Aggregator) | Step 5 validation — review PR branch code against generated copilot-instructions.md |
| Exercise 2 (Expert MR Reviewer) | Full MR review — fetch PR via GitHub MCP, run SonarLint/JaCoCo/PITest/Trivy |

## Branches

| Branch | Description |
|--------|-------------|
| `main` | Baseline implementation — simple user registration, plain text passwords, no validation |
| `feature/USER-142-user-registration` | PR branch — adds BCrypt hashing and duplicate check (contains intentional bugs for review) |

## Tech Stack

- Java 17
- Spring Boot 3.2.4
- Spring Data JPA + H2 (in-memory, for demo)
- Maven
- JaCoCo (coverage)
- PITest (mutation testing)

## API

### POST /api/users/register

```json
Request:
{ "email": "user@example.com", "password": "Secret123" }

Response 201:
{ "userId": "550e8400-e29b-41d4-a716-446655440000" }
```

## Running Locally

```bash
mvn spring-boot:run
```

## Running Tests with Coverage

```bash
# Run tests + generate JaCoCo report
mvn clean verify

# Open coverage report
open target/site/jacoco/index.html
```

## Running Mutation Tests

```bash
mvn pitest:mutationCoverage
# Open: target/pit-reports/<timestamp>/index.html
```

## Running Security Scan

```bash
trivy fs --scanners vuln .
```
