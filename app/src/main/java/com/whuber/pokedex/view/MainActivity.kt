package com.whuber.pokedex.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.whuber.pokedex.R
import com.whuber.pokedex.api.ListPokemonResult
import com.whuber.pokedex.core.State
import com.whuber.pokedex.databinding.ActivityMainBinding
import com.whuber.pokedex.model.PokemonModel
import com.whuber.pokedex.recyclerview.`interface`.SelectListener
import com.whuber.pokedex.recyclerview.adapater.PokemonAdapter
import com.whuber.pokedex.utils.UrlUtils
import com.whuber.pokedex.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), View.OnClickListener, SelectListener {

    private lateinit var pokemons: List<PokemonModel>
    private lateinit var searchView: androidx.appcompat.widget.SearchView

    private val viewModel: MainViewModel by viewModel()

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val adapter: PokemonAdapter by lazy {
        PokemonAdapter()
    }
    private val recyclerView: RecyclerView by lazy {
        findViewById(R.id.rv_pokemons)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        searchView = binding.svFilterPokemons

        binding.ivBtBack.setOnClickListener(this)
        binding.ivBtNext.setOnClickListener(this)

        configureRecyclerView()

        configureFilterListener()

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    }

    override fun onClick(view: View) {
        if(view.id == R.id.iv_bt_back) {
            if (UrlUtils.previousPage.isNotEmpty()) {
                UrlUtils.currentPage = UrlUtils.previousPage
                viewModel.getPageFromPagination(UrlUtils.previousPage)
                viewModel.listPokemonResult.observe(this, Observer<State<List<ListPokemonResult>>>{
                    when(it) {
                        State.Loading -> {
                            binding.pbList.visibility = View.VISIBLE
                        }
                        is State.Error -> {
                            binding.pbList.visibility = View.GONE
                        }
                        is State.Success -> {
                            binding.pbList.visibility = View.GONE
                            pokemons = viewModel.convertListPokemonResultToListPokemonModel(it.result)
                            adapter.setListPokemon(pokemons)

                        }
                    }
                })
            }
        } else if (view.id == R.id.iv_bt_next) {
            if (UrlUtils.nextPage.isNotEmpty()) {
                UrlUtils.currentPage = UrlUtils.nextPage
                viewModel.getPageFromPagination(UrlUtils.nextPage)
                viewModel.listPokemonResult.observe(this, Observer<State<List<ListPokemonResult>>>{
                    when(it) {
                        State.Loading -> {
                            binding.pbList.visibility = View.VISIBLE
                        }
                        is State.Error -> {
                            binding.pbList.visibility = View.GONE
                        }
                        is State.Success -> {
                            binding.pbList.visibility = View.GONE
                            pokemons = viewModel.convertListPokemonResultToListPokemonModel(it.result)
                            adapter.setListPokemon(pokemons)

                        }
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

        viewModel.listPokemonResult.observe(this, Observer<State<List<ListPokemonResult>>>{
            when(it) {
                State.Loading -> {
                    binding.pbList.visibility = View.VISIBLE

                }
                is State.Error -> {
                    binding.pbList.visibility = View.GONE
                }
                is State.Success -> {
                    binding.pbList.visibility = View.GONE
                    pokemons = viewModel.convertListPokemonResultToListPokemonModel(it.result)
                    recyclerView.post {
                        recyclerView.layoutManager = LinearLayoutManager(this)
                        adapter.setListPokemon(pokemons)
                        adapter.setContext(this)
                        adapter.setListener(this)
                        recyclerView.adapter = adapter
                    }

                }
            }
        })
    }
}

