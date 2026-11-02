# Banking Platform — Application Architecture

## Scope

This repository is a DevOps engineering demonstration. It models service boundaries and delivery patterns for a banking-style application. It is not a real banking system and must never be connected to real customer accounts, payment rails, or funds.

## Service boundaries

| Service | Responsibility | Initial local port |
|---|---|---:|
| Customer | Customer profile lifecycle | 8081 |
| Account | Account metadata and status | 8082 |
| Transaction | Transaction workflow boundary and audit trail | 8083 |
| Payment | Payment instruction orchestration boundary | 8084 |
| Loan | Loan application lifecycle | 8085 |
| Notification | Notification request handling | 8086 |

## Delivery architecture

Developer → GitHub → Jenkins → Maven build and tests → SonarQube → Docker build → Trivy image scan → Amazon ECR → GitOps manifests → Argo CD → Amazon EKS.

## Engineering principles

- Each service is independently buildable and containerizable.
- Health endpoints are exposed through Spring Boot Actuator.
- Business endpoints will be added with validation and automated tests.
- Transaction examples must demonstrate idempotency, auditability, and explicit state transitions.
- Secrets are supplied at runtime; credentials must never be committed.
- Production-like controls in this repository are demonstrations, not a certification of banking compliance.

## Current implementation stage

Phase 1 establishes the Maven multi-module structure and service bootstraps. Persistence, authentication/authorization, inter-service messaging, and business workflows are subsequent implementation stages.
