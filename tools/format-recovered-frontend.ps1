$ErrorActionPreference = 'Stop'

$root = (Resolve-Path (Join-Path $PSScriptRoot '..')).Path
$assetRoot = Join-Path $root 'frontend\recovered\assets'
if (-not (Test-Path $assetRoot)) {
    throw "Recovered frontend not found: $assetRoot. Run tools\extract-frontend.mjs first."
}

$files = Get-ChildItem $assetRoot -File -Filter '*.js' | ForEach-Object FullName
$arguments = @('--yes', 'prettier@3.6.2', '--write', '--ignore-path', 'NUL', '--parser', 'babel') + [string[]]$files
& npx @arguments
if ($LASTEXITCODE -ne 0) {
    throw "Prettier failed with exit code $LASTEXITCODE"
}

Write-Host "Formatted $($files.Count) recovered JavaScript chunks."
