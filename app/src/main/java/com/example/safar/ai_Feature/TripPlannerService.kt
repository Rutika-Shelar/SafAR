package com.example.safar.ai_Feature

import com.example.veezar.ai_Feature.TripRequest
import com.example.veezar.ai_Feature.TripResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface TripPlannerService {
    @POST("plan_trip")
    suspend fun planTrip(@Body request: TripRequest): TripResponse
}
