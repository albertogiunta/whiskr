package com.jaus.albertogiunta.giftimewaster.networking


import com.jaus.albertogiunta.giftimewaster.model.Gif
import io.reactivex.Observable
import retrofit2.http.GET

interface GiphyService {

    companion object {
        //        val APIKEY = "dc6zaTOxFJmzC"
        val APIKEY = "1bbd4d4318ec4f67afce073a4e2772c1"
        val SERVICE_ENDPOINT = "http://api.giphy.com/v1/gifs/"
    }

    @get:GET("random?api_key=1bbd4d4318ec4f67afce073a4e2772c1&rating=G&tag=cat+cute")
    val randomKitty: Observable<Gif>

}
