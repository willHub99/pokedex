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


class PokemonAdapter() : RecyclerView.Adapter<ViewHolderPokemon>() {

    private lateinit var listPokemon: List<PokemonModel?>
    private lateinit var context: Context
    private lateinit var listener: SelectListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPokemon {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_list_pokemon, parent, false)

        return ViewHolderPokemon(view)
    }

    override fun onBindViewHolder(holder: ViewHolderPokemon, position: Int) {
        val pokemon = listPokemon[position]

        holder.tvNamePokemon.text = pokemon?.name.let { name ->
            name?.replaceFirstChar { firstChar ->
                firstChar.uppercase()
            }
        }.toString()

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

        setVisibilityViewInFirstPokemonList(position, holder.view)

    }

    fun setListPokemon(pokemonList: List<PokemonModel>) {
        this.listPokemon = pokemonList
        notifyDataSetChanged()
    }

    fun setContext(context: Context) {
        this.context = context
    }

    fun setListener(listener: SelectListener) {
        this.listener = listener
    }

    private fun setVisibilityViewInFirstPokemonList(position: Int, view: View) {
        if (position == 0) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return listPokemon.size
    }

    fun filter (listFilter: List<PokemonModel>) {
        this.listPokemon = listFilter
        notifyDataSetChanged()
    }

    private fun settingViewPokemonDetail(pokemon: PokemonModel, holder: ViewHolderPokemon) {
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