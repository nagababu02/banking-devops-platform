# Local Development Guide

## Prerequisites

- JDK 21
- Maven 3.9 or later
- Docker Engine/Desktop with the Docker Compose plugin
- Git

## Build and test

Run from the repository root:

```bash
mvn clean verify
```

This builds the Maven reactor and runs each service's Spring Boot context and controller status test.

## Run with Docker Compose

```bash
docker compose up --build -d
docker compose ps
```

Each service exposes a status endpoint:

- Customer: `http://localhost:8081/api/v1/customers/status`
- Account: `http://localhost:8082/api/v1/accounts/status`
- Transaction: `http://localhost:8083/api/v1/transactions/status`
- Payment: `http://localhost:8084/api/v1/payments/status`
- Loan: `http://localhost:8085/api/v1/loans/status`
- Notification: `http://localhost:8086/api/v1/notifications/status`

Actuator health endpoint: `http://localhost:<service-port>/actuator/health`.

Stop containers:

```bash
docker compose down
```

## Run one service with Maven

For example, run the account service and its required reactor modules:

```bash
mvn -pl banking-services/account-service -am spring-boot:run
```

## Troubleshooting

- If a port is already in use, stop the process using it or override `SERVER_PORT`.
- If Docker build fails, run the command from the repository root; the Dockerfiles use the root as their build context.
- If Maven cannot resolve dependencies, check network/proxy access and run `mvn -U clean verify`.
- Do not commit passwords, API keys, private keys, real customer information, or local environment files.
