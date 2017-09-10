package com.jaus.albertogiunta.giftimewaster.swipeactivity

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v7.widget.CardView
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.jaus.albertogiunta.giftimewaster.R
import com.jaus.albertogiunta.giftimewaster.networking.APIFactory
import com.jaus.albertogiunta.giftimewaster.networking.GiphyService
import com.vansuita.gaussianblur.GaussianBlur
import io.reactivex.android.schedulers.AndroidSchedulers
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.layoutInflater


class SwipeStackAdapter(private val context: Context, private val mData: List<String>) : BaseAdapter() {

    override fun getCount(): Int = mData.size

    override fun getItem(position: Int): String = mData[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val cv: View = convertView ?: context.layoutInflater.inflate(R.layout.card, parent, false)
        val ivGif = cv.findViewById<ImageView>(R.id.ivGif)
        loadGif(cv, ivGif)
        return cv
    }

    private fun loadGif(cv: View, ivGif: ImageView) {
        APIFactory
                .createRetrofitService(GiphyService::class.java)
                .randomKitty
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { (data) ->
                            run {
                                val cvv: CardView = cv.findViewById(R.id.cv)
                                Glide.with(context)
                                        .load(data.fixed_height_small_still_url)
                                        .asBitmap()
                                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                        .into(object : SimpleTarget<Bitmap>(350, 350) {
                                            override fun onResourceReady(bitmap: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
                                                val blurredBitmap: Bitmap = GaussianBlur.with(context).radius(25).render(bitmap)
                                                val ob = BitmapDrawable(context.resources, blurredBitmap)
                                                cvv.backgroundDrawable = ob
                                            }
                                        })

                                Glide.with(context)
                                        .load(data.fixed_height_small_url)
                                        .asGif()
                                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                        .into(ivGif)
                            }
                        }
                )
    }
}