package com.ernest.nest.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ernest.nest.R
import com.ernest.nest.navigation.ROUT_LOGIN
import com.ernest.nest.ui.theme.newblue
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    // Navigate after delay
    LaunchedEffect(Unit) {
        delay(2500)
        navController.navigate(ROUT_LOGIN) {
            popUpTo(0) { inclusive = true }
        }
    }

    // Diagonal cut card
    val diagonalShape = GenericShape { size, _ ->
        moveTo(0f, 0f)
        lineTo(size.width, 0f)
        lineTo(size.width, size.height * 0.85f)
        lineTo(0f, size.height)
        close()
    }

    // Top wave shape (creative)
    val waveShape = GenericShape { size, _ ->
        moveTo(0f, size.height * 0.5f)
        quadraticBezierTo(size.width / 2, 0f, size.width, size.height * 0.5f)
        lineTo(size.width, 0f)
        lineTo(0f, 0f)
        close()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(newblue,Color(0xFF2138B5))
                )
            )
    ) {
        // Wave banner at top
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(waveShape)
                .background(Color.White.copy(alpha = 0.1f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Main card with image

                Image(
                    painter = painterResource(id = R.drawable.ic_chart),
                    contentDescription = "Finance Logo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(200.dp)
                )


            Spacer(modifier = Modifier.height(24.dp))

            // App Name
            Text(
                text = "NEST",
                fontSize = 42.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Tagline with emphasis
            Text(
                text = buildAnnotatedString {
                    append("Plan Smart. ")
                    withStyle(SpanStyle(color = Color.Yellow, fontWeight = FontWeight.Bold)) {
                        append("Spend Wisely. ")
                    }
                    append("Save More.")
                },
                fontSize = 18.sp,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center,
                lineHeight = 24.sp,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Themed badges/icons (budget, goals, tracking)
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconBadge(R.drawable.ic_goal, "Goals")
                IconBadge(R.drawable.ic_chart, "Tracking")
                IconBadge(R.drawable.ic_wallet, "Budget")
            }

            Spacer(modifier = Modifier.height(30.dp))

            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 3.dp,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun IconBadge(iconRes: Int, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            color = Color.White.copy(alpha = 0.1f),
            tonalElevation = 4.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = label,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 12.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(navController = rememberNavController())
}
