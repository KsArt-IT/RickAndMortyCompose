package pro.ksart.rickandmorty.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class Info(
    val next: String? = null,
)
