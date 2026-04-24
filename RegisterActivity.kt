package com.example.uberclone

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    var etName: EditText? = null
    var etPhone: EditText? = null
    var etAddress: EditText? = null
    var btnRegister: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etName = findViewById<EditText>(R.id.etName)
        etPhone = findViewById<EditText>(R.id.etPhone)
        etAddress = findViewById<EditText>(R.id.etAddress)
        btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister!!.setOnClickListener(View.OnClickListener { v: View? -> registerUser() })
    }

    private fun registerUser() {
        val name = etName!!.getText().toString()
        val phone = etPhone!!.getText().toString()
        val address = etAddress!!.getText().toString()

        if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Save data
        val prefs = getSharedPreferences("USER", MODE_PRIVATE)
        if (prefs.getBoolean("isRegistered", false)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        val editor = prefs.edit()

        editor.putString("name", name)
        editor.putString("phone", phone)
        editor.putString("address", address)
        editor.putBoolean("isRegistered", true)

        editor.apply()

        Toast.makeText(this, "Registered Successfully 🎉", Toast.LENGTH_SHORT).show()

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}