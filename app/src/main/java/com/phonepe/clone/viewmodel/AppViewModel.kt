package com.phonepe.clone.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phonepe.clone.data.mock.MockData
import com.phonepe.clone.data.model.*
import com.phonepe.clone.data.repository.AppRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppViewModel : ViewModel() {
    private val repository = AppRepository()

    val user = repository.user
    val banks = repository.banks
    val contacts = repository.contacts
    val transactions = repository.transactions
    val investments = repository.investments
    val offers = repository.offers

    private val _quickActions = MutableStateFlow(MockData.quickActions)
    val quickActions: StateFlow<List<QuickAction>> = _quickActions.asStateFlow()

    private val _allServices = MutableStateFlow(MockData.allServices)
    val allServices: StateFlow<List<Service>> = _allServices.asStateFlow()

    private val _billProviders = MutableStateFlow(MockData.billProviders)
    val billProviders: StateFlow<List<BillProvider>> = _billProviders.asStateFlow()

    private val _rechargePlans = MutableStateFlow(MockData.rechargePlans)
    val rechargePlans: StateFlow<List<RechargePlan>> = _rechargePlans.asStateFlow()

    private val _insurancePlans = MutableStateFlow(MockData.insurancePlans)
    val insurancePlans: StateFlow<List<InsurancePlan>> = _insurancePlans.asStateFlow()

    private val _stores = MutableStateFlow(MockData.stores)
    val stores: StateFlow<List<String>> = _stores.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun getTotalBalance(): Double = repository.getTotalBalance()
    fun getPrimaryBank(): BankAccount = repository.getPrimaryBank()

    fun makePayment(to: String, amount: Double, note: String) {
        val txn = Transaction(
            id = System.currentTimeMillis().toString(),
            title = "Paid to $to",
            subtitle = "UPI Payment",
            amount = amount,
            type = TransactionType.DEBIT,
            status = TransactionStatus.SUCCESS,
            date = "Today",
            time = "Now",
            category = TransactionCategory.UPI,
            reference = "UPI/${System.currentTimeMillis()}/REF"
        )
        repository.addTransaction(txn)
    }

    fun toggleFavorite(contactId: String) = repository.toggleFavoriteContact(contactId)

    fun refresh() {
        viewModelScope.launch {
            _isLoading.value = true
            delay(1500)
            _isLoading.value = false
        }
    }
}
