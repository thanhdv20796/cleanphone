package com.example.cleanphone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.cleanphone.databinding.MainActivityBinding
import com.example.cleanphone.lib.BatteryUsageWorker
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_activity.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val appbarConfiguration = AppBarConfiguration.Builder()
        .setFallbackOnNavigateUpListener {
            finish()
            super.onSupportNavigateUp()
        }
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_tools, R.id.navigation_settings
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        setupBatteryUsageWorker(ExistingPeriodicWorkPolicy.REPLACE)
    }

    fun navView(isVisible: Boolean) {
        nav_view.isVisible = isVisible
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.main_nav_host_fragment).navigateUp(appbarConfiguration)
    }


    private fun resetServiceClick() {
        setupBatteryUsageWorker(ExistingPeriodicWorkPolicy.KEEP)
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