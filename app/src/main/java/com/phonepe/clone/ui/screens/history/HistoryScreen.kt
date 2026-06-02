package com.phonepe.clone.ui.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.phonepe.clone.data.model.Transaction
import com.phonepe.clone.data.model.TransactionCategory
import com.phonepe.clone.data.model.TransactionStatus
import com.phonepe.clone.data.model.TransactionType
import com.phonepe.clone.ui.navigation.Screen
import com.phonepe.clone.ui.theme.*
import com.phonepe.clone.viewmodel.AppViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun HistoryScreen(navController: NavController) {
    val viewModel: AppViewModel = viewModel()
    val transactions by viewModel.transactions.collectAsState()
    var selectedFilter by remember { mutableStateOf("All") }

    val filtered = when (selectedFilter) {
        "Sent" -> transactions.filter { it.type == TransactionType.DEBIT }
        "Received" -> transactions.filter { it.type == TransactionType.CREDIT }
        "Pending" -> transactions.filter { it.status == TransactionStatus.PENDING }
        else -> transactions
    }

    val grouped = filtered.groupBy { it.date }

    val totalCredit = filtered.filter { it.type == TransactionType.CREDIT }.sumOf { it.amount }
    val totalDebit = filtered.filter { it.type == TransactionType.DEBIT && it.status == TransactionStatus.SUCCESS }.sumOf { it.amount }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F0FA))) {
        // Header
        Surface(color = PurplePrimary) {
            Column {
                Spacer(Modifier.statusBarsPadding())
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "Transaction History",
                            color = White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = { /* search */ }) {
                            Icon(Icons.Filled.Search, contentDescription = null, tint = White)
                        }
                        IconButton(onClick = { /* filter */ }) {
                            Icon(Icons.Filled.FilterList, contentDescription = null, tint = White)
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        SummaryCard(
                            title = "Money Sent",
                            amount = "₹${NumberFormat.getNumberInstance(Locale("en", "IN")).format(totalDebit)}",
                            color = RedDebit,
                            modifier = Modifier.weight(1f)
                        )
                        SummaryCard(
                            title = "Money Received",
                            amount = "₹${NumberFormat.getNumberInstance(Locale("en", "IN")).format(totalCredit)}",
                            color = GreenSuccess,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        // Filter chips
        LazyColumn {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("All", "Sent", "Received", "Pending").forEach { filter ->
                        FilterChip(
                            text = filter,
                            isSelected = selectedFilter == filter,
                            onClick = { selectedFilter = filter }
                        )
                    }
                }
            }

            grouped.forEach { (date, txns) ->
                item {
                    Text(
                        text = date,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
                items(txns) { txn ->
                    TransactionItem(transaction = txn, onClick = {
                        navController.navigate(Screen.TransactionDetail.createRoute(txn.id))
                    })
                }
            }
            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

@Composable
private fun SummaryCard(title: String, amount: String, color: Color, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = White.copy(alpha = 0.15f)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(title, color = White.copy(alpha = 0.9f), fontSize = 11.sp)
            Spacer(Modifier.height(4.dp))
            Text(amount, color = White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun FilterChip(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.clickable(onClick = onClick).clip(RoundedCornerShape(20.dp)),
        color = if (isSelected) PurplePrimary else White
    ) {
        Text(
            text,
            color = if (isSelected) White else PurpleDark,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
        )
    }
}

@Composable
private fun TransactionItem(transaction: Transaction, onClick: () -> Unit) {
    val isCredit = transaction.type == TransactionType.CREDIT
    val color = when {
        transaction.status == TransactionStatus.FAILED -> Color.Gray
        isCredit -> GreenSuccess
        else -> RedDebit
    }
    val icon = getCategoryIcon(transaction.category)
    val sign = if (isCredit) "+" else "-"

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        color = White
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(44.dp).clip(CircleShape).background(PurplePrimary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = PurplePrimary, modifier = Modifier.size(22.dp))
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(transaction.title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color.Black, maxLines = 1)
                Spacer(Modifier.height(2.dp))
                Text(
                    "${transaction.subtitle} • ${transaction.time}",
                    color = Color.Gray,
                    fontSize = 11.sp,
                    maxLines = 1
                )
                if (transaction.status != TransactionStatus.SUCCESS) {
                    Spacer(Modifier.height(2.dp))
                    Text(
                        transaction.status.name,
                        color = if (transaction.status == TransactionStatus.FAILED) RedDebit else OrangeWarning,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Text(
                text = "$sign₹${NumberFormat.getNumberInstance(Locale("en", "IN")).format(transaction.amount)}",
                color = color,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}

private fun getCategoryIcon(category: TransactionCategory): ImageVector = when (category) {
    TransactionCategory.UPI -> Icons.Filled.Send
    TransactionCategory.RECHARGE -> Icons.Filled.PhoneAndroid
    TransactionCategory.BILL_PAY -> Icons.Filled.Receipt
    TransactionCategory.BANK_TRANSFER -> Icons.Filled.AccountBalance
    TransactionCategory.CASHBACK -> Icons.Filled.CardGiftcard
    TransactionCategory.REFUND -> Icons.Filled.Undo
    TransactionCategory.INSURANCE -> Icons.Filled.Shield
    TransactionCategory.INVESTMENT -> Icons.Filled.TrendingUp
}
