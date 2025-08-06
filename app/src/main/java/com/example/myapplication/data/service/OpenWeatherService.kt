package com.example.myapplication.data.service

import com.example.myapplication.domain.model.WeatherResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") unit: String = "metric",
    ): Response<WeatherResponseModel>
}