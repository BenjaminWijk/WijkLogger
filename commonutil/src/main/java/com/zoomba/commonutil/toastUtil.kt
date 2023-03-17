package com.zoomba.commonutil

import android.widget.Toast

const val DEFAULT_TOAST_LENGTH = Toast.LENGTH_LONG
fun toast(msg: String, duration: Int = DEFAULT_TOAST_LENGTH) {
    Toast.makeText(fromKoin(), msg, duration).show()
}

fun toast(msgResId: Int, duration:Int = DEFAULT_TOAST_LENGTH){
    Toast.makeText(fromKoin(), msgResId, duration).show()
}