package ru.vk_hack.museumguide.utils

import android.graphics.Bitmap
import android.util.Base64
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.ByteArrayOutputStream

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

    fun convert(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, outputStream)
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
    }
}