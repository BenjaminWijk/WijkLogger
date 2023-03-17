package com.zoomba.commonutil

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun <T> StateFlow<T>.bind(vararg key: Any?): State<T> = remember(*key) { this }.collectAsState()

@Composable
fun <T> Flow<T>.bind(init: T): State<T> = remember { this }.collectAsState(init)