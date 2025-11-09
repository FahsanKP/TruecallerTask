package com.example.truecallerassignment.di

import com.example.truecallerassignment.data.remote.adapter.NetworkResultCallAdapterFactory
import com.example.truecallerassignment.data.remote.api.WebContentApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.truecaller.com/") // Base URL
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())// For plain text
            .addCallAdapterFactory(NetworkResultCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideWebContentApi(retrofit: Retrofit): WebContentApi {
        return retrofit.create(WebContentApi::class.java)
    }
}