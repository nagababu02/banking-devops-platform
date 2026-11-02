# CI/CD Overview

## End-to-end delivery flow

```text
Developer
   |
   v
GitHub main
   |
   v
Jenkins CI
   |-- Maven build + unit tests
   |-- SonarQube analysis + quality gate
   |-- Trivy filesystem/IaC scan
   |-- Docker build
   |-- Trivy image scan
   |
   v
Amazon ECR
   |  immutable Git commit SHA tag
   |
   v
Jenkins GitOps promotion
   |-- create gitops/update-dev-* branch
   |-- update kubernetes/overlays/dev/kustomization.yaml
   |-- commit image tags
   |-- push branch
   |-- create GitHub Pull Request
   |
   v
GitHub main (after PR approval/merge)
   |
   v
Argo CD
   |
   v
Amazon EKS
```

Jenkins owns CI and the promotion hand-off. Argo CD owns deployment reconciliation. Jenkins does not run `kubectl apply` against EKS.

## Jenkins pipeline stages

1. Checkout the selected Git branch and derive a 12-character Git commit image tag.
2. Verify JDK 21, Maven, Docker, Trivy, AWS CLI, Python 3, and GitHub CLI are available on the Jenkins agent.
3. Run `mvn -B clean verify` across all six Maven modules and publish JUnit reports.
4. Run SonarQube analysis using `security/sonar-project.properties`.
5. Wait for the SonarQube quality gate; fail the pipeline if it is not passed.
6. Run a Trivy filesystem/IaC scan using `security/trivy.yaml`.
7. Build six Docker images from the repository root and fail on HIGH/CRITICAL Trivy image findings.
8. On the `main` branch only, authenticate using the agent's AWS identity and push immutable commit-tagged images to ECR.
9. On successful ECR publishing, create a unique GitOps branch.
10. Update all six `newTag` values in `kubernetes/overlays/dev/kustomization.yaml` to the same immutable image tag.
11. Commit and push the manifest change, then create a GitHub Pull Request targeting `main`.
12. After the GitOps PR is reviewed and merged, Argo CD detects the change and synchronizes the dev overlay into EKS.

## Manifest update

The helper script `jenkins/update-gitops.sh` updates the six service image tags. For a source commit such as `a82f31c7e921`, the dev overlay changes from:

```yaml
newTag: dev
```

to:

```yaml
newTag: a82f31c7e921
```

The image tag is immutable and matches the image pushed to ECR by the same Jenkins run.

## Jenkins prerequisites

Install/configure the following:

- Pipeline and Git plugins.
- JUnit plugin.
- SonarQube Scanner for Jenkins and Sonar Quality Gates integration.
- Workspace Cleanup plugin.
- JDK installation name: `jdk21`.
- Maven installation name: `maven3`.
- SonarQube server name: `SonarQube`.
- Docker Engine.
- Trivy.
- AWS CLI v2.
- Python 3.
- GitHub CLI (`gh`).

The Jenkins agent must be able to build Docker images and reach AWS and GitHub APIs.

## Jenkins GitHub credential

Create a Jenkins **Secret Text** credential with ID:

```text
github-token
```

The token is used only during the GitOps promotion stage. It needs permission to push branches and create pull requests in this repository. Do not put the token in the Jenkinsfile or repository.

For a production implementation, prefer a GitHub App installation token with repository-scoped permissions instead of a long-lived personal access token.

## AWS permissions

Prefer an attached IAM role or supported workload identity over long-lived access keys. The publishing role needs only the permissions required to identify the account, authenticate to ECR, and push images to the six project repositories. Scope repository actions to `banking-devops/*`; grant `ecr:GetAuthorizationToken` at the required account scope.

## Argo CD prerequisites

Argo CD must be installed in the EKS cluster and must be able to read this repository. The repository contains `argocd/applications/banking-dev.yaml`, which watches `kubernetes/overlays/dev` on `main` and enables automated sync, prune, and self-heal.

The dev overlay must contain the real AWS account ID and the ALB/ACM configuration required by the cluster before deployment.

## Important operating controls

- Protect `main` and require pull-request review before GitOps promotion reaches Argo CD.
- Use commit SHA image tags; ECR repositories are configured for immutable tags.
- Do not bypass the SonarQube quality gate or Trivy failure threshold to force a release.
- Configure the SonarQube webhook to the Jenkins `sonarqube-webhook/` endpoint.
- Keep AWS credentials, SonarQube tokens, and GitHub tokens out of the repository.
- Do not let Jenkins run `kubectl apply`; Git remains the deployment source of truth.
- Use a separate production overlay and an explicit production approval path before promoting beyond dev.
