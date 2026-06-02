package com.phonepe.clone.ui.screens.qr

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.phonepe.clone.ui.navigation.Screen
import com.phonepe.clone.ui.theme.*

@Composable
fun QrScannerScreen(navController: NavController) {
    val context = LocalContext.current
    var hasPermission by remember {
        mutableStateOf(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
    }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        hasPermission = granted
    }
    var flashOn by remember { mutableStateOf(false) }
    var showMyQr by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!hasPermission) launcher.launch(Manifest.permission.CAMERA)
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        if (hasPermission) {
            // Camera placeholder
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            colors = listOf(Color(0xFF1A1A1A), Color.Black)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("Camera Preview", color = White.copy(alpha = 0.3f), fontSize = 16.sp)
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(Icons.Filled.CameraAlt, contentDescription = null, tint = White, modifier = Modifier.size(64.dp))
                Spacer(Modifier.height(16.dp))
                Text("Camera Permission Required", color = White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text(
                    "Allow camera access to scan QR codes",
                    color = White.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = { launcher.launch(Manifest.permission.CAMERA) },
                    colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary)
                ) { Text("Allow Camera") }
            }
        }

        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.5f))
            ) {
                Icon(Icons.Filled.Close, contentDescription = "Close", tint = White)
            }
            Spacer(Modifier.weight(1f))
            TextButton(
                onClick = { showMyQr = !showMyQr },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.Black.copy(alpha = 0.5f),
                    contentColor = White
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Icon(Icons.Filled.QrCode2, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(6.dp))
                Text("My QR", fontSize = 13.sp)
            }
        }

        // Scan area
        Box(
            modifier = Modifier
                .size(260.dp)
                .align(Alignment.Center)
                .border(width = 3.dp, color = White, shape = RoundedCornerShape(20.dp))
        )

        // Corner indicators
        ScanCorners(modifier = Modifier.align(Alignment.Center))

        // Bottom controls
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Align QR within the frame to pay",
                color = White,
                fontSize = 14.sp,
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Spacer(Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircleActionButton(icon = Icons.Filled.Image, label = "Gallery") {}
                CircleActionButton(
                    icon = if (flashOn) Icons.Filled.FlashOn else Icons.Filled.FlashOff,
                    label = "Flash",
                    highlighted = flashOn
                ) { flashOn = !flashOn }
                CircleActionButton(icon = Icons.Filled.QrCode2, label = "My QR") {}
            }
        }

        if (showMyQr) {
            MyQrDialog(onDismiss = { showMyQr = false })
        }
    }
}

@Composable
private fun BoxScope.ScanCorners(modifier: Modifier = Modifier) {
    val cornerSize = 30.dp
    val strokeWidth = 4.dp
    val color = PurpleLight

    Box(modifier = modifier.size(260.dp)) {
        // Top-left
        Box(modifier = Modifier.align(Alignment.TopStart).size(cornerSize)) {
            Box(modifier = Modifier.align(Alignment.TopStart).size(cornerSize).background(Color.Transparent))
        }
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .width(cornerSize)
                .height(strokeWidth)
                .background(color)
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .width(strokeWidth)
                .height(cornerSize)
                .background(color)
        )
        // Top-right
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .width(cornerSize)
                .height(strokeWidth)
                .background(color)
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .width(strokeWidth)
                .height(cornerSize)
                .background(color)
        )
        // Bottom-left
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .width(cornerSize)
                .height(strokeWidth)
                .background(color)
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .width(strokeWidth)
                .height(cornerSize)
                .background(color)
        )
        // Bottom-right
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .width(cornerSize)
                .height(strokeWidth)
                .background(color)
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .width(strokeWidth)
                .height(cornerSize)
                .background(color)
        )
    }
}

@Composable
private fun CircleActionButton(
    icon: ImageVector,
    label: String,
    highlighted: Boolean = false,
    onClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(if (highlighted) PurplePrimary else Color.White.copy(alpha = 0.2f))
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = label, tint = White, modifier = Modifier.size(24.dp))
        }
        Spacer(Modifier.height(4.dp))
        Text(label, color = White, fontSize = 11.sp)
    }
}

@Composable
private fun MyQrDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = White,
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Text("Aarav Kumar", fontWeight = FontWeight.Bold)
                Text("9876543210@ybl", color = Color.Gray, fontSize = 12.sp)
            }
        },
        text = {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(White)
                    .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.QrCode2, contentDescription = null, modifier = Modifier.size(150.dp), tint = PurpleDark)
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("Close", color = PurplePrimary) }
        }
    )
}
