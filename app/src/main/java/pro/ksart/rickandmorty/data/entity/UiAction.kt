package pro.ksart.rickandmorty.data.entity

sealed class UiAction<out T : Any> {

    class Click<out T : Any>(val data: T) : UiAction<T>()

    object Scroll : UiAction<Nothing>()

}
