package data.cache

import io.github.reactivecircus.cache4k.Cache
import models.Note

fun provideNotesCacheClient() = Cache.Builder<Long, Note>().build()