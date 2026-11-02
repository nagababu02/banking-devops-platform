# Application Team → DevOps Handover Requirements

This document defines the information the application team must provide before DevOps can build, secure, deploy, and operate a service. It is a project handover template; values below are initial examples for this portfolio application.

## 1. Service inventory

| Service | Repository module | Local port | Context path |
|---|---|---:|---|
| Customer | customer-service | 8081 | /api/v1/customers |
| Account | account-service | 8082 | /api/v1/accounts |
| Transaction | transaction-service | 8083 | /api/v1/transactions |
| Payment | payment-service | 8084 | /api/v1/payments |
| Loan | loan-service | 8085 | /api/v1/loans |
| Notification | notification-service | 8086 | /api/v1/notifications |

For every new service, the application team must supply its owner, business purpose, repository/module path, runtime, port, API contract, upstream/downstream dependencies, and support contact.

## 2. Build requirements

Provide:

- Supported JDK and Maven versions.
- Exact build and test commands.
- Unit, integration, and contract test commands.
- Required Maven profiles and build-time properties.
- Artifact name and expected output path.
- Any external repositories or private dependency credentials required.

Do not embed secrets in Maven settings committed to Git.

## 3. Runtime and container requirements

For each service, provide:

- Startup command and expected startup time.
- HTTP port and context path.
- CPU/memory requests and limits based on load testing.
- Readiness, liveness, and startup probe paths.
- Graceful shutdown behavior.
- Writable filesystem paths, if any.
- Required Linux capabilities (default expectation: none).
- Supported architecture and base-image constraints.

Services must run as a non-root user and must not require privileged containers.

## 4. Configuration and secrets

Classify every configuration item as non-sensitive configuration or secret.

- Non-sensitive values: environment variables or ConfigMaps.
- Credentials, tokens, certificates, and private keys: secret manager integration.
- State whether a restart is required after configuration changes.
- Document rotation requirements and expiration behavior.

Never provide real production credentials or real customer data in this repository or handover document.

## 5. Data and transaction requirements

For every datastore, provide engine/version, database/schema name, connectivity path, backup/restore expectations, retention, encryption, migration strategy, and capacity estimate.

For transaction-related workflows, document idempotency behavior, duplicate request handling, concurrency controls, audit events, state transitions, reconciliation, and failure recovery. Financial state changes must not rely on in-memory state.

## 6. Service dependencies and network flows

Provide a source/destination matrix for every dependency:

| Source | Destination | Protocol/port | Purpose | Required? |
|---|---|---|---|---|
| transaction-service | account-service | HTTP / 8082 | Account validation (planned) | Yes for transaction workflow |
| all services | DNS | UDP/TCP 53 | Name resolution | Yes |
| Prometheus | service pods | HTTP / service port | Metrics scraping | Operational |

List external APIs, message brokers, databases, DNS names, TLS requirements, and expected timeouts/retry policies. Retries must be safe for non-idempotent operations.

## 7. Observability and support

Provide:

- Structured log format and correlation/trace ID propagation.
- Metrics that represent request rate, errors, latency, queue depth, and business workflow outcomes.
- Distributed tracing requirements.
- Alert thresholds and actionable runbook links.
- Service owner and on-call escalation path.
- SLO/SLI targets and maintenance windows.

Do not log passwords, access tokens, full account numbers, payment credentials, or sensitive personal data.

## 8. Release and rollback

Provide semantic versioning or commit-based release policy, database migration compatibility, backward compatibility expectations, feature-flag strategy, rollback constraints, and any irreversible operations.

The release must pass tests, static analysis, image scanning, and the required review/approval workflow before production promotion.

## 9. DevOps acceptance checklist

- [ ] Build and test commands documented and repeatable.
- [ ] API contract and dependencies documented.
- [ ] Health and metrics endpoints available.
- [ ] Runtime configuration and secrets classified.
- [ ] Container runs non-root and passes image scan.
- [ ] CPU/memory requirements supplied.
- [ ] Network flows and TLS requirements approved.
- [ ] Database migration and recovery plan reviewed.
- [ ] Logs, metrics, traces, alerts, and runbooks supplied.
- [ ] Release and rollback strategy approved.
