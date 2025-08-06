package com.example.myapplication.data.repository

import com.example.myapplication.data.datasource.WeatherRemoteDatasource
import jakarta.inject.Inject

class WeatherRepository @Inject constructor(private val remoteDatasource: WeatherRemoteDatasource) {

    suspend fun getWeather(
        city: String,
    ) = remoteDatasource.getWeather(city)
}