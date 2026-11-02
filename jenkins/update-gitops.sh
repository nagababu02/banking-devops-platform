#!/usr/bin/env bash
set -euo pipefail

: "${IMAGE_TAG:?IMAGE_TAG must be set}"

MANIFEST="kubernetes/overlays/dev/kustomization.yaml"
SERVICES=(
  customer-service
  account-service
  transaction-service
  payment-service
  loan-service
  notification-service
)

for service in "${SERVICES[@]}"; do
  python3 - "$MANIFEST" "$service" "$IMAGE_TAG" <<'PY'
import pathlib
import re
import sys

path, service, tag = sys.argv[1:]
text = pathlib.Path(path).read_text()
pattern = re.compile(
    rf'(name:\s+banking-devops/{re.escape(service)}\s*\n\s*newName:.*\n\s*newTag:)\s*[^\n]+'
)
updated, count = pattern.subn(rf'\1 {tag}', text, count=1)
if count != 1:
    raise SystemExit(f'Could not update exactly one image entry for {service}; matches={count}')
pathlib.Path(path).write_text(updated)
PY
done

expected=${#SERVICES[@]}
actual=$(grep -cE '^\s*newTag:\s+' "$MANIFEST")
if [ "$actual" -ne "$expected" ]; then
  echo "Expected $expected image tags but found $actual in $MANIFEST" >&2
  exit 1
fi

grep -A2 -E '^\s*- name: banking-devops/' "$MANIFEST"
