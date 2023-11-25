package pro.ksart.rickandmorty.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class CharacterLocation(
    val name: String,
    val url: String,
)
