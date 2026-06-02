package com.phonepe.clone.data.mock

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.Color
import com.phonepe.clone.data.model.*
import com.phonepe.clone.ui.theme.*

object MockData {
    val banks = listOf(
        BankAccount(
            id = "1",
            bankName = "HDFC Bank",
            accountNumber = "XXXX1234",
            ifsc = "HDFC0001234",
            upiId = "9876543210@hdfc",
            balance = 25430.50,
            isPrimary = true
        ),
        BankAccount(
            id = "2",
            bankName = "SBI",
            accountNumber = "XXXX5678",
            ifsc = "SBIN0005678",
            upiId = "9876543210@sbi",
            balance = 12450.00
        ),
        BankAccount(
            id = "3",
            bankName = "ICICI Bank",
            accountNumber = "XXXX9012",
            ifsc = "ICIC0009012",
            upiId = "9876543210@icici",
            balance = 8730.75
        ),
        BankAccount(
            id = "4",
            bankName = "Axis Bank",
            accountNumber = "XXXX3456",
            ifsc = "UTIB0003456",
            upiId = "9876543210@axis",
            balance = 5000.00
        )
    )

    val contacts = listOf(
        Contact("1", "Rahul Sharma", "9876543210", "R", "rahul@oksbi", true),
        Contact("2", "Priya Verma", "9876543211", "P", "priya@ybl", true),
        Contact("3", "Amit Kumar", "9876543212", "A", null, true),
        Contact("4", "Sneha Patel", "9876543213", "S", "sneha@paytm"),
        Contact("5", "Vikram Singh", "9876543214", "V", null),
        Contact("6", "Ananya Gupta", "9876543215", "A", "ananya@okhdfc"),
        Contact("7", "Rohan Mehta", "9876543216", "R", null),
        Contact("8", "Kavita Reddy", "9876543217", "K", "kavita@ybl"),
        Contact("9", "Suresh Iyer", "9876543218", "S", null, true),
        Contact("10", "Meera Joshi", "9876543219", "M", "meera@paytm")
    )

    val transactions = listOf(
        Transaction("1", "Paid to Rahul Sharma", "UPI/9876543210", 500.0, TransactionType.DEBIT, TransactionStatus.SUCCESS, "Today", "14:32", TransactionCategory.UPI, "UPI/4321/REF"),
        Transaction("2", "Mobile Recharge", "Prepaid • Airtel", 299.0, TransactionType.DEBIT, TransactionStatus.SUCCESS, "Today", "10:15", TransactionCategory.RECHARGE, "TXN/12345"),
        Transaction("3", "Cashback Received", "Reward", 25.0, TransactionType.CREDIT, TransactionStatus.SUCCESS, "Today", "10:15", TransactionCategory.CASHBACK, "CASH/098"),
        Transaction("4", "Electricity Bill", "BSES Rajdhani", 1245.0, TransactionType.DEBIT, TransactionStatus.SUCCESS, "Yesterday", "18:45", TransactionCategory.BILL_PAY, "BILL/5678"),
        Transaction("5", "Received from Priya", "UPI/priya@ybl", 1500.0, TransactionType.CREDIT, TransactionStatus.SUCCESS, "Yesterday", "12:20", TransactionCategory.UPI, "UPI/8765/REF"),
        Transaction("6", "DTH Recharge", "Tata Play", 450.0, TransactionType.DEBIT, TransactionStatus.SUCCESS, "Yesterday", "09:30", TransactionCategory.RECHARGE, "TXN/23456"),
        Transaction("7", "Insurance Premium", "HDFC Life", 1200.0, TransactionType.DEBIT, TransactionStatus.PENDING, "25 May", "16:00", TransactionCategory.INSURANCE, "INS/111"),
        Transaction("8", "Mutual Fund SIP", "Axis Bluechip", 1000.0, TransactionType.DEBIT, TransactionStatus.SUCCESS, "24 May", "08:00", TransactionCategory.INVESTMENT, "MF/222"),
        Transaction("9", "Refund - Flipkart", "Order #12345", 599.0, TransactionType.CREDIT, TransactionStatus.SUCCESS, "23 May", "11:30", TransactionCategory.REFUND, "REF/333"),
        Transaction("10", "Gas Bill", "Indraprastha Gas", 850.0, TransactionType.DEBIT, TransactionStatus.SUCCESS, "22 May", "19:15", TransactionCategory.BILL_PAY, "BILL/7890"),
        Transaction("11", "Paid to Amit Kumar", "UPI/9876543212", 250.0, TransactionType.DEBIT, TransactionStatus.FAILED, "21 May", "14:00", TransactionCategory.UPI, "UPI/1111/REF"),
        Transaction("12", "Credit Card Bill", "HDFC Regalia", 4500.0, TransactionType.DEBIT, TransactionStatus.SUCCESS, "20 May", "20:00", TransactionCategory.BILL_PAY, "CC/4567")
    )

    val rechargePlans = listOf(
        RechargePlan("1", 199.0, "24 days", "1.5 GB/day", "Unlimited", "100/day", listOf("Disney+ Hotstar Mobile"), false),
        RechargePlan("2", 299.0, "28 days", "2 GB/day", "Unlimited", "100/day", listOf("Disney+ Hotstar Mobile"), true),
        RechargePlan("3", 549.0, "56 days", "2 GB/day", "Unlimited", "100/day", listOf("Disney+ Hotstar Mobile", "Apollo 24|7"), true),
        RechargePlan("4", 719.0, "84 days", "1.5 GB/day", "Unlimited", "100/day", listOf("Disney+ Hotstar Mobile"), false),
        RechargePlan("5", 999.0, "84 days", "24 GB total", "Unlimited", "100/day", listOf("Disney+ Hotstar Mobile", "Apollo 24|7", "Wynk Music"), true),
        RechargePlan("6", 1799.0, "365 days", "24 GB/year", "Unlimited", "100/day", listOf("Disney+ Hotstar Mobile", "Apollo 24|7", "Wynk Music", "hellotunes"), false)
    )

    val billProviders = listOf(
        BillProvider("1", "Airtel", "Mobile", "A", Color(0xFFE60000)),
        BillProvider("2", "Jio", "Mobile", "J", Color(0xFF0A2885)),
        BillProvider("3", "Vi", "Mobile", "V", Color(0xFFE60000)),
        BillProvider("4", "BSES Rajdhani", "Electricity", "B", Color(0xFF1976D2)),
        BillProvider("5", "Tata Power", "Electricity", "T", Color(0xFFE65100)),
        BillProvider("6", "Indraprastha Gas", "Gas", "I", Color(0xFFD32F2F)),
        BillProvider("7", "Mahanagar Gas", "Gas", "M", Color(0xFF388E3C)),
        BillProvider("8", "Tata Play", "DTH", "T", Color(0xFF6A1B9A)),
        BillProvider("9", "Dish TV", "DTH", "D", Color(0xFF00838F)),
        BillProvider("10", "Airtel Broadband", "Broadband", "A", Color(0xFFE60000)),
        BillProvider("11", "Jio Fiber", "Broadband", "J", Color(0xFF0A2885)),
        BillProvider("12", "HDFC Life", "Insurance", "H", Color(0xFF1565C0))
    )

    val insurancePlans = listOf(
        InsurancePlan("1", "Health Insurance", "HDFC ERGO", 500.0, 500000.0, "1 year", listOf("Cashless hospitals", "No claim bonus", "Free health checkup")),
        InsurancePlan("2", "Life Insurance", "Max Life", 800.0, 1000000.0, "20 years", listOf("Tax benefits", "Maturity benefit", "Accidental cover")),
        InsurancePlan("3", "Bike Insurance", "Bajaj Allianz", 200.0, 100000.0, "1 year", listOf("Zero dep", "Engine protection", "Roadside assistance")),
        InsurancePlan("4", "Car Insurance", "ICICI Lombard", 350.0, 500000.0, "1 year", listOf("Cashless garages", "Zero dep", "Towing service")),
        InsurancePlan("5", "Travel Insurance", "TATA AIG", 100.0, 50000.0, "Trip duration", listOf("Medical cover", "Baggage loss", "Trip cancellation"))
    )

    val investments = listOf(
        Investment("1", "Axis Bluechip Fund", "Mutual Fund", 25000.0, 28500.0, 3500.0, 14.0),
        Investment("2", "SBI Small Cap Fund", "Mutual Fund", 15000.0, 19200.0, 4200.0, 28.0),
        Investment("3", "Digital Gold", "Gold", 10000.0, 11500.0, 1500.0, 15.0),
        Investment("4", "HDFC Flexi Cap", "Mutual Fund", 8000.0, 9200.0, 1200.0, 15.0),
        Investment("5", "Stocks - TCS", "Equity", 30000.0, 35400.0, 5400.0, 18.0)
    )

    val offers = listOf(
        Offer("1", "Flat ₹150 Cashback", "Min purchase ₹999", "Flipkart", "₹150", "30 Jun 2026", null, Color(0xFF2874F0)),
        Offer("2", "Get 20% Off", "On first order", "Swiggy", "20%", "15 Jul 2026", null, Color(0xFFFC8019)),
        Offer("3", "Up to ₹500 Cashback", "Bill payment offer", "Airtel", "₹500", "20 Jun 2026", null, Color(0xFFE60000)),
        Offer("4", "Flat 10% Off", "On medicines", "Apollo Pharmacy", "10%", "31 Jul 2026", null, Color(0xFF00A39B)),
        Offer("5", "₹100 Cashback", "Recharge ₹299+", "Jio", "₹100", "10 Jun 2026", null, Color(0xFF0A2885)),
        Offer("6", "Buy 1 Get 1 Free", "Movie tickets", "BookMyShow", "BOGO", "25 Jun 2026", null, Color(0xFFE53935))
    )

    val quickActions = listOf(
        QuickAction("1", "Mobile Recharge", Icons.Filled.PhoneAndroid, Color(0xFF5F259F), "recharge"),
        QuickAction("2", "Electricity", Icons.Filled.Bolt, Color(0xFFFF9800), "electricity"),
        QuickAction("3", "DTH", Icons.Filled.Tv, Color(0xFF2196F3), "dth"),
        QuickAction("4", "Credit Card", Icons.Filled.CreditCard, Color(0xFFE53935), "credit_card")
    )

    val allServices = listOf(
        Service("1", "Mobile Recharge", "Prepaid/Postpaid", Icons.Filled.PhoneAndroid, Color(0xFF5F259F), route = "recharge"),
        Service("2", "Electricity", null, Icons.Filled.Bolt, Color(0xFFFF9800), route = "electricity"),
        Service("3", "Water Bill", null, Icons.Filled.WaterDrop, Color(0xFF2196F3), route = "water"),
        Service("4", "Gas Cylinder", null, Icons.Filled.LocalFireDepartment, Color(0xFFE53935), route = "gas"),
        Service("5", "DTH", null, Icons.Filled.Tv, Color(0xFF00BCD4), route = "dth"),
        Service("6", "Broadband", null, Icons.Filled.Wifi, Color(0xFF9C27B0), route = "broadband"),
        Service("7", "Credit Card", null, Icons.Filled.CreditCard, Color(0xFFE91E63), route = "credit_card"),
        Service("8", "Rent Payment", null, Icons.Filled.Home, Color(0xFF4CAF50), route = "rent"),
        Service("9", "Insurance", null, Icons.Filled.Shield, Color(0xFF3F51B5), route = "insurance", badge = "New"),
        Service("10", "Wealth", null, Icons.Filled.TrendingUp, Color(0xFFFF5722), route = "wealth"),
        Service("11", "Mutual Funds", null, Icons.Filled.AccountBalance, Color(0xFF009688), route = "mutual_funds"),
        Service("12", "Gold", null, Icons.Filled.MonetizationOn, Color(0xFFFFC107), route = "gold"),
        Service("13", "Travel", null, Icons.Filled.Flight, Color(0xFF673AB7), route = "travel"),
        Service("14", "Donations", null, Icons.Filled.VolunteerActivism, Color(0xFFE91E63), route = "donations"),
        Service("15", "FASTag", null, Icons.Filled.DirectionsCar, Color(0xFF795548), route = "fastag"),
        Service("16", "All Services", null, Icons.Filled.GridView, Color(0xFF607D8B), route = "all_services")
    )

    val stores = listOf(
        "Reliance Digital", "Croma", "Vijay Sales", "Samsung", "Apple",
        "Big Bazaar", "DMart", "More", "Spencer's",
        "Pizza Hut", "Domino's", "KFC", "McDonald's", "Subway",
        "Apollo Pharmacy", "MedPlus", "1mg",
        "Pantaloons", "Lifestyle", "Westside", "H&M", "Zara"
    )
}
