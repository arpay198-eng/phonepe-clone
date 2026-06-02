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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.phonepe.clone.ui.theme.*
import com.phonepe.clone.viewmodel.AppViewModel

@Composable
fun BankAccountsScreen(navController: NavController) {
    val viewModel: AppViewModel = viewModel()
    val banks by viewModel.banks.collectAsState()

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
                    Text("Bank Accounts", color = White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(banks.size) { index ->
                val bank = banks[index]
                Surface(shape = RoundedCornerShape(12.dp), color = White) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(50.dp).clip(RoundedCornerShape(8.dp)).background(bank.color),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Filled.AccountBalance, contentDescription = null, tint = White, modifier = Modifier.size(26.dp))
                        }
                        Spacer(Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(bank.bankName, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.Black)
                                if (bank.isPrimary) {
                                    Spacer(Modifier.width(6.dp))
                                    Box(
                                        modifier = Modifier.clip(RoundedCornerShape(4.dp))
                                            .background(PurplePrimary)
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text("PRIMARY", color = White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                            Text("A/c ${bank.accountNumber}", color = Color.Gray, fontSize = 12.sp)
                            Text("UPI: ${bank.upiId}", color = Color.Gray, fontSize = 11.sp)
                            Text("Balance: ₹${String.format("%,.2f", bank.balance)}", color = GreenSuccess, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                        .clip(RoundedCornerShape(12.dp)),
                    color = White,
                    border = androidx.compose.foundation.BorderStroke(1.dp, PurplePrimary)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(40.dp).clip(CircleShape).background(PurplePrimary.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = null, tint = PurplePrimary)
                        }
                        Spacer(Modifier.width(12.dp))
                        Text("Add New Bank Account", color = PurplePrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    }
                }
            }
            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}
