package com.example.iquii_test.ui.pokemon

import androidx.lifecycle.*
import com.example.iquii_test.database.Pokemon
import com.example.iquii_test.database.PokemonRepository
import kotlinx.coroutines.launch

class PokemonViewModel(private val repository: PokemonRepository) : ViewModel() {


    // Using LiveData and caching what allPokemons returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allPokemon: LiveData<List<Pokemon>> = repository.allPokemon.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(pokemon: Pokemon) = viewModelScope.launch {
        repository.insert(pokemon)
    }

    //delete pokemon by giving specific pokemon name
    fun deletebyPokemonName(pokemonName: String) = viewModelScope.launch {
        repository.deleteByUserName(pokemonName)
    }
}

class PokemonViewModelFactory(private val repository: PokemonRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PokemonViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}