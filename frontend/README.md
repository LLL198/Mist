# Mist frontend compatibility baseline

`dist/` is the complete static frontend extracted from the running Mist Web
image. It is served by the local Nginx image below and keeps the existing
Vue/Vite route and API behaviour while the source-level Vue components are
being reconstructed from the bundle.

The static bundle is intentionally kept out of Git because it is a generated
deployment artifact. It is the current behavior-equivalence baseline while
source-level Vue components are replaced one route at a time. After extracting
or rebuilding it, remove the product-license UI and API with:

```powershell
New-Item -ItemType Directory -Force frontend/dist
docker cp foam-web:/usr/share/nginx/html/. frontend/dist
node tools\remove-license-from-frontend-dist.mjs
```

Check all extracted routes against the oracle and reconstructed web server with:

```powershell
node tools\frontend-route-smoke.mjs
```

Extract a readable copy of the bundle for source-level migration work:

```powershell
node tools\extract-frontend.mjs
npx --yes prettier --write "frontend/recovered/assets/*.js" "frontend/recovered/assets/*.css"
```

The generated `frontend/recovered/` directory contains the current chunk
layout plus `EXTRACTION.json`; it is ignored as a generated artifact.

The staged source layer is under `frontend/reconstructed-src/`. It currently
covers the HTTP client, local session, login, route metadata, route guards and
registration/payment calls. Product authorization is not required. Verify its
framework-independent core with:

```powershell
node tools\generate-frontend-contract.mjs
node tools\frontend-reconstruction-smoke.mjs
```
