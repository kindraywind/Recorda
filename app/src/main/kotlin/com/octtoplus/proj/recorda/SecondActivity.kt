package com.octtoplus.proj.recorda

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    internal var res = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val intent = intent
        res = intent.getIntExtra("result", 0)

        initInstances()
    }

    private fun initInstances() {
        resLabel.text = "Res: " + res

        goto3Button.setOnClickListener {
            val intent = Intent(this@SecondActivity,
                    ThirdActivity::class.java)
            intent.putExtra("result", 500)
            startActivity(intent)
        }

    }
}
