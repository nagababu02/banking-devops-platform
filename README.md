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
