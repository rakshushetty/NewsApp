package com.test.newsapp.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.test.newsapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL


object Utils {
    /**
     * Loads the image data from data url into imageView
     *
     * @param data image url
     * @param imageView imageView instance to which image from data link is loaded
     */
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

    /**
     * Creates a notification channel for showing notification
     *
     * @param id channel id
     * @param name channel name
     * @param importance notification priority
     * @param context present context reference
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    fun makeNotificationChannel(id: String?, name: String?, importance: Int, context: Context) {
        val channel = NotificationChannel(id, name, importance)
        channel.setShowBadge(true)
        val notificationManager =
            (context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager?)!!
        notificationManager.createNotificationChannel(channel)
    }
}