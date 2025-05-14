package com.ernest.nest.ui.screens.reports

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ernest.nest.navigation.ROUT_HOME
import com.ernest.nest.ui.theme.newblue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reports") },
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
                    .padding(16.dp)
            ) {
                Text(
                    "Monthly Summary",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                SummaryCard("Total Income", "KSH200,000", Color(0xFFD0F8CE))
                Spacer(modifier = Modifier.height(12.dp))
                SummaryCard("Total Expenses", "KSH125,000", Color(0xFFFFCDD2))
                Spacer(modifier = Modifier.height(12.dp))
                SummaryCard("Net Savings", "KSH75,000", Color(0xFFC8E6C9))

                Spacer(modifier = Modifier.height(24.dp))
                Text("Spending Breakdown", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)

                Spacer(modifier = Modifier.height(16.dp))
                ChartPlaceholder("Spending Chart")

                Spacer(modifier = Modifier.height(24.dp))
                Text("Top Categories", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                CategoryItem("Food", "KSH45,000")
                CategoryItem("Transport", "KSH25,000")
                CategoryItem("Bills", "KSH30,000")
            }
        }
    )
}

@Composable
fun SummaryCard(title: String, amount: String, backgroundColor: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(title, color = Color.DarkGray, fontSize = 14.sp)
            Text(amount, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ChartPlaceholder(label: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text("$label (Chart Coming Soon)", color = Color.DarkGray)
    }
}

@Composable
fun CategoryItem(category: String, amount: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(category, fontSize = 16.sp)
        Text(amount, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun ReportsScreenPreview() {
    ReportsScreen(rememberNavController())
}
