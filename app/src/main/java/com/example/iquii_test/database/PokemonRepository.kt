package com.example.iquii_test.database

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow


class PokemonRepository(private val pokemonDao: PokemonDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allPokemon: Flow<List<Pokemon>> = pokemonDao.getAllPokemon()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(pokemon: Pokemon) {
        pokemonDao.insert(pokemon)
    }

    fun deleteByUserName(pokemonName: String) {
        pokemonDao.deleteByUserName(pokemonName)
    }
}