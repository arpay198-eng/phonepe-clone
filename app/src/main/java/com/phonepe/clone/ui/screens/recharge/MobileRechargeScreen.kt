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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.phonepe.clone.data.model.RechargePlan
import com.phonepe.clone.ui.navigation.Screen
import com.phonepe.clone.ui.theme.*
import com.phonepe.clone.viewmodel.AppViewModel

@Composable
fun MobileRechargeScreen(navController: NavController) {
    val viewModel: AppViewModel = viewModel()
    val plans by viewModel.rechargePlans.collectAsState()
    var phoneNumber by remember { mutableStateOf("9876543210") }
    var operator by remember { mutableStateOf("Airtel") }
    var circle by remember { mutableStateOf("Maharashtra") }
    var selectedPlan by remember { mutableStateOf<RechargePlan?>(null) }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F0FA))) {
        // Header
        Surface(color = PurplePrimary, shadowElevation = 0.dp) {
            Column {
                Spacer(Modifier.statusBarsPadding())
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null, tint = White)
                    }
                    Text("Mobile Recharge", color = White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Phone number card
            item {
                Surface(shape = RoundedCornerShape(12.dp), color = White, modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Recharge for", fontSize = 12.sp, color = Color.Gray)
                        Spacer(Modifier.height(6.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            BasicTextField(
                                value = phoneNumber,
                                onValueChange = { phoneNumber = it },
                                textStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.Black),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                modifier = Modifier.weight(1f)
                            )
                            TextButton(onClick = { /* contacts */ }) {
                                Icon(Icons.Filled.Contacts, contentDescription = null, tint = PurplePrimary, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("Contacts", color = PurplePrimary, fontSize = 12.sp)
                            }
                        }
                        Spacer(Modifier.height(12.dp))
                        Row {
                            TagPill(text = "Operator: $operator")
                            Spacer(Modifier.width(8.dp))
                            TagPill(text = "Circle: $circle")
                        }
                    }
                }
            }

            // Popular plans header
            item {
                Text("Popular Plans", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = PurpleDark)
            }

            // Plan list
            items(plans) { plan ->
                RechargePlanCard(
                    plan = plan,
                    isSelected = selectedPlan == plan,
                    onClick = { selectedPlan = plan }
                )
            }

            // Continue button
            item {
                if (selectedPlan != null) {
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = {
                            navController.navigate(
                                Screen.PaymentConfirm.createRoute("Airtel Recharge", selectedPlan!!.amount.toInt().toString())
                            )
                        },
                        modifier = Modifier.fillMaxWidth().height(54.dp),
                        shape = RoundedCornerShape(27.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary)
                    ) {
                        Text("Recharge with ₹${selectedPlan!!.amount.toInt()}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun TagPill(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(PurplePrimary.copy(alpha = 0.1f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text, color = PurplePrimary, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun RechargePlanCard(plan: RechargePlan, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = White,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, PurplePrimary) else null
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("₹${plan.amount.toInt()}", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = PurpleDark)
                    if (plan.isPopular) {
                        Spacer(Modifier.width(6.dp))
                        Box(
                            modifier = Modifier.clip(RoundedCornerShape(4.dp)).background(Color(0xFFFF6B35))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("POPULAR", color = White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                Spacer(Modifier.height(4.dp))
                Text("Validity: ${plan.validity}", fontSize = 12.sp, color = Color.Gray)
                Text("Data: ${plan.data} • ${plan.calls}", fontSize = 12.sp, color = Color.Gray)
                if (plan.benefits.isNotEmpty()) {
                    Spacer(Modifier.height(2.dp))
                    Text(plan.benefits.joinToString(" + "), fontSize = 11.sp, color = PurplePrimary, fontWeight = FontWeight.Medium)
                }
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
