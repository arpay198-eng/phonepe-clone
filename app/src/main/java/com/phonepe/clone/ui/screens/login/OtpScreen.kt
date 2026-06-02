package com.phonepe.clone.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.phonepe.clone.ui.navigation.Screen
import com.phonepe.clone.ui.theme.PurpleDark
import com.phonepe.clone.ui.theme.PurplePrimary
import com.phonepe.clone.ui.theme.White
import kotlinx.coroutines.delay

@Composable
fun OtpScreen(navController: NavController, phone: String) {
    var otp by remember { mutableStateOf("") }
    var timer by remember { mutableStateOf(30) }
    val isComplete = otp.length == 6

    LaunchedEffect(Unit) {
        while (timer > 0) {
            delay(1000)
            timer--
        }
    }

    LaunchedEffect(isComplete) {
        if (isComplete) {
            delay(500)
            navController.navigate(Screen.BankLink.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(PurplePrimary, PurpleDark)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Spacer(Modifier.statusBarsPadding())
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = White)
            }

            Spacer(Modifier.height(24.dp))

            Text("Verify OTP", color = White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text(
                "We've sent a 6-digit code to +91 $phone",
                color = White.copy(alpha = 0.85f),
                fontSize = 14.sp
            )

            Spacer(Modifier.height(40.dp))

            // OTP boxes
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                repeat(6) { index ->
                    OtpDigitBox(
                        value = otp.getOrNull(index)?.toString() ?: "",
                        isFocused = otp.length == index,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // Hidden text field for input
            BasicTextField(
                value = otp,
                onValueChange = { v -> if (v.length <= 6 && v.all { c -> c.isDigit() }) otp = v },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                textStyle = TextStyle(color = androidx.compose.ui.graphics.Color.Transparent),
                cursorBrush = SolidColor(androidx.compose.ui.graphics.Color.Transparent),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (timer > 0) {
                    Text(
                        "Resend OTP in ${timer}s",
                        color = White.copy(alpha = 0.8f),
                        fontSize = 14.sp
                    )
                } else {
                    TextButton(onClick = { timer = 30 }) {
                        Text("Resend OTP", color = White, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = {
                    navController.navigate(Screen.BankLink.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(27.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = White,
                    contentColor = PurplePrimary,
                    disabledContainerColor = White.copy(alpha = 0.5f)
                ),
                enabled = isComplete
            ) {
                Text("Verify & Continue", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun OtpDigitBox(
    value: String,
    isFocused: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(58.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(White)
            .border(
                width = if (isFocused) 2.dp else 0.dp,
                color = if (isFocused) PurpleDark else androidx.compose.ui.graphics.Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isFocused) {
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(28.dp)
                    .background(PurplePrimary)
            )
        } else {
            Text(
                text = value,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = PurpleDark
            )
        }
    }
}
