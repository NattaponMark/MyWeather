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

package com.example.myapplication.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.task.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.myapplication.domain.usecase.Result


@AndroidEntryPoint
class MainActivity() : ComponentActivity() {


    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    responsiveLayout()
                }
            }
        }
    }

    @Composable
    fun responsiveLayout(){
        BoxWithConstraints {
            when {
                maxWidth < 600.dp -> {
                    phoneLayout()
                }
                maxWidth < 840.dp -> {
                    mediumLayout()
                }
                else -> {
                    tabletLayout()
                }
            }
        }
    }

    @Composable fun phoneLayout(){
        Row(modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
        ){
            MainNavigation(viewModel = viewModel)
        }
    }

    @Composable fun mediumLayout(){
        Row(modifier = Modifier.padding(bottom = 24.dp, top = 24.dp)
        ){
            MainNavigation(viewModel = viewModel)
        }
    }

    @Composable fun tabletLayout(){
        Row(modifier = Modifier.padding(bottom = 32.dp, top = 32.dp)
        ){
            MainNavigation(viewModel = viewModel)
        }
    }
}


