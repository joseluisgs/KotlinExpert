package utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*


fun dateParser(date: Instant = Clock.System.now(), locale: Locale = Locale.getDefault()): String {
    val ldt = LocalDateTime.ofInstant(date.toJavaInstant(), ZoneId.systemDefault())
    val dtf = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.MEDIUM)
    return dtf.format(ldt)
}