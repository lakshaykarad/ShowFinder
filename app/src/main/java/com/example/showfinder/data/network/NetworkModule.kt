package com.example.showfinder.data.model.network

import com.example.showfinder.data.network.OmdbApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    /*
    This is most importent part of code and with some complexity
    where we make okhttpClient Retrofit and access ApiServices
    */
    // provideOkHttpClient // provideRetrofit // provideApiService

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    @Named("DefaultRetrofit")
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            //bcaab -> shortcut to remmeber all the properties
            .baseUrl(ApiConstants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(
        @Named("DefaultRetrofit") retrofit: Retrofit
    ): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    // OMBD Builder
    @Provides
    @Singleton
    @Named("OMDbRetrofit")
    fun provideOMDbRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.OMDB_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    // Api Service of OmbdApiService
    @Provides
    @Singleton
    fun provideOmdbService(
        @Named("OMDbRetrofit") retrofit: Retrofit
    ): OmdbApiService {
        return retrofit.create(OmdbApiService::class.java)
    }

}