package com.example.android.codelabs.navigation

import android.content.res.Resources
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI

/**
 * A simple activity demonstrating use of a NavHostFragment with a navigation drawer.
 */
class MainActivity : AppCompatActivity() {

  private var drawerLayout: DrawerLayout? = null


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.navigation_activity)

    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    setSupportActionBar(toolbar!!)

    val navHost: NavHostFragment = supportFragmentManager
      .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return


    // Set up Action Bar
    val navController = navHost.navController

    setupActionBar(navController)
    setupNavigationMenu(navController)
    setupBottomNavMenu(navController)

    navController.addOnNavigatedListener { _, destination ->
      val dest: String = try {
        resources.getResourceName(destination.id)
      } catch (e: Resources.NotFoundException) {
        Integer.toString(destination.id)
      }

      Toast.makeText(this@MainActivity, "Navigated to $dest",
        Toast.LENGTH_SHORT).show()
      Log.d("NavigationActivity", "Navigated to $dest")
    }
  }

  private fun setupBottomNavMenu(navController: NavController) {
    findViewById<BottomNavigationView>(R.id.bottom_nav_view)?.let { bottomNavView ->
      NavigationUI.setupWithNavController(bottomNavView, navController)
    }
  }

  private fun setupNavigationMenu(navController: NavController) {
    findViewById<NavigationView>(R.id.nav_view)?.let { navigationView ->
      NavigationUI.setupWithNavController(navigationView, navController)
    }
  }

  private fun setupActionBar(navController: NavController) {
    drawerLayout = findViewById(R.id.drawer_layout)

    NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    val retValue = super.onCreateOptionsMenu(menu)
    val navigationView = findViewById<NavigationView>(R.id.nav_view)

    if (navigationView == null) {
      menuInflater.inflate(R.menu.menu_overflow, menu)
      return true
    }
    return retValue
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return NavigationUI
      .onNavDestinationSelected(
        item,
        Navigation.findNavController(this, R.id.my_nav_host_fragment)
      ) ||
      super.onOptionsItemSelected(item)
  }

  override fun onSupportNavigateUp(): Boolean {
    return NavigationUI
      .navigateUp(drawerLayout, Navigation.findNavController(this, R.id.my_nav_host_fragment))
  }
}
