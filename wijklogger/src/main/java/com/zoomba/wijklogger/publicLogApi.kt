package com.zoomba.wijklogger

import com.zoomba.wijklogger.WijkLogLevel.*

//msg function -> we only create the string when needed
//TODO: does not work when used in global functions. add non-extension versions of the log for those? might be confusing.
fun Any.logVerbose(tag: String = resolveTag(), lazyMsg: () -> String) =
    logInstance.log(lazyMsg(), VERBOSE, tag = tag)

fun Any.logDebug(tag: String = resolveTag(), lazyMsg: () -> String) =
    logInstance.log(lazyMsg(), DEBUG, tag = tag)

fun Any.logInfo(tag: String = resolveTag(), lazyMsg: () -> String) =
    logInstance.log(lazyMsg(), INFO, tag = tag)

fun Any.logWarn(tag: String = resolveTag(), lazyMsg: () -> String) =
    logInstance.log(lazyMsg(), WARN, tag = tag)

fun Any.logError(throwable: Throwable? = null, tag: String = resolveTag(), lazyMsg: () -> String) =
    logInstance.log(lazyMsg(), ERROR, throwable, tag)

interface WijkLoggerTag {
    val logTag: String
}

enum class WijkLogLevel {
    VERBOSE,
    DEBUG,
    INFO,
    WARN,
    ERROR;

    companion object {
        val values = values()
    }
}

/**
 * Intended to be used by extension functions (logInfo,logDebug etc), use those instead where applicable
 */

private var logInstance: WijkLogger = WijkLogger.DEFAULT_LOGGER

interface WijkLogger {

    companion object {
        val DEFAULT_LOGGER = NativeWijkLogger()

        var enabledLogLevels = hashSetOf<WijkLogLevel>()
            private set

        fun setLogLevel(level: WijkLogLevel) {
            enabledLogLevels = WijkLogLevel.values.mapNotNull {
                it.takeIf { it.ordinal >= level.ordinal }
            }.toHashSet()
        }

        fun setSpecificLogLevels(vararg levels: WijkLogLevel) {
            enabledLogLevels = hashSetOf(*levels)
        }

        fun setLogger(logger: WijkLogger) {
            logInstance = logger
        }
    }

    fun log(
        msg: String,
        level: WijkLogLevel,
        throwable: Throwable? = null,
        tag: String
    )
}

//Will work for pretty much everything except anonymous objects (e.g. "object: MyInterface { .... }"
fun Any.resolveTag(): String {
    return if (this is WijkLoggerTag)
        logTag
    else
        this::class.simpleName
            ?: "<UNRESOLVED TAG>"

}


