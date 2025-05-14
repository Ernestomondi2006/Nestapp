package com.ernest.nest.ui.screens.budget

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.*
import com.ernest.nest.navigation.ROUT_HOME
import com.ernest.nest.ui.theme.newblue
import kotlinx.coroutines.launch

// 1. Entity
@Entity(tableName = "budget_table")
data class BudgetEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val month: String,
    val amount: Float
)

// 2. DAO
@Dao
interface BudgetDao {
    @Insert suspend fun insert(budget: BudgetEntity)
    @Query("SELECT * FROM budget_table ORDER BY id DESC") suspend fun getAll(): List<BudgetEntity>
}

// 3. Database
@Database(entities = [BudgetEntity::class], version = 1)
abstract class BudgetDatabase : RoomDatabase() {
    abstract fun budgetDao(): BudgetDao

    companion object {
        @Volatile private var INSTANCE: BudgetDatabase? = null

        fun getInstance(context: Context): BudgetDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    BudgetDatabase::class.java,
                    "budget_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}

// 4. UI Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreen(navController: NavController) {
    val context = LocalContext.current
    val db = remember { BudgetDatabase.getInstance(context) }
    val dao = db.budgetDao()
    val scope = rememberCoroutineScope()

    var monthText by remember { mutableStateOf("") }
    var amountText by remember { mutableStateOf("") }
    var budgetList by remember { mutableStateOf(listOf<BudgetEntity>()) }

    LaunchedEffect(Unit) {
        budgetList = dao.getAll()
    }

    val maxAmount = budgetList.maxOfOrNull { it.amount } ?: 1f

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Budget") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = newblue,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Handle other navigation */ },
                containerColor = newblue
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Budget", tint = Color.White)
            }
        },
        bottomBar = {
            NavigationBar(containerColor = newblue) {
                NavigationBarItem(
                    selected = true,
                    onClick = { navController.navigate(ROUT_HOME) },
                    icon = { Icon(Icons.Default.Add, contentDescription = "Home") },
                    label = { Text("Home") }
                )
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Enter Monthly Budget", fontSize = 18.sp, fontWeight = FontWeight.Bold)

                OutlinedTextField(
                    value = monthText,
                    onValueChange = { monthText = it },
                    label = { Text("Month (e.g. May 2025)") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = amountText,
                    onValueChange = { amountText = it },
                    label = { Text("Amount (KSH)") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        val amount = amountText.toFloatOrNull()
                        if (!monthText.isBlank() && amount != null) {
                            scope.launch {
                                dao.insert(BudgetEntity(month = monthText, amount = amount))
                                budgetList = dao.getAll()
                                monthText = ""
                                amountText = ""
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(newblue)
                ) {
                    Text("Save Budget")
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text("Saved Budgets", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)

                budgetList.forEach {
                    val percentage = (it.amount / maxAmount) * 100
                    BudgetSummaryCard(month = it.month, amount = it.amount, percentage = percentage)
                }
            }
        }
    )
}

@Composable
fun BudgetSummaryCard(month: String, amount: Float, percentage: Float) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF5F5F5),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Month: $month", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Budget Amount: KSH ${amount.toInt()}", fontSize = 14.sp)
            }

            Box(
                modifier = Modifier
                    .size(64.dp)
                    .padding(start = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = (percentage / 100f).coerceIn(0f, 1f),
                    color = newblue,
                    strokeWidth = 6.dp
                )
                Text(
                    text = "${percentage.toInt()}%",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BudgetScreenPreview() {
    BudgetScreen(rememberNavController())
}
