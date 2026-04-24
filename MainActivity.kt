package com.example.uberclone

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var pickup: EditText? = null
    var drop: EditText? = null
    var rideType: Spinner? = null
    var txtFare: TextView? = null
    var btnBook: Button? = null

    var rides: Array<String?> = arrayOf<String?>("Mahim", "Kurla", "Shivaji Park")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pickup = findViewById<EditText>(R.id.pickup)
        drop = findViewById<EditText>(R.id.drop)
        rideType = findViewById<Spinner>(R.id.rideType)
        txtFare = findViewById<TextView>(R.id.txtFare)
        btnBook = findViewById<Button>(R.id.btnBook)

        val adapter = ArrayAdapter<String?>(
            this, android.R.layout.simple_spinner_dropdown_item, rides
        )
        rideType!!.setAdapter(adapter)

        btnBook!!.setOnClickListener(View.OnClickListener { v: View? -> calculateFare() })
    }


    private fun calculateFare() {
        val pick = pickup!!.getText().toString()
        val drp = drop!!.getText().toString()

        if (pick.isEmpty() || drp.isEmpty()) {
            Toast.makeText(this, "Enter locations", Toast.LENGTH_SHORT).show()
            return
        }

        val baseFare: Int

        when (rideType!!.getSelectedItem().toString()) {
            "Mahim" -> baseFare = 100
            "Kurla" -> baseFare = 150
            else -> baseFare = 200
        }

        val distance = 3 + (Math.random() * 10).toInt()
        val fare = baseFare + distance * 10

        txtFare!!.setText("Fare: ₹" + fare)

        showDriverPopup()
    }

    private fun showDriverPopup() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Searching Driver...")
            .setMessage("Please wait...")
            .setCancelable(false)
            .create()

        dialog.show()

        Handler().postDelayed(Runnable {
            dialog.dismiss()
            AlertDialog.Builder(this)
                .setTitle("Driver Assigned 🚗")
                .setMessage("Rahul is arriving in 3 mins\nCar: Swift Dzire")
                .setPositiveButton("OK", null)
                .show()
        }, 3000)
    }
}