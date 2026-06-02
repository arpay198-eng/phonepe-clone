# ============================================
# PhonePe Clone - GitHub Push Helper
# ============================================
# Yeh script GitHub pe push karta hai
# Pehle: https://github.com par account banao aur naya repo banao
# ============================================

$git = "C:\Program Files\Git\bin\git.exe"
$projectPath = "E:\Phonepe"

Write-Host "============================================" -ForegroundColor Magenta
Write-Host "  PhonePe Clone - GitHub Push Helper" -ForegroundColor Magenta
Write-Host "============================================" -ForegroundColor Magenta
Write-Host ""

# Step 1: Check GitHub username
Write-Host "[1/4] GitHub username enter karo:" -ForegroundColor Yellow
$username = Read-Host "Username"
if ([string]::IsNullOrWhiteSpace($username)) {
    Write-Host "Username required!" -ForegroundColor Red
    exit 1
}

# Step 2: Check repo name
Write-Host "[2/4] Repository name (default: phonepe-clone):" -ForegroundColor Yellow
$repoName = Read-Host "Repo name"
if ([string]::IsNullOrWhiteSpace($repoName)) {
    $repoName = "phonepe-clone"
}

# Step 3: Visibility
Write-Host "[3/4] Repo public ya private? (public/Private) [default: public]:" -ForegroundColor Yellow
$visibility = Read-Host "Visibility"
if ([string]::IsNullOrWhiteSpace($visibility)) {
    $visibility = "public"
}

Write-Host ""
Write-Host "Pehle GitHub par login karke yeh repo manually banao:" -ForegroundColor Cyan
Write-Host "  https://github.com/new" -ForegroundColor White
Write-Host "  - Name: $repoName" -ForegroundColor Gray
Write-Host "  - Visibility: $visibility" -ForegroundColor Gray
Write-Host "  - 'Initialize with README' UNCHECKED rakho" -ForegroundColor Gray
Write-Host ""
Write-Host "Repo bana liya? (y/n)" -ForegroundColor Yellow
$ready = Read-Host "y/n"
if ($ready -ne "y") {
    Write-Host "Cancel. Pehle repo bana lo." -ForegroundColor Red
    exit 1
}

# Step 4: Setup remote and push
Write-Host "[4/4] GitHub par push ho raha hai..." -ForegroundColor Yellow
Write-Host ""

# Use PAT (Personal Access Token) or HTTPS credentials
Write-Host "GitHub password ke liye Personal Access Token (PAT) chahiye." -ForegroundColor Cyan
Write-Host "PAT banane ke liye: https://github.com/settings/tokens/new" -ForegroundColor Gray
Write-Host "  - Note: 'PhonePe Clone Push'" -ForegroundColor Gray
Write-Host "  - Expiration: 30 days" -ForegroundColor Gray
Write-Host "  - Scopes: sirf 'repo' select karo" -ForegroundColor Gray
Write-Host "  - 'Generate token' click karo" -ForegroundColor Gray
Write-Host "  - Token copy karo (ek baar dikhega!)" -ForegroundColor Gray
Write-Host ""

# Configure remote
$remoteUrl = "https://github.com/$username/$repoName.git"
& $git -C $projectPath remote remove origin 2>$null
& $git -C $projectPath remote add origin $remoteUrl 2>&1
& $git -C $projectPath branch -M main 2>&1

# Push
Write-Host "Pushing to $remoteUrl ..." -ForegroundColor Yellow
& $git -C $projectPath push -u origin main 2>&1

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "============================================" -ForegroundColor Green
    Write-Host "  SUCCESS! Code push ho gaya!" -ForegroundColor Green
    Write-Host "============================================" -ForegroundColor Green
    Write-Host ""
    Write-Host "Ab APK build karne ke liye:" -ForegroundColor Cyan
    Write-Host "  1. https://github.com/$username/$repoName khole" -ForegroundColor White
    Write-Host "  2. 'Actions' tab click karo" -ForegroundColor White
    Write-Host "  3. Latest workflow run dekho" -ForegroundColor White
    Write-Host "  4. 5-10 min wait karo" -ForegroundColor White
    Write-Host "  5. 'app-debug' artifact se APK download karo" -ForegroundColor White
    Write-Host ""
} else {
    Write-Host ""
    Write-Host "Push fail hua. Common reasons:" -ForegroundColor Red
    Write-Host "  - GitHub username galat hai" -ForegroundColor Gray
    Write-Host "  - Personal Access Token galat/expired hai" -ForegroundColor Gray
    Write-Host "  - Repo name galat hai" -ForegroundColor Gray
    Write-Host "  - Internet issue" -ForegroundColor Gray
}
