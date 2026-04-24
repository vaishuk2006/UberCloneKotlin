package com.example.uberclone

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private lateinit var spinnerPickup: EditText
    private lateinit var spinnerDrop: EditText
    private lateinit var spinnerCab: Spinner
    private lateinit var txtFare: TextView
    private lateinit var btnConfirm: Button

    // 📍 Mumbai Locations
    private val locations = mapOf(
        "Dadar" to LatLng(19.0186, 72.8424),
        "Bandra" to LatLng(19.0596, 72.8295),
        "Andheri" to LatLng(19.1136, 72.8697),
        "Kurla" to LatLng(19.0728, 72.8826),
        "Thane" to LatLng(19.2183, 72.9781)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        spinnerPickup = findViewById(R.id.spinnerPickup)
        spinnerDrop = findViewById(R.id.spinnerDrop)
        spinnerCab = findViewById(R.id.spinnerCab)
        txtFare = findViewById(R.id.txtFare)
        btnConfirm = findViewById(R.id.btnConfirm)

        // Map setup
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Cab types
        val cabTypes = arrayOf("Mini", "Sedan", "SUV")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cabTypes)
        spinnerCab.adapter = adapter

        // Calculate fare when button clicked
        btnConfirm.setOnClickListener {


            val pickupText = spinnerPickup.text.toString()
            val dropText = spinnerDrop.text.toString()

            val start = getLatLng(pickupText)
            val end = getLatLng(dropText)

            if (start != null && end != null) {

                val distance = calculateDistance(start, end)

                val rate = when (spinnerCab.selectedItem.toString()) {
                    "Kurla" -> 10
                    "Mahim" -> 15
                    else -> 20
                }

                val fare = (distance * rate).toInt()

                txtFare.text = "Fare: ₹$fare (%.2f km)".format(distance)


                // 🚗 Popup
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Driver Assigned 🚗")
                builder.setMessage("Rahul is arriving in 3 mins\nCar: Swift Dzire\n\n$fare ₹")

                builder.setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }

                builder.setCancelable(false)
                builder.show()

            } else {
                Toast.makeText(
                    this,
                    "Enter valid locations: Dadar, Bandra, Andheri, Kurla, Thane",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // Convert text → LatLng
    private fun getLatLng(place: String): LatLng? {
        return locations[place.trim()]
    }

    // Distance calculation (km)
    private fun calculateDistance(start: LatLng, end: LatLng): Double {

        val results = FloatArray(1)

        android.location.Location.distanceBetween(
            start.latitude,
            start.longitude,
            end.latitude,
            end.longitude,
            results
        )

        return results[0].toDouble() / 1000
    }

    // Map ready
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val mumbai = LatLng(19.0760, 72.8777)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mumbai, 12f))
    }
}