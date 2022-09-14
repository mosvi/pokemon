package com.example.iquii_test.ui.pokemon

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.iquii_test.PokemonApplication
import com.example.iquii_test.adapters.GVAdapter
import com.example.iquii_test.database.Pokemon
import com.example.iquii_test.databinding.FragmentPokemonBinding

class PokemonFragment : Fragment() {

    private var _binding: FragmentPokemonBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val TAG = "PokemonFragment"

    private val pokemonViewModel: PokemonViewModel by viewModels {
        PokemonViewModelFactory((requireActivity().application as PokemonApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPokemonBinding.inflate(inflater, container, false)
        val root: View = binding.root

        pokemonViewModel.allPokemon.observe(viewLifecycleOwner) { pokemons ->

            //this if block check if there is no data
            //in database it will show empty textview
            if(pokemons.isEmpty()){
                binding.tvEmpty.visibility = View.VISIBLE

            }else{
                binding.tvEmpty.visibility = View.GONE
                pokemons.let {
                    val adapter = GVAdapter(it, root.context)
                    binding.gridView.adapter = adapter
                }
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




