package data.cache

import io.github.reactivecircus.cache4k.Cache
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import models.Note

fun provideNotesCacheClient() = Cache.Builder<Long, Note>().build()