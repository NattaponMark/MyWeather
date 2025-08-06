/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.myapplication.ui.task

import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import com.example.myapplication.R
import com.example.myapplication.domain.model.WeatherResponseModel
import com.example.myapplication.domain.usecase.Result

@Composable
fun TaskScreen(
    modifier: Modifier = Modifier,
    viewModel: TaskViewModel = hiltViewModel(),
    weatherViewModel: WeatherViewModel = hiltViewModel()
) {
    val items by viewModel.uiState.collectAsStateWithLifecycle()
    val weather by weatherViewModel.weatherResponse.observeAsState()

    if (items is TaskUiState.Success) {
        TaskScreen(
            items = (items as TaskUiState.Success).data,
            onSave = { taskName ->
                viewModel.addTask(taskName)
            },
            weather = weather,
            onFetchWeather = { city ->
                weatherViewModel.fetchWeather(city)
            },
            modifier = modifier,
        )
    }
}


@Composable
internal fun TaskScreen(
    items: List<String>,
    onSave: (name: String) -> Unit,
    modifier: Modifier = Modifier.padding(16.dp),
    weather: Result<WeatherResponseModel>?,
    onFetchWeather: (city: String) -> Unit,
) {

    Column(modifier) {
        var nameTask by remember { mutableStateOf("") }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp, top = 36.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                value = nameTask,
                onValueChange = { nameTask = it },
                placeholder = {
                    Text(text = stringResource(id = R.string.text_enter_city))
                },
                modifier = Modifier.weight(1f)
            )

            Button(modifier = Modifier.width(96.dp), onClick = {
                onSave(nameTask)
                onFetchWeather(nameTask)
            }) {
                Text(stringResource(id = R.string.search_button))
            }
        }
        items.forEach {
        }

        weather.let { result ->
            when (result) {
                is Result.Success -> {
                    val weatherData = result.data
                    Text("City: ${weatherData?.name ?: stringResource(R.string.text_response_unknown)}")
                    Text("Temp: ${weatherData?.main?.temp ?: "--"}°C")
                    Text(
                        "Condition: ${
                            weatherData?.weather?.firstOrNull()?.description ?: stringResource(
                                R.string.text_response_unknown
                            )
                        }"
                    )
                }

                is Result.Failure -> {
                    Text("${stringResource(R.string.text_network_failure)}")
                    Text("Code: ${result.code}")
                    Text("Result: ${result.error}")
                }

                is Result.Errors -> {
                    Text("City: ${if (!nameTask.isNullOrEmpty()) nameTask else stringResource(R.string.text_response_errors)}")
                    Text("Code: ${result.code}")
                    Text("Result: ${result.data}")
                }

                is Result.Loading -> {

                }

                else -> {

                }
            }
        }
    }
}

// Previews
@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    MyApplicationTheme {
        TaskScreen(
            items = listOf("Compose", "Room", "Kotlin"),
            onSave = {},
            weather = null,
            onFetchWeather = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    MyApplicationTheme {
        TaskScreen(listOf("Compose", "Room", "Kotlin"),
            onSave = {},
            weather = null,
            onFetchWeather = {}
        )
    }
}


