package com.whuber.pokedex.recyclerview.adapater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.whuber.pokedex.R
import com.whuber.pokedex.constants.TypesPokemonConstants
import com.whuber.pokedex.model.PokemonModel
import com.whuber.pokedex.recyclerview.`interface`.SelectListener
import com.whuber.pokedex.recyclerview.viewholder.ViewHolderPokemon
import com.whuber.pokedex.utils.ColorsUtils
import com.whuber.pokedex.utils.ImageUtils


class PokemonAdapter(
    private var listPokemons: List<PokemonModel?>,
    private val context: Context,
    private val listener: SelectListener
) : RecyclerView.Adapter<ViewHolderPokemon>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPokemon {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_list_pokemon, parent, false)

        return ViewHolderPokemon(view)
    }

    override fun onBindViewHolder(holder: ViewHolderPokemon, position: Int) {
        val pokemon = listPokemons[position]

        holder.tvNamePokemon.text = pokemon?.name
        holder.tvWeight.text = pokemon?.weight.toString()
        holder.tvHeight.text = pokemon?.height.toString()

        if (pokemon != null) {
            settingViewPokemonDetail(pokemon, holder)
        }

        holder.ivBoxDetailPokemon.setOnClickListener(View.OnClickListener() {
            if (pokemon != null) {
                listener.onItemClick(pokemon)
            }
        })


    }

    override fun getItemCount(): Int {
        return listPokemons.size
    }

    fun filter (listFilter: List<PokemonModel>) {
        this.listPokemons = listFilter
        notifyDataSetChanged()
    }

    fun settingViewPokemonDetail(pokemon: PokemonModel, holder: ViewHolderPokemon) {
        //change background box detail pokemon
       var color = TypesPokemonConstants(context).getColorByTypePokemon(pokemon.types[0])
       ColorsUtils.changeBackgroundColorBoxDetailPokemon(holder.ivBoxDetailPokemon, color)

        //set image pokemon
        ImageUtils.setImagePokemon(context, pokemon.url, holder.ivPokemonPhoto)

        holder.llTypesPokemons.removeAllViews()
        //set type pokemon
        ImageUtils.createImageTypePokemon(context, holder.llTypesPokemons, pokemon.types)
    }
}