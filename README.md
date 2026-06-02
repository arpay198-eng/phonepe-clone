# PhonePe-Inspired Clone App

A modern Android application built with Kotlin & Jetpack Compose that replicates the PhonePe mobile app's UI/UX with full feature set.

## Tech Stack

- **Language**: Kotlin 1.9.22
- **UI Framework**: Jetpack Compose
- **Min SDK**: 24 (Android 7.0+)
- **Target SDK**: 34 (Android 14)
- **Navigation**: Jetpack Navigation Compose
- **Architecture**: MVVM
- **State Management**: StateFlow

## Features Implemented

### 1. Onboarding Flow
- Splash Screen with logo animation
- Onboarding screens with auto-scroll
- Language selection (8 Indian languages)
- Phone number + OTP login
- Bank account linking (12+ banks)
- UPI PIN setup with numeric keypad

### 2. Home Screen
- Bank balance card with show/hide
- Quick actions (Scan QR, Pay, To Bank)
- Money transfers carousel
- Recharge & Bills shortcuts
- All services grid (16+ services)
- Offers carousel
- Promo banners
- Bottom navigation (5 tabs)

### 3. Payments
- QR Code Scanner with camera permission
- Pay via Contact list
- Pay to Mobile Number
- Bank/UPI ID transfer
- Payment confirmation with amount entry
- UPI PIN entry overlay
- Payment success screen with confetti

### 4. Recharge & Bills
- Mobile Recharge (Operator detection, plan selection)
- Electricity Bill Payment
- DTH Recharge
- Multi-provider support

### 5. History
- Transaction list with category icons
- Filter (All/Sent/Received/Pending)
- Date grouping
- Transaction details screen
- Summary cards (Money sent/received)

### 6. Profile
- User profile with KYC status
- UPI ID management
- Bank accounts list
- Settings (Security, Payments, Preferences)
- Help & Support
- FAQ section

### 7. Insurance
- 4 categories (Health, Life, Vehicle, Travel)
- Featured plans list
- Plan details with features

### 8. Wealth
- Portfolio overview with returns
- Mutual Funds screen
- Digital Gold with live rates
- Quick action chips

### 9. Stores
- Top offers carousel
- Category filters
- Store grid (3-column layout)
- 20+ stores

## Setup Instructions

1. Open this folder in **Android Studio** (Hedgehog | 2023.1.1 or newer)
2. Wait for Gradle sync to complete
3. Connect an Android device or start an emulator
4. Click Run

## Project Structure

```
app/src/main/java/com/phonepe/clone/
├── MainActivity.kt              # Entry point
├── data/
│   ├── model/                   # Data models (User, Transaction, etc.)
│   ├── repository/              # AppRepository
│   └── mock/                    # Mock data (banks, contacts, transactions)
├── ui/
│   ├── theme/                   # Colors, Typography, Theme
│   ├── navigation/              # NavGraph, Screen routes
│   ├── components/              # Reusable UI components
│   └── screens/
│       ├── splash/              # Splash screen
│       ├── onboarding/          # Onboarding & Language
│       ├── login/               # Login & OTP
│       ├── pin/                 # UPI PIN setup
│       ├── bank/                # Bank linking
│       ├── home/                # Home screen
│       ├── payment/             # Pay, QR, Confirm, Success
│       ├── qr/                  # QR Scanner
│       ├── recharge/            # Mobile, Electricity, DTH
│       ├── history/             # Transactions
│       ├── profile/             # Profile, Settings, Help
│       ├── insurance/           # Insurance
│       ├── wealth/              # Mutual Funds, Gold
│       └── stores/              # Stores & Offers
└── viewmodel/                   # AppViewModel
```

## Screens: 30+

1. Splash
2. Onboarding (3 pages)
3. Language Selection
4. Login
5. OTP Verification
6. Bank Linking
7. UPI PIN Setup
8. Home
9. Profile
10. Bank Accounts
11. UPI IDs
12. Settings
13. Help & Support
14. QR Scanner
15. Pay (Contacts)
16. Pay to Phone
17. Bank/UPI Transfer
18. Payment Confirmation
19. Payment Success
20. Mobile Recharge
21. Electricity Bill
22. DTH Recharge
23. Transaction History
24. Transaction Detail
25. Insurance
26. Insurance Plans
27. Wealth
28. Mutual Funds
29. Digital Gold
30. Stores

## Theme

- Primary: PhonePe Purple (#5F259F)
- Dark Purple: #4A1B7A
- Light Purple: #7B3FCB
- Status bar matches primary

## Notes

- This is a **UI/UX clone** for learning purposes
- No real UPI/banking integration
- All data is mocked in `data/mock/MockData.kt`
- Camera permission required for QR scanner
