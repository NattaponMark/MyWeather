package com.example.myapplication.domain.usecase

import com.example.myapplication.data.repository.WeatherRepository
import jakarta.inject.Inject

class WeatherUseCase @Inject constructor(private val repository: WeatherRepository) {

    suspend fun getWeather(city: String) = repository.getWeather(city)
}