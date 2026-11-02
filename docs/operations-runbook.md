# Operations Runbook

## Health checks

Each service exposes:

- `/actuator/health` — application health.
- `/actuator/health/liveness` — process liveness.
- `/actuator/health/readiness` — readiness to receive traffic.

Local status endpoints are documented in [Local Development](local-development.md).

## Local Docker Compose operations

```bash
docker compose ps
docker compose logs --tail=100 transaction-service
docker compose restart transaction-service
docker compose down
```

## Kubernetes operations (once manifests are added)

```bash
kubectl get pods -n banking
kubectl get deploy,svc,ingress -n banking
kubectl describe pod <pod-name> -n banking
kubectl logs deploy/<deployment-name> -n banking --since=30m
kubectl rollout status deployment/<deployment-name> -n banking
kubectl rollout history deployment/<deployment-name> -n banking
```

For a controlled rollback, inspect the rollout history first, then use `kubectl rollout undo` only after confirming the previous revision is safe.

## Incident record

Capture: incident start/end time, impacted service, symptoms, alert/source, deployment revision, relevant correlation IDs, mitigation, root cause, and preventive action. Do not paste secrets or sensitive customer data into tickets or logs.
