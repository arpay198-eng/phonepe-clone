package com.phonepe.clone.ui.screens.recharge

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.phonepe.clone.data.model.BillProvider
import com.phonepe.clone.ui.navigation.Screen
import com.phonepe.clone.ui.theme.*

@Composable
fun ElectricityBillScreen(navController: NavController) {
    val providers = listOf(
        BillProvider("1", "BSES Rajdhani", "Electricity", "B", Color(0xFF1976D2)),
        BillProvider("2", "BSES Yamuna", "Electricity", "B", Color(0xFF1976D2)),
        BillProvider("3", "Tata Power", "Electricity", "T", Color(0xFFE65100)),
        BillProvider("4", "Adani Electricity", "Electricity", "A", Color(0xFF1565C0)),
        BillProvider("5", "BESCOM", "Electricity", "B", Color(0xFF388E3C))
    )
    var selectedProvider by remember { mutableStateOf<BillProvider?>(null) }
    var consumerNumber by remember { mutableStateOf("") }

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
                    Text("Electricity Bill", color = White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Surface(shape = RoundedCornerShape(12.dp), color = White) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Enter Consumer Number", fontSize = 12.sp, color = Color.Gray)
                        Spacer(Modifier.height(6.dp))
                        BasicTextField(
                            value = consumerNumber,
                            onValueChange = { consumerNumber = it },
                            textStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.Black),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            item {
                Text("Select your provider", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = PurpleDark)
            }

            items(providers) { provider ->
                ProviderCard(
                    provider = provider,
                    isSelected = selectedProvider == provider,
                    onClick = { selectedProvider = provider }
                )
            }

            if (selectedProvider != null && consumerNumber.length >= 6) {
                item {
                    Surface(shape = RoundedCornerShape(12.dp), color = PurplePrimary.copy(alpha = 0.1f)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Bill Details", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = PurpleDark)
                            Spacer(Modifier.height(8.dp))
                            Row {
                                Text("Amount Due:", color = Color.Gray, modifier = Modifier.weight(1f))
                                Text("₹1,245.00", fontWeight = FontWeight.Bold, color = PurpleDark)
                            }
                            Row {
                                Text("Due Date:", color = Color.Gray, modifier = Modifier.weight(1f))
                                Text("25 Jun 2026", fontWeight = FontWeight.SemiBold, color = Color.Black)
                            }
                        }
                    }
                }

                item {
                    Button(
                        onClick = {
                            navController.navigate(
                                Screen.PaymentConfirm.createRoute("Electricity Bill", "1245")
                            )
                        },
                        modifier = Modifier.fillMaxWidth().height(54.dp),
                        shape = RoundedCornerShape(27.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary)
                    ) {
                        Text("Pay ₹1,245", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun ProviderCard(provider: BillProvider, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        color = White,
        border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, PurplePrimary) else null
    ) {
        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(44.dp).clip(CircleShape).background(provider.color),
                contentAlignment = Alignment.Center
            ) {
                Text(provider.logo, color = White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(provider.name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color.Black)
                Text(provider.category, fontSize = 11.sp, color = Color.Gray)
            }
            if (isSelected) {
                Box(
                    modifier = Modifier.size(24.dp).clip(CircleShape).background(PurplePrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Check, contentDescription = null, tint = White, modifier = Modifier.size(16.dp))
                }
            } else {
                Icon(Icons.Filled.RadioButtonUnchecked, contentDescription = null, tint = Color.LightGray)
            }
        }
    }
}
