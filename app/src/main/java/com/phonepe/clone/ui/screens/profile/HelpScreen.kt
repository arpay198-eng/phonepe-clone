package com.phonepe.clone.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.phonepe.clone.ui.theme.*

@Composable
fun HelpScreen(navController: NavController) {
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
                    Text("Help & Support", color = White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            // Search
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Search for help") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = White, unfocusedContainerColor = White)
            )

            Spacer(Modifier.height(16.dp))

            // Quick actions
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                HelpCard("Chat with us", Icons.Filled.Chat, Color(0xFF00B8A9), Modifier.weight(1f)) {}
                HelpCard("Call us", Icons.Filled.Phone, Color(0xFFFF6B35), Modifier.weight(1f)) {}
                HelpCard("Email", Icons.Filled.Email, Color(0xFF2196F3), Modifier.weight(1f)) {}
            }

            Spacer(Modifier.height(20.dp))

            Text("Frequently Asked Questions", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = PurpleDark)
            Spacer(Modifier.height(8.dp))

            val faqs = listOf(
                "How to set UPI PIN?",
                "How to add a bank account?",
                "What to do if payment fails?",
                "How to check transaction history?",
                "How to change UPI ID?",
                "What are the transaction limits?"
            )
            faqs.forEach { question ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { }
                        .clip(RoundedCornerShape(10.dp)),
                    color = White
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Filled.HelpOutline, contentDescription = null, tint = PurplePrimary, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(12.dp))
                        Text(question, fontSize = 13.sp, color = Color.Black, modifier = Modifier.weight(1f))
                        Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(18.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun HelpCard(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color, modifier: Modifier) {
    Surface(
        modifier = modifier.clickable { },
        shape = RoundedCornerShape(12.dp),
        color = White
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp)).background(color),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = White, modifier = Modifier.size(20.dp))
            }
            Spacer(Modifier.height(8.dp))
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, color = Color.Black)
        }
    }
}
