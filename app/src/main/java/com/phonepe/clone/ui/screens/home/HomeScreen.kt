package com.phonepe.clone.ui.screens.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.phonepe.clone.ui.components.CircularAvatar
import com.phonepe.clone.ui.navigation.Screen
import com.phonepe.clone.ui.theme.*
import com.phonepe.clone.viewmodel.AppViewModel
import coil.compose.AsyncImage

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: AppViewModel = viewModel()
    val user by viewModel.user.collectAsState()
    val banks by viewModel.banks.collectAsState()
    
    var showBalance by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .verticalScroll(rememberScrollState())
    ) {
        // Status bar space (we use edge-to-edge, so status bars padding is handled by NavHost Scaffold)
        
        // Curved Top Header with share.market Promo
        HeaderSection(
            userName = user.name,
            onProfileClick = { navController.navigate(Screen.Profile.route) },
            onHelpClick = { navController.navigate(Screen.Help.route) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Money Transfers Card Section
        MoneyTransfersSection(
            navController = navController,
            onCheckBalanceClick = { showBalance = !showBalance },
            showBalance = showBalance,
            balanceAmount = banks.sumOf { it.balance }.toString()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Mutual Funds & Gold Row Section
        DailyInvestmentsSection(navController)

        Spacer(modifier = Modifier.height(20.dp))

        // Recharge & Bills Section
        RechargeAndBillsSection(navController)

        Spacer(modifier = Modifier.height(40.dp)) // Extra bottom padding for navigation bar overlap
    }
}

@Composable
fun HeaderSection(
    userName: String,
    onProfileClick: () -> Unit,
    onHelpClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1F0B46), Color(0xFF0F368A))
                )
            )
            .padding(bottom = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // Top row: Avatar & Help
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Profile Avatar with circular badge
                Box(
                    modifier = Modifier.size(44.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f))
                            .clickable(onClick = onProfileClick)
                    ) {
                        AsyncImage(
                            model = "https://i.pravatar.cc/150?img=33",
                            contentDescription = "Profile Picture",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    // Small QrCode scanner badge on bottom right
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .align(Alignment.BottomEnd)
                            .clip(CircleShape)
                            .background(Color.Black)
                            .border(1.dp, Color.White.copy(alpha = 0.5f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.QrCodeScanner,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(9.dp)
                        )
                    }
                }

                // Help Icon
                IconButton(onClick = onHelpClick) {
                    Icon(
                        imageVector = Icons.Outlined.HelpOutline,
                        contentDescription = "Help",
                        tint = Color.White,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // share.market promo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    // share.market text with green dot
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "share",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal
                        )
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 2.dp)
                                .size(5.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF00E676))
                        )
                        Text(
                            text = "market",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Trade with 5x leverage",
                        color = Color(0xFFAEEA00),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Balance & Buying power equation
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Balance
                        Column(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Black.copy(alpha = 0.35f))
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Balance", color = Color.White.copy(alpha = 0.7f), fontSize = 10.sp)
                            Text("₹100", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }

                        Text("=", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)

                        // Buying power
                        Column(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Black.copy(alpha = 0.35f))
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Buying power", color = Color.White.copy(alpha = 0.7f), fontSize = 10.sp)
                            Text("₹500", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Start Now Button
                    Surface(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .border(1.dp, Color.White, RoundedCornerShape(20.dp))
                            .clickable { }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Start now",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Filled.ChevronRight,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }

                // 3D rising arrows diagram
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .padding(bottom = 12.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    // Isometric base platform
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val path = Path().apply {
                            moveTo(size.width / 2, size.height - 5f)
                            lineTo(size.width, size.height - 18f)
                            lineTo(size.width / 2, size.height - 30f)
                            lineTo(0f, size.height - 18f)
                            close()
                        }
                        drawPath(path, Color(0xFF311B92).copy(alpha = 0.5f))
                    }
                    
                    // Rising Arrows
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.Bottom,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        RisingArrow3D(color = Color(0xFF00BCD4), height = 36)
                        RisingArrow3D(color = Color(0xFF4CAF50), height = 50)
                        RisingArrow3D(color = Color(0xFF2196F3), height = 42)
                    }
                }
            }
        }
    }
}

@Composable
fun RisingArrow3D(color: Color, height: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(14.dp)
    ) {
        // Arrow Triangle Head
        Canvas(modifier = Modifier.size(12.dp, 6.dp)) {
            val path = Path().apply {
                moveTo(size.width / 2, 0f)
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
            drawPath(path, color)
        }
        // Stem
        Box(
            modifier = Modifier
                .width(6.dp)
                .height((height - 6).dp)
                .background(color)
        )
    }
}

@Composable
fun MobileTransferIcon() {
    Box(
        modifier = Modifier.size(28.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            
            // Outer phone body (white)
            drawRoundRect(
                color = Color.White,
                topLeft = androidx.compose.ui.geometry.Offset(w * 0.15f, h * 0.05f),
                size = androidx.compose.ui.geometry.Size(w * 0.7f, h * 0.9f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(4.dp.toPx(), 4.dp.toPx())
            )
            
            // Inner screen (light purple)
            drawRoundRect(
                color = Color(0xFFD1C4E9),
                topLeft = androidx.compose.ui.geometry.Offset(w * 0.22f, h * 0.12f),
                size = androidx.compose.ui.geometry.Size(w * 0.56f, h * 0.72f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(2.dp.toPx(), 2.dp.toPx())
            )
            
            // Speaker
            drawRect(
                color = Color.DarkGray,
                topLeft = androidx.compose.ui.geometry.Offset(w * 0.4f, h * 0.08f),
                size = androidx.compose.ui.geometry.Size(w * 0.2f, 1.dp.toPx())
            )
        }
        
        Icon(
            imageVector = Icons.Filled.Call,
            contentDescription = null,
            tint = Color(0xFF5F259F),
            modifier = Modifier.size(13.dp)
        )
    }
}

@Composable
fun BankTransferIcon() {
    Canvas(modifier = Modifier.size(28.dp)) {
        val w = size.width
        val h = size.height
        
        // Roof triangle
        val roofPath = Path().apply {
            moveTo(w * 0.1f, h * 0.35f)
            lineTo(w * 0.5f, h * 0.1f)
            lineTo(w * 0.9f, h * 0.35f)
            close()
        }
        drawPath(roofPath, Color.White)
        
        // Horizontal bar under roof
        drawRect(
            color = Color.White,
            topLeft = androidx.compose.ui.geometry.Offset(w * 0.15f, h * 0.35f),
            size = androidx.compose.ui.geometry.Size(w * 0.7f, h * 0.08f)
        )
        
        // Pillars
        val pillarWidth = w * 0.1f
        val pillarHeight = h * 0.35f
        val pillarY = h * 0.43f
        
        drawRect(color = Color.White, topLeft = androidx.compose.ui.geometry.Offset(w * 0.22f, pillarY), size = androidx.compose.ui.geometry.Size(pillarWidth, pillarHeight))
        drawRect(color = Color.White, topLeft = androidx.compose.ui.geometry.Offset(w * 0.45f, pillarY), size = androidx.compose.ui.geometry.Size(pillarWidth, pillarHeight))
        drawRect(color = Color.White, topLeft = androidx.compose.ui.geometry.Offset(w * 0.68f, pillarY), size = androidx.compose.ui.geometry.Size(pillarWidth, pillarHeight))
        
        // Base foundation
        drawRect(
            color = Color.White,
            topLeft = androidx.compose.ui.geometry.Offset(w * 0.1f, h * 0.78f),
            size = androidx.compose.ui.geometry.Size(w * 0.8f, h * 0.12f)
        )
    }
}

@Composable
fun WalletIcon() {
    Box(
        modifier = Modifier.size(28.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            
            // Wallet body
            drawRoundRect(
                color = Color.White,
                topLeft = androidx.compose.ui.geometry.Offset(w * 0.1f, h * 0.35f),
                size = androidx.compose.ui.geometry.Size(w * 0.8f, h * 0.55f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(3.dp.toPx(), 3.dp.toPx())
            )
            
            // Flap
            drawRoundRect(
                color = Color(0xFFD1C4E9),
                topLeft = androidx.compose.ui.geometry.Offset(w * 0.55f, h * 0.5f),
                size = androidx.compose.ui.geometry.Size(w * 0.35f, h * 0.25f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(2.dp.toPx(), 2.dp.toPx())
            )
            
            // Button dot
            drawCircle(
                color = Color.White,
                radius = 1.5.dp.toPx(),
                center = androidx.compose.ui.geometry.Offset(w * 0.65f, h * 0.625f)
            )
            
            // Card slipping out
            drawRoundRect(
                color = Color(0xFFE0E0E0),
                topLeft = androidx.compose.ui.geometry.Offset(w * 0.3f, h * 0.1f),
                size = androidx.compose.ui.geometry.Size(w * 0.4f, h * 0.35f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(2.dp.toPx(), 2.dp.toPx())
            )
        }
        
        Box(
            modifier = Modifier
                .offset(y = (-5).dp)
                .size(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "₹",
                color = Color(0xFF5F259F),
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun CheckBalanceIcon() {
    Box(
        modifier = Modifier.size(28.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            // White card background
            drawRoundRect(
                color = Color.White,
                topLeft = androidx.compose.ui.geometry.Offset(w * 0.2f, h * 0.1f),
                size = androidx.compose.ui.geometry.Size(w * 0.6f, h * 0.8f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(3.dp.toPx(), 3.dp.toPx())
            )
        }
        Text(
            text = "₹",
            color = Color(0xFF5F259F),
            fontSize = 16.sp,
            fontWeight = FontWeight.Black
        )
    }
}

@Composable
fun MoneyTransfersSection(
    navController: NavController,
    onCheckBalanceClick: () -> Unit,
    showBalance: Boolean,
    balanceAmount: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF151517))
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Money Transfers",
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            // Refer badge pill
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFE65100))
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.LocalOffer,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Refer \u2192 \u20B9200",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Icons Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 1. To Mobile Number
            MoneyTransferAction(
                title = "To Mobile\nNumber",
                hasGreenDot = true,
                onClick = { navController.navigate(Screen.PayToPhone.route) }
            ) {
                MobileTransferIcon()
            }

            // 2. To Bank & Self
            MoneyTransferAction(
                title = "To Bank &\nSelf A/c",
                onClick = { navController.navigate(Screen.BankTransfer.route) }
            ) {
                BankTransferIcon()
            }

            // 3. PhonePe Wallet
            MoneyTransferAction(
                title = "PhonePe\nWallet",
                onClick = { }
            ) {
                WalletIcon()
            }

            // 4. Check Balance
            val balanceText = if (showBalance) "₹$balanceAmount" else "Check\nBalance"
            MoneyTransferAction(
                title = balanceText,
                onClick = onCheckBalanceClick
            ) {
                CheckBalanceIcon()
            }
        }
    }
}

@Composable
fun RowScope.MoneyTransferAction(
    title: String,
    hasGreenDot: Boolean = false,
    onClick: () -> Unit,
    iconContent: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color(0xFF5F259F)),
            contentAlignment = Alignment.Center
        ) {
            iconContent()
            if (hasGreenDot) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00C853))
                        .align(Alignment.TopEnd)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            lineHeight = 14.sp
        )
    }
}

@Composable
fun DailyInvestmentsSection(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Daily Mutual Fund SIP
        Row(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF151517))
                .clickable { navController.navigate(Screen.MutualFunds.route) }
                .padding(horizontal = 12.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Green cash bills drawing
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFF1B5E20)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Money,
                    contentDescription = null,
                    tint = Color(0xFF00E676),
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Daily Mutual Fund SIP @ \u20B910",
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 14.sp
            )
        }

        // Daily Gold saving
        Row(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF151517))
                .clickable { navController.navigate(Screen.Gold.route) }
                .padding(horizontal = 12.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Gold coin drawing
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(Color(0xFFFFD54F), Color(0xFFFFB300))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "24k",
                    color = Color.Black,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Daily Gold saving in Wallet",
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 14.sp
            )
        }
    }
}

@Composable
fun MobileRechargeDrawing() {
    Box(
        modifier = Modifier.size(36.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            // Phone body
            drawRoundRect(
                color = Color(0xFF1976D2),
                topLeft = androidx.compose.ui.geometry.Offset(w * 0.25f, h * 0.1f),
                size = androidx.compose.ui.geometry.Size(w * 0.5f, h * 0.8f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(4.dp.toPx(), 4.dp.toPx())
            )
            // Screen
            drawRoundRect(
                color = Color(0xFF0D47A1),
                topLeft = androidx.compose.ui.geometry.Offset(w * 0.29f, h * 0.16f),
                size = androidx.compose.ui.geometry.Size(w * 0.42f, h * 0.68f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(2.dp.toPx(), 2.dp.toPx())
            )
        }
        Icon(
            imageVector = Icons.Filled.FlashOn,
            contentDescription = null,
            tint = Color(0xFFFFC107),
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
fun TuitionFeesDrawing() {
    Box(
        modifier = Modifier.size(40.dp)
    ) {
        Canvas(modifier = Modifier.size(32.dp).align(Alignment.BottomCenter)) {
            val w = size.width
            val h = size.height
            
            // Left page
            val leftPage = Path().apply {
                moveTo(w * 0.1f, h * 0.2f)
                quadraticBezierTo(w * 0.3f, h * 0.15f, w * 0.5f, h * 0.2f)
                lineTo(w * 0.5f, h * 0.85f)
                quadraticBezierTo(w * 0.3f, h * 0.8f, w * 0.1f, h * 0.85f)
                close()
            }
            drawPath(leftPage, Color.White)
            
            // Right page
            val rightPage = Path().apply {
                moveTo(w * 0.5f, h * 0.2f)
                quadraticBezierTo(w * 0.7f, h * 0.15f, w * 0.9f, h * 0.2f)
                lineTo(w * 0.9f, h * 0.85f)
                quadraticBezierTo(w * 0.7f, h * 0.8f, w * 0.5f, h * 0.85f)
                close()
            }
            drawPath(rightPage, Color(0xFFF5F5F5))
            
            // Spine
            drawLine(
                color = Color.LightGray,
                start = androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.2f),
                end = androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.85f),
                strokeWidth = 1.dp.toPx()
            )
            
            // Pen
            drawLine(
                color = Color(0xFFD32F2F),
                start = androidx.compose.ui.geometry.Offset(w * 0.7f, h * 0.3f),
                end = androidx.compose.ui.geometry.Offset(w * 0.85f, h * 0.7f),
                strokeWidth = 2.dp.toPx()
            )
        }
        
        Row(
            modifier = Modifier
                .offset(x = 0.dp, y = (-2).dp)
                .clip(RoundedCornerShape(3.dp))
                .background(Color(0xFFC2185B))
                .padding(horizontal = 4.dp, vertical = 2.dp)
                .align(Alignment.TopStart),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF00E676)),
                contentAlignment = Alignment.Center
            ) {
                Text("%", color = Color.White, fontSize = 5.sp, fontWeight = FontWeight.Bold)
            }
            Text(
                "2.3%fe...",
                color = Color.White,
                fontSize = 7.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}

@Composable
fun ElectricityBillDrawing() {
    Canvas(modifier = Modifier.size(36.dp)) {
        val w = size.width
        val h = size.height
        
        val domeCenter = androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.4f)
        val domeRadius = w * 0.25f
        
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFFFFEE58), Color(0xFFF57F17)),
                center = domeCenter,
                radius = domeRadius
            ),
            center = domeCenter,
            radius = domeRadius
        )
        
        val neckPath = Path().apply {
            moveTo(w * 0.38f, h * 0.58f)
            lineTo(w * 0.62f, h * 0.58f)
            lineTo(w * 0.58f, h * 0.75f)
            lineTo(w * 0.42f, h * 0.75f)
            close()
        }
        drawPath(neckPath, Color.Gray)
        
        drawLine(Color.DarkGray, androidx.compose.ui.geometry.Offset(w * 0.38f, h * 0.63f), androidx.compose.ui.geometry.Offset(w * 0.62f, h * 0.63f), 1.5.dp.toPx())
        drawLine(Color.DarkGray, androidx.compose.ui.geometry.Offset(w * 0.4f, h * 0.69f), androidx.compose.ui.geometry.Offset(w * 0.6f, h * 0.69f), 1.5.dp.toPx())
        
        drawCircle(Color.Black, 2.dp.toPx(), androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.76f))
        
        val filamentPath = Path().apply {
            moveTo(w * 0.45f, h * 0.52f)
            lineTo(w * 0.45f, h * 0.4f)
            lineTo(w * 0.5f, h * 0.32f)
            lineTo(w * 0.55f, h * 0.4f)
            lineTo(w * 0.55f, h * 0.52f)
        }
        drawPath(filamentPath, Color.White, style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.dp.toPx()))
    }
}

@Composable
fun LoanRepaymentDrawing() {
    Box(
        modifier = Modifier.size(36.dp)
    ) {
        Canvas(modifier = Modifier.size(28.dp).align(Alignment.TopStart)) {
            val w = size.width
            val h = size.height
            
            drawCircle(
                color = Color(0xFFD84315),
                radius = w * 0.38f,
                center = androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.6f)
            )
            val neckPath = Path().apply {
                moveTo(w * 0.35f, h * 0.3f)
                lineTo(w * 0.65f, h * 0.3f)
                lineTo(w * 0.75f, h * 0.15f)
                lineTo(w * 0.25f, h * 0.15f)
                close()
            }
            drawPath(neckPath, Color(0xFFD84315))
            
            drawLine(
                color = Color(0xFFFFB300),
                start = androidx.compose.ui.geometry.Offset(w * 0.33f, h * 0.32f),
                end = androidx.compose.ui.geometry.Offset(w * 0.67f, h * 0.32f),
                strokeWidth = 2.dp.toPx()
            )
        }
        
        Column(
            modifier = Modifier
                .size(16.dp, 16.dp)
                .align(Alignment.BottomEnd)
                .clip(RoundedCornerShape(2.dp))
                .background(Color.White)
                .border(0.5.dp, Color.LightGray, RoundedCornerShape(2.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(Color(0xFF1976D2))
            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "31",
                    color = Color(0xFF1976D2),
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }
    }
}

@Composable
fun RechargeAndBillsSection(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Header
        Text(
            "Recharge & Bills",
            color = Color.White,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Grid row (4 squares)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 1. Mobile Recharge
            RechargeSquareButton(
                title = "Mobile\nRecharge",
                onClick = { navController.navigate(Screen.MobileRecharge.route) }
            ) {
                MobileRechargeDrawing()
            }

            // 2. Tuition Fees
            RechargeSquareButton(
                title = "Tuition\nFees",
                onClick = { }
            ) {
                TuitionFeesDrawing()
            }

            // 3. Electricity Bill
            RechargeSquareButton(
                title = "Electricity\nBill",
                onClick = { navController.navigate(Screen.ElectricityBill.route) }
            ) {
                ElectricityBillDrawing()
            }

            // 4. Loan Repayment
            RechargeSquareButton(
                title = "Loan\nRepayment",
                onClick = { }
            ) {
                LoanRepaymentDrawing()
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // SIM card card & More button row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Free Delivery of Jio SIM Card
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF151517))
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Free Delivery of Jio SIM",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                // Red SIM Card Icon
                Box(
                    modifier = Modifier
                        .size(width = 24.dp, height = 30.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color(0xFFD50000)),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        // Draw angled cut corner on top right
                        val path = Path().apply {
                            moveTo(size.width, 0f)
                            lineTo(size.width - 6f, 0f)
                            lineTo(size.width, 6f)
                            close()
                        }
                        drawPath(path, Color(0xFF151517))
                    }
                    // Yellow contact lines
                    Box(
                        modifier = Modifier
                            .size(10.dp, 12.dp)
                            .border(1.dp, Color(0xFFFFD54F), RoundedCornerShape(1.dp))
                    )
                }
            }

            // More Button
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF151517))
                    .clickable { }
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "More",
                    color = Color(0xFF9575CD),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = null,
                    tint = Color(0xFF9575CD),
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

@Composable
fun RowScope.RechargeSquareButton(
    title: String,
    onClick: () -> Unit,
    icon: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF151517)),
            contentAlignment = Alignment.Center
        ) {
            icon()
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            lineHeight = 14.sp
        )
    }
}
