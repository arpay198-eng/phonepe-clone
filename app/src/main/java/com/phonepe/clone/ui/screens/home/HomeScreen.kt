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
import io.coil.compose.AsyncImage

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
                icon = Icons.Filled.SendToMobile,
                hasGreenDot = true,
                onClick = { navController.navigate(Screen.PayToPhone.route) }
            )

            // 2. To Bank & Self
            MoneyTransferAction(
                title = "To Bank &\nSelf A/c",
                icon = Icons.Filled.AccountBalance,
                onClick = { navController.navigate(Screen.BankTransfer.route) }
            )

            // 3. PhonePe Wallet
            MoneyTransferAction(
                title = "PhonePe\nWallet",
                icon = Icons.Filled.Wallet,
                onClick = { }
            )

            // 4. Check Balance
            CheckBalanceAction(
                showBalance = showBalance,
                balanceAmount = balanceAmount,
                onClick = onCheckBalanceClick
            )
        }
    }
}

@Composable
fun MoneyTransferAction(
    title: String,
    icon: ImageVector,
    hasGreenDot: Boolean = false,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(72.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color(0xFF5F259F))
                .padding(14.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
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
fun CheckBalanceAction(
    showBalance: Boolean,
    balanceAmount: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(72.dp)
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
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF7B3FCB)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (showBalance) "₹" else "₹",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = if (showBalance) "₹$balanceAmount" else "Check\nBalance",
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
                Box(modifier = Modifier.size(36.dp), contentAlignment = Alignment.Center) {
                    Box(
                        modifier = Modifier
                            .size(width = 16.dp, height = 28.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(Color(0xFF1E88E5))
                    )
                    Icon(
                        imageVector = Icons.Filled.FlashOn,
                        contentDescription = null,
                        tint = Color(0xFFFFB300),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // 2. Tuition Fees
            RechargeSquareButton(
                title = "Tuition\nFees",
                onClick = { }
            ) {
                Box(modifier = Modifier.size(36.dp)) {
                    Icon(
                        imageVector = Icons.Filled.MenuBook,
                        contentDescription = null,
                        tint = Color(0xFFEEEEEE),
                        modifier = Modifier.size(28.dp).align(Alignment.Center)
                    )
                    // Tag "2.3% fe..."
                    Box(
                        modifier = Modifier
                            .offset(x = (-4).dp, y = (-4).dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(Color(0xFFC2185B))
                            .padding(horizontal = 3.dp, vertical = 1.dp)
                            .align(Alignment.TopStart)
                    ) {
                        Text("2.3%fe...", color = Color.White, fontSize = 6.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // 3. Electricity Bill
            RechargeSquareButton(
                title = "Electricity\nBill",
                onClick = { navController.navigate(Screen.ElectricityBill.route) }
            ) {
                Box(modifier = Modifier.size(36.dp), contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Filled.Lightbulb,
                        contentDescription = null,
                        tint = Color(0xFFFFB300),
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            // 4. Loan Repayment
            RechargeSquareButton(
                title = "Loan\nRepayment",
                onClick = { }
            ) {
                Box(modifier = Modifier.size(36.dp)) {
                    Icon(
                        imageVector = Icons.Filled.LocalMall,
                        contentDescription = null,
                        tint = Color(0xFF8D6E63),
                        modifier = Modifier.size(26.dp).align(Alignment.Center)
                    )
                    // Small Calendar card overlay
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .clip(RoundedCornerShape(1.dp))
                            .background(Color.White)
                            .border(0.5.dp, Color.Gray, RoundedCornerShape(1.dp))
                            .align(Alignment.BottomEnd),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("31", color = Color(0xFF1976D2), fontSize = 7.sp, fontWeight = FontWeight.Black)
                    }
                }
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
fun RechargeSquareButton(
    title: String,
    onClick: () -> Unit,
    icon: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .width(80.dp)
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
