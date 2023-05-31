package com.Farmer.fra

import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainPage : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var signOut : Button
    lateinit var toggle : ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)


        supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        supportActionBar?.setCustomView(R.layout.action_bar)
        val drawerLayout :  DrawerLayout = findViewById(R.id.drawer_layout)
        val navView : NavigationView = findViewById(R.id.nav_view)
        toggle =  ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        navView.setNavigationItemSelectedListener {
            when(it.itemId)
            {
                R.id.maps->{
                    Toast.makeText(applicationContext,"clicked on Maps",Toast.LENGTH_SHORT).show()
                    val intent =  Intent(this@MainPage,MapsActivity::class.java)
                    startActivity(intent)

                }
                R.id.profile->Toast.makeText(applicationContext,"clicked on profile",Toast.LENGTH_SHORT).show()


            }
            true
        }
        auth = FirebaseAuth.getInstance()
        signOut = findViewById(R.id.signout)
        signOut.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, Login::class.java))

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.bu_menu,menu)
        return super.onCreateOptionsMenu(menu)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout->{
                auth.signOut()
                startActivity(Intent(this,Login::class.java))
                Toast.makeText(applicationContext,"Logout SuccesFully",Toast.LENGTH_SHORT).show()
            }
           }

        if (toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }



}