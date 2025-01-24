package com.example.safar.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.safar.R
import com.example.safar.components.BottomAppComposable
import com.example.safar.components.SearchBox
import com.example.safar.components.TravelCards
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

fun getProfileImageUrl(): String? {
    val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    return user?.photoUrl?.toString()
}

@Composable
fun HomeScreen(navController: NavController) {
    val profileImageUrl = getProfileImageUrl() ?: ""
    val username = FirebaseAuth.getInstance().currentUser?.displayName ?: "User"
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column {
            Column(
                modifier = Modifier.padding(start = 30.dp, top = 30.dp, end = 30.dp),
                horizontalAlignment = Alignment.Start
            ) {
           com.example.safar.components.ProfileHeader(
    profileImageUrl = profileImageUrl,
    username = username
)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(id = R.string.tile),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
SearchBox(
    placeholder = "Where to?",
    onSearch = { location: String ->
        navController.navigate("search/$location")
    }
)
            }
            Column(
                modifier = Modifier.padding(start = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    Modifier.horizontalScroll(rememberScrollState())
                ) {
                    TravelCards(
                        drawable = R.drawable.tajmahal,
                        head = stringResource(id = R.string.taj_mahal2),
                        description = stringResource(id = R.string.wonder2)
                    )
                    TravelCards(
                        drawable = R.drawable.download,
                        head = stringResource(id = R.string.taj_mahal),
                        description = stringResource(id = R.string.wonder)
                    )
                    TravelCards(
                        drawable = R.drawable.tajmahal,
                        head = stringResource(id = R.string.taj_mahal2),
                        description = stringResource(id = R.string.wonder)
                    )
                }
            }
            Box(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                    BottomAppComposable(navController)
                    BottomAppComposable(navController)
                }
            }
        }
    }
}
