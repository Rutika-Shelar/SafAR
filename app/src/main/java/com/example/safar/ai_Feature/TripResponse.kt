package com.example.veezar.ai_Feature

import com.google.gson.annotations.SerializedName

data class TripResponse(
    @SerializedName("budget_distribution") val budgetDistribution: String?,
    @SerializedName("day_wise_plan") val dayWisePlan: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("local_cuisines") val localCuisines: String?,
    @SerializedName("nearby_places") val nearbyPlaces: String?,
    @SerializedName("photo") val photo: String?,
    @SerializedName("trip_plan") val tripPlan: String?
)