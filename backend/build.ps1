$ErrorActionPreference = 'Stop'

$backendRoot = (Resolve-Path (Join-Path $PSScriptRoot '.')).Path
$projectRoot = (Resolve-Path (Join-Path $backendRoot '..')).Path
$baselineClasses = Join-Path $projectRoot 'build\app-expanded-2\BOOT-INF\classes'
$baselineLib = Join-Path $projectRoot 'build\app-expanded-2\BOOT-INF\lib'
$sourceRoot = Join-Path $backendRoot 'src\main\java'
$resourceRoot = Join-Path $backendRoot 'src\main\resources'
$targetRoot = Join-Path $backendRoot 'target'
$targetClasses = Join-Path $targetRoot 'classes'
$fallbackFile = Join-Path $backendRoot 'reconstruction-fallback.txt'

if (-not (Test-Path $baselineClasses)) {
    throw "Recovered class directory not found: $baselineClasses"
}
if (-not (Test-Path $baselineLib)) {
    throw "Recovered dependency directory not found: $baselineLib"
}

if (Test-Path $targetClasses) {
    Remove-Item -LiteralPath $targetClasses -Recurse -Force
}
New-Item -ItemType Directory -Force -Path $targetClasses | Out-Null

$fallback = @{}
Get-Content $fallbackFile | ForEach-Object {
    $line = $_.Trim()
    if ($line -and -not $line.StartsWith('#')) {
        $fallback[$line.Replace('/', '\')] = $true
    }
}

# Only retain extracted bytecode while a source file is explicitly listed as
# an unfinished fallback. With an empty fallback list the runtime output is
# produced solely from reconstructed sources; the recovered classes below are
# compiler-only references, never runtime inputs.
if ($fallback.Count -gt 0) {
    Copy-Item -Path (Join-Path $baselineClasses '*') -Destination $targetClasses -Recurse -Force
}

$sources = Get-ChildItem $sourceRoot -Recurse -File -Filter '*.java' | Where-Object {
    $relative = $_.FullName.Substring($backendRoot.Length + 1)
    -not $fallback.ContainsKey($relative)
}
$sourceList = Join-Path $targetRoot 'sources.txt'
$utf8NoBom = New-Object System.Text.UTF8Encoding($false)
[System.IO.File]::WriteAllLines($sourceList, [string[]]$sources.FullName, $utf8NoBom)

$libraries = (Get-ChildItem (Join-Path $backendRoot 'lib') -File -Filter '*.jar' | ForEach-Object { $_.FullName })
if (-not $libraries) {
    New-Item -ItemType Directory -Force -Path (Join-Path $backendRoot 'lib') | Out-Null
    Copy-Item -Path (Join-Path $baselineLib '*') -Destination (Join-Path $backendRoot 'lib') -Force
    $libraries = (Get-ChildItem (Join-Path $backendRoot 'lib') -File -Filter '*.jar' | ForEach-Object { $_.FullName })
}
$classpath = (($libraries -join ';') + ';' + $baselineClasses + ';' + $targetClasses)

& javac -encoding UTF-8 -parameters -proc:none -cp $classpath -d $targetClasses ("@" + $sourceList)
if ($LASTEXITCODE -ne 0) {
    throw "Java compilation failed. Fallback classes remain in $targetClasses; inspect the compiler output above."
}

if (Test-Path $resourceRoot) {
    Get-ChildItem $resourceRoot -Recurse -File | Where-Object { $_.Extension -ne '.class' } | ForEach-Object {
        $relative = $_.FullName.Substring($resourceRoot.Length + 1)
        $destination = Join-Path $targetClasses $relative
        New-Item -ItemType Directory -Force -Path (Split-Path $destination) | Out-Null
        Copy-Item -LiteralPath $_.FullName -Destination $destination -Force
    }
}

$manifest = Join-Path $targetRoot 'MANIFEST.MF'
@(
    'Manifest-Version: 1.0'
    'Main-Class: com.una.embyhub.FoamApiApplication'
    ''
) | Set-Content -Encoding ascii $manifest

$jarPath = Join-Path $targetRoot 'mist-api-reconstructed.jar'
& jar cfm $jarPath $manifest -C $targetClasses .
if ($LASTEXITCODE -ne 0) {
    throw "JAR packaging failed."
}

Write-Host "Built $jarPath"
Write-Host "Sources compiled: $($sources.Count)"
Write-Host "Explicit bytecode fallbacks: $($fallback.Count)"
