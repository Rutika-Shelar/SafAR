package com.example.safar.ai_Feature

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripPlannerApp(viewModel: TripPlannerViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    var budget by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var days by remember { mutableStateOf("") }
    var activities by remember { mutableStateOf("") }
    var companion by remember { mutableStateOf("") }
    var showResultScreen by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Plan Your Trip",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
            )
        }
    ) { paddingValues ->
        if (showResultScreen) {
            TripResultScreen(response = viewModel.responseText) {
                showResultScreen = false
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    InputRow(label = "Budget", value = budget, onValueChange = { budget = it })
                }
                item {
                    InputRow(label = "Location", value = location, onValueChange = { location = it })
                }
                item {
                    InputRow(label = "Days", value = days, onValueChange = { days = it })
                }
                item {
                    InputRow(label = "Activities", value = activities, onValueChange = { activities = it })
                }
                item {
                    InputRow(label = "Companion", value = companion, onValueChange = { companion = it })
                }
                item {
                    OutlinedButton(
                        onClick = {
                            viewModel.planTrip(
                                TripRequest(
                                    budget = budget,
                                    location = location,
                                    days = days,
                                    activities = activities,
                                    companion = companion
                                )
                            ) {
                                showResultScreen = true
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFF2196F3)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Plan Trip", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
                item {
                    if (viewModel.loading) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            LinearProgressIndicator()
                        }
                    }
                }
                item {
                    if (viewModel.errorMessage.isNotEmpty()) {
                        Text(
                            text = viewModel.errorMessage,
                            color = Color.Red,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InputRow(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE3F2FD), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(text = label, fontSize = 12.sp, color = Color.Black, modifier = Modifier.padding(5.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(8.dp))
        )
    }
}

class TripPlannerViewModel : ViewModel() {
    var responseText by mutableStateOf("")
    var loading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    fun planTrip(request: TripRequest, onSuccess: () -> Unit) {
        viewModelScope.launch {
            loading = true
            errorMessage = ""
            try {
                val response = RetrofitInstance.api.planTrip(request)
                responseText = """
                    Description: ${response.description.orEmpty()}
                    Photo URL: ${response.photo.orEmpty()}
                    Day-wise Plan: ${response.dayWisePlan.orEmpty()}
                    Budget Distribution: ${response.budgetDistribution.orEmpty()}
                    Local Cuisines: ${response.localCuisines.orEmpty()}
                """.trimIndent()
                onSuccess()
            } catch (e: SocketTimeoutException) {
                errorMessage = "Request timed out. Please try again later."
            } catch (e: Exception) {
                errorMessage = "Error: ${e.message}"
            } finally {
                loading = false
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTripPlannerApp() {
    TripPlannerApp()
}
