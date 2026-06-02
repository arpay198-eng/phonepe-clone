package com.phonepe.clone.ui.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.phonepe.clone.data.model.TransactionStatus
import com.phonepe.clone.data.model.TransactionType
import com.phonepe.clone.ui.components.CircularAvatar
import com.phonepe.clone.ui.theme.*
import com.phonepe.clone.viewmodel.AppViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun TransactionDetailScreen(navController: NavController, id: String) {
    val viewModel: AppViewModel = viewModel()
    val transactions by viewModel.transactions.collectAsState()
    val transaction = transactions.find { it.id == id }

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
                    Text("Transaction Details", color = White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        if (transaction != null) {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(16.dp))
                Box(
                    modifier = Modifier.size(80.dp).clip(CircleShape).background(PurplePrimary.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        if (transaction.type == TransactionType.CREDIT) Icons.Filled.CallReceived else Icons.Filled.CallMade,
                        contentDescription = null,
                        tint = PurplePrimary,
                        modifier = Modifier.size(36.dp)
                    )
                }
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "${if (transaction.type == TransactionType.CREDIT) "+" else "-"}₹${NumberFormat.getNumberInstance(Locale("en", "IN")).format(transaction.amount)}",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = PurpleDark
                )
                Text(transaction.title, fontSize = 14.sp, color = Color.Gray)
                Spacer(Modifier.height(8.dp))
                if (transaction.status == TransactionStatus.SUCCESS) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(GreenSuccess.copy(alpha = 0.15f))
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text("✓ Successful", color = GreenSuccess, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }

                Spacer(Modifier.height(24.dp))

                Surface(shape = RoundedCornerShape(12.dp), color = White, modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        DetailRow("Transaction ID", transaction.reference ?: "N/A")
                        Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color.LightGray.copy(alpha = 0.3f))
                        DetailRow("Date & Time", "${transaction.date}, ${transaction.time}")
                        Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color.LightGray.copy(alpha = 0.3f))
                        DetailRow("Payment Method", "HDFC Bank • XXXX1234")
                        Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color.LightGray.copy(alpha = 0.3f))
                        DetailRow("Category", transaction.category.name.replace("_", " "))
                    }
                }

                Spacer(Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick = { /* raise dispute */ },
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, PurplePrimary)
                    ) {
                        Text("Need Help?", color = PurplePrimary, fontSize = 13.sp)
                    }
                    OutlinedButton(
                        onClick = { /* repeat */ },
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, PurplePrimary)
                    ) {
                        Text("Pay Again", color = PurplePrimary, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(label, color = Color.Gray, fontSize = 12.sp, modifier = Modifier.weight(1f))
        Text(value, color = Color.Black, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
    }
}
