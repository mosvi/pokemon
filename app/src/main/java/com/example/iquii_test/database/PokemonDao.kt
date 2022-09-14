package com.example.iquii_test.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    // The flow always holds/caches latest version of data. Notifies its observers when the
    // data has changed.
    @Query("SELECT * FROM pokemon_table")
    fun getAllPokemon(): Flow<List<Pokemon>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pokemon: Pokemon)

    @Query("DELETE FROM pokemon_table WHERE name = :pokemonName")
    fun deleteByUserName(pokemonName: String)

}