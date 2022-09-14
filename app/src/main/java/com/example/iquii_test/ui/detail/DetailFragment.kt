package com.example.iquii_test.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.iquii_test.MainActivity
import com.example.iquii_test.adapters.RVAdapter
import com.example.iquii_test.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null

    private val binding get() = _binding!!
    private val TAG = "DetailFragment"

    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val detailViewModel =
            ViewModelProvider(this).get(DetailViewModel::class.java)

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root


        detailViewModel.image.observe(viewLifecycleOwner) {

            Glide
                .with(binding.root.context)
                .load(it)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(binding.centerImg);
        }

        detailViewModel.name.observe(viewLifecycleOwner) {
            binding.tvName.text = it
        }

        detailViewModel.hp.observe(viewLifecycleOwner) {
            binding.barHp.progress = it
        }

        detailViewModel.attack.observe(viewLifecycleOwner) {
            binding.barAttack.progress = it
        }

        detailViewModel.defense.observe(viewLifecycleOwner) {
            binding.barDefense.progress = it
        }

        detailViewModel.spAttack.observe(viewLifecycleOwner) {
            binding.barSpAttack.progress = it
        }

        detailViewModel.spDefense.observe(viewLifecycleOwner) {
            binding.barSpDefense.progress = it
        }

        detailViewModel.speed.observe(viewLifecycleOwner) {
            binding.barSpeed.progress = it
        }

        return root
    }

}


