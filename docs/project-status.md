# Project Status

Last updated: 2026-11-02

This status reflects files committed to the current feature branch. It does not imply that cloud resources have been deployed or that builds have passed.

| Workstream | Status | Notes |
|---|---|---|
| Repository and architecture | Scaffolded | README, architecture notes, team handover requirements |
| Java microservices | Scaffolded | Six Spring Boot 4.1.2 / Java 21 modules with status APIs |
| Automated tests | Added, not executed | One controller smoke test per service |
| Local containers | Defined, not executed | Six non-root Dockerfiles and Docker Compose |
| Jenkins CI | Defined, not executed | Maven, SonarQube quality gate, Trivy, ECR publishing on main |
| Jenkins GitOps CD hand-off | Implemented, not executed | Updates six dev Kustomize image tags, pushes a promotion branch, and opens a GitHub PR |
| Argo CD | Defined, not deployed | Watches the dev overlay on main and reconciles merged GitOps changes |
| AWS Terraform | Drafted, not validated | VPC, EKS 1.36, ECR, Jenkins ECR push policy, S3 backend example |
| Kubernetes | Drafted, not validated | Kustomize, deployments, services, security policies, ALB ingress |
| Monitoring | Defined, not deployed | Prometheus registry, ServiceMonitors, kube-prometheus-stack values |
| Security documentation | Added | Security baseline, Trivy, SonarQube, operations runbook |

## End-to-end delivery model

```text
GitHub main -> Jenkins CI -> ECR -> GitOps PR -> merge -> Argo CD -> EKS
```

Jenkins does not run `kubectl apply`. Git remains the source of truth for Kubernetes desired state.

## Remaining work before a credible end-to-end demo

1. Run `mvn clean verify` and fix any compile/test issues.
2. Build all six Docker images and verify each health and metrics endpoint.
3. Run `terraform fmt -recursive`, `terraform init`, `terraform validate`, and review `terraform plan`.
4. Restrict the EKS public API endpoint CIDR before applying Terraform.
5. Configure Jenkins tools, SonarQube webhook, Linux/Docker agent, `gh` CLI, Python 3, and the `github-token` Jenkins credential.
6. Install AWS Load Balancer Controller, Argo CD, and kube-prometheus-stack.
7. Replace ECR account IDs, immutable image tags, ACM certificate ARN, and DNS host placeholders.
8. Validate Kubernetes policies and traffic paths in a real EKS test environment.
9. Implement persistent data storage, authentication/authorization, and business workflows. Transaction processing must use durable idempotency, audit records, and database concurrency controls.
10. Execute the GitOps PR flow and end-to-end deployment/rollback test in a controlled EKS environment.

## Safety and cost

This repository is a portfolio project, not a real banking platform. Do not connect real customer data, funds, payment rails, or production credentials. Terraform provisions billable AWS infrastructure when applied; review costs and destroy the lab when not in use.
