package com.octtoplus.proj.recorda

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

private val REQUEST_EXTERNAL_STORAGE = 1
private val PERMISSIONS_STORAGE = arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentVersion = Build.VERSION.SDK_INT
        if (currentVersion >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            verifyStoragePermissions(this)
        }


        setUpViews()
    }


    private fun setUpViews() {
        //toolbar
        setSupportActionBar(toolbar)

        //fab
        fab.setOnClickListener {
            Snackbar.make(it, "Replace with your own action2", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show()
        }

        //hoho - what?
        hohoButton.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java).apply {
                putExtra("result", 200)
            }
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(this, "Action menu", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStop() {
        super.onStop()
    }

    /**
     * Checks if the app has permission to write to device storage

     * If the app does not has permission then the user will be prompted to grant permissions

     * @param activity
     */
    fun verifyStoragePermissions(activity: Activity) {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE)
        }
    }

}