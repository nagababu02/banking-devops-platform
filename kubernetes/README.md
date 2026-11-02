# Kubernetes Deployment

The base manifests define the six service Deployments and ClusterIP Services, a restricted namespace, a tokenless ServiceAccount, resource quotas, container defaults, and a namespace-scoped NetworkPolicy.

## Before applying

1. Build and push each service image to the matching ECR repository.
2. In `overlays/dev/kustomization.yaml`, replace `REPLACE_WITH_ACCOUNT_ID` with your AWS account ID.
3. Confirm the EKS nodes can pull from ECR.
4. Confirm your CNI supports Kubernetes NetworkPolicy enforcement. If it does not, install and configure a policy-capable CNI before relying on these policies.

## Preview and apply

```bash
kubectl kustomize kubernetes/overlays/dev
kubectl apply -k kubernetes/overlays/dev
kubectl get deploy,svc,pods -n banking
```

The dev overlay scales each service to one replica. The base defaults to two replicas. The HPA and metrics-server integration will be added as a later stage.

## Security notes

- Pods run as UID/GID 10001, use a read-only root filesystem, drop Linux capabilities, and do not receive a Kubernetes API token.
- The namespace enforces the Kubernetes Pod Security `restricted` standard.
- The NetworkPolicy permits same-namespace service traffic and DNS to kube-system. Add explicit egress rules when database, external API, or observability dependencies are introduced.
- The ECR image names are placeholders until the account ID is set.
