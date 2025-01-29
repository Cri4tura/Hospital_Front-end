package com.example.panacea.data

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = Date::class)
object DateSerializer : KSerializer<Date> {
    private val format = SimpleDateFormat("yyyy-MM-dd")

    override fun deserialize(decoder: Decoder): Date {
        val dateStr = decoder.decodeString()
        return format.parse(dateStr) ?: throw IllegalArgumentException("Invalid date format")
    }

    override fun serialize(encoder: Encoder, value: Date) {
        encoder.encodeString(format.format(value))
    }
}