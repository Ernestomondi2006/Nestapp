package com.ernest.nest.ui.screens.accounts

import androidx.lifecycle.viewmodel.compose.viewModel

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.*
import com.ernest.nest.navigation.ROUT_HOME
import com.ernest.nest.ui.theme.newblue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

// -------------------- DATA ------------------------

@Entity(tableName = "linked_accounts")
data class LinkedAccount(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val balance: Float
)

@Dao
interface LinkedAccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: LinkedAccount)

    @Query("SELECT * FROM linked_accounts")
    fun getAllAccounts(): Flow<List<LinkedAccount>>
}

@Database(entities = [LinkedAccount::class], version = 1)
abstract class LinkedAccountDatabase : RoomDatabase() {
    abstract fun linkedAccountDao(): LinkedAccountDao

    companion object {
        @Volatile private var INSTANCE: LinkedAccountDatabase? = null

        fun getDatabase(context: Application): LinkedAccountDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LinkedAccountDatabase::class.java,
                    "linked_accounts_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

// -------------------- VIEWMODEL ------------------------

class LinkedAccountViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = LinkedAccountDatabase.getDatabase(application).linkedAccountDao()
    val allAccounts: Flow<List<LinkedAccount>> = dao.getAllAccounts()

    fun addAccount(name: String, balance: Float) {
        viewModelScope.launch {
            dao.insert(LinkedAccount(name = name, balance = balance))
        }
    }
}

@Composable
fun rememberLinkedAccountViewModel(): LinkedAccountViewModel {
    val context = LocalContext.current.applicationContext as Application
    return viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return LinkedAccountViewModel(context) as T
        }
    })
}

// -------------------- UI ------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsScreen(navController: NavController) {
    val viewModel = rememberLinkedAccountViewModel()
    val accounts by viewModel.allAccounts.collectAsState(initial = emptyList())

    var name by remember { mutableStateOf("") }
    var balance by remember { mutableStateOf("") }
    val totalBalance = accounts.map { it.balance }.sum()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Accounts") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(ROUT_HOME) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = newblue,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AccountSummary(totalBalance = totalBalance)

                Text("Add Linked Account", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Account Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = balance,
                    onValueChange = { balance = it },
                    label = { Text("Balance") },
                    modifier = Modifier.fillMaxWidth(),

                )
                Button(
                    onClick = {
                        if (name.isNotBlank() && balance.toFloatOrNull() != null) {
                            viewModel.addAccount(name, balance.toFloat())
                            name = ""
                            balance = ""
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add Account")
                }

                Divider(modifier = Modifier.padding(vertical = 12.dp))

                Text("Linked Accounts", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)

                accounts.forEach { account ->
                    AccountItem(name = account.name, balance = account.balance)
                }
            }
        }
    )
}

@Composable
fun AccountSummary(totalBalance: Float) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFE0F7FA),
        tonalElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Total Balance", fontSize = 16.sp, color = Color.Gray)
            Text("KSH${totalBalance.toInt()}", fontSize = 24.sp, color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun AccountItem(name: String, balance: Float) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF5F5F5),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* optional details */ }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(name, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Text("KSH${balance.toInt()}", fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AccountsScreenPreview() {
    AccountsScreen(rememberNavController())
}
