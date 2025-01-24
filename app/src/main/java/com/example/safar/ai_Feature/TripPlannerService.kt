package com.example.safar.ai_Feature

import com.example.safar.ai_Feature.TripRequest
import com.example.safar.ai_Feature.TripResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface TripPlannerService {
    @POST("plan_trip")
    suspend fun planTrip(@Body request: TripRequest): TripResponse
}
