package com.phonepe.clone.ui.screens.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.phonepe.clone.ui.components.CircularAvatar
import com.phonepe.clone.ui.navigation.Screen
import com.phonepe.clone.ui.theme.*

@Composable
fun PayToPhoneScreen(navController: NavController) {
    var phoneNumber by remember { mutableStateOf("") }
    val isValid = phoneNumber.length == 10

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(PurplePrimary, PurpleDark)))
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp)
        ) {
            Spacer(Modifier.statusBarsPadding())
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = null, tint = White)
                }
                Spacer(Modifier.width(8.dp))
                Text("Pay to Mobile Number", color = White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(32.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = White
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("Enter mobile number", fontSize = 12.sp, color = Color.Gray)
                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🇮🇳", fontSize = 22.sp)
                        Spacer(Modifier.width(8.dp))
                        Text("+91", fontWeight = FontWeight.SemiBold, color = PurpleDark, fontSize = 16.sp)
                        Spacer(Modifier.width(12.dp))
                        Box(modifier = Modifier.width(1.dp).height(28.dp).background(Color.LightGray))
                        Spacer(Modifier.width(12.dp))
                        BasicTextField(
                            value = phoneNumber,
                            onValueChange = { if (it.length <= 10 && it.all { c -> c.isDigit() }) phoneNumber = it },
                            textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = PurpleDark),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            singleLine = true,
                            modifier = Modifier.weight(1f),
                            cursorBrush = androidx.compose.ui.graphics.SolidColor(PurplePrimary)
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // Common contacts
            Text("Recent contacts", color = White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(12.dp))
            listOf(
                "Rahul Sharma" to "9876543210",
                "Priya Verma" to "9876543211",
                "Amit Kumar" to "9876543212"
            ).forEach { (name, phone) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(White)
                        .clickable { phoneNumber = phone.takeLast(10) }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularAvatar(name = name, size = 40, backgroundColor = getAvatarColor(name))
                    Spacer(Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color.Black)
                        Text(phone, color = Color.Gray, fontSize = 12.sp)
                    }
                    Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = Color.Gray)
                }
                Spacer(Modifier.height(8.dp))
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = {
                    navController.navigate(
                        Screen.PaymentConfirm.createRoute("Contact $phoneNumber", "0")
                    )
                },
                modifier = Modifier.fillMaxWidth().height(54.dp),
                shape = RoundedCornerShape(27.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = White,
                    contentColor = PurplePrimary,
                    disabledContainerColor = White.copy(alpha = 0.5f)
                ),
                enabled = isValid
            ) {
                Text("Continue", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}
