package com.example.primarydetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.primarydetail.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mBinding.root
        setContentView(view)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment
        mNavController = navHostFragment.navController

        setSupportActionBar(mBinding.toolbar)
        val appBarConfiguration = AppBarConfiguration(mNavController.graph)
        setupActionBarWithNavController(mNavController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return mNavController.navigateUp()
                || super.onSupportNavigateUp()
    }
}
