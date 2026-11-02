# Helm Operations

Kustomize is used for application-specific Kubernetes overlays in this repository. Helm is used to install shared platform components and their upstream charts.

## Platform components

### AWS Load Balancer Controller

The controller is required by the ALB Ingress in `kubernetes/base/ingress.yaml`.

Before installation:
- Create the controller IAM policy and role using the official AWS Load Balancer Controller guide.
- Associate the role through EKS Pod Identity or IRSA.
- Create the `aws-load-balancer-controller` ServiceAccount in `kube-system`.
- Confirm the VPC ID and EKS cluster name.

Install:

```bash
helm repo add eks https://aws.github.io/eks-charts
helm repo update
helm upgrade --install aws-load-balancer-controller eks/aws-load-balancer-controller \
  --namespace kube-system \
  --set clusterName=banking-devops-eks \
  --set region=us-east-1 \
  --set vpcId=<VPC_ID> \
  --set serviceAccount.create=false \
  --set serviceAccount.name=aws-load-balancer-controller
```

Use the official chart documentation and pin a tested chart version in a repeatable environment.

### Prometheus and Grafana

See [Monitoring](../monitoring/README.md) and `monitoring/kube-prometheus-stack-values.yaml`.

## Versioning

For repeatable deployments, pin Helm chart versions in the deployment procedure and review chart release notes before upgrades. Do not use unpinned `latest` versions in a production workflow.
