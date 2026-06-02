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
fun MutualFundsScreen(navController: NavController) {
    val funds = listOf(
        Triple("Axis Bluechip Fund", "Equity • Large Cap", "14.2%"),
        Triple("SBI Small Cap Fund", "Equity • Small Cap", "28.5%"),
        Triple("HDFC Flexi Cap Fund", "Equity • Flexi Cap", "15.8%"),
        Triple("ICICI Pru Balanced", "Hybrid • Balanced", "11.2%"),
        Triple("Mirae Asset ELSS", "Tax Saver", "16.5%")
    )

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
                    Text("Mutual Funds", color = White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            // Banner
            Surface(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                color = Color.Transparent
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Brush.horizontalGradient(listOf(PurplePrimary, PurpleLight)))
                        .padding(16.dp)
                ) {
                    Column {
                        Text("Start SIP from ₹100", color = White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text("Build wealth with small monthly investments", color = White.copy(alpha = 0.9f), fontSize = 12.sp)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Text("Top Performing Funds", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = PurpleDark)
            Spacer(Modifier.height(8.dp))

            funds.forEach { (name, type, returns) ->
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = White,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp)).background(PurplePrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(name.first().toString(), color = White, fontWeight = FontWeight.Bold)
                        }
                        Spacer(Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(name, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color.Black)
                            Text(type, color = Color.Gray, fontSize = 11.sp)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("3Y Returns", fontSize = 10.sp, color = Color.Gray)
                            Text(returns, color = GreenSuccess, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
            }
        }
    }
}
