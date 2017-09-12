package com.jaus.albertogiunta.giftimewaster.networking


import com.jaus.albertogiunta.giftimewaster.model.Gif
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyService {

    companion object {
        val API_KEY = "1bbd4d4318ec4f67afce073a4e2772c1"
        val RATING = "g"
        val SERVICE_ENDPOINT = "http://api.giphy.com/v1/gifs/"
    }

    @GET("random?")
    fun random(
            @Query("api_key") apiKey: String,
            @Query("rating") rating: String,
            @Query("tag") tag: String
    ): Observable<Gif>

}
