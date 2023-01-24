package com.whuber.pokedex.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.whuber.pokedex.R
import com.whuber.pokedex.api.PokemonModelResponse
import com.whuber.pokedex.databinding.ActivityMainBinding
import com.whuber.pokedex.model.PokemonModel
import com.whuber.pokedex.recyclerview.`interface`.SelectListener
import com.whuber.pokedex.recyclerview.adapater.PokemonAdapter
import com.whuber.pokedex.utils.UrlUtils
import com.whuber.pokedex.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), View.OnClickListener, SelectListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: MainViewModel
    private lateinit var pokemons: List<PokemonModel>
    private lateinit var adapter: PokemonAdapter
    private lateinit var searchView: androidx.appcompat.widget.SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById<RecyclerView>(R.id.rv_pokemons)
        searchView = binding.svFilterPokemons
        viewModel = MainViewModel()

        binding.ivBtBack.setOnClickListener(this)
        binding.ivBtNext.setOnClickListener(this)

        Thread(Runnable {

            configureRecyclerView()

        }).start()

        configureFilterListener()

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    }

    override fun onStart() {
        super.onStart()

    }

    override fun onClick(view: View) {
        if(view.id == R.id.iv_bt_back) {
            if (UrlUtils.previousPage.isNotEmpty() && UrlUtils.previousPage != null) {
                viewModel.getPokemonsPagination(adapter, recyclerView, false)
            }
        } else if (view.id == R.id.iv_bt_next) {
            if (UrlUtils.nextPage.isNotEmpty() && UrlUtils.nextPage != null) {
                viewModel.getPokemonsPagination(adapter, recyclerView, true)
            }
        }
    }

    override fun onItemClick(pokemon: PokemonModel) {
        val intent: Intent = Intent(this, PokemonDetailActivity::class.java)
        intent.putExtra("id", pokemon.id)
        startActivity(intent)
    }

    fun configureFilterListener () {
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.filter(newText, pokemons, adapter)
                return true
            }

        })
    }

    private fun configureRecyclerView() {
        var pageResult = viewModel.getPagePokemon()

        pageResult.let {
            UrlUtils.previousPage(it.previous.toString())
            UrlUtils.nextPage(it.next.toString())
        }

        pageResult.results.let {
            pokemons = viewModel.getPokemons(it)
            recyclerView.post {
                recyclerView.layoutManager = LinearLayoutManager(this)
                adapter = PokemonAdapter(pokemons, applicationContext, this)
                recyclerView.adapter = adapter
            }
        }
    }
}