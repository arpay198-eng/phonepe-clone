package com.phonepe.clone.ui.screens.recharge

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.phonepe.clone.ui.navigation.Screen
import com.phonepe.clone.ui.theme.*

@Composable
fun DthRechargeScreen(navController: NavController) {
    val providers = listOf(
        "Tata Play" to Color(0xFF6A1B9A),
        "Dish TV" to Color(0xFF00838F),
        "Airtel Digital TV" to Color(0xFFE60000),
        "Sun Direct" to Color(0xFFFF6F00),
        "Videocon D2H" to Color(0xFF1976D2)
    )
    var selectedProvider by remember { mutableStateOf<Pair<String, Color>?>(null) }
    var subscriberId by remember { mutableStateOf("") }

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
                    Text("DTH Recharge", color = White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Surface(shape = RoundedCornerShape(12.dp), color = White) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Enter Subscriber ID", fontSize = 12.sp, color = Color.Gray)
                    Spacer(Modifier.height(6.dp))
                    BasicTextField(
                        value = subscriberId,
                        onValueChange = { subscriberId = it },
                        textStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.Black),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Text("Select DTH Provider", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = PurpleDark)
            Spacer(Modifier.height(12.dp))

            providers.forEach { (name, color) ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { selectedProvider = name to color },
                    color = White,
                    border = if (selectedProvider?.first == name) androidx.compose.foundation.BorderStroke(2.dp, PurplePrimary) else null
                ) {
                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(40.dp).clip(CircleShape).background(color),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(name.first().toString(), color = White, fontWeight = FontWeight.Bold)
                        }
                        Spacer(Modifier.width(12.dp))
                        Text(name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color.Black, modifier = Modifier.weight(1f))
                        if (selectedProvider?.first == name) {
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

            Spacer(Modifier.weight(1f))

            if (selectedProvider != null) {
                Button(
                    onClick = {
                        navController.navigate(
                            Screen.PaymentConfirm.createRoute("DTH Recharge", "450")
                        )
                    },
                    modifier = Modifier.fillMaxWidth().height(54.dp),
                    shape = RoundedCornerShape(27.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary)
                ) {
                    Text("Continue", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
