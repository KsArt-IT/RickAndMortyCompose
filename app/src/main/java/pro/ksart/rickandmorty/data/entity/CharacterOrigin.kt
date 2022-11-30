package pro.ksart.rickandmorty.data.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterOrigin(
    val name: String,
    val url: String
)
