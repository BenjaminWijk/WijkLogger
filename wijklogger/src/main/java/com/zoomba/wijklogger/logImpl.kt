package com.zoomba.wijklogger

import android.util.Log

class NativeWijkLogger : WijkLogger {

    override fun log(
        msg: String,
        level: LogLevel,
        throwable: Throwable?,
        tag: String
    ) {
        if (!WijkLogger.enabledLogLevels.contains(level)) return

        when (level) {
            LogLevel.VERBOSE -> Log.v(tag, msg)
            LogLevel.DEBUG -> Log.d(tag, msg)
            LogLevel.INFO -> Log.i(tag, msg)
            LogLevel.WARN -> Log.w(tag, msg)
            LogLevel.ERROR -> Log.e(tag, msg, throwable)
        }
    }
}



