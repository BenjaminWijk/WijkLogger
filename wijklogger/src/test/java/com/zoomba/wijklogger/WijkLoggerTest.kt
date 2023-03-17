package com.zoomba.wijklogger

import com.zoomba.wijklogger.WijkLogger.LogLevel.*
import org.junit.Test

internal class WijkLoggerTest {

    @Test
    fun textLogLevel_upTo() {
        fun assertContains(input: WijkLogger.LogLevel, vararg expected: WijkLogger.LogLevel) {
            fun Collection<WijkLogger.LogLevel>.enumString() = joinToString { it.name }
            
            val actual = WijkLogger.LogLevel.upTo(input)

            val missing = expected.mapNotNull { it.takeIf { !actual.contains(it) } }

            assert(expected.all { actual.contains(it) }) {
                "Input $input did not contain all expected values. " +
                        "\n\tExpected: ${expected.toList().enumString()}. " +
                        "\n\tActual: ${actual.enumString()}" +
                        "\n\tMissing: ${missing.enumString()}"

            }
            assert(actual.size == expected.size) { "Expected size ${expected.size}, was ${actual.size}" }
        }

        assertContains(ERROR, ERROR)
        assertContains(WARN, ERROR, WARN)
        assertContains(INFO, ERROR, WARN, INFO)
        assertContains(DEBUG, ERROR, WARN, INFO, DEBUG)
        assertContains(VERBOSE, ERROR, WARN, INFO, DEBUG, VERBOSE)
    }

}