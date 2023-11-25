package pro.ksart.rickandmorty.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EpisodeResponse(
    @SerialName("error")
    val error: String? = "",
    @SerialName("info")
    val info: Info,
    @SerialName("results")
    val episodes: List<Episode>,
)
