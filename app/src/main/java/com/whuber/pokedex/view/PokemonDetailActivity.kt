package com.whuber.pokedex.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.whuber.pokedex.R
import com.whuber.pokedex.api.ListPokemonResult
import com.whuber.pokedex.constants.TypesPokemonConstants
import com.whuber.pokedex.databinding.ActivityPokemonDetailBinding
import com.whuber.pokedex.model.PokemonModel
import com.whuber.pokedex.utils.ColorsUtils
import com.whuber.pokedex.utils.ImageUtils
import com.whuber.pokedex.viewmodel.PokemonDetailViewModel


class PokemonDetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityPokemonDetailBinding
    private lateinit var viewModel: PokemonDetailViewModel
    lateinit var pokemon: PokemonModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = PokemonDetailViewModel()

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

        viewModel.getPokemon(bundle!!.getInt("id"))

        viewModel.pokemonResponse.observe(this, Observer<ListPokemonResult> { result ->
            Log.d("detailPokemon", result.toString())
            pokemon = convertListPokemonResultInPokemonModel(result)
            configureViewDetailsPokemon()
            bindingStatsPokemonIntoLinearProgressIndicator()
            bindingColorIndicatorLinearProgressIndicator()
            bindingValueProgressLinearProgressIndicator()
        })
    }

    private fun convertListPokemonResultInPokemonModel(pokemonResult: ListPokemonResult): PokemonModel {
        return pokemonResult?.let {pokemon ->
            PokemonModel(
                pokemon.id,
                pokemon.name,
                pokemon.height,
                pokemon.weight,
                pokemon.types.map {type ->
                    type.type.type
                } as MutableList<String>,
                pokemon.stats
            )
        }!!
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
        val stringResourceHpStat: String = getString(R.string.attribute_hp)
        binding.tvHp.text = stringResourceHpStat + " " + pokemon.stats[0].stat
        val stringResourceAttackStat: String = getString(R.string.attribute_attack)
        binding.tvAttack.text = stringResourceAttackStat + " " + pokemon.stats[1].stat
        val stringResourceDefenseStat: String = getString(R.string.attribute_defense)
        binding.tvDefense.text = stringResourceDefenseStat + " " +  pokemon.stats[2].stat
        val stringResourceSpecialAttackStat: String = getString(R.string.attribute_special_attack)
        binding.tvSpecialAttack.text = stringResourceSpecialAttackStat + " " +  pokemon.stats[3].stat
        val stringResourceSpecialDefenseStat: String = getString(R.string.attribute_special_defense)
        binding.tvSpecialDefense.text = stringResourceSpecialDefenseStat + " " +  pokemon.stats[4].stat
        val stringResourceSpeedStat: String = getString(R.string.attribute_speed)
        binding.tvSpeed.text = stringResourceSpeedStat + " " +  pokemon.stats[5].stat
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
        binding.tvNamePokemon.text = pokemon.name
        ImageUtils.createImageTypePokemon(this, binding.llTypesPokemonDetail, pokemon.types)
    }
}