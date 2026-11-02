# CI/CD Overview

## Jenkins pipeline stages currently defined

1. Checkout the selected Git branch and derive a 12-character Git commit image tag.
2. Verify JDK 21, Maven, Docker, Trivy, and AWS CLI are available on the Jenkins agent.
3. Run `mvn -B clean verify` across all six Maven modules and publish JUnit reports.
4. Run SonarQube analysis using `security/sonar-project.properties`.
5. Wait for the SonarQube quality gate; fail the pipeline if it is not passed.
6. Build six Docker images from the repository root and fail on HIGH/CRITICAL Trivy image findings.
7. On the `main` branch only, authenticate using the agent's AWS identity and push immutable commit-tagged images to ECR.

The pipeline does not yet deploy to EKS or update the GitOps repository. Those steps will be added after the image build and infrastructure stages are validated.

## Jenkins prerequisites

Install/configure the following:

- Pipeline and Git plugins.
- JUnit plugin.
- SonarQube Scanner for Jenkins and Sonar Quality Gates integration.
- Workspace Cleanup plugin.
- JDK installation name: `jdk21`.
- Maven installation name: `maven3`.
- SonarQube server name: `SonarQube`.

The Jenkins agent must have Docker Engine, Trivy, and AWS CLI v2 installed. It must be able to build Docker images and reach AWS APIs.

## AWS permissions

Prefer an attached IAM role or supported workload identity over long-lived access keys. The publishing role needs only the permissions required to identify the account, authenticate to ECR, and push images to the six project repositories. Scope repository actions to `banking-devops/*`; grant `ecr:GetAuthorizationToken` at the required account scope.

## Important operating controls

- Protect `main` and require pull-request review.
- Use commit SHA image tags; ECR repositories are configured for immutable tags.
- Do not bypass the SonarQube quality gate or Trivy failure threshold to force a release.
- Configure the SonarQube webhook to the Jenkins `sonarqube-webhook/` endpoint.
- Keep AWS credentials, SonarQube tokens, and GitHub tokens out of the repository.
- Add a reviewed GitOps change and explicit production approval before implementing deployment stages.
