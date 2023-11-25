package pro.ksart.rickandmorty.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class CharacterRam(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val location: CharacterLocation,
    val image: String,
    val url: String,
    val created: String,
)
