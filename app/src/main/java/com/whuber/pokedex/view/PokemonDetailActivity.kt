package com.whuber.pokedex.view

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.whuber.pokedex.R
import com.whuber.pokedex.constants.TypesPokemonConstants
import com.whuber.pokedex.databinding.ActivityPokemonDetailBinding
import com.whuber.pokedex.model.PokemonModel
import com.whuber.pokedex.utils.ColorsUtils
import com.whuber.pokedex.utils.ImageUtils
import com.whuber.pokedex.viewmodel.PokemonDetailViewModel


class PokemonDetailActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityPokemonDetailBinding
    lateinit var viewModel: PokemonDetailViewModel
    lateinit var pokemon: PokemonModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = PokemonDetailViewModel()

        var bundle: Bundle ?= intent.extras
        if(bundle!!.getInt("id") != null) {
            Thread(Runnable {
                detailPokemon(bundle)
            }).start()
        }

        binding.ivButtonBack.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        if (view.id == R.id.iv_button_back) {
            finish()
        }
    }

    fun detailPokemon(bundle: Bundle) {

        pokemon = viewModel.getPokemon(bundle!!.getInt("id"))
        runOnUiThread {
            ImageUtils.setImagePokemon(this, pokemon.url, binding.ivPokemonPicture)
            val colorBackground = TypesPokemonConstants(this).getColorByTypePokemon(pokemon.types[0])
            ColorsUtils.changeBackgroundColorBoxDetailPokemon(binding.ivCardPokemonDetail, colorBackground)

            val trackColorLinearProgress = TypesPokemonConstants(this).getTrackColorByTypePokemon(pokemon.types[0])

            binding.lpHp.progress = pokemon.stats[0].stat
            binding.lpHp.trackColor = trackColorLinearProgress

            binding.lpAttack.progress = pokemon.stats[1].stat
            binding.lpAttack.trackColor = trackColorLinearProgress

            binding.lpDefense.progress = pokemon.stats[2].stat
            binding.lpDefense.trackColor = trackColorLinearProgress

            binding.lpSpecialAttack.progress = pokemon.stats[3].stat
            binding.lpSpecialAttack.trackColor = trackColorLinearProgress

            binding.lpSpecialDefense.progress = pokemon.stats[4].stat
            binding.lpSpecialDefense.trackColor = trackColorLinearProgress

            binding.lpSpeed.progress = pokemon.stats[5].stat
            binding.lpSpeed.trackColor = trackColorLinearProgress

            binding.tvNamePokemon.text = pokemon.name

            ImageUtils.createImageTypePokemon(this, binding.llTypesPokemonDetail, pokemon.types)

        }

    }
}