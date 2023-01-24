package com.whuber.pokedex.utils

import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.core.view.marginStart
import androidx.core.view.setPadding
import com.bumptech.glide.Glide
import com.whuber.pokedex.R
import com.whuber.pokedex.constants.ImagePokemonConstants


class ImageUtils {

    companion object {
        fun setImagePokemon(context: Context, url: String, ivPokemon: ImageView) {
            Glide.with(context)
                .load(url)
                .into(ivPokemon)
        }

        fun createImageTypePokemon(context: Context, layout: LinearLayout, listTypes: List<String>){

            val viewGroupParams: ViewGroup.LayoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            var imagePokemonConstants: ImagePokemonConstants = ImagePokemonConstants(context)

            for (type in listTypes) {
                var image: ImageView = ImageView(context)
                image.layoutParams = viewGroupParams
                image.layoutParams.height = 128
                image.layoutParams.width = 128
                image.setPadding(13 )


                var params: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(image.layoutParams)
                params.setMargins(100, 0,100,0)

                var imageResource: Int = imagePokemonConstants.getImageByTypePokemon(type)
                image.setImageResource(imageResource)

                layout.addView(image)
            }
        }
    }

}