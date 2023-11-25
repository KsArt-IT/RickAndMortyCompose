package pro.ksart.rickandmorty.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class CharacterOrigin(
    val name: String,
    val url: String
)
