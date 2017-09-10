package com.jaus.albertogiunta.giftimewaster.networking

import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory



object APIFactory {

    fun <T> createRetrofitService(clazz: Class<T>): T {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

        val gson = GsonBuilder()
                .create()

        val restAdapter = Retrofit.Builder()
                .baseUrl(GiphyService.SERVICE_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(client)
                .build()

        return restAdapter.create(clazz)
    }

}
