package com.whuber.pokedex.utils

import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.core.widget.ImageViewCompat

class ColorsUtils {

    companion object {

        fun changeBackgroundColorBoxDetailPokemon(ivDetailPokemon: ImageView, color: Int) {
            val colorState = ColorStateList.valueOf(color)
            ImageViewCompat.setImageTintList(ivDetailPokemon, colorState)
        }

    }

}