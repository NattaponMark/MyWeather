package com.example.myapplication.domain.model

data class WeatherDescResponseModel(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String,
)
