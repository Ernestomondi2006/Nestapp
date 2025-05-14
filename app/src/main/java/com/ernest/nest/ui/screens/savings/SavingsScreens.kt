package com.ernest.nest.ui.screens.savings

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.*
import com.ernest.nest.R
import com.ernest.nest.ui.theme.newblue
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

// 1. Room Entity
@Entity(tableName = "savings_table")
data class SavingsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String,
    val amount: Double,
    val timestamp: String
)

// 2. DAO
@Dao
interface SavingsDao {
    @Insert suspend fun insert(saving: SavingsEntity)
    @Query("SELECT * FROM savings_table ORDER BY id DESC") suspend fun getAll(): List<SavingsEntity>
}

// 3. Database
@Database(entities = [SavingsEntity::class], version = 1)
abstract class SavingsDatabase : RoomDatabase() {
    abstract fun savingsDao(): SavingsDao
    companion object {
        @Volatile private var INSTANCE: SavingsDatabase? = null
        fun getInstance(context: Context): SavingsDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    SavingsDatabase::class.java,
                    "savings_db"
                ).build().also { INSTANCE = it }
            }
    }
}

// 4. UI
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavingsScreen(navController: NavController) {
    val context = LocalContext.current
    val db = remember { SavingsDatabase.getInstance(context) }
    val dao = db.savingsDao()
    val scope = rememberCoroutineScope()

    // Form state
    var amountText by remember { mutableStateOf("") }
    val categories = listOf("Emergency Fund", "Vacation", "New Laptop", "Other")
    var selectedCategory by remember { mutableStateOf(categories[0]) }
    var dropdownExpanded by remember { mutableStateOf(false) }

    // Loaded savings
    var savingsList by remember { mutableStateOf(listOf<SavingsEntity>()) }

    // Load on start
    LaunchedEffect(Unit) { savingsList = dao.getAll() }

    // Compute total
    val totalSavings = remember(savingsList) { savingsList.sumOf { it.amount } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Savings") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1E88E5))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)

                .verticalScroll(rememberScrollState())
        ) {

            Text(
                "Overview",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            GoalCard(goalName = "Emergency Fund", current = 60_000, target = 100_000)
            Spacer(modifier = Modifier.height(12.dp))
            GoalCard(goalName = "Vacation", current = 25_000, target = 50_000)
            Spacer(modifier = Modifier.height(12.dp))
            GoalCard(goalName = "New Laptop", current = 15_000, target = 80_000)


            Spacer(Modifier.height(12.dp))

            ExposedDropdownMenuBox(
                expanded = dropdownExpanded,
                onExpandedChange = { dropdownExpanded = !dropdownExpanded }
            ) {
                OutlinedTextField(
                    value = selectedCategory,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false }
                ) {
                    categories.forEach { cat ->
                        DropdownMenuItem(
                            text = { Text(cat) },
                            onClick = {
                                selectedCategory = cat
                                dropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    val amt = amountText.toDoubleOrNull()
                    if (amt != null) {
                        val ts = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
                        scope.launch {
                            dao.insert(SavingsEntity(category = selectedCategory, amount = amt, timestamp = ts))
                            savingsList = dao.getAll()
                        }
                        amountText = ""
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(newblue)
            ) {
                Text("Save", color = Color.White)
            }


            Spacer(modifier = Modifier.height(24.dp))

            Text("Add a New Saving", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = amountText,
                onValueChange = { amountText = it },
                label = { Text("Amount (KSH)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            // Total Savings Card
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFBBDEFB))
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total Savings", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    Text("KSH ${"%.2f".format(totalSavings)}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(16.dp))

            Text("Your Savings Records", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(12.dp))

            savingsList.forEach { record ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(record.category, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(Modifier.height(4.dp))
                        Text("KSH ${record.amount}", fontSize = 14.sp)
                        Spacer(Modifier.height(4.dp))
                        Text(record.timestamp, fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}



@Composable
fun GoalCard(goalName: String, current: Int, target: Int) {
    val progress = current.toFloat() / target

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(goalName, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = newblue,
                trackColor = Color.LightGray
            )
            Text(
                "$current / $target saved",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SavingsScreenPreview() {
    SavingsScreen(navController = rememberNavController())
}
