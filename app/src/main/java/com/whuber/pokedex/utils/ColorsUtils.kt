package com.whuber.pokedex.utils

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.core.widget.ImageViewCompat

class ColorsUtils {

    companion object {

        fun changeBackgroundColorBoxDetailPokemon(context: Context, ivDetailPokemon: ImageView, color: Int) {
            val colorState = ColorStateList.valueOf(color)
            ImageViewCompat.setImageTintList(ivDetailPokemon, colorState)
        }

    }

}