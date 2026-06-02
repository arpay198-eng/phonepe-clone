# Android Studio Install Guide (Hindi)

## Option A: Android Studio Install (Best for Beginners)

### Step 1: Download
- Website: https://developer.android.com/studio
- Windows users: `.exe` file download karo
- File size: ~1.2 GB

### Step 2: Install
1. Downloaded `.exe` run karo
2. "Next" click karo
3. "Android Virtual Device" checkbox ON rakho
4. Install location choose karo (default theek hai)
5. "Install" click karo
6. ~10-15 min lagenge

### Step 3: First Time Setup
1. Android Studio khole
2. "Standard" installation select karo
3. Theme: "Light" ya "Dark" choose karo
4. ~3-5 GB SDK download hoga (15-20 min)

### Step 4: Open Project
1. "Open" button click karo
2. `E:\Phonepe` folder select karo
3. Wait for Gradle sync (2-3 min first time)

### Step 5: Run
1. Top me green Play button dabao
2. Apna phone USB se connect karo (USB debugging ON) ya emulator create karo
3. App install ho jayegi!

---

## Option B: Command Line Build (No Android Studio)

### Step 1: JDK 17 Install
Download: https://adoptium.net/
- Windows x64 MSI download karo
- Install karo, JAVA_HOME auto set ho jayega

Verify:
```powershell
java -version
```
Output me `17` dikhna chahiye

### Step 2: Android SDK Install
1. Download Command-line tools: https://developer.android.com/studio#command-line-tools-only
2. `commandlinetools-win-*.zip` download karo
3. Extract karo `C:\Android\cmdline-tools\latest\` me
4. `ANDROID_HOME=C:\Android` set karo environment variable me

### Step 3: SDK Packages Install
```powershell
sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"
```

### Step 4: Build APK
PowerShell me `E:\Phonepe` folder me jao:
```powershell
cd E:\Phonepe
.\gradlew.bat assembleDebug
```

APK milegi: `app\build\outputs\apk\debug\app-debug.apk`

### Step 5: Install on Phone
Phone USB se connect karo (USB debugging ON), phir:
```powershell
adb install app\build\outputs\apk\debug\app-debug.apk
```

---

## Option C: GitHub Actions (No Install - Online Build)

### Step 1: GitHub Account banao
- https://github.com par jao

### Step 2: Repository banao
- "New repository" click karo
- Name: `phonepe-clone`
- Public rakho
- Create karo

### Step 3: Code push karo
Git install karo: https://git-scm.com/download/win

PowerShell me:
```powershell
cd E:\Phonepe
git init
git add .
git commit -m "PhonePe clone"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/phonepe-clone.git
git push -u origin main
```

### Step 4: APK Download
1. GitHub repo khole
2. "Actions" tab click karo
3. Latest workflow run click karo
4. Scroll down to "Artifacts"
5. "app-debug" download karo → APK file milegi

### Step 5: Phone me install
- APK file phone me bhejo
- "Install from unknown sources" allow karo
- Install ho jayegi!

---

## Phone Setup (for running APK)

1. Phone me jaao: Settings > About Phone
2. "Build Number" 7 baar tap karo (Developer mode ON)
3. Settings > Developer Options > USB Debugging ON karo
4. Phone ko USB cable se PC se connect karo
5. "Allow USB Debugging" pop-up pe OK karo

---

## Troubleshooting

### Gradle sync fails
- Internet connection check karo
- Project ko delete karke dobara open karo
- File > Invalidate Caches > Restart

### Build error: "SDK not found"
- File > Project Structure > SDK Location
- Android SDK path set karo

### APK install nahi ho rahi
- Phone me purani app uninstall karo
- "Unknown sources" allow karo
- APK file manager se open karo

### Camera (QR Scanner) kaam nahi kar raha
- Phone me camera permission de do
- Real device me test karo (emulator me limited hota hai)
