package com.phonepe.clone.ui.screens.payment

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.phonepe.clone.ui.components.CircularAvatar
import com.phonepe.clone.ui.navigation.Screen
import com.phonepe.clone.ui.theme.*
import com.phonepe.clone.viewmodel.AppViewModel

@Composable
fun PaymentConfirmScreen(
    navController: NavController,
    contact: String,
    initialAmount: String
) {
    val viewModel: AppViewModel = viewModel()
    var amount by remember { mutableStateOf(initialAmount.takeIf { it != "0" } ?: "") }
    var note by remember { mutableStateOf("") }
    var showPin by remember { mutableStateOf(false) }

    if (showPin) {
        PinEntryOverlay(
            onDismiss = { showPin = false },
            onComplete = {
                showPin = false
                viewModel.makePayment(contact, amount.toDoubleOrNull() ?: 0.0, note)
                navController.navigate(Screen.PaymentSuccess.createRoute(amount)) {
                    popUpTo(Screen.Home.route)
                }
            }
        )
        return
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(PurplePrimary, PurpleDark)))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.padding(24.dp)) {
                Spacer(Modifier.statusBarsPadding())
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null, tint = White)
                    }
                    Spacer(Modifier.width(8.dp))
                    Text("Paying $contact", color = White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            // Contact card
            Surface(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                color = White
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularAvatar(name = contact, size = 50, backgroundColor = getAvatarColor(contact))
                    Spacer(Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(contact, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                        Text("9876543210@ybl", color = Color.Gray, fontSize = 12.sp)
                    }
                    Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = GreenSuccess, modifier = Modifier.size(24.dp))
                }
            }

            Spacer(Modifier.height(24.dp))

            // Amount card
            Surface(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                color = White
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Enter amount", fontSize = 12.sp, color = Color.Gray)
                    Spacer(Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("₹", color = PurpleDark, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.width(8.dp))
                        BasicTextField(
                            value = amount,
                            onValueChange = { if (it.length <= 7 && (it.isEmpty() || it.toDoubleOrNull() != null)) amount = it },
                            textStyle = TextStyle(fontSize = 36.sp, fontWeight = FontWeight.Bold, color = PurpleDark),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            singleLine = true,
                            cursorBrush = androidx.compose.ui.graphics.SolidColor(PurplePrimary)
                        )
                    }
                    Divider(modifier = Modifier.padding(vertical = 16.dp), color = Color.LightGray)
                    // Note
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Edit, contentDescription = null, tint = PurplePrimary, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        BasicTextField(
                            value = note,
                            onValueChange = { if (it.length <= 50) note = it },
                            textStyle = TextStyle(fontSize = 13.sp, color = PurpleDark),
                            modifier = Modifier.weight(1f),
                            cursorBrush = androidx.compose.ui.graphics.SolidColor(PurplePrimary),
                            decorationBox = { innerTextField ->
                                if (note.isEmpty()) {
                                    Text("Add a note (optional)", color = Color.Gray, fontSize = 13.sp)
                                }
                                innerTextField()
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Bank selection
            Surface(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                color = White
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.size(36.dp).clip(RoundedCornerShape(8.dp)).background(PurplePrimary),
                        contentAlignment = Alignment.Center
                    ) { Text("₹", color = White, fontSize = 18.sp, fontWeight = FontWeight.Black) }
                    Spacer(Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("HDFC Bank", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color.Black)
                        Text("XXXX1234 • Primary", color = Color.Gray, fontSize = 11.sp)
                    }
                    Icon(Icons.Filled.ExpandMore, contentDescription = null, tint = Color.Gray)
                }
            }

            Spacer(Modifier.weight(1f))

            // Pay button
            Button(
                onClick = { showPin = true },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).height(54.dp),
                shape = RoundedCornerShape(27.dp),
                colors = ButtonDefaults.buttonColors(containerColor = White, contentColor = PurplePrimary),
                enabled = amount.isNotEmpty() && (amount.toDoubleOrNull() ?: 0.0) > 0
            ) {
                Text("Pay ₹$amount", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun PinEntryOverlay(
    onDismiss: () -> Unit,
    onComplete: () -> Unit
) {
    var pin by remember { mutableStateOf("") }
    LaunchedEffect(pin) {
        if (pin.length == 6) {
            kotlinx.coroutines.delay(300)
            onComplete()
        }
    }
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.85f)),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            shape = RoundedCornerShape(20.dp),
            color = White
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Spacer(Modifier.weight(1f))
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Filled.Close, contentDescription = null)
                    }
                }
                Icon(Icons.Filled.Lock, contentDescription = null, tint = PurplePrimary, modifier = Modifier.size(40.dp))
                Spacer(Modifier.height(12.dp))
                Text("Enter UPI PIN", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
                Spacer(Modifier.height(20.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    repeat(6) { i ->
                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .clip(CircleShape)
                                .background(if (i < pin.length) PurplePrimary else Color.LightGray)
                        )
                    }
                }
                Spacer(Modifier.height(20.dp))
                com.phonepe.clone.ui.screens.pin.NumberPad(
                    onDigit = { if (pin.length < 6) pin += it.toString() },
                    onBackspace = { if (pin.isNotEmpty()) pin = pin.dropLast(1) }
                )
            }
        }
    }
}
