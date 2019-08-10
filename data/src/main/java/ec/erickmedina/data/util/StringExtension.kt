package ec.erickmedina.data.util

import java.lang.NumberFormatException

fun String.nextIndex(): String =
    try {
        this.toInt().plus(1).toString()
    } catch (e: NumberFormatException) {
        "1"
    }