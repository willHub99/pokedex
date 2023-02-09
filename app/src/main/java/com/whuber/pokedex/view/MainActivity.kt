package com.whuber.pokedex.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.whuber.pokedex.R
import com.whuber.pokedex.api.ListPokemonResult
import com.whuber.pokedex.databinding.ActivityMainBinding
import com.whuber.pokedex.model.PokemonModel
import com.whuber.pokedex.recyclerview.`interface`.SelectListener
import com.whuber.pokedex.recyclerview.adapater.PokemonAdapter
import com.whuber.pokedex.utils.UrlUtils
import com.whuber.pokedex.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        configureRecyclerView()

        configureFilterListener()

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    }

   override fun onResume() {
        super.onResume()

       viewModel.getPageFromPagination(UrlUtils.currentPage)

       viewModel.listPokemonResult.observe(this, Observer<List<ListPokemonResult>>{
           pokemons = viewModel.convertListPokemonResultToListPokemonModel(it)
           GlobalScope.launch(Dispatchers.Main) {
               adapter.pagination(pokemons)
           }
       })
    }

    override fun onClick(view: View) {
        if(view.id == R.id.iv_bt_back) {
            if (UrlUtils.previousPage.isNotEmpty()) {
                UrlUtils.currentPage = UrlUtils.previousPage
                viewModel.getPageFromPagination(UrlUtils.previousPage)
                viewModel.listPokemonResult.observe(this, Observer<List<ListPokemonResult>>{
                    pokemons = viewModel.convertListPokemonResultToListPokemonModel(it)
                    GlobalScope.launch(Dispatchers.Main) {
                        adapter.pagination(pokemons)
                    }
                })
            }
        } else if (view.id == R.id.iv_bt_next) {
            if (UrlUtils.nextPage.isNotEmpty()) {
                UrlUtils.currentPage = UrlUtils.nextPage
                viewModel.getPageFromPagination(UrlUtils.nextPage)
                viewModel.listPokemonResult.observe(this, Observer<List<ListPokemonResult>>{
                    pokemons = viewModel.convertListPokemonResultToListPokemonModel(it)
                    GlobalScope.launch(Dispatchers.Main) {
                        adapter.pagination(pokemons)
                    }
                })
            }
        }
    }

    override fun onItemClick(pokemon: PokemonModel) {
        val intent: Intent = Intent(this, PokemonDetailActivity::class.java)
        intent.putExtra("id", pokemon.id)
        startActivity(intent)
    }

    private fun configureFilterListener () {
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
        viewModel.getPagePokemon()

        viewModel.listPokemonResult.observe(this, Observer<List<ListPokemonResult>>{
            pokemons = viewModel.convertListPokemonResultToListPokemonModel(it)
            recyclerView.post {
                recyclerView.layoutManager = LinearLayoutManager(this)
                adapter = PokemonAdapter(pokemons, applicationContext, this)
                recyclerView.adapter = adapter
            }
        })
    }
}