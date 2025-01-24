package com.example.safar.ai_Feature

data class TripRequest(
    val budget: String,
    val location: String,
    val days: String,
    val activities: String,
    val companion: String,
)