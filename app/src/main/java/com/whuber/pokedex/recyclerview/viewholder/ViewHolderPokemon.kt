package com.whuber.pokedex.recyclerview.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.whuber.pokedex.R

class ViewHolderPokemon(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val ivPokemonPhoto: ImageView = itemView.findViewById(R.id.iv_pokemon)
    val ivBoxDetailPokemon: ImageView = itemView.findViewById(R.id.iv_box_detail_pokemon)
    val tvNamePokemon: TextView = itemView.findViewById(R.id.tv_name_pokemon)
    val tvWeight: TextView = itemView.findViewById(R.id.tv_weight_value)
    val tvHeight: TextView = itemView.findViewById(R.id.tv_height_value)
    val llTypesPokemons: LinearLayout = itemView.findViewById(R.id.ll_types_pokemon)


}