package com.denisyordanp.mymoviecatalogue.di

import com.denisyordanp.mymoviecatalogue.network.MovieService
import com.denisyordanp.mymoviecatalogue.tools.NetworkConfig
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideOkHttp(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient.Builder =
        OkHttpClient.Builder().apply {
            readTimeout(NetworkConfig.timeout(), TimeUnit.SECONDS)
            connectTimeout(NetworkConfig.timeout(), TimeUnit.SECONDS)
            if (NetworkConfig.isDebugMode()) {
                addNetworkInterceptor(loggingInterceptor)
            }
            val key = NetworkConfig.getAuthKey()
            addInterceptor(Interceptor { chain ->
                val requestBuilder: okhttp3.Request.Builder = chain.request().newBuilder()
                requestBuilder.header("Authorization", "Bearer $key")
                return@Interceptor chain.proceed(requestBuilder.build())
            })
        }

    @Provides
    fun provideRetrofit(
        gsonConverter: GsonConverterFactory,
    ): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(NetworkConfig.getBaseUrl())
            .addConverterFactory(gsonConverter)

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

    @Provides
    fun provideGsonConverter(): GsonConverterFactory =
        GsonConverterFactory.create(
            GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
        )

    @Provides
    fun provideSoliteServices(
        retrofit: Retrofit.Builder,
        okHttpBuilder: OkHttpClient.Builder
    ): MovieService {
        return retrofit
            .client(okHttpBuilder.build())
            .build().create(MovieService::class.java)
    }
}
