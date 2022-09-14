package com.example.iquii_test

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.iquii_test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                //menu name for bottom navigation
                R.id.navigation_home, R.id.navigation_saved
            )
        )

        //bottom navigation setup code
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        //for hide/show bottom Navigation bar
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home -> showNavBar()
                R.id.navigation_saved -> showNavBar()
                else -> hideNavBar()
            }
        }
    }

    private fun showNavBar() {
        binding.navView.visibility = View.VISIBLE
        this.supportActionBar?.show()


    }

    private fun hideNavBar() {
        binding.navView.visibility = View.GONE
        this.supportActionBar?.hide()

    }
}