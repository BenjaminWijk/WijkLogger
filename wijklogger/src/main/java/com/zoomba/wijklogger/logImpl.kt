package com.zoomba.wijklogger

import android.util.Log
import com.zoomba.wijklogger.WijkLogger.LogLevel
import com.zoomba.wijklogger.WijkLogger.LogLevel.*

class NativeWijkLogger : WijkLogger {

    override fun log(
        msg: String,
        level: LogLevel,
        throwable: Throwable?,
        tag: String
    ) {
        if (WijkLogger.levelDisabled(level)) return

        when (level) {
            VERBOSE -> Log.v(tag, msg)
            DEBUG -> Log.d(tag, msg)
            INFO -> Log.i(tag, msg)
            WARN -> Log.w(tag, msg)
            ERROR -> Log.e(tag, msg, throwable)
        }
    }
}



