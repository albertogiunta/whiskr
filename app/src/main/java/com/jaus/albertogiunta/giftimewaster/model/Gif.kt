package com.jaus.albertogiunta.giftimewaster.model

data class Gif(var data: Data)
data class Data(var image_url: String,
                var fixed_height_downsampled_url: String,
                var fixed_height_small_url: String,
//                var image_width: Int,
//                var image_height: Int,
                var fixed_height_small_still_url: String)