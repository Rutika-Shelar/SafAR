package com.example.safar.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.safar.R

@Composable
fun SmallTextComposable(value: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = value,
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 28.sp,
                fontFamily = FontFamily(Font(R.font.manrope)),
                fontWeight = FontWeight(700),
            ),
            modifier = Modifier
                .heightIn(min = 40.dp),
            color = colorResource(id = R.color.blue),
        )
    }
}
@Composable
fun CardDescription(value: String) {
    Box(
        modifier = Modifier.fillMaxWidth().padding(8.dp,0.dp,0.dp,0.dp),
        contentAlignment = Alignment.TopStart) {
        Text(
            text = value,
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 28.sp,
                fontFamily = FontFamily(Font(R.font.manrope)),
                fontWeight = FontWeight(700),
            ),
            modifier = Modifier
                .heightIn(min = 40.dp),
            color = colorResource(id = R.color.blue),
        )
    }
}


@Composable
fun CardHeading(value: String) {
    Box(
        modifier = Modifier.fillMaxWidth().padding(8.dp,0.dp,0.dp,0.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Text(
            text = value,
            style = TextStyle(
                fontSize = 24.sp,
                lineHeight = 28.sp,
                fontFamily = FontFamily(Font(R.font.manrope)),
                fontWeight = FontWeight(700),
                color = Color(0xFF0D141C),
            ),
            modifier = Modifier
                .heightIn(min = 40.dp),
            color = colorResource(id = R.color.black),
        )
    }
}

@Composable
fun HeadingTextComposable(value: String) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = value,
            style = TextStyle(
                fontSize = 30.sp,
                lineHeight = 28.sp,
                fontFamily = FontFamily(Font(R.font.manrope)),
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D141C),
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier
                .heightIn(min = 40.dp),
            color = colorResource(id = R.color.black),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MyTextFieldComponent(value: String, onValueChange: (String) -> Unit, placeholder: String) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(placeholder, color = Color(0xFF4F7396))
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFE8EDF2),
            unfocusedContainerColor = Color(0xFFE8EDF2),
            disabledContainerColor = Color(0xFFE8EDF2),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        modifier = Modifier
            .width(390.dp)
            .padding(12.dp)
            .background(
                Color(0xFFE8EDF2),
                RoundedCornerShape(12.dp)
            )
    )
}

@Composable
fun PasswordTextFieldComponent(value: String, onValueChange: (String) -> Unit, placeholder: String) {
    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(placeholder, color = Color(0xFF4F7396))
        },
        keyboardOptions = KeyboardOptions(keyboardType = if (passwordVisible) KeyboardType.Text else KeyboardType.Password),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible) painterResource(id = R.drawable.baseline_visibility_24) else painterResource(id = R.drawable.baseline_visibility_off_24)
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(painter = image, contentDescription = null)
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFE8EDF2),
            unfocusedContainerColor = Color(0xFFE8EDF2),
            disabledContainerColor = Color(0xFFE8EDF2),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        modifier = Modifier
            .width(390.dp)
            .padding(12.dp)
            .background(
                Color(0xFFE8EDF2),
                RoundedCornerShape(12.dp)
            )
    )
}

@Composable
fun ProfileHeader(profileImageUrl: String, username: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        if (profileImageUrl.isNotEmpty()) {
            Image(
                painter = rememberImagePainter(data = profileImageUrl),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
        } else {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
               Text(
    text = username.first().toString(),
    style = MaterialTheme.typography.titleLarge,
    color = Color.White
)
}
Spacer(modifier = Modifier.width(8.dp))
Text(
    text = username,
    style = MaterialTheme.typography.titleLarge,
    color = Color.Black
)
    }
}
}
@Composable
fun ButtonComponent(value: String, onClick: () -> Unit = {}) {
    Button(
        onClick = onClick,
        colors = buttonColors(
            colorResource(id = R.color.blue_600)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clip(RoundedCornerShape(8.dp))
            .sizeIn(minWidth = 150.dp)
    ) {
        Text(
            text = value,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
@Composable
fun SearchBox(onSearch: (String) -> Unit, placeholder: String) {
    var text by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .width(366.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFE8EDF2))
    ) {
        val containerColor = Color(0xFFE8EDF2)
        TextField(
            value = text,
            onValueChange = { text = it },
            placeholder = {
                Text(text = "Search", color = Color(0xFF4F7396))
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color(0xFF4F7396)
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = containerColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(text)
                    keyboardController?.hide()
                }
            )
        )
    }
}

@Composable
fun TravelCards(drawable: Int, head: String, description: String){
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(390.dp)
                .width(260.dp)
        ) {
            // Background Image
            val image: Painter = painterResource(drawable)
            Image(
                painter = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
                    .background(Color.White, RoundedCornerShape(16.dp)),
            ) {
                CardHeading(head)
                CardDescription(description)
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun TravelCardsPreview() {
    TravelCards(
        drawable = R.drawable.tajmahal,
        head = stringResource(id = R.string.taj_mahal2),
        description = stringResource(id = R.string.wonder2)
    )
}
@Composable
fun BottomAppComposable(navController: NavController) {
    BottomAppBar(
        containerColor = Color(0xFFE8EDF2),
        modifier = Modifier.background(Color.White),
        contentColor = Color.Black,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { navController.navigate("home") }) {
                Icon(
                    painter = painterResource(id = R.drawable.explore),
                    contentDescription = null,
                    tint = Color(0xFF4F7396)
                )
            }

            IconButton(onClick = { navController.navigate("ai_feature") }) {
                Icon(
                    painter = painterResource(id = R.drawable.trips),
                    contentDescription = null,
                    tint = Color(0xFF4F7396)
                )
            }
//            IconButton(onClick = { /*TODO*/ }) {
//                Icon(
//                    painter = painterResource(id = R.drawable.inbox),
//                    contentDescription = null,
//                    tint = Color(0xFF4F7396)
//                )
//            }
            IconButton(onClick = { navController.navigate("profile") }) {
                Icon(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = null,
                    tint = Color(0xFF4F7396)
                )

            }
        }
    }
}

