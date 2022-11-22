package pro.ksart.rickandmorty.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import pro.ksart.rickandmorty.data.repository.CharacterRepositoryImpl
import pro.ksart.rickandmorty.domain.repository.CharacterRepository

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    @ViewModelScoped
    fun provideMoviesRepository(impl: CharacterRepositoryImpl): CharacterRepository
}
