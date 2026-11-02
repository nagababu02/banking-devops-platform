# Banking DevOps Platform

A production-style banking microservices platform designed to demonstrate AWS DevOps, Kubernetes, CI/CD, GitOps, Infrastructure as Code, observability, and DevSecOps practices.

## Project Goals

- Build a realistic banking microservices application
- Containerize services with Docker
- Provision AWS infrastructure with Terraform
- Deploy workloads to Amazon EKS
- Use Amazon ECR for container images
- Implement Jenkins-based CI/CD
- Implement Argo CD GitOps deployment
- Add Prometheus and Grafana observability
- Apply security practices such as IAM, RBAC, Secrets, SonarQube, and Trivy
- Document the platform as a real DevOps project

## Planned Architecture

Developer → GitHub → Jenkins → Build/Test → SonarQube → Docker → Trivy → Amazon ECR → GitOps Repository → Argo CD → Amazon EKS

## Planned Banking Services

- Customer Service
- Account Service
- Transaction Service
- Payment Service
- Loan Service
- Notification Service

## Repository Structure

```text
banking-devops-platform/
├── banking-services/
├── docker/
├── kubernetes/
├── helm/
├── terraform/
├── jenkins/
├── argocd/
├── monitoring/
├── security/
└── docs/
```

## Implementation Approach

The project will be built incrementally. Application development comes first, followed by containerization, Kubernetes, AWS infrastructure, CI/CD, GitOps, monitoring, security, and production documentation.

> This project is for DevOps/Cloud engineering demonstration and learning. It does not process real financial transactions or real customer funds.


## Local Development

Prerequisites: Git, Docker with the Compose plugin, and (for running Maven directly) JDK 21 and Maven 3.9+.

Build and test all modules from the repository root:

```bash
mvn clean verify
```

Start all six services with Docker Compose:

```bash
docker compose up --build -d
docker compose ps
```

Check service status endpoints:

| Service | Status URL |
|---|---|
| Customer | http://localhost:8081/api/v1/customers/status |
| Account | http://localhost:8082/api/v1/accounts/status |
| Transaction | http://localhost:8083/api/v1/transactions/status |
| Payment | http://localhost:8084/api/v1/payments/status |
| Loan | http://localhost:8085/api/v1/loans/status |
| Notification | http://localhost:8086/api/v1/notifications/status |

Actuator health is available at `/actuator/health` on each service.

Stop the local stack:

```bash
docker compose down
```

See [Local Development](docs/local-development.md) for details and troubleshooting.
