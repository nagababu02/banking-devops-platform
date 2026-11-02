# Security Tooling

This directory contains configuration and operating guidance for security checks in the demo platform.

## Planned controls

- SonarQube: source-code quality and static analysis.
- Trivy: filesystem, dependency, configuration, and container-image scanning.
- AWS IAM: short-lived role-based access for CI/CD; avoid long-lived access keys.
- Kubernetes RBAC: least-privilege service accounts and namespace-scoped permissions.
- Secrets: runtime secret injection through AWS Secrets Manager / External Secrets; never commit secret values.
- Network controls: private worker nodes, security groups, and Kubernetes NetworkPolicies.

## Important

A passing scanner is not proof that an application is secure or compliant. Findings must be triaged, documented, and remediated. This portfolio project is not a banking compliance certification.
