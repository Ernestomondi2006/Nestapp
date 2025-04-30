package com.ernest.nest.ui.screens.splash

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ernest.nest.R
import com.ernest.nest.navigation.ROUT_LOGIN
import com.ernest.nest.ui.theme.newblue
import com.ernest.nest.ui.theme.newblue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SplashScreen(navController: NavController){

    //Navigation

    val coroutine = rememberCoroutineScope()
    coroutine.launch {

        delay(2000)
        navController.navigate(ROUT_LOGIN)

    }

    //End of Navigation

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(newblue),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){


        Image(
            painter = painterResource(R.drawable.finance),
            contentDescription = "home",
            modifier = Modifier
                .size(300.dp)
                .clip(shape = CircleShape),
            contentScale = ContentScale.Crop


        )

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "NEST",
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold

        )
        Spacer(modifier = Modifier.height(25.dp))
        Text(

            text = "Manage all your finances Effectively",
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold

        )













    }


}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview(){
    SplashScreen(rememberNavController())

}