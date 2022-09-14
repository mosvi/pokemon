package com.example.iquii_test.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.iquii_test.R
import com.example.iquii_test.database.Pokemon
import com.example.iquii_test.ui.home.HomeFragment
import com.example.iquii_test.utils.RVItemModel

internal class GVAdapter(
    // on below line we are creating two
    // variables for pokemon list and context
    private val gridList: List<Pokemon>,
    private val context: Context
) :
    BaseAdapter() {
    // in base adapter class we are creating variables
    // for layout inflater, pokemon image view and pokemon text view.
    private var layoutInflater: LayoutInflater? = null
    private lateinit var tvName: TextView
    private lateinit var image: ImageView

    // below method is use to return the count of pokemon list
    override fun getCount(): Int {
        return gridList.size
    }

    // below function is use to return the item of grid view.
    override fun getItem(position: Int): Any? {
        return null
    }

    // below function is use to return item id of grid view.
    override fun getItemId(position: Int): Long {
        return 0
    }

    // in below function we are getting individual item of grid view.
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        // on blow line we are checking if layout inflater
        // is null, if it is null we are initializing it.
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        // on the below line we are checking if convert view is null.
        // If it is null we are initializing it.
        if (convertView == null) {
            // on below line we are passing the layout file
            // which we have to inflate for each item of grid view.
            convertView = layoutInflater!!.inflate(R.layout.grid_item, null)
        }
        // on below line we are initializing our pokemon image view
        // and pokemon text view.
        tvName = convertView!!.findViewById(R.id.tv_name)
        image = convertView!!.findViewById(R.id.image)
        // on below line we are setting image for our pokemon image view.
        Glide
            .with(context)
            .load(gridList.get(position).image)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(image)
        // on below line we are setting text in our pokemon text view.
        tvName.setText(gridList.get(position).name)
        // at last we are returning our convert view.

        convertView.setOnClickListener(View.OnClickListener { view ->

            //getting the url of clicked pokemon
            HomeFragment.newURL = gridList.get(position).url

            //navigate to the detail fragment
            view.findNavController().navigate(R.id.action_navigation_saved_to_navigation_detail)

        })

        return convertView
    }
}