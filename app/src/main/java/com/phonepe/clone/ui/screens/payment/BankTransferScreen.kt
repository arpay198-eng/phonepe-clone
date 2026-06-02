package com.phonepe.clone.ui.screens.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.phonepe.clone.ui.navigation.Screen
import com.phonepe.clone.ui.theme.*

@Composable
fun BankTransferScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf(0) }
    var inputValue by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(PurplePrimary, PurpleDark)))
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
            Spacer(Modifier.statusBarsPadding())
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = null, tint = White)
                }
                Spacer(Modifier.width(8.dp))
                Text("Send to Bank / UPI ID", color = White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(24.dp))

            // Tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(25.dp))
                    .background(White.copy(alpha = 0.2f))
                    .padding(4.dp)
            ) {
                listOf("To UPI ID", "To Bank A/C").forEachIndexed { index, label ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(22.dp))
                            .background(if (selectedTab == index) White else Color.Transparent)
                            .clickable { selectedTab = index }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            label,
                            color = if (selectedTab == index) PurplePrimary else White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = White
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        if (selectedTab == 0) "Enter UPI ID" else "Account number",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = inputValue,
                        onValueChange = { inputValue = it },
                        placeholder = {
                            Text(
                                if (selectedTab == 0) "example@upi" else "0000 0000 0000",
                                color = Color.Gray
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PurplePrimary,
                            unfocusedBorderColor = Color.LightGray
                        )
                    )
                    if (selectedTab == 1) {
                        Spacer(Modifier.height(12.dp))
                        Text("IFSC Code", fontSize = 12.sp, color = Color.Gray)
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(
                            placeholder = { Text("HDFC0001234", color = Color.Gray) },
                            value = "",
                            onValueChange = {},
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PurplePrimary,
                                unfocusedBorderColor = Color.LightGray
                            )
                        )
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            // Verify button
            Button(
                onClick = {
                    navController.navigate(
                        Screen.PaymentConfirm.createRoute(
                            inputValue.ifEmpty { "UPI User" },
                            "0"
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth().height(54.dp),
                shape = RoundedCornerShape(27.dp),
                colors = ButtonDefaults.buttonColors(containerColor = White, contentColor = PurplePrimary),
                enabled = inputValue.isNotEmpty()
            ) {
                Text(if (selectedTab == 0) "Verify UPI ID" else "Verify Account", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}
