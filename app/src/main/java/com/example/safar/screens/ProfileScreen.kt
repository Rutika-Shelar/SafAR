package com.example.safar.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.safar.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

@Composable
fun ProfileScreen(navController: NavHostController) {
    val user = FirebaseAuth.getInstance().currentUser
    if (user == null) {

        navController.navigate("login") {
            popUpTo("profile") { inclusive = true }
        }
    } else {
        ProfileContent(navController, user)
    }
}

@Composable
fun ProfileContent(navController: NavController, user: FirebaseUser) {
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = user.photoUrl?.let { rememberImagePainter(data = it) }
                    ?: painterResource(id = R.drawable.wallpaper),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = user.displayName ?: "Guest",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = user.email ?: "No Email",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { showDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0))
        ) {
            Text(text = "Edit Profile", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Account settings",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        SettingOption(text = "Personal information", onClick = { navController.navigate("personalInfo") })
        SettingOption(text = "Notifications", onClick = { navController.navigate("notifications") })
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Log out",
            color = Color.Red,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    logOut(navController)
                }
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
    }
}

fun logOut(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    auth.signOut()
    Toast.makeText(navController.context, "Logged out successfully", Toast.LENGTH_SHORT).show()
    navController.navigate("login") {
        popUpTo("profile") { inclusive = true }
    }
}

@Composable
fun SettingOption(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium
    )
}