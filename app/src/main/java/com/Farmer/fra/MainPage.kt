package com.Farmer.fra

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainPage : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var signOut : Button
    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)
        drawerLayout = findViewById(R.id.drawer_layout)
        coordinatorLayout = findViewById(R.id.coordinator)
        toolbar = findViewById(R.id.toolbar)
        frameLayout = findViewById(R.id.frame)
        navigationView = findViewById(R.id.navigation_bar)

        val DrawerToggle = ActionBarDrawerToggle(this@MainPage,
            drawerLayout , R.string.open_drawer, R.string.close_drawer)
        drawerLayout.addDrawerListener(DrawerToggle)
        DrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {
            when(it.itemId)
            {
                R.id.dash_profile->{
                    Toast.makeText(this@MainPage,"clicked to profile",Toast.LENGTH_SHORT).show()
                }
            }
            return@setNavigationItemSelectedListener true
        }

        auth = FirebaseAuth.getInstance()
        signOut = findViewById(R.id.signout)
        signOut.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, Login::class.java))

        }
        setupToolbar()

    }
    fun setupToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title="Farmer"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id == android.R.id.home)
        {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }


}