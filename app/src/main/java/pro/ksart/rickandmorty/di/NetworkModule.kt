package pro.ksart.rickandmorty.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pro.ksart.rickandmorty.data.network.CharacterService
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Named("InterceptorForOkHttpClient")
    @Provides
    fun provideLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun provideOkHttpClient(
        @Named("InterceptorForOkHttpClient") interceptor: Interceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor)
            .retryOnConnectionFailure(false) // not necessary but useful!
            .build()
    }

    // Kotlinx Serialization Converter
    @Named("KotlinXJson")
    @Provides
    fun providerConverter(): Converter.Factory = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }.asConverterFactory("application/json".toMediaType())

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @Named("KotlinXJson") converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(CharacterService.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideCharacterService(retrofit: Retrofit): CharacterService = retrofit.create()

}
