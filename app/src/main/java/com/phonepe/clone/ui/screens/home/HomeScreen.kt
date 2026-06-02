package com.phonepe.clone.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items as lazyItems
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.phonepe.clone.data.model.Service
import com.phonepe.clone.ui.components.CircularAvatar
import com.phonepe.clone.ui.components.QuickActionCard
import com.phonepe.clone.ui.components.ServiceGridItem
import com.phonepe.clone.ui.navigation.Screen
import com.phonepe.clone.ui.theme.*
import com.phonepe.clone.viewmodel.AppViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: AppViewModel = viewModel()
    val user by viewModel.user.collectAsState()
    val banks by viewModel.banks.collectAsState()
    val quickActions by viewModel.quickActions.collectAsState()
    val allServices by viewModel.allServices.collectAsState()
    val offers by viewModel.offers.collectAsState()

    var showBalance by remember { mutableStateOf(false) }
    val totalBalance = banks.sumOf { it.balance }
    val formattedBalance = NumberFormat.getNumberInstance(Locale("en", "IN")).format(totalBalance)

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F0FA))) {
        // Purple header background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(PurplePrimary, PurpleDark)
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // Top bar
            HomeTopBar(
                userName = user.name,
                onProfileClick = { navController.navigate(Screen.Profile.route) }
            )

            // Balance card
            BalanceCard(
                balance = formattedBalance,
                bankName = banks.first().bankName,
                upiId = user.upiId,
                showBalance = showBalance,
                onToggleBalance = { showBalance = !showBalance }
            )

            Spacer(Modifier.height(8.dp))

            // Quick action row
            QuickActionRow(
                onQrClick = { navController.navigate(Screen.QrScanner.route) },
                onPayClick = { navController.navigate(Screen.PayContact.route) },
                onBankClick = { navController.navigate(Screen.BankTransfer.route) }
            )

            // Scrollable content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(Modifier.height(12.dp))

                // Money transfers section
                SectionHeader(title = "Money Transfers", onSeeAll = {})
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(4) { index ->
                        when (index) {
                            0 -> QuickActionCard(
                                title = "To Mobile\nNumber",
                                icon = Icons.Filled.Phone,
                                backgroundColor = PurplePrimary,
                                onClick = { navController.navigate(Screen.PayToPhone.route) }
                            )
                            1 -> QuickActionCard(
                                title = "To Bank/\nUPI ID",
                                icon = Icons.Filled.AccountBalance,
                                backgroundColor = Color(0xFF00B8A9),
                                onClick = { navController.navigate(Screen.BankTransfer.route) }
                            )
                            2 -> QuickActionCard(
                                title = "Self\nTransfer",
                                icon = Icons.Filled.SwapHoriz,
                                backgroundColor = Color(0xFFFF6B35),
                                onClick = {}
                            )
                            3 -> QuickActionCard(
                                title = "Check\nBalance",
                                icon = Icons.Filled.AccountBalanceWallet,
                                backgroundColor = Color(0xFF2196F3),
                                onClick = {}
                            )
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))

                // Recharge & Bills
                SectionHeader(title = "Recharge & Bills", onSeeAll = { navController.navigate(Screen.MobileRecharge.route) })
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(4) { index ->
                        val (title, icon, color, route) = when (index) {
                            0 -> Quadruple("Mobile\nRecharge", Icons.Filled.PhoneAndroid, PurplePrimary, Screen.MobileRecharge.route)
                            1 -> Quadruple("Electricity", Icons.Filled.Bolt, Color(0xFFFF9800), Screen.ElectricityBill.route)
                            2 -> Quadruple("DTH", Icons.Filled.Tv, Color(0xFF2196F3), Screen.DthRecharge.route)
                            else -> Quadruple("Credit\nCard", Icons.Filled.CreditCard, Color(0xFFE91E63), Screen.MobileRecharge.route)
                        }
                        QuickActionCard(
                            title = title,
                            icon = icon,
                            backgroundColor = color,
                            onClick = { navController.navigate(route) }
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

                // All services grid
                ServicesGrid(
                    services = allServices.take(12),
                    onServiceClick = { service ->
                        when (service.route) {
                            "recharge" -> navController.navigate(Screen.MobileRecharge.route)
                            "electricity" -> navController.navigate(Screen.ElectricityBill.route)
                            "dth" -> navController.navigate(Screen.DthRecharge.route)
                            "insurance" -> navController.navigate(Screen.Insurance.route)
                            "wealth", "mutual_funds" -> navController.navigate(Screen.MutualFunds.route)
                            "gold" -> navController.navigate(Screen.Gold.route)
                            else -> { }
                        }
                    }
                )

                Spacer(Modifier.height(20.dp))

                // Offers carousel
                SectionHeader(title = "Offers for you", onSeeAll = {})
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    lazyItems(offers.take(5)) { offer ->
                        OfferCard(
                            title = offer.title,
                            subtitle = offer.subtitle,
                            store = offer.store,
                            cashback = offer.cashback,
                            color = offer.color
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))

                // Promotional banner
                PromoBanner()
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun HomeTopBar(userName: String, onProfileClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularAvatar(
            name = userName,
            size = 38,
            backgroundColor = White,
            textColor = PurplePrimary,
            onClick = onProfileClick
        )
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text("Hi, $userName", color = White, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            Text(userName.split(" ").first() + " 👋", color = White.copy(alpha = 0.85f), fontSize = 12.sp)
        }
        IconButton(onClick = {}) {
            Icon(Icons.Filled.Notifications, contentDescription = "Notifications", tint = White)
        }
        IconButton(onClick = {}) {
            Icon(Icons.Filled.Search, contentDescription = "Search", tint = White)
        }
    }
}

@Composable
private fun BalanceCard(
    balance: String,
    bankName: String,
    upiId: String,
    showBalance: Boolean,
    onToggleBalance: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        color = White,
        shadowElevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(PurplePrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Text("₹", color = White, fontSize = 20.sp, fontWeight = FontWeight.Black)
                }
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("UPI Linked", color = Color.Gray, fontSize = 11.sp)
                    Text(upiId, color = PurpleDark, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                }
                IconButton(onClick = onToggleBalance) {
                    Icon(
                        if (showBalance) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        contentDescription = null,
                        tint = PurplePrimary
                    )
                }
            }
            Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color.LightGray.copy(alpha = 0.3f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Total Balance", color = Color.Gray, fontSize = 12.sp)
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = if (showBalance) "₹$balance" else "₹ • • • • •",
                        color = PurpleDark,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text("$bankName • Primary", color = Color.Gray, fontSize = 11.sp)
                }
                Surface(
                    color = PurplePrimary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        "View All",
                        color = PurplePrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickActionRow(
    onQrClick: () -> Unit,
    onPayClick: () -> Unit,
    onBankClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        QuickActionButton(
            modifier = Modifier.weight(1f),
            title = "Scan QR",
            icon = Icons.Filled.QrCodeScanner,
            backgroundColor = PurplePrimary,
            onClick = onQrClick
        )
        QuickActionButton(
            modifier = Modifier.weight(1f),
            title = "Pay",
            icon = Icons.Filled.Send,
            backgroundColor = Color(0xFF00B8A9),
            onClick = onPayClick
        )
        QuickActionButton(
            modifier = Modifier.weight(1f),
            title = "To Bank",
            icon = Icons.Filled.AccountBalance,
            backgroundColor = Color(0xFFFF6B35),
            onClick = onBankClick
        )
    }
}

@Composable
private fun QuickActionButton(
    title: String,
    icon: ImageVector,
    backgroundColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        color = backgroundColor,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = White, modifier = Modifier.size(24.dp))
            Spacer(Modifier.height(4.dp))
            Text(title, color = White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun SectionHeader(title: String, onSeeAll: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = PurpleDark,
            modifier = Modifier.weight(1f)
        )
        TextButton(onClick = onSeeAll) {
            Text("See All", color = PurplePrimary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun ServicesGrid(
    services: List<Service>,
    onServiceClick: (Service) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(White)
            .padding(vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            services.take(4).forEach { service ->
                ServiceGridItem(
                    service = service,
                    onClick = { onServiceClick(service) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            services.drop(4).take(4).forEach { service ->
                ServiceGridItem(
                    service = service,
                    onClick = { onServiceClick(service) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            services.drop(8).take(4).forEach { service ->
                ServiceGridItem(
                    service = service,
                    onClick = { onServiceClick(service) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun OfferCard(
    title: String,
    subtitle: String,
    store: String,
    cashback: String,
    color: Color
) {
    Surface(
        modifier = Modifier
            .width(220.dp)
            .clip(RoundedCornerShape(12.dp)),
        color = White,
        shadowElevation = 2.dp
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(color),
                contentAlignment = Alignment.CenterStart
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(store, color = White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("Cashback $cashback", color = White.copy(alpha = 0.9f), fontSize = 11.sp)
                }
            }
            Column(modifier = Modifier.padding(12.dp)) {
                Text(title, color = PurpleDark, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, maxLines = 1)
                Spacer(Modifier.height(2.dp))
                Text(subtitle, color = Color.Gray, fontSize = 11.sp, maxLines = 1)
            }
        }
    }
}

@Composable
private fun PromoBanner() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp)),
        color = Color(0xFFFFE0B2)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFF9800)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.LocalOffer, contentDescription = null, tint = White)
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("Refer & Earn ₹100", fontWeight = FontWeight.Bold, color = PurpleDark, fontSize = 14.sp)
                Text("Invite friends to PhonePe", color = Color.Gray, fontSize = 12.sp)
            }
            Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = PurpleDark)
        }
    }
}

data class Quadruple<A, B, C, D>(val a: A, val b: B, val c: C, val d: D)
