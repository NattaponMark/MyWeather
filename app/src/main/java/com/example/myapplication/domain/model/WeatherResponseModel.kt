package com.example.myapplication.domain.model

data class WeatherResponseModel(
    val coord: CoordResponseModel,
    val weather: List<WeatherDescResponseModel>,
    val main: MainResponseModel,
    val wind: WindResponseModel,
    val name: String,
)
