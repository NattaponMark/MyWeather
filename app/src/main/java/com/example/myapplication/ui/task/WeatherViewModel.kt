package com.example.myapplication.ui.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.WeatherResponseModel
import com.example.myapplication.domain.usecase.WeatherUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import utils.NoConnectivityException
import utils.retryIO
import com.example.myapplication.domain.usecase.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class WeatherViewModel @Inject constructor(private val useCase: WeatherUseCase) : ViewModel() {

    private val _weatherResponse = MutableLiveData<Result<WeatherResponseModel>>()
    val weatherResponse: LiveData<Result<WeatherResponseModel>> = _weatherResponse


    open fun fetchWeather(city: String) {
        try {
            viewModelScope.launch {
                try {
                    withContext(Dispatchers.IO) {
                        retryIO(times = 3) {
                            useCase.getWeather(city)
                        }
                    }.apply {
                        _weatherResponse.value = this
                    }
                } catch (e: NoConnectivityException) {
                    _weatherResponse.value = Result.Failure(e.message)
                } catch (e: Exception) {
                    _weatherResponse.value = Result.Failure(e.message)
                }
            }
        } catch (e: Exception) {

        }
    }
}