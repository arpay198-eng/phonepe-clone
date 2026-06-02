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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.phonepe.clone.ui.components.CircularAvatar
import com.phonepe.clone.ui.navigation.Screen
import com.phonepe.clone.ui.theme.*
import com.phonepe.clone.viewmodel.AppViewModel

@Composable
fun ProfileScreen(navController: NavController) {
    val viewModel: AppViewModel = viewModel()
    val user by viewModel.user.collectAsState()
    val banks by viewModel.banks.collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F0FA))) {
        // Header
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
                    Text("Profile", color = White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // User card
            item {
                Surface(shape = RoundedCornerShape(12.dp), color = White, modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularAvatar(name = user.name, size = 60, backgroundColor = PurplePrimary)
                        Spacer(Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(user.name, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
                            Text("+91 ${user.phone}", color = Color.Gray, fontSize = 13.sp)
                            Spacer(Modifier.height(4.dp))
                            Box(
                                modifier = Modifier.clip(RoundedCornerShape(4.dp))
                                    .background(GreenSuccess.copy(alpha = 0.15f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text("KYC Complete", color = GreenSuccess, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        Icon(Icons.Filled.Edit, contentDescription = null, tint = PurplePrimary)
                    }
                }
            }

            // UPI & Bank section
            item {
                Text("PAYMENTS", color = Color.Gray, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 8.dp))
            }
            item {
                Surface(shape = RoundedCornerShape(12.dp), color = White) {
                    Column {
                        ProfileItem("UPI ID", user.upiId, Icons.Filled.AccountBalanceWallet) {
                            navController.navigate(Screen.UpiId.route)
                        }
                        Divider(color = Color.LightGray.copy(alpha = 0.3f), modifier = Modifier.padding(horizontal = 16.dp))
                        ProfileItem("Bank Accounts", "${banks.size} linked", Icons.Filled.AccountBalance) {
                            navController.navigate(Screen.BankAccounts.route)
                        }
                    }
                }
            }

            // Settings
            item {
                Spacer(Modifier.height(4.dp))
                Text("PREFERENCES", color = Color.Gray, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 8.dp))
            }
            item {
                Surface(shape = RoundedCornerShape(12.dp), color = White) {
                    Column {
                        ProfileItem("Language", "English", Icons.Filled.Language) {}
                        Divider(color = Color.LightGray.copy(alpha = 0.3f), modifier = Modifier.padding(horizontal = 16.dp))
                        ProfileItem("Notifications", "Manage", Icons.Filled.Notifications) {}
                        Divider(color = Color.LightGray.copy(alpha = 0.3f), modifier = Modifier.padding(horizontal = 16.dp))
                        ProfileItem("Settings", "Privacy, Security", Icons.Filled.Settings) {
                            navController.navigate(Screen.Settings.route)
                        }
                    }
                }
            }

            // Support
            item {
                Spacer(Modifier.height(4.dp))
                Text("SUPPORT", color = Color.Gray, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 8.dp))
            }
            item {
                Surface(shape = RoundedCornerShape(12.dp), color = White) {
                    Column {
                        ProfileItem("Help & Support", "24x7 available", Icons.Filled.Help) {
                            navController.navigate(Screen.Help.route)
                        }
                        Divider(color = Color.LightGray.copy(alpha = 0.3f), modifier = Modifier.padding(horizontal = 16.dp))
                        ProfileItem("Refer & Earn", "Get ₹100", Icons.Filled.CardGiftcard) {}
                        Divider(color = Color.LightGray.copy(alpha = 0.3f), modifier = Modifier.padding(horizontal = 16.dp))
                        ProfileItem("About", "v1.0.0", Icons.Filled.Info) {}
                    }
                }
            }

            item {
                Spacer(Modifier.height(8.dp))
                TextButton(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Logout", color = RedDebit, fontWeight = FontWeight.Bold)
                }
            }
            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun ProfileItem(title: String, subtitle: String, icon: ImageVector, onClick: () -> Unit) {
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
            Icon(imageVector = icon, contentDescription = null, tint = PurplePrimary, modifier = Modifier.size(18.dp))
        }
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color.Black)
            Text(subtitle, color = Color.Gray, fontSize = 11.sp)
        }
        Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = Color.Gray)
    }
}
