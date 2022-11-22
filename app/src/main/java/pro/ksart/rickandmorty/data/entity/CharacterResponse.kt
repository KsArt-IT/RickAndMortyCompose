package pro.ksart.rickandmorty.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterResponse(
    @Json(name = "results")
    val characterRams: List<CharacterRam>
)
