# Mist API reconstructed source

This directory contains the Java source recovered from the deployed Mist API
artifact and compiled as a standalone application. The source was recovered
with Vineflower and then repaired where the decompiler emitted invalid Java.

The build is deliberately independent of the upstream Spring Boot launcher:
it produces a normal application JAR plus a dependency directory. This keeps
the old API JAR out of the runtime classpath while retaining the existing
database migrations and public API contract.

## Build

The build expects the recovered artifact under `../build/app-expanded-2`.
Run from this directory:

```powershell
./build.ps1
```

The script compiles all repaired sources, copies migrations/resources, and
writes `target/mist-api-reconstructed.jar`. The recovered class directory is
used only as a compiler reference; with an empty `reconstruction-fallback.txt`
the runtime JAR contains only classes produced by the reconstructed sources.
