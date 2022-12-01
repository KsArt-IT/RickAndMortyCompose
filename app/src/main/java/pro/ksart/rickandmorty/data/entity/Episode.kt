package pro.ksart.rickandmorty.data.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Episode(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val url: String,
    val created: String,
)
