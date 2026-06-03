package com.phonepe.clone.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.phonepe.clone.ui.theme.TextSecondary
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.phonepe.clone.ui.components.BottomNavBar
import com.phonepe.clone.ui.screens.profile.BankAccountsScreen
import com.phonepe.clone.ui.screens.bank.BankLinkScreen
import com.phonepe.clone.ui.screens.history.HistoryScreen
import com.phonepe.clone.ui.screens.history.TransactionDetailScreen
import com.phonepe.clone.ui.screens.home.HomeScreen
import com.phonepe.clone.ui.screens.insurance.InsurancePlansScreen
import com.phonepe.clone.ui.screens.insurance.InsuranceScreen
import com.phonepe.clone.ui.screens.login.LoginScreen
import com.phonepe.clone.ui.screens.login.OtpScreen
import com.phonepe.clone.ui.screens.onboarding.LanguageScreen
import com.phonepe.clone.ui.screens.onboarding.OnboardingScreen
import com.phonepe.clone.ui.screens.payment.BankTransferScreen
import com.phonepe.clone.ui.screens.payment.PayToPhoneScreen
import com.phonepe.clone.ui.screens.payment.PaymentConfirmScreen
import com.phonepe.clone.ui.screens.payment.PaymentSuccessScreen
import com.phonepe.clone.ui.screens.payment.PayContactScreen
import com.phonepe.clone.ui.screens.pin.PinSetupScreen
import com.phonepe.clone.ui.screens.profile.HelpScreen
import com.phonepe.clone.ui.screens.profile.ProfileScreen
import com.phonepe.clone.ui.screens.profile.SettingsScreen
import com.phonepe.clone.ui.screens.profile.UpiIdScreen
import com.phonepe.clone.ui.screens.qr.QrScannerScreen
import com.phonepe.clone.ui.screens.recharge.DthRechargeScreen
import com.phonepe.clone.ui.screens.recharge.ElectricityBillScreen
import com.phonepe.clone.ui.screens.recharge.MobileRechargeScreen
import com.phonepe.clone.ui.screens.splash.SplashScreen
import com.phonepe.clone.ui.screens.stores.StoresScreen
import com.phonepe.clone.ui.screens.wealth.MutualFundsScreen
import com.phonepe.clone.ui.screens.wealth.GoldScreen
import com.phonepe.clone.ui.screens.wealth.WealthScreen

@Composable
fun NavGraph(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.Search.route,
        Screen.Alerts.route,
        Screen.History.route,
        Screen.Stores.route,
        Screen.InsuranceTab.route,
        Screen.Wealth.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(navController)
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            NavHost(
                navController = navController,
                startDestination = Screen.Splash.route
            ) {
                composable(Screen.Splash.route) { SplashScreen(navController) }
                composable(Screen.Onboarding.route) { OnboardingScreen(navController) }
                composable(Screen.Language.route) { LanguageScreen(navController) }
                composable(Screen.Login.route) { LoginScreen(navController) }
                composable(
                    route = Screen.OtpVerification.route,
                    arguments = listOf(navArgument("phone") { type = NavType.StringType })
                ) { entry ->
                    OtpScreen(navController, entry.arguments?.getString("phone") ?: "")
                }
                composable(Screen.ProfileSetup.route) { PinSetupScreen(navController) }
                composable(Screen.BankLink.route) { BankLinkScreen(navController) }
                composable(Screen.PinSetup.route) { PinSetupScreen(navController) }

                composable(Screen.Search.route) { SearchPlaceholder(navController) }
                composable(Screen.Alerts.route) { AlertsPlaceholder(navController) }
                composable(Screen.Home.route) { HomeScreen(navController) }
                composable(Screen.Stores.route) { StoresScreen(navController) }
                composable(Screen.InsuranceTab.route) { InsuranceScreen(navController) }
                composable(Screen.Wealth.route) { WealthScreen(navController) }
                composable(Screen.History.route) { HistoryScreen(navController) }

                composable(Screen.Profile.route) { ProfileScreen(navController) }
                composable(Screen.QrScanner.route) { QrScannerScreen(navController) }
                composable(Screen.PayContact.route) { PayContactScreen(navController) }
                composable(Screen.PayToPhone.route) { PayToPhoneScreen(navController) }
                composable(Screen.BankTransfer.route) { BankTransferScreen(navController) }
                composable(
                    route = Screen.PaymentConfirm.route,
                    arguments = listOf(
                        navArgument("contact") { type = NavType.StringType },
                        navArgument("amount") { type = NavType.StringType }
                    )
                ) { entry ->
                    PaymentConfirmScreen(
                        navController,
                        entry.arguments?.getString("contact") ?: "",
                        entry.arguments?.getString("amount") ?: "0"
                    )
                }
                composable(
                    route = Screen.PaymentSuccess.route,
                    arguments = listOf(navArgument("amount") { type = NavType.StringType })
                ) { entry ->
                    PaymentSuccessScreen(navController, entry.arguments?.getString("amount") ?: "0")
                }
                composable(Screen.MobileRecharge.route) { MobileRechargeScreen(navController) }
                composable(Screen.ElectricityBill.route) { ElectricityBillScreen(navController) }
                composable(Screen.DthRecharge.route) { DthRechargeScreen(navController) }
                composable(Screen.Insurance.route) { InsuranceScreen(navController) }
                composable(Screen.InsurancePlans.route) { InsurancePlansScreen(navController) }
                composable(Screen.MutualFunds.route) { MutualFundsScreen(navController) }
                composable(Screen.Gold.route) { GoldScreen(navController) }
                composable(Screen.Help.route) { HelpScreen(navController) }
                composable(Screen.Settings.route) { SettingsScreen(navController) }
                composable(Screen.BankAccounts.route) { BankAccountsScreen(navController) }
                composable(Screen.UpiId.route) { UpiIdScreen(navController) }
                composable(
                    route = Screen.TransactionDetail.route,
                    arguments = listOf(navArgument("id") { type = NavType.StringType })
                ) { entry ->
                    TransactionDetailScreen(navController, entry.arguments?.getString("id") ?: "")
                }
            }
        }
    }
}

@Composable
fun SearchPlaceholder(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text("Search", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
    }
}

@Composable
fun AlertsPlaceholder(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text("Alerts", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
    }
}
