package com.example.primarydetail.di

import com.example.primarydetail.services.ApiService
import com.example.primarydetail.util.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {

    factory { provideOkHttpClient() }

    factory { provideApiService(get()) }

    single { provideRetrofit(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
}

fun provideOkHttpClient(): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient().newBuilder()
        .addInterceptor(logging)
        .build()
}

fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
