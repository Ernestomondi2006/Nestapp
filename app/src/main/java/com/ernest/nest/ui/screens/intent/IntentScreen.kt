package com.ernest.nest.ui.screens.intent

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ernest.nest.R
import com.ernest.nest.ui.theme.newblue
import com.ernest.nest.ui.theme.newbwhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntentScreen(navController: NavController) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Help & Support", color = newbwhite) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = newbwhite)
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("ROUT_SETTINGS") }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings", tint = newbwhite)
                    }
                    IconButton(onClick = { navController.navigate("ROUT_BUDGET") }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Budget", tint = newbwhite)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = newblue)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_goal), // Ensure you have a help image in res/drawable
                contentDescription = "Help Illustration",
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Need Help Managing Your Budget?",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "• Add your total income in the Home section.\n" +
                        "• Track your expenses in real time under Budget.\n" +
                        "• Set savings goals to grow your money.\n" +
                        "• View reports to improve your financial habits.",
                fontSize = 16.sp,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    val shareIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "Check out Nest Budget App — it's helping me manage my money better!")
                        type = "text/plain"
                    }
                    context.startActivity(Intent.createChooser(shareIntent, "Share Nest via"))
                },
                colors = ButtonDefaults.buttonColors(containerColor = newblue),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Share, contentDescription = "Share", tint = newbwhite)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Share App", color = newbwhite)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Need further assistance?",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "Email us: support@nestapp.com",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IntentScreenPreview() {
    IntentScreen(rememberNavController())
}
