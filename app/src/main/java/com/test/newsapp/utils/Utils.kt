package com.test.newsapp.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.test.newsapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

object Utils {
    fun setImageData(data: String?, imageView: ImageView) {
        if (data.isNullOrEmpty()) imageView.setImageResource(R.color.imageBackGround)
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val imageUrl = URL(data)
                val bitmap =
                    BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream())
                withContext(Dispatchers.Main) {
                    imageView.setImageBitmap(
                        Bitmap.createScaledBitmap(
                            bitmap,
                            100,
                            100,
                            false
                        )
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}