package com.jaus.albertogiunta.giftimewaster.swipeactivity

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v7.widget.CardView
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.jaus.albertogiunta.giftimewaster.R
import com.jaus.albertogiunta.giftimewaster.networking.APIFactory
import com.jaus.albertogiunta.giftimewaster.networking.GiphyService
import com.jaus.albertogiunta.giftimewaster.utils.Sources
import com.vansuita.gaussianblur.GaussianBlur
import io.reactivex.android.schedulers.AndroidSchedulers
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.layoutInflater
import trikita.log.Log


class SwipeStackAdapter(private val context: Context, private val data: MutableList<String>) : BaseAdapter() {

    override fun getCount(): Int = data.size

    override fun getItem(position: Int): String = data[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val cardLayout: View = convertView ?: context.layoutInflater.inflate(R.layout.card, parent, false)
        val ivGif = cardLayout.findViewById<ImageView>(R.id.ivGif)
        loadGif(cardLayout, ivGif)
        return cardLayout
    }

    private fun loadGif(cardLayout: View, ivGif: ImageView) {
        val tags: String = Sources.newTagCombination()
        val cvBottom: CardView = cardLayout.findViewById(R.id.cvBottom)
        val tvTags: TextView = cvBottom.findViewById(R.id.tvTags)
        tvTags.text = Sources.stringToTags(tags)

        APIFactory
                .createRetrofitService(GiphyService::class.java)
                .random(apiKey = GiphyService.API_KEY, rating = GiphyService.RATING, tag = tags)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { (data) ->
                            run {
                                val cvTop: CardView = cardLayout.findViewById(R.id.cvTop)
                                Glide.with(context)
                                        .load(data.fixed_height_small_still_url)
                                        .asBitmap()
                                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                        .into(object : SimpleTarget<Bitmap>(350, 350) {
                                            override fun onResourceReady(bitmap: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
                                                val blurredBitmap: Bitmap = GaussianBlur.with(context).radius(25).render(bitmap)
                                                val ob = BitmapDrawable(context.resources, blurredBitmap)
                                                cvTop.backgroundDrawable = ob
                                            }
                                        })

                                Glide.with(context)
                                        .load(data.fixed_height_small_url)
                                        .asGif()
                                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                        .into(ivGif)
                            }
                        },
                        { error -> Log.e("loadGif: $error") }
                )
    }
}