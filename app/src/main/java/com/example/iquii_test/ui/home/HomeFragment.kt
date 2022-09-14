package com.example.iquii_test.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.iquii_test.PokemonApplication
import com.example.iquii_test.adapters.RVAdapter
import com.example.iquii_test.database.Pokemon
import com.example.iquii_test.databinding.FragmentHomeBinding
import com.example.iquii_test.ui.pokemon.PokemonViewModel
import com.example.iquii_test.ui.pokemon.PokemonViewModelFactory
import com.example.iquii_test.utils.RVItemModel

class HomeFragment : Fragment(), RVAdapter.RVInterface  {

    private var _binding: FragmentHomeBinding? = null

    companion object{
        lateinit var newURL: String
    }

    private lateinit var mPokemonList: List<Pokemon>
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val TAG = "HomeFragment"
    private val pokemonViewModel: PokemonViewModel by viewModels {
        PokemonViewModelFactory((requireActivity().application as PokemonApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerview.layoutManager = LinearLayoutManager(root.context)


        //getting list stored in local database
        pokemonViewModel.allPokemon.observe(viewLifecycleOwner) {
            mPokemonList = it
        }

        homeViewModel.dataList.observe(viewLifecycleOwner) { list ->

            //matching stored items with list coming from API
            for (i in 0 until list.size) {

                var item1 = list.get(i)

                for (j in 0 until mPokemonList.size){

                    //item from database
                    val item2 = mPokemonList.get(j)

                    //if check for matching same items
                    if(item1.textName == item2.name){
                        item1.isChecked = true
                    }
                }
            }

            val adapter = RVAdapter(list, this)
            // Setting the Adapter with the recyclerview
            binding.recyclerview.adapter = adapter
        }

        return root
    }


    //interface to insert/delete item
    override fun getCurrentItem(pokemon: Pokemon, isChecked: Boolean) {
        if(isChecked){
            pokemonViewModel.insert(pokemon)
            Toast.makeText(binding.root.context, "Saved", Toast.LENGTH_SHORT).show()
        }else{
            pokemonViewModel.deletebyPokemonName(pokemon.name)
            Toast.makeText(binding.root.context, "UnSaved", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}