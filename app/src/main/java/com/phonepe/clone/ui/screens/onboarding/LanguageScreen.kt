package com.phonepe.clone.ui.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.phonepe.clone.ui.navigation.Screen
import com.phonepe.clone.ui.theme.PurpleDark
import com.phonepe.clone.ui.theme.PurplePrimary
import com.phonepe.clone.ui.theme.White

@Composable
fun LanguageScreen(navController: NavController) {
    val languages = listOf(
        "English" to "English",
        "हिन्दी" to "Hindi",
        "தமிழ்" to "Tamil",
        "తెలుగు" to "Telugu",
        "ಕನ್ನಡ" to "Kannada",
        "বাংলা" to "Bengali",
        "मराठी" to "Marathi",
        "ગુજરાતી" to "Gujarati"
    )
    var selected by remember { mutableStateOf("English") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(PurplePrimary, PurpleDark)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Spacer(Modifier.height(60.dp))
            Text(
                text = "Choose your Language",
                color = White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Select your preferred language to continue",
                color = White.copy(alpha = 0.85f),
                fontSize = 14.sp
            )

            Spacer(Modifier.height(32.dp))

            // Language list
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = White
            ) {
                Column {
                    languages.forEachIndexed { index, (label, name) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selected = name }
                                .padding(horizontal = 20.dp, vertical = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = label,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = PurpleDark,
                                modifier = Modifier.weight(1f)
                            )
                            if (selected == name) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(PurplePrimary),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        Icons.Filled.Check,
                                        contentDescription = null,
                                        tint = White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                        if (index < languages.size - 1) {
                            Divider(color = androidx.compose.ui.graphics.Color.LightGray.copy(alpha = 0.3f))
                        }
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = { navController.navigate(Screen.Login.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(27.dp),
                colors = ButtonDefaults.buttonColors(containerColor = White, contentColor = PurplePrimary)
            ) {
                Text("Continue", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
