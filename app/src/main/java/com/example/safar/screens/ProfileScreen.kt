package com.example.veezar.screens

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
import com.example.veezar.R
import com.google.firebase.auth.FirebaseAuth
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

@Composable
fun ProfileScreen(navController: NavHostController) {
    val user = FirebaseAuth.getInstance().currentUser
    ProfileContent(navController, user)
}

@Composable
fun ProfileContent(navController: NavController, user: FirebaseUser?) {
    var showDialog by remember { mutableStateOf(false) }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    var description by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(user) {
        user?.uid?.let { userId ->
            val db = FirebaseFirestore.getInstance()
            db.collection("users").document(userId).get().addOnSuccessListener { document ->
                description = document.getString("description") ?: "Nothing"
            }
        }
    }

    if (showDialog) {
        EditProfileDialog(
            user = user,
            onDismiss = { showDialog = false },
            onSave = { newUri, newDescription ->
                profileImageUri = newUri
                description = newDescription
                updateProfile(user, newUri, newDescription, context)
                showDialog = false
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = user?.photoUrl?.let { rememberImagePainter(data = it) }
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
                    text = user?.displayName ?: "Guest",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = description,
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
    val googleSignInClient = GoogleSignIn.getClient(navController.context, GoogleSignInOptions.DEFAULT_SIGN_IN)

    auth.signOut()
    googleSignInClient.signOut().addOnCompleteListener {
        googleSignInClient.revokeAccess().addOnCompleteListener {
            Toast.makeText(navController.context, "Logged out successfully", Toast.LENGTH_SHORT).show()
            navController.navigate("login")
        }
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

@Composable
fun EditProfileDialog(user: FirebaseUser?, onDismiss: () -> Unit, onSave: (Uri?, String) -> Unit) {
    var newDescription by remember { mutableStateOf("") }
    var newProfileImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        newProfileImageUri = uri
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Edit Profile") },
        text = {
            Column {
                TextField(
                    value = newDescription,
                    onValueChange = { newDescription = it },
                    label = { Text("Description") },
                    isError = newDescription.split(" ").size > 20
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { launcher.launch("image/*") }) {
                    Text("Select Profile Image")
                }
                newProfileImageUri?.let {
                    Image(
                        painter = rememberImagePainter(data = it),
                        contentDescription = "New Profile Image",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (newDescription.split(" ").size <= 20) {
                        onSave(newProfileImageUri, newDescription)
                    } else {
                        Toast.makeText(context, "Description should be 20 words or less", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

fun updateProfile(user: FirebaseUser?, newProfileImageUri: Uri?, newDescription: String, context: android.content.Context) {
    val db = FirebaseFirestore.getInstance()
    val storage = FirebaseStorage.getInstance()
    val userId = user?.uid ?: return

    if (newProfileImageUri != null) {
        val storageRef = storage.reference.child("profile_images/$userId.jpg")
        storageRef.putFile(newProfileImageUri).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                db.collection("users").document(userId)
                    .update("profileImageUrl", uri.toString(), "description", newDescription)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Failed to update profile", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    } else {
        db.collection("users").document(userId)
            .update("description", newDescription)
            .addOnSuccessListener {
                Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to update profile", Toast.LENGTH_SHORT).show()
            }
    }
}