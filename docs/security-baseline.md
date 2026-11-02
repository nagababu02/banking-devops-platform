# Security Baseline and Threat Boundaries

## Scope

The application is a demonstration environment. Do not use real personally identifiable information, bank account data, credentials, or real payment integrations.

## Secure delivery requirements

1. Protect the default branch and require pull-request review before merging.
2. Run Maven tests and static analysis for every pull request.
3. Scan source files and container images; fail builds for agreed HIGH/CRITICAL findings.
4. Use immutable image tags (commit SHA or release version); avoid deploying mutable `latest`.
5. Authenticate Jenkins to AWS with an assumed IAM role or supported workload identity. Do not store long-lived AWS access keys in source.
6. Scope ECR and EKS permissions to the resources required by the pipeline.
7. Keep EKS worker nodes in private subnets where practical; expose only required ingress endpoints.
8. Use Kubernetes service accounts and namespace-scoped RBAC with least privilege.
9. Inject secrets at runtime from a secret manager. Never store secret values in Git, Docker images, Helm values, or ConfigMaps.
10. Define readiness, liveness, and startup probes; set CPU/memory requests and limits.
11. Enable audit logs and centralize application/platform logs without logging secrets or sensitive payloads.
12. Review dependency updates and base-image updates regularly.

## Transaction-specific design requirements

When transaction workflows are implemented, they must include:

- An idempotency key with a unique database constraint to prevent duplicate submission.
- Explicit transaction states and validated state transitions.
- Durable audit events with actor, timestamp, correlation ID, and outcome.
- Database transactions and concurrency controls for ledger updates.
- Authorization checks at the service boundary.
- No sensitive payment credentials or full account identifiers in logs.

## Incident response outline

1. Detect and acknowledge the alert.
2. Identify affected service, namespace, deployment revision, and time window.
3. Preserve relevant logs and audit events.
4. Mitigate safely (rollback, scale, isolate, or disable the affected route as appropriate).
5. Validate service health and dependent workflows.
6. Record root cause, customer impact assumptions, corrective actions, and follow-up owners.

These controls are engineering targets for the portfolio project, not evidence of regulatory compliance.
