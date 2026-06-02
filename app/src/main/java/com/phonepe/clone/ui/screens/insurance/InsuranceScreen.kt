package com.phonepe.clone.ui.screens.insurance

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.phonepe.clone.ui.components.CircularAvatar
import com.phonepe.clone.ui.theme.*
import com.phonepe.clone.viewmodel.AppViewModel

@Composable
fun InsuranceScreen(navController: NavController) {
    val viewModel: AppViewModel = viewModel()
    val plans by viewModel.insurancePlans.collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F0FA))) {
        // Header
        Surface(
            color = PurplePrimary,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Spacer(Modifier.statusBarsPadding())
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Insurance", color = White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text("Protect what matters most", color = White.copy(alpha = 0.85f), fontSize = 13.sp)
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Categories
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    CategoryCard("Health", Icons.Filled.LocalHospital, Color(0xFFE91E63), Modifier.weight(1f)) {}
                    CategoryCard("Life", Icons.Filled.Favorite, Color(0xFF9C27B0), Modifier.weight(1f)) {}
                    CategoryCard("Vehicle", Icons.Filled.DirectionsCar, Color(0xFF2196F3), Modifier.weight(1f)) {}
                    CategoryCard("Travel", Icons.Filled.Flight, Color(0xFFFF9800), Modifier.weight(1f)) {}
                }
            }

            // Featured plans
            item {
                Text("Featured Plans", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = PurpleDark)
            }

            items(plans) { plan ->
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = White,
                    modifier = Modifier.fillMaxWidth().clickable { }
                ) {
                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(50.dp).clip(RoundedCornerShape(10.dp))
                                .background(Brush.linearGradient(listOf(PurplePrimary, PurpleLight))),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                when (plan.type) {
                                    "Health Insurance" -> Icons.Filled.LocalHospital
                                    "Life Insurance" -> Icons.Filled.Favorite
                                    "Bike Insurance" -> Icons.Filled.DirectionsBike
                                    "Car Insurance" -> Icons.Filled.DirectionsCar
                                    else -> Icons.Filled.Flight
                                },
                                contentDescription = null,
                                tint = White,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                        Spacer(Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(plan.type, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)
                            Text(plan.provider, color = Color.Gray, fontSize = 11.sp)
                            Spacer(Modifier.height(4.dp))
                            Row {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("Coverage", fontSize = 10.sp, color = Color.Gray)
                                    Text("₹${String.format("%,.0f", plan.coverage)}", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = PurpleDark)
                                }
                                Column {
                                    Text("Premium/month", fontSize = 10.sp, color = Color.Gray)
                                    Text("₹${plan.premium.toInt()}", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = GreenSuccess)
                                }
                            }
                        }
                        Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = Color.Gray)
                    }
                }
            }

            item {
                // Promo card
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFFFE0B2),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(50.dp).clip(CircleShape).background(Color(0xFFFF9800)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Filled.LocalOffer, contentDescription = null, tint = White)
                        }
                        Spacer(Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Up to 20% off", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = PurpleDark)
                            Text("On first insurance purchase", color = Color.Gray, fontSize = 12.sp)
                        }
                    }
                }
            }
            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun CategoryCard(name: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color, modifier: Modifier, onClick: () -> Unit) {
    Surface(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        color = White
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(40.dp).clip(RoundedCornerShape(10.dp)).background(color),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = White, modifier = Modifier.size(20.dp))
            }
            Spacer(Modifier.height(6.dp))
            Text(name, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, color = Color.Black)
        }
    }
}
