package com.phonepe.clone.ui.screens.wealth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.phonepe.clone.ui.navigation.Screen
import com.phonepe.clone.ui.theme.*
import com.phonepe.clone.viewmodel.AppViewModel
import java.text.NumberFormat
import java.util.Locale
import java.text.NumberFormat
import java.util.Locale

@Composable
fun WealthScreen(navController: NavController) {
    val viewModel: AppViewModel = viewModel()
    val investments by viewModel.investments.collectAsState()

    val totalInvested = investments.sumOf { it.invested }
    val totalCurrent = investments.sumOf { it.current }
    val totalReturns = totalCurrent - totalInvested
    val returnsPercent = (totalReturns / totalInvested) * 100

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F0FA))) {
        // Header
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.Transparent
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.verticalGradient(listOf(PurplePrimary, PurpleDark)))
            ) {
                Column {
                    Spacer(Modifier.statusBarsPadding())
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Wealth", color = White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(20.dp))
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = White.copy(alpha = 0.15f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Total Value", color = White.copy(alpha = 0.85f), fontSize = 12.sp)
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    "₹${NumberFormat.getNumberInstance(Locale("en", "IN")).format(totalCurrent)}",
                                    color = White,
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(Modifier.height(8.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        Icons.Filled.TrendingUp,
                                        contentDescription = null,
                                        tint = GreenSuccess,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(Modifier.width(4.dp))
                                    Text(
                                        "₹${NumberFormat.getNumberInstance(Locale("en", "IN")).format(totalReturns)} (${"%.2f".format(returnsPercent)}%)",
                                        color = GreenSuccess,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Quick actions
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            WealthAction("Mutual Funds", Icons.Filled.AccountBalance, Modifier.weight(1f)) {
                navController.navigate(Screen.MutualFunds.route)
            }
            WealthAction("Gold", Icons.Filled.MonetizationOn, Modifier.weight(1f)) {
                navController.navigate(Screen.Gold.route)
            }
            WealthAction("Stocks", Icons.Filled.ShowChart, Modifier.weight(1f)) {}
            WealthAction("SIP", Icons.Filled.Schedule, Modifier.weight(1f)) {}
        }

        // Investment list
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text("Your Investments", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = PurpleDark)
            }
            items(investments) { inv ->
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = White,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(44.dp).clip(RoundedCornerShape(10.dp))
                                .background(Brush.linearGradient(listOf(PurplePrimary, PurpleLight))),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                when (inv.type) {
                                    "Mutual Fund" -> Icons.Filled.AccountBalance
                                    "Gold" -> Icons.Filled.MonetizationOn
                                    "Equity" -> Icons.Filled.ShowChart
                                    else -> Icons.Filled.TrendingUp
                                },
                                contentDescription = null,
                                tint = White,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(inv.name, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color.Black, maxLines = 1)
                            Text(inv.type, color = Color.Gray, fontSize = 11.sp)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                "₹${NumberFormat.getNumberInstance(Locale("en", "IN")).format(inv.current)}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = PurpleDark
                            )
                            Text(
                                "+${"%.1f".format(inv.returnsPercent)}%",
                                color = GreenSuccess,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun WealthAction(name: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier, onClick: () -> Unit) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(40.dp).clip(CircleShape).background(PurplePrimary.copy(alpha = 0.1f))
                    .clickable(onClick = onClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = PurplePrimary, modifier = Modifier.size(20.dp))
            }
            Spacer(Modifier.height(6.dp))
            Text(name, fontWeight = FontWeight.SemiBold, fontSize = 11.sp, color = Color.Black)
        }
    }
}
