package com.phonepe.clone.ui.screens.stores

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.phonepe.clone.data.mock.MockData
import com.phonepe.clone.ui.theme.*
import com.phonepe.clone.viewmodel.AppViewModel
import kotlin.random.Random

@Composable
fun StoresScreen(navController: NavController) {
    val viewModel: AppViewModel = viewModel()
    val offers by viewModel.offers.collectAsState()
    val stores by viewModel.stores.collectAsState()

    val storeColors = listOf(
        Color(0xFFE91E63), Color(0xFF9C27B0), Color(0xFF3F51B5), Color(0xFF2196F3),
        Color(0xFF00BCD4), Color(0xFF009688), Color(0xFF4CAF50), Color(0xFF8BC34A),
        Color(0xFFFFC107), Color(0xFFFF9800), Color(0xFFFF5722), Color(0xFF795548)
    )

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F0FA))) {
        // Header
        Surface(color = PurplePrimary) {
            Column {
                Spacer(Modifier.statusBarsPadding())
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Stores", color = White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text("Pay at your favorite stores & earn cashback", color = White.copy(alpha = 0.85f), fontSize = 13.sp)
                }
            }
        }

        // Offers carousel
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Top Offers", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = PurpleDark)
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                offers.take(2).forEach { offer ->
                    Surface(
                        modifier = Modifier.weight(1f).height(120.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = offer.color
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize().padding(12.dp),
                            contentAlignment = Alignment.BottomStart
                        ) {
                            Column {
                                Text(offer.cashback, color = White, fontSize = 22.sp, fontWeight = FontWeight.Black)
                                Text(offer.subtitle, color = White.copy(alpha = 0.9f), fontSize = 10.sp)
                                Spacer(Modifier.height(4.dp))
                                Text(offer.store, color = White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }

        // Categories
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text("Categories", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = PurpleDark)
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CategoryChip("All", true, Modifier.weight(1f)) {}
                CategoryChip("Food", false, Modifier.weight(1f)) {}
                CategoryChip("Grocery", false, Modifier.weight(1f)) {}
                CategoryChip("Fashion", false, Modifier.weight(1f)) {}
            }
        }

        Spacer(Modifier.height(16.dp))

        // Store list
        Text(
            "All Stores",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = PurpleDark,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(stores) { store ->
                StoreGridItem(name = store, color = storeColors[Random.nextInt(storeColors.size)])
            }
        }
    }
}

@Composable
private fun CategoryChip(text: String, isSelected: Boolean, modifier: Modifier, onClick: () -> Unit) {
    Surface(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) PurplePrimary else White
    ) {
        Text(
            text,
            color = if (isSelected) White else PurpleDark,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()
        )
    }
}

@Composable
private fun StoreGridItem(name: String, color: Color) {
    val initials = name.split(" ").take(2).map { it.firstOrNull()?.uppercase() ?: "" }.joinToString("")
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(White)
            .clickable { }
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(50.dp).clip(CircleShape).background(color),
            contentAlignment = Alignment.Center
        ) {
            Text(initials, color = White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
        Spacer(Modifier.height(6.dp))
        Text(name, fontSize = 10.sp, color = Color.Black, fontWeight = FontWeight.Medium, maxLines = 2, textAlign = TextAlign.Center)
    }
}
