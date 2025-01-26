package com.example.safar.navigation

import com.example.safar.screens.LoginScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.safar.ai_Feature.TripPlannerApp
import com.example.safar.screens.HomeScreen
import com.example.safar.screens.MapScreen
import com.example.safar.screens.ProfileScreen
import com.example.safar.screens.SignUpScreen
import com.google.android.gms.auth.api.signin.GoogleSignInClient


@Composable
fun AppNavGraph(navController: NavHostController, googleSignInClient: GoogleSignInClient) {    NavHost(navController = navController, startDestination = "signup") {
        composable("signup") { SignUpScreen(navController, googleSignInClient) }
        composable("login") { LoginScreen(navController, googleSignInClient) }
        composable("home") { HomeScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("ai_feature") { TripPlannerApp() }
        composable(
            route = "search/{location}",
            arguments = listOf(navArgument("location") { type = NavType.StringType })
        ) { backStackEntry ->
            val location = backStackEntry.arguments?.getString("location") ?: "Unknown Location"
            MapScreen(location = location, navController = navController)
        }
    }
}



