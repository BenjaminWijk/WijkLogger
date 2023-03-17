package com.zoomba.commonutil

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun inMainScope(block: suspend CoroutineScope.() -> Unit): Job = CoroutineScope(Dispatchers.Main).launch { block() }

fun inIoScope(block: suspend CoroutineScope.() -> Unit): Job = CoroutineScope(Dispatchers.IO).launch { block() }