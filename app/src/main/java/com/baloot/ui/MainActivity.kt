package com.baloot.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.baloot.R
import com.baloot.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _vBinding: ActivityMainBinding? = null
    private val vBinding get() = _vBinding!!
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _vBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vBinding.root)

        navController = findNavController(R.id.navHostFragment)
        vBinding.bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _vBinding = null
    }


}