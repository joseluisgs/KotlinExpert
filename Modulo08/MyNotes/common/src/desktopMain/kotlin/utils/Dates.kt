package utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


fun dateParser(date: Instant = Clock.System.now()): String {
    // Lo paso a LocalDateTime para poder formatearlo, usando el timezone del sistema, que no tiene que coicidir con el UTC
    // que es el que se usa para almacenar las fechas en la base de datos
    val ldt = LocalDateTime.ofInstant(date.toJavaInstant(), ZoneId.systemDefault())
    val dtf = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.MEDIUM)
    return dtf.format(ldt)
}


