# AWS Load Balancer Controller Ingress

The base Ingress routes the six API prefixes through an AWS Application Load Balancer using the AWS Load Balancer Controller.

## Prerequisites

- AWS Load Balancer Controller installed in the EKS cluster.
- Controller IAM permissions configured using EKS Pod Identity or IRSA.
- An ACM certificate in the same AWS region as the ALB.
- DNS record for `banking.example.com` pointing to the ALB after it is provisioned.

## Before applying

1. Replace `REPLACE_WITH_ACM_CERTIFICATE_ARN` in `base/ingress.yaml` with the ACM certificate ARN.
2. Replace `banking.example.com` with a domain you control.
3. Ensure the ALB security group exposes only the required HTTP/HTTPS ports and apply any source-IP restrictions required by your environment.
4. Ensure the AWS Load Balancer Controller is installed and has permissions to create ALBs, target groups, listeners, and security group rules.

## Routes

- `/api/v1/customers` → customer-service
- `/api/v1/accounts` → account-service
- `/api/v1/transactions` → transaction-service
- `/api/v1/payments` → payment-service
- `/api/v1/loans` → loan-service
- `/api/v1/notifications` → notification-service

HTTP requests are redirected to HTTPS. The ALB target health check uses each service's Spring Boot readiness endpoint.

Do not apply the Ingress until the certificate ARN, hostname, controller, and EKS networking are configured.
