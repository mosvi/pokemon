package com.example.iquii_test.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.iquii_test.R
import com.example.iquii_test.database.Pokemon
import com.example.iquii_test.utils.RVItemModel
import com.example.iquii_test.databinding.ListItemBinding
import com.example.iquii_test.ui.home.HomeFragment
import com.example.iquii_test.ui.home.HomeViewModel
import java.text.DecimalFormat

class RVAdapter(private val mList: List<RVItemModel>, private val mHandler: RVInterface) :
    RecyclerView.Adapter<RVAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val binding = ListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder) {
            val itemsList = mList[position]

            iconCheck(binding, itemsList)

            Glide
                .with(binding.root.context)
                .load(itemsList.image)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(binding.imageView);

            val dFormat = DecimalFormat("'#'000")

            binding.tvNumber.text = dFormat.format(itemsList.textNumber)
            binding.tvName.text = itemsList.textName

            binding.fav.setOnClickListener(View.OnClickListener {

                //it does check/unchecked the btn when favourite button clicked
                itemsList.isChecked = !itemsList.isChecked

                val pokemon = Pokemon(itemsList.image, itemsList.textName, itemsList.url)

                //calling hanlder to insert/delete item
                mHandler.getCurrentItem(pokemon, itemsList.isChecked)

                //updating fav btn checked/unchecked
                iconCheck(binding, itemsList)
            })

            itemView.setOnClickListener(View.OnClickListener { view ->

                //getting the url of clicked pokemon
                HomeFragment.newURL = itemsList.url

                //navigate to the detail fragment
                view.findNavController().navigate(R.id.action_navigation_home_to_navigation_detail)

            })
        }

    }

    fun iconCheck(binding: ListItemBinding, itemsList: RVItemModel) {

        if (itemsList.isChecked) {
            binding.fav.setImageResource(R.drawable.ic_fav_clicked)
        } else {
            binding.fav.setImageResource(R.drawable.ic_fav)
        }

    }


    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    inner class ViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    interface RVInterface {
        fun getCurrentItem(pokemon: Pokemon, isChecked: Boolean)
    }

}