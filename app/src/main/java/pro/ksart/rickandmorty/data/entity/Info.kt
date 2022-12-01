package pro.ksart.rickandmorty.data.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Info(
    val next: String? = null,
)
