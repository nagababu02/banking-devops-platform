# CI/CD Overview

## Initial Jenkins pipeline

The initial pipeline is intentionally limited to source checkout, toolchain verification, and Maven verification:

1. Checkout the selected Git branch.
2. Verify the configured JDK 21 and Maven installations.
3. Run `mvn -B clean verify` across the Maven multi-module project.
4. Publish JUnit XML reports and clean the Jenkins workspace.

## Jenkins prerequisites

Configure these named tools in **Manage Jenkins → Tools**:

- JDK installation name: `jdk21`
- Maven installation name: `maven3`

The Jenkins agent must have Docker access before container build and image-scan stages are introduced.

## Planned pipeline extensions

- SonarQube static analysis and quality gate
- Build each service image with a traceable version
- Trivy filesystem and image scans
- Push approved images to Amazon ECR using a least-privilege IAM role
- Update the GitOps deployment repository
- Require an explicit production approval before promotion

Do not store AWS keys, registry passwords, or SonarQube tokens in this file. Use Jenkins credentials or workload identity with narrowly scoped permissions.
