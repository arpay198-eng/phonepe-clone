package com.phonepe.clone.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.phonepe.clone.ui.theme.*

@Composable
fun SettingsScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F0FA))) {
        Surface(color = PurplePrimary) {
            Column {
                Spacer(Modifier.statusBarsPadding())
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null, tint = White)
                    }
                    Text("Settings", color = White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Surface(shape = RoundedCornerShape(12.dp), color = White) {
                    Column {
                        SettingItem("App Lock", "Secure with PIN", Icons.Filled.Fingerprint, true) {}
                        Divider(color = Color.LightGray.copy(alpha = 0.3f), modifier = Modifier.padding(horizontal = 16.dp))
                        SettingItem("Biometric Auth", "Use fingerprint", Icons.Filled.Fingerprint, true) {}
                        Divider(color = Color.LightGray.copy(alpha = 0.3f), modifier = Modifier.padding(horizontal = 16.dp))
                        SettingItem("Transaction Limit", "₹1,00,000 per day", Icons.Filled.AccountBalanceWallet, false) {}
                        Divider(color = Color.LightGray.copy(alpha = 0.3f), modifier = Modifier.padding(horizontal = 16.dp))
                        SettingItem("UPI PIN", "Change UPI PIN", Icons.Filled.Lock, false) {}
                    }
                }
            }

            item {
                Surface(shape = RoundedCornerShape(12.dp), color = White) {
                    Column {
                        SettingItem("Payment Methods", "Manage banks & cards", Icons.Filled.CreditCard, false) {}
                        Divider(color = Color.LightGray.copy(alpha = 0.3f), modifier = Modifier.padding(horizontal = 16.dp))
                        SettingItem("Auto-pay", "Recurring payments", Icons.Filled.Autorenew, true) {}
                        Divider(color = Color.LightGray.copy(alpha = 0.3f), modifier = Modifier.padding(horizontal = 16.dp))
                        SettingItem("Default Bank", "HDFC Bank", Icons.Filled.AccountBalance, false) {}
                    }
                }
            }

            item {
                Surface(shape = RoundedCornerShape(12.dp), color = White) {
                    Column {
                        SettingItem("Notifications", "Manage alerts", Icons.Filled.Notifications, true) {}
                        Divider(color = Color.LightGray.copy(alpha = 0.3f), modifier = Modifier.padding(horizontal = 16.dp))
                        SettingItem("Language", "English", Icons.Filled.Language, false) {}
                        Divider(color = Color.LightGray.copy(alpha = 0.3f), modifier = Modifier.padding(horizontal = 16.dp))
                        SettingItem("Dark Mode", "System default", Icons.Filled.DarkMode, false) {}
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingItem(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector, hasSwitch: Boolean, onClick: () -> Unit) {
    var checked by remember { mutableStateOf(true) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(36.dp).clip(RoundedCornerShape(8.dp)).background(PurplePrimary.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = PurplePrimary, modifier = Modifier.size(18.dp))
        }
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color.Black)
            Text(subtitle, color = Color.Gray, fontSize = 11.sp)
        }
        if (hasSwitch) {
            Switch(checked = checked, onCheckedChange = { checked = it }, colors = SwitchDefaults.colors(checkedThumbColor = White, checkedTrackColor = PurplePrimary))
        } else {
            Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = Color.Gray)
        }
    }
}
