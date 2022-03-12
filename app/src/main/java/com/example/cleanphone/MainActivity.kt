package com.example.cleanphone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.cleanphone.databinding.MainActivityBinding
import com.example.cleanphone.lib.BatteryUsageWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        supportActionBar?.hide()

        supportActionBar?.setDisplayHomeAsUpEnabled(false);
        supportActionBar?.setHomeButtonEnabled(false);

        val navView = binding.navView

        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_tools, R.id.navigation_settings
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        setupBatteryUsageWorker(ExistingPeriodicWorkPolicy.KEEP)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = navController
        return navController.navigateUp() || super.onSupportNavigateUp()
    }



    private fun resetServiceClick() {
        setupBatteryUsageWorker(ExistingPeriodicWorkPolicy.REPLACE)
    }

    private fun setupBatteryUsageWorker(existingPeriodicWorkPolicy: ExistingPeriodicWorkPolicy) {
        val batteryUsageWorkRequest =
            PeriodicWorkRequestBuilder<BatteryUsageWorker>(
                PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS,
                TimeUnit.MILLISECONDS
            )
                .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "UK_PERIODIC_BATTERY_USAGE",
            existingPeriodicWorkPolicy,
            batteryUsageWorkRequest
        )
    }


}