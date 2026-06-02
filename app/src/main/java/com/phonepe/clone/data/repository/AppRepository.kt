package com.phonepe.clone.data.repository

import com.phonepe.clone.data.mock.MockData
import com.phonepe.clone.data.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AppRepository {
    private val _user = MutableStateFlow(
        UserProfile(
            name = "Aarav Kumar",
            phone = "9876543210",
            email = "aarav.kumar@email.com",
            upiId = "9876543210@ybl",
            isKycComplete = true,
            profileImage = null
        )
    )
    val user: StateFlow<UserProfile> = _user

    private val _banks = MutableStateFlow(MockData.banks)
    val banks: StateFlow<List<BankAccount>> = _banks

    private val _contacts = MutableStateFlow(MockData.contacts)
    val contacts: StateFlow<List<Contact>> = _contacts

    private val _transactions = MutableStateFlow(MockData.transactions)
    val transactions: StateFlow<List<Transaction>> = _transactions

    private val _investments = MutableStateFlow(MockData.investments)
    val investments: StateFlow<List<Investment>> = _investments

    private val _offers = MutableStateFlow(MockData.offers)
    val offers: StateFlow<List<Offer>> = _offers

    fun getPrimaryBank(): BankAccount = _banks.value.first { it.isPrimary }
    fun getTotalBalance(): Double = _banks.value.sumOf { it.balance }

    fun addTransaction(transaction: Transaction) {
        _transactions.value = listOf(transaction) + _transactions.value
    }

    fun toggleFavoriteContact(contactId: String) {
        _contacts.value = _contacts.value.map {
            if (it.id == contactId) it.copy(isFavorite = !it.isFavorite) else it
        }
    }
}

data class UserProfile(
    val name: String,
    val phone: String,
    val email: String,
    val upiId: String,
    val isKycComplete: Boolean,
    val profileImage: String? = null
)
