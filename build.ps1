# PhonePe Clone - Build & Install Helper Script
# PowerShell me run karo: .\build.ps1

param(
    [switch]$Install,    # APK phone me install karo
    [switch]$Clean,      # Clean build
    [switch]$Help        # Help dikhao
)

if ($Help) {
    Write-Host "PhonePe Clone Build Script" -ForegroundColor Magenta
    Write-Host ""
    Write-Host "Usage:" -ForegroundColor Cyan
    Write-Host "  .\build.ps1              # Build debug APK"
    Write-Host "  .\build.ps1 -Clean       # Clean aur build"
    Write-Host "  .\build.ps1 -Install     # Build aur phone me install"
    Write-Host "  .\build.ps1 -Help        # Yeh help"
    exit 0
}

$ErrorActionPreference = "Stop"
$apkPath = "app\build\outputs\apk\debug\app-debug.apk"

Write-Host "PhonePe Clone - Build Script" -ForegroundColor Magenta
Write-Host "================================" -ForegroundColor Magenta
Write-Host ""

# Check Java
Write-Host "[1/4] Checking Java..." -ForegroundColor Yellow
try {
    $javaVersion = java -version 2>&1 | Select-String "version"
    Write-Host "  Found: $javaVersion" -ForegroundColor Green
} catch {
    Write-Host "  ERROR: Java not found!" -ForegroundColor Red
    Write-Host "  Install JDK 17 from: https://adoptium.net/" -ForegroundColor Yellow
    exit 1
}

# Check Android SDK
Write-Host "[2/4] Checking Android SDK..." -ForegroundColor Yellow
if ($env:ANDROID_HOME -or $env:ANDROID_SDK_ROOT) {
    Write-Host "  ANDROID_HOME: $($env:ANDROID_HOME)$($env:ANDROID_SDK_ROOT)" -ForegroundColor Green
} else {
    Write-Host "  WARNING: ANDROID_HOME not set. Build may fail." -ForegroundColor Yellow
    Write-Host "  Set ANDROID_HOME to your Android SDK location" -ForegroundColor Yellow
}

# Clean (optional)
if ($Clean) {
    Write-Host "[3/4] Cleaning..." -ForegroundColor Yellow
    .\gradlew.bat clean
}

# Build
Write-Host "[3/4] Building APK..." -ForegroundColor Yellow
Write-Host "  This may take 2-5 minutes on first run..." -ForegroundColor Gray
.\gradlew.bat assembleDebug

if ($LASTEXITCODE -ne 0) {
    Write-Host "  BUILD FAILED!" -ForegroundColor Red
    exit 1
}

# Verify APK
Write-Host "[4/4] Checking APK..." -ForegroundColor Yellow
if (Test-Path $apkPath) {
    $size = (Get-Item $apkPath).Length / 1MB
    Write-Host "  APK built successfully!" -ForegroundColor Green
    Write-Host "  Path: $apkPath" -ForegroundColor Cyan
    Write-Host "  Size: $($size.ToString('0.00')) MB" -ForegroundColor Cyan
} else {
    Write-Host "  APK not found at expected location!" -ForegroundColor Red
    exit 1
}

# Install (optional)
if ($Install) {
    Write-Host ""
    Write-Host "Installing on connected device..." -ForegroundColor Yellow
    $devices = adb devices
    if ($devices -match "device$") {
        adb install -r $apkPath
        if ($LASTEXITCODE -eq 0) {
            Write-Host "  Installed successfully!" -ForegroundColor Green
        } else {
            Write-Host "  Install failed!" -ForegroundColor Red
        }
    } else {
        Write-Host "  No device connected. Connect phone via USB." -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "Build complete!" -ForegroundColor Green
