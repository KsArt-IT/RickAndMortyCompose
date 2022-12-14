package pro.ksart.rickandmorty.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterResponse(
    @Json(name = "error")
    val error: String? = "",
    @Json(name = "info")
    val info: Info,
    @Json(name = "results")
    val characterRams: List<CharacterRam>,
)
