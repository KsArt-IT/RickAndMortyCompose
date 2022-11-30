package pro.ksart.rickandmorty.data.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterDetail(
    val id: Int,
    val name: String,
    val image: String,
    val created: String,
    val episode: List<String>,
    val gender: String,
    val location: CharacterLocation,
    val origin: CharacterOrigin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)
/*
{
  "id": 2,
  "name": "Morty Smith",
  "status": "Alive",
  "species": "Human",
  "type": "",
  "gender": "Male",
  "origin": {
    "name": "Earth",
    "url": "https://rickandmortyapi.com/api/location/1"
  },
  "location": {
    "name": "Earth",
    "url": "https://rickandmortyapi.com/api/location/20"
  },
  "image": "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
  "episode": [
    "https://rickandmortyapi.com/api/episode/1",
    "https://rickandmortyapi.com/api/episode/2",
    // ...
  ],
  "url": "https://rickandmortyapi.com/api/character/2",
  "created": "2017-11-04T18:50:21.651Z"
}
 */
