package com.phonepe.clone.ui.screens.wealth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.phonepe.clone.ui.theme.*

@Composable
fun GoldScreen(navController: NavController) {
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
                    Text("Digital Gold", color = White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            // Gold balance card
            Surface(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
                color = Color.Transparent
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Brush.linearGradient(listOf(Color(0xFFFF9800), Color(0xFFFFC107))))
                        .padding(20.dp)
                ) {
                    Column {
                        Text("Your Gold Balance", color = White.copy(alpha = 0.9f), fontSize = 12.sp)
                        Spacer(Modifier.height(6.dp))
                        Text("0.85 grams", color = White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(4.dp))
                        Text("≈ ₹6,120.50", color = White.copy(alpha = 0.9f), fontSize = 14.sp)
                        Spacer(Modifier.height(16.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = {},
                                colors = ButtonDefaults.buttonColors(containerColor = White, contentColor = Color(0xFFFF9800)),
                                shape = RoundedCornerShape(20.dp)
                            ) { Text("Buy Gold") }
                            OutlinedButton(
                                onClick = {},
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = White),
                                border = androidx.compose.foundation.BorderStroke(1.dp, White),
                                shape = RoundedCornerShape(20.dp)
                            ) { Text("Sell Gold") }
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            Text("Live Gold Rate", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = PurpleDark)
            Spacer(Modifier.height(8.dp))

            Surface(shape = RoundedCornerShape(12.dp), color = White, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("24K Gold (per gram)", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.weight(1f))
                        Text("₹7,200", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = PurpleDark)
                    }
                    Spacer(Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("22K Gold (per gram)", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.weight(1f))
                        Text("₹6,600", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = PurpleDark)
                    }
                    Spacer(Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Today's change", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.weight(1f))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.TrendingUp, contentDescription = null, tint = GreenSuccess, modifier = Modifier.size(14.dp))
                            Spacer(Modifier.width(2.dp))
                            Text("+0.5%", color = GreenSuccess, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            Text("Benefits", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = PurpleDark)
            Spacer(Modifier.height(8.dp))

            val benefits = listOf(
                "99.99% pure 24K gold" to Icons.Filled.Verified,
                "Stored in secure vaults" to Icons.Filled.Lock,
                "Sell anytime at live rates" to Icons.Filled.Sell,
                "No making charges" to Icons.Filled.LocalOffer
            )
            benefits.forEach { (text, icon) ->
                Row(modifier = Modifier.padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(icon, contentDescription = null, tint = PurplePrimary, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(12.dp))
                    Text(text, fontSize = 13.sp, color = Color.Black)
                }
            }
        }
    }
}
