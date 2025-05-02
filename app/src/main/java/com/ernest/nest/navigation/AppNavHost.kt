package com.ernest.nest.navigation


import android.provider.ContactsContract.Profile
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ernest.nest.ui.screens.intent.IntentScreen
import com.ernest.nest.data.UserDatabase
import com.ernest.nest.repository.UserRepository
import com.ernest.nest.ui.screens.accounts.AccountsScreen
import com.ernest.nest.ui.screens.auth.LoginScreen
import com.ernest.nest.ui.screens.auth.RegisterScreen
import com.ernest.nest.ui.screens.budget.BudgetScreen
import com.ernest.nest.ui.screens.contact.ContactScreen
import com.ernest.nest.ui.screens.home.HomeScreen
import com.ernest.nest.ui.screens.profile.ProfileScreen
import com.ernest.nest.ui.screens.reports.ReportsScreen
import com.ernest.nest.ui.screens.savings.SavingsScreen
import com.ernest.nest.ui.screens.settings.SettingsScreen
import com.ernest.nest.ui.screens.splash.SplashScreen
import com.ernest.nest.viewmodel.AuthViewModel
import okhttp3.internal.http2.Settings
import android.window.SplashScreen as SplashScreen1

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUT_SPLASH
) {
    val context = LocalContext.current


    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(ROUT_HOME) {
            HomeScreen(navController)
        }


        composable(ROUT_ACCOUNTS) {
            AccountsScreen(navController)
        }
        composable(ROUT_CONTACT) {
            BudgetScreen(navController)
        }
        composable(ROUT_CONTACT) {
            ContactScreen(navController)
        }
        composable(ROUT_REPORTS) {
          ReportsScreen(navController)
        }
        composable(ROUT_SAVINGS) {
            SavingsScreen(navController)
        }
        composable(ROUT_SETTINGS) {
            SettingsScreen(navController)
        }
        composable(ROUT_PROFILE) {
            ProfileScreen(navController)
        }
        composable(ROUT_SPLASH) {
            SplashScreen(navController)
        }
        composable(ROUT_BUDGET) {
            BudgetScreen(navController)
        }
        composable(ROUT_BUDGET) {
            IntentScreen(navController)
        }






        //AUTHENTICATION


        // Initialize Room Database and Repository for Authentication
        val appDatabase = UserDatabase.getDatabase(context)
        val authRepository = UserRepository(appDatabase.userDao())
        val authViewModel: AuthViewModel = AuthViewModel(authRepository)
        composable(ROUT_REGISTER) {
            RegisterScreen(authViewModel, navController) {
                navController.navigate(ROUT_LOGIN) {
                    popUpTo(ROUT_REGISTER) { inclusive = true }
                }
            }
        }

        composable(ROUT_LOGIN) {
            LoginScreen(authViewModel, navController) {
                navController.navigate(ROUT_HOME) {
                    popUpTo(ROUT_LOGIN) { inclusive = true }
                }
            }
        }

    }
}