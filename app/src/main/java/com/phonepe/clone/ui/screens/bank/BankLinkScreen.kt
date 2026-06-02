package com.phonepe.clone.ui.screens.bank

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Search
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
import com.phonepe.clone.ui.theme.PurpleDark
import com.phonepe.clone.ui.theme.PurplePrimary
import com.phonepe.clone.ui.theme.White

data class Bank(
    val name: String,
    val color: Color
)

val supportedBanks = listOf(
    Bank("HDFC Bank", Color(0xFF004B87)),
    Bank("SBI", Color(0xFF22458F)),
    Bank("ICICI Bank", Color(0xFFE42528)),
    Bank("Axis Bank", Color(0xFF97144D)),
    Bank("Kotak Mahindra", Color(0xFF003F87)),
    Bank("Yes Bank", Color(0xFF1D3A8C)),
    Bank("Punjab National", Color(0xFF6F2A2A)),
    Bank("Bank of Baroda", Color(0xFFE97132)),
    Bank("Canara Bank", Color(0xFF1F4E9C)),
    Bank("IDFC FIRST", Color(0xFF8B0E5A)),
    Bank("Federal Bank", Color(0xFFFFCB05)),
    Bank("IndusInd Bank", Color(0xFFE91E63))
)

@Composable
fun BankLinkScreen(navController: NavController) {
    var selectedBank by remember { mutableStateOf<Bank?>(null) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredBanks = supportedBanks.filter { it.name.contains(searchQuery, ignoreCase = true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(PurplePrimary, PurpleDark)))
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            Column(modifier = Modifier.padding(24.dp)) {
                Spacer(Modifier.statusBarsPadding())
                Spacer(Modifier.height(8.dp))
                Text("Link Bank Account", color = White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text(
                    "Select your bank to link with PhonePe",
                    color = White.copy(alpha = 0.85f),
                    fontSize = 14.sp
                )
            }

            Spacer(Modifier.height(8.dp))

            // Content
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                color = androidx.compose.ui.graphics.Color(0xFFF5F5F5)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    // Search bar
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search banks") },
                        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PurplePrimary,
                            unfocusedBorderColor = androidx.compose.ui.graphics.Color.LightGray,
                            focusedContainerColor = White,
                            unfocusedContainerColor = White
                        )
                    )

                    Spacer(Modifier.height(16.dp))

                    Text(
                        "Popular Banks",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = PurpleDark
                    )
                    Spacer(Modifier.height(12.dp))

                    // Bank list
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        filteredBanks.forEach { bank ->
                            BankListItem(
                                bank = bank,
                                isSelected = selectedBank == bank,
                                onClick = { selectedBank = bank }
                            )
                        }
                    }
                }
            }
        }

        // Continue button
        if (selectedBank != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    onClick = { navController.navigate(Screen.PinSetup.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(27.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary)
                ) {
                    Text("Continue with ${selectedBank?.name}", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun BankListItem(
    bank: Bank,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        color = White,
        border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, PurplePrimary) else null
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(bank.color),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.AccountBalance, contentDescription = null, tint = White, modifier = Modifier.size(20.dp))
            }
            Spacer(Modifier.width(12.dp))
            Text(
                text = bank.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(PurplePrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Check, contentDescription = null, tint = White, modifier = Modifier.size(16.dp))
                }
            } else {
                Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = Color.Gray)
            }
        }
    }
}
