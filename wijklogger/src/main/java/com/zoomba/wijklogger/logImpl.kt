package com.zoomba.wijklogger

import android.util.Log

class NativeWijkLogger : WijkLogger {

    override fun log(
        msg: String,
        level: WijkLogLevel,
        throwable: Throwable?,
        tag: String
    ) {
        if (!WijkLogger.enabledLogLevels.contains(level)) return

        when (level) {
            WijkLogLevel.VERBOSE -> Log.v(tag, msg)
            WijkLogLevel.DEBUG -> Log.d(tag, msg)
            WijkLogLevel.INFO -> Log.i(tag, msg)
            WijkLogLevel.WARN -> Log.w(tag, msg)
            WijkLogLevel.ERROR -> Log.e(tag, msg, throwable)
        }
    }
}



