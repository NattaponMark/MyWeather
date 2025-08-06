package com.example.myapplication.data.datasource

import com.example.myapplication.data.service.OpenWeatherService
import com.example.myapplication.domain.model.WeatherResponseModel
import com.example.myapplication.domain.usecase.Result
import jakarta.inject.Inject
import jakarta.inject.Named

class WeatherRemoteDatasource @Inject constructor(
    private val service: OpenWeatherService,
    @Named("API_KEY") private val apiKey: String
) {

    suspend fun getWeather(city: String): Result<WeatherResponseModel> {
        return try {
            val response = service.getCurrentWeather(city, apiKey = apiKey)

            when (response.code()) {
                in 200..299 -> {
                    val body = response.body()

                    if (body != null) {
                        Result.Success(body)
                    } else {
                        Result.Failure("Response body is null")
                    }
                }

                else -> Result.Errors("Api error", response.code()) as Result<WeatherResponseModel>
            }
        } catch (e: Exception) {
            Result.Failure(e.message)
        }
    }
}