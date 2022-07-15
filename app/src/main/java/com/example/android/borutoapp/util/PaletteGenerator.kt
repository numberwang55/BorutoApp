package com.example.android.borutoapp.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult

object PaletteGenerator {

    suspend fun convertImageUrlToBitmap(
        imageUrl: String,
        context: Context
    ): Bitmap? {
        val loader = ImageLoader(context = context)
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .allowHardware(false)
            .build()
        val imageResult = loader.execute(request = request)
        return if (imageResult is SuccessResult) {
            (imageResult.drawable as BitmapDrawable).bitmap
        } else {
            null
        }
    }

    fun extractColoursFromBitmap(bitmap: Bitmap): Map<String, String> {
        return mapOf(
            "vibrant" to parseColourSwatch(colour = Palette.from(bitmap).generate().vibrantSwatch),
            "darkVibrant" to parseColourSwatch(colour = Palette.from(bitmap).generate().darkVibrantSwatch),
            "onDarkVibrant" to parseBodyColour(Palette.from(bitmap).generate().darkVibrantSwatch?.bodyTextColor)
        )
    }

    private fun parseColourSwatch(colour: Palette.Swatch?): String {
        return if (colour != null) {
            val parsedColour = Integer.toHexString(colour.rgb)
            return "#$parsedColour"
        } else {
            "#000000"
        }
    }

    private fun parseBodyColour(colour: Int?): String {
        return if (colour != null) {
            val parsedColour = Integer.toHexString(colour)
            "#$parsedColour"
        } else {
            "#FFFFFF"
        }
    }

}