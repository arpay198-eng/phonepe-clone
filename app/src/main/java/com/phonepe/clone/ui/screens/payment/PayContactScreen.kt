package com.phonepe.clone.ui.screens.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.phonepe.clone.data.model.Contact
import com.phonepe.clone.ui.components.CircularAvatar
import com.phonepe.clone.ui.navigation.Screen
import com.phonepe.clone.ui.theme.*
import com.phonepe.clone.viewmodel.AppViewModel

@Composable
fun PayContactScreen(navController: NavController) {
    val viewModel: AppViewModel = viewModel()
    val contacts by viewModel.contacts.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var showAll by remember { mutableStateOf(false) }

    val filteredContacts = if (showAll) contacts else contacts.filter { it.isFavorite }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(PurplePrimary, PurpleDark)))
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Spacer(Modifier.statusBarsPadding())
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null, tint = White)
                    }
                    Spacer(Modifier.width(8.dp))
                    Text("Pay anyone using PhonePe", color = White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }

            // Search bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search name or number") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = White,
                    unfocusedContainerColor = White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )

            Spacer(Modifier.height(16.dp))

            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                color = Color(0xFFF5F5F5)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    // Toggle
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(25.dp))
                            .background(PurplePrimary.copy(alpha = 0.1f))
                            .padding(4.dp)
                    ) {
                        TabChip(
                            text = "Favorites",
                            isSelected = !showAll,
                            onClick = { showAll = false },
                            modifier = Modifier.weight(1f)
                        )
                        TabChip(
                            text = "All Contacts",
                            isSelected = showAll,
                            onClick = { showAll = true },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    // Contacts list
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        items(filteredContacts) { contact ->
                            ContactListItem(
                                contact = contact,
                                onClick = {
                                    navController.navigate(
                                        Screen.PaymentConfirm.createRoute(contact.name, "0")
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TabChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) PurplePrimary else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            color = if (isSelected) White else PurpleDark,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp
        )
    }
}

@Composable
private fun ContactListItem(contact: Contact, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(White)
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularAvatar(
            name = contact.name,
            size = 44,
            backgroundColor = getAvatarColor(contact.name)
        )
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(contact.name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color.Black)
                if (contact.isFavorite) {
                    Spacer(Modifier.width(4.dp))
                    Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFFFC107), modifier = Modifier.size(14.dp))
                }
            }
            Text(contact.phone, color = Color.Gray, fontSize = 12.sp)
        }
        if (contact.upiId != null) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(PurplePrimary.copy(alpha = 0.1f))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text("UPI", color = PurplePrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

fun getAvatarColor(name: String): Color {
    val colors = listOf(
        Color(0xFF5F259F), Color(0xFF00B8A9), Color(0xFFFF6B35),
        Color(0xFF2196F3), Color(0xFFE91E63), Color(0xFF8BC34A),
        Color(0xFFFFC107), Color(0xFF9C27B0)
    )
    return colors[name.hashCode().let { if (it < 0) -it else it } % colors.size]
}
