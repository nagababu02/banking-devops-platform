# Monitoring and Observability

Each Spring Boot service exposes Prometheus metrics through `/actuator/prometheus`. The Kubernetes base includes one Prometheus Operator `ServiceMonitor` per service.

## Install kube-prometheus-stack

Create the Grafana admin secret through your approved secret-management process. Do not commit the secret values.

```bash
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update
kubectl create namespace monitoring
kubectl -n monitoring create secret generic banking-grafana-admin \
  --from-literal=admin-user=admin \
  --from-literal=admin-password='<SET_A_STRONG_PASSWORD>'
helm upgrade --install banking-monitoring prometheus-community/kube-prometheus-stack \
  --namespace monitoring \
  --values monitoring/kube-prometheus-stack-values.yaml
```

Review chart defaults and the values file against the chart version you intend to pin before installing it.

## Apply ServiceMonitors

The ServiceMonitors are included in the Kubernetes base and are applied with the banking overlay:

```bash
kubectl apply -k kubernetes/overlays/dev
kubectl get servicemonitors -n banking
```

## Access Grafana

Use port-forwarding for a local lab instead of exposing Grafana publicly:

```bash
kubectl -n monitoring port-forward svc/banking-monitoring-grafana 3000:80
```

Then open `http://localhost:3000`. Retrieve the admin password from the secret store or Kubernetes Secret using an authorized account.

Prometheus and Grafana are internal services. For shared environments, use SSO/authentication, TLS, network restrictions, and a managed secret workflow.
