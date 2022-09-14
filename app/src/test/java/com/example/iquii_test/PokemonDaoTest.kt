package com.example.iquii_test

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.iquii_test.database.Pokemon
import com.example.iquii_test.database.PokemonDao
import com.example.iquii_test.database.PokemonRoomDatabase
import org.junit.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PokemonDaoTest {

    private lateinit var pokemonDao: PokemonDao
    private lateinit var db: PokemonRoomDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()

        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, PokemonRoomDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        pokemonDao = db.pokemonDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun getAllPokemons() = runBlocking {
        val data = Pokemon("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/2.png",
            "ivysaur","https://pokeapi.co/api/v2/pokemon/2")
        pokemonDao.insert(data)
        val data2 = Pokemon("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png",
            "venusaue","https://pokeapi.co/api/v2/pokemon/3")
        pokemonDao.insert(data2)
        val allData = pokemonDao.getAllPokemon().first()
        assertEquals(allData[0].name, data.name)
        assertEquals(allData[1].name, data2.name)
    }

    @Test
    @Throws(Exception::class)
    fun deleteByName() = runBlocking {
        val data = Pokemon("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/2.png",
            "ivysaur","https://pokeapi.co/api/v2/pokemon/2")
        pokemonDao.insert(data)
        val data2 = Pokemon("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png",
            "venusaue","https://pokeapi.co/api/v2/pokemon/3")
        pokemonDao.insert(data2)
        pokemonDao.deleteByUserName("ivysaur")
        val allData = pokemonDao.getAllPokemon().first()
        assertEquals(allData.size,1 )
    }
}