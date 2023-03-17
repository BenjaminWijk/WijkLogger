package com.zoomba.commonutil

import org.koin.java.KoinJavaComponent

//Just shorten inline injection from global Koin context a bit
inline fun <reified T : Any> fromKoin() = KoinJavaComponent.getKoin().get<T>()