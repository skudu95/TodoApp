package com.sk.todoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.sk.todoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var customToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // inflate the custom ActionBar layout
        customToolbar = binding.toolbarMainActivity
        setSupportActionBar(customToolbar)

        // textview inside the custom toolbar
        val tvTitle = binding.toolbarTitle

        // navController and AppBarConfig setup
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfig = AppBarConfiguration(
            setOf(
                R.id.nav_all_tasks,
                R.id.nav_add_task,
            )
        )

        // title update according to fragment changes
        navController.addOnDestinationChangedListener { _, destination, _ ->
            tvTitle.text = destination.label
        }
        setupActionBarWithNavController(navController, appBarConfig)

    }
}