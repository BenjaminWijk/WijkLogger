package com.zoomba.wijklogger

import com.zoomba.wijklogger.WijkLogger.LogLevel.*
import com.zoomba.wijklogger.WijkLogger.LoggerTag


//msg function -> we only create the string when needed
//TODO: does not work when used in global functions. add non-extension versions of the log for those? might be confusing.
fun Any.logVerbose(tag: String = resolveTag(), lazyMsg: () -> String) =
    wijkLogInstance.log(lazyMsg(), VERBOSE, tag = tag)

fun Any.logDebug(tag: String = resolveTag(), lazyMsg: () -> String) =
    wijkLogInstance.log(lazyMsg(), DEBUG, tag = tag)

fun Any.logInfo(tag: String = resolveTag(), lazyMsg: () -> String) =
    wijkLogInstance.log(lazyMsg(), INFO, tag = tag)

fun Any.logWarn(tag: String = resolveTag(), lazyMsg: () -> String) =
    wijkLogInstance.log(lazyMsg(), WARN, tag = tag)

fun Any.logError(throwable: Throwable? = null, tag: String = resolveTag(), lazyMsg: () -> String) =
    wijkLogInstance.log(lazyMsg(), ERROR, throwable, tag)


/**
 * Intended to be used by extension functions (logInfo,logDebug etc), use those instead where applicable
 */
var wijkLogInstance: WijkLogger = WijkLogger.DEFAULT_LOGGER
    private set

interface WijkLogger {

    companion object {
        val DEFAULT_LOGGER = NativeWijkLogger()
        private val DEFAULT_LOG_LEVEL = LogLevel.upTo(DEBUG)

        fun setLogger(logger: WijkLogger) {
            wijkLogInstance = logger
        }

        private var enabledLogLevels = DEFAULT_LOG_LEVEL

        fun levelEnabled(level: LogLevel): Boolean = enabledLogLevels.contains(level)
        fun levelDisabled(level: LogLevel): Boolean = !levelEnabled(level)

        fun setLogLevel(level: LogLevel) {
            enabledLogLevels = LogLevel.upTo(level)
        }

        fun setSpecificLogLevels(vararg levels: LogLevel) {
            enabledLogLevels = hashSetOf(*levels)
        }
    }

    fun log(
        msg: String,
        level: LogLevel,
        throwable: Throwable? = null,
        tag: String
    )

    interface LoggerTag {
        val logTag: String
    }

    enum class LogLevel {
        VERBOSE,
        DEBUG,
        INFO,
        WARN,
        ERROR;

        companion object {
            val values = values()

            /**
             * Get set only including the specified level + the ones "below" it. (The visual order of the enum values)
             * e.g. upTo(WARN) will include ERROR and WARN
             * e.g. upTo(VERBOSE) will include all levels, since it's the "highest"
             */
            fun upTo(level: LogLevel): HashSet<LogLevel> =
                values.mapNotNull {
                    it.takeIf { it.ordinal >= level.ordinal }
                }.toHashSet()
        }
    }
}

//Will work for pretty much everything except anonymous objects (e.g. "object: MyInterface { .... }"
fun Any.resolveTag(): String {
    return if (this is LoggerTag)
        logTag
    else
        this::class.simpleName
            ?: "<UNRESOLVED TAG>"

}


