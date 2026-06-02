package com.phonepe.clone.data.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class BankAccount(
    val id: String,
    val bankName: String,
    val accountNumber: String,
    val ifsc: String,
    val upiId: String,
    val balance: Double,
    val isPrimary: Boolean = false,
    val color: Color = Color(0xFF5F259F)
)

data class Contact(
    val id: String,
    val name: String,
    val phone: String,
    val avatar: String,
    val upiId: String? = null,
    val isFavorite: Boolean = false
)

data class Transaction(
    val id: String,
    val title: String,
    val subtitle: String,
    val amount: Double,
    val type: TransactionType,
    val status: TransactionStatus,
    val date: String,
    val time: String,
    val category: TransactionCategory,
    val reference: String? = null,
    val icon: String? = null
)

enum class TransactionType { DEBIT, CREDIT }
enum class TransactionStatus { SUCCESS, PENDING, FAILED }
enum class TransactionCategory {
    UPI, RECHARGE, BILL_PAY, BANK_TRANSFER, CASHBACK, REFUND, INSURANCE, INVESTMENT
}

data class Service(
    val id: String,
    val title: String,
    val subtitle: String? = null,
    val icon: ImageVector,
    val backgroundColor: Color,
    val iconColor: Color = Color.White,
    val route: String,
    val badge: String? = null
)

data class RechargePlan(
    val id: String,
    val amount: Double,
    val validity: String,
    val data: String,
    val calls: String,
    val sms: String,
    val benefits: List<String> = emptyList(),
    val isPopular: Boolean = false
)

data class BillProvider(
    val id: String,
    val name: String,
    val category: String,
    val logo: String,
    val color: Color
)

data class InsurancePlan(
    val id: String,
    val type: String,
    val provider: String,
    val premium: Double,
    val coverage: Double,
    val tenure: String,
    val features: List<String>
)

data class Investment(
    val id: String,
    val name: String,
    val type: String,
    val invested: Double,
    val current: Double,
    val returns: Double,
    val returnsPercent: Double
)

data class Offer(
    val id: String,
    val title: String,
    val subtitle: String,
    val store: String,
    val cashback: String,
    val validTill: String,
    val imageUrl: String? = null,
    val color: Color
)

data class QuickAction(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val backgroundColor: Color,
    val route: String
)
