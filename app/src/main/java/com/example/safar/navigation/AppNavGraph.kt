package com.example.safar.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.safar.screens.LoginScreen
import com.example.safar.screens.HomeScreen
import com.example.safar.screens.ProfileScreen
import com.example.safar.screens.EditProfileScreen
import com.example.safar.ai_Feature.TripPlannerApp
import com.example.safar.screens.SignUpScreen
import com.google.android.gms.auth.api.signin.GoogleSignInClient

@Composable
fun AppNavGraph(
    navController: NavHostController,
    googleSignInClient: GoogleSignInClient,
    startDestination: String
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("signup") { SignUpScreen(navController, googleSignInClient) }
        composable("login") { LoginScreen(navController, googleSignInClient) }
        composable("home") { HomeScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("ai_feature") { TripPlannerApp() }
        composable("editProfile") { EditProfileScreen(navController) }
    }
}