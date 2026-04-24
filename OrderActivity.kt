package com.example.uberclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class OrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val txtSummary = findViewById<TextView>(R.id.txtSummary)
        val btnDone = findViewById<Button>(R.id.btnDone)

        val name = intent.getStringExtra("name")
        val pickup = intent.getStringExtra("pickup")
        val drop = intent.getStringExtra("drop")
        val cab = intent.getStringExtra("cab")
        val fare = intent.getStringExtra("fare")

        txtSummary.text = """
            Ride Booked Successfully 🚗
            
            Name: $name
            Pickup: $pickup
            Drop: $drop
            Cab: $cab
            $fare
        """.trimIndent()

        btnDone.setOnClickListener {
            finish()
        }
    }
}