package com.example.weatherapp.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

/**
 * Use this in xml for bindng images
 * first parameter in view, second one is url of image and third one for thumbnail
 * Here we have used cache for images so we can see them offline (Glide Cahche Strategy)
 */
@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, url: String?) {
    if (url != null) {
        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(imageView.context).load(url)
            .apply(requestOptions).into(imageView)
    }
}