package com.phonepe.clone.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Language : Screen("language")
    object Login : Screen("login")
    object OtpVerification : Screen("otp/{phone}") {
        fun createRoute(phone: String) = "otp/$phone"
    }
    object ProfileSetup : Screen("profile_setup")
    object BankLink : Screen("bank_link")
    object PinSetup : Screen("pin_setup")
    object Main : Screen("main")

    // Home tabs
    object Home : Screen("home")
    object Stores : Screen("stores")
    object InsuranceTab : Screen("insurance_tab")
    object Wealth : Screen("wealth")
    object History : Screen("history")

    // Detail screens
    object Search : Screen("search")
    object Alerts : Screen("alerts")
    object Profile : Screen("profile")
    object QrScanner : Screen("qr_scanner")
    object PayContact : Screen("pay_contact")
    object PayToPhone : Screen("pay_to_phone")
    object BankTransfer : Screen("bank_transfer")
    object PaymentConfirm : Screen("payment_confirm/{contact}/{amount}") {
        fun createRoute(contact: String, amount: String) = "payment_confirm/$contact/$amount"
    }
    object PaymentSuccess : Screen("payment_success/{amount}") {
        fun createRoute(amount: String) = "payment_success/$amount"
    }
    object MobileRecharge : Screen("recharge")
    object ElectricityBill : Screen("electricity")
    object DthRecharge : Screen("dth")
    object Insurance : Screen("insurance")
    object InsurancePlans : Screen("insurance_plans")
    object MutualFunds : Screen("mutual_funds")
    object Gold : Screen("gold")
    object Offers : Screen("offers")
    object Help : Screen("help")
    object Settings : Screen("settings")
    object BankAccounts : Screen("bank_accounts")
    object UpiId : Screen("upi_id")
    object TransactionDetail : Screen("transaction_detail/{id}") {
        fun createRoute(id: String) = "transaction_detail/$id"
    }
}
