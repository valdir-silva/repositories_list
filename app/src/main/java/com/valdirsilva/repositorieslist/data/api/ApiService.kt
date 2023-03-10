package com.valdirsilva.repositorieslist.data.api

import android.content.Context
import co.infinum.retromock.Retromock
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.valdirsilva.repositorieslist.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiService {

    private var intance: ApiService? = null

    lateinit var apiEndpoints: ApiEndpoints
    lateinit var mockEndpoints: MockEndpoints

    fun getInstance(context: Context): ApiService {
        if (intance == null) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(client)
                .build()

            val retromock: Retromock = Retromock.Builder()
                .retrofit(retrofit)
                .defaultBodyFactory(context.assets::open)
                .build()

            apiEndpoints = retrofit.create(ApiEndpoints::class.java)
            mockEndpoints = retromock.create(MockEndpoints::class.java)
        }
        return this
    }
}
