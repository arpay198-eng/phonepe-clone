package com.phonepe.clone.ui.screens.insurance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.phonepe.clone.ui.theme.*
import com.phonepe.clone.viewmodel.AppViewModel

@Composable
fun InsurancePlansScreen(navController: NavController) {
    val viewModel: AppViewModel = viewModel()
    val plans by viewModel.insurancePlans.collectAsState()

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
                    Text("Insurance Plans", color = White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(plans) { plan ->
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = White,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier.size(56.dp).clip(RoundedCornerShape(12.dp))
                                    .background(Brush.linearGradient(listOf(PurplePrimary, PurpleLight))),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Filled.Shield, contentDescription = null, tint = White, modifier = Modifier.size(28.dp))
                            }
                            Spacer(Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(plan.type, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.Black)
                                Text(plan.provider, color = Color.Gray, fontSize = 12.sp)
                                Text("Tenure: ${plan.tenure}", color = Color.Gray, fontSize = 11.sp)
                            }
                        }
                        Spacer(Modifier.height(12.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Coverage", fontSize = 11.sp, color = Color.Gray)
                                Text("₹${String.format("%,.0f", plan.coverage)}", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = PurpleDark)
                            }
                            Column {
                                Text("Premium", fontSize = 11.sp, color = Color.Gray)
                                Text("₹${plan.premium.toInt()}/mo", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = GreenSuccess)
                            }
                        }
                        Spacer(Modifier.height(12.dp))
                        plan.features.forEach { feature ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = GreenSuccess, modifier = Modifier.size(14.dp))
                                Spacer(Modifier.width(6.dp))
                                Text(feature, fontSize = 12.sp, color = Color.DarkGray)
                            }
                        }
                        Spacer(Modifier.height(12.dp))
                        Button(
                            onClick = {},
                            modifier = Modifier.fillMaxWidth().height(44.dp),
                            shape = RoundedCornerShape(22.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary)
                        ) {
                            Text("View Details", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
    }
}
