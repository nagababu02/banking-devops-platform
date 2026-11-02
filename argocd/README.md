# Argo CD GitOps

The `banking-dev` Application watches the `kubernetes/overlays/dev` Kustomize overlay in this repository and reconciles it into the `banking` namespace.

## Prerequisites

- Argo CD is installed in the target EKS cluster.
- The repository is reachable by Argo CD. Configure a GitHub deploy key or repository credential if the repository is made private.
- The dev overlay image names have been updated with the correct AWS account ID and image tags.
- The application manifests have been merged into the configured Git branch.

## Register the application

After Argo CD is installed and the application manifests are on the target branch:

```bash
kubectl apply -f argocd/applications/banking-dev.yaml
kubectl get applications -n argocd
```

## Delivery flow

Jenkins builds, tests, scans, and pushes immutable images to ECR. A later pipeline stage updates the image tag in the GitOps repository through a reviewed commit or pull request. Argo CD observes the Git change and reconciles the cluster.

Production should use a separate Argo CD Application, namespace, promotion path, and approval policy. Do not point production at the dev overlay.
