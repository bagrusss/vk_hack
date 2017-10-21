package ru.vk_hack.museumguide.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by bagrusss on 21.10.17
 */
object ImageUtils {

    @JvmStatic
    fun loadImage(iv: ImageView, url: String) {
        Glide.with(iv.context)
             .load(url)
             .centerCrop()
             .into(iv)
    }
}