package com.whuber.pokedex.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.whuber.pokedex.R
import com.whuber.pokedex.api.ListPokemonResult
import com.whuber.pokedex.constants.TypesPokemonConstants
import com.whuber.pokedex.core.State
import com.whuber.pokedex.databinding.ActivityPokemonDetailBinding
import com.whuber.pokedex.model.PokemonModel
import com.whuber.pokedex.utils.ColorsUtils
import com.whuber.pokedex.utils.ImageUtils
import com.whuber.pokedex.viewmodel.PokemonDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class PokemonDetailActivity : AppCompatActivity(), View.OnClickListener {

    private val binding: ActivityPokemonDetailBinding by lazy {
        ActivityPokemonDetailBinding.inflate(layoutInflater)
    }
    private val viewModel: PokemonDetailViewModel by viewModel()
    lateinit var pokemon: PokemonModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var bundle: Bundle ?= intent.extras
        if(bundle?.getInt("id") != null) {
            detailPokemon(bundle)
        }

        binding.ivButtonBack.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        if (view.id == R.id.iv_button_back) {
            finish()
        }
    }

    private fun detailPokemon(bundle: Bundle) {

        viewModel.getPokemon(bundle.getInt("id"))

        viewModel.pokemonResponse.observe(this, Observer<State<ListPokemonResult>> { result ->
            when(result) {
               State.Loading -> {
                   binding.pbDetail.visibility = View.VISIBLE
               }
               is State.Error -> {
                   binding.pbDetail.visibility = View.GONE
                }
                is State.Success -> {
                    binding.pbDetail.visibility = View.GONE
                    pokemon = convertListPokemonResultInPokemonModel(result.result)
                    configureViewDetailsPokemon()
                    bindingStatsPokemonIntoLinearProgressIndicator()
                    bindingColorIndicatorLinearProgressIndicator()
                    bindingValueProgressLinearProgressIndicator()
                }
            }
        })
    }

    private fun convertListPokemonResultInPokemonModel(pokemonResult: ListPokemonResult): PokemonModel {
        return pokemonResult.let { pokemon ->
            PokemonModel(
                pokemon.id,
                pokemon.name,
                pokemon.height,
                pokemon.weight,
                pokemon.types.map { type ->
                    type.type.type
                } as MutableList<String>,
                pokemon.stats
            )
        }
    }

    private fun bindingColorIndicatorLinearProgressIndicator() {
        val trackColorLinearProgress = TypesPokemonConstants(this).getTrackColorByTypePokemon(pokemon.types[0])
        binding.lpHp.trackColor = trackColorLinearProgress
        binding.lpAttack.trackColor = trackColorLinearProgress
        binding.lpDefense.trackColor = trackColorLinearProgress
        binding.lpSpecialAttack.trackColor = trackColorLinearProgress
        binding.lpSpecialDefense.trackColor = trackColorLinearProgress
        binding.lpSpeed.trackColor = trackColorLinearProgress
    }

    private fun bindingValueProgressLinearProgressIndicator() {
        binding.tvValueHp.text = pokemon.stats[0].stat.toString()
        binding.tvValueAttack.text = pokemon.stats[1].stat.toString()
        binding.tvValueDefense.text = pokemon.stats[2].stat.toString()
        binding.tvValueSpecialAttack.text = pokemon.stats[3].stat.toString()
        binding.tvValueSpecialDefense.text = pokemon.stats[4].stat.toString()
        binding.tvValueSpeed.text = pokemon.stats[5].stat.toString()
    }

    private fun bindingStatsPokemonIntoLinearProgressIndicator() {
        binding.lpHp.progress = pokemon.stats[0].stat
        binding.lpAttack.progress = pokemon.stats[1].stat
        binding.lpDefense.progress = pokemon.stats[2].stat
        binding.lpSpecialAttack.progress = pokemon.stats[3].stat
        binding.lpSpecialDefense.progress = pokemon.stats[4].stat
        binding.lpSpeed.progress = pokemon.stats[5].stat
    }

    private fun configureViewDetailsPokemon() {
        ImageUtils.setImagePokemon(this, pokemon.url, binding.ivPokemonPicture)
        val colorBackground = TypesPokemonConstants(this).getColorByTypePokemon(pokemon.types[0])
        ColorsUtils.changeBackgroundColorBoxDetailPokemon(binding.ivCardPokemonDetail, colorBackground)
        binding.tvNamePokemon.text = pokemon.name.let { name ->
            name.replaceFirstChar { firstChar ->
                firstChar.uppercase()
            }
        }.toString()
        ImageUtils.createImageTypePokemon(this, binding.llTypesPokemonDetail, pokemon.types)
    }
}