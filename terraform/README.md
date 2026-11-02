# AWS Infrastructure

This Terraform root provisions the first AWS platform foundation:

- A VPC across three Availability Zones.
- Public and private subnets with Kubernetes load-balancer discovery tags.
- A single NAT Gateway for development egress (not multi-AZ highly available).
- An Amazon EKS 1.36 cluster with managed private worker nodes.
- Six Amazon ECR repositories with immutable tags, scan-on-push, encryption, and lifecycle policies.

## Prerequisites

- Terraform 1.16.x (or newer than 1.6.0).
- AWS CLI v2 configured with credentials for the target account.
- An IAM identity permitted to create VPC, IAM, EKS, EC2, and ECR resources.

The region is currently set to `us-east-1` in `versions.tf`. Update it consistently if you intentionally choose another region.

## Important security change before apply

In `main.tf`, replace the EKS API endpoint CIDR `0.0.0.0/0` with your current public IP in CIDR notation, for example `x.x.x.x/32`. Do not leave the Kubernetes API endpoint open to every IPv4 address for a shared or long-lived environment.

## Commands

Run from this directory:

```bash
terraform fmt -recursive
terraform init
terraform validate
terraform plan
```

Review the complete plan, estimated cost, and account/region before running `terraform apply`. No AWS resources are created by the commands above.

After a successful apply:

```bash
aws eks update-kubeconfig --region us-east-1 --name banking-devops-eks
kubectl get nodes
```

Destroy only when you are certain the environment contains no data or resources you need:

```bash
terraform destroy
```

## Cost and lifecycle warnings

EKS control plane, two EC2 worker nodes, NAT Gateway, NAT data processing, public IPv4 addresses, and load balancers can incur ongoing charges. The single NAT Gateway is a cost-saving development choice and a single point of failure. Review AWS pricing and destroy the lab when it is not in use.

The ECR lifecycle policy keeps a maximum of 40 images per repository and expires untagged images after 14 days. Do not use this policy unchanged if you later require a longer release-retention period.

## State management

This directory currently uses local Terraform state. Before a team or persistent environment uses it, configure a dedicated encrypted S3 backend with versioning, public-access blocking, and a state-locking strategy supported by the selected Terraform version. Do not store state files in Git.
