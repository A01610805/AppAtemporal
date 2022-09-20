package com.example.appatemporal.framework.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appatemporal.databinding.ActivityComprobacionBinding


class CheckIfLogged : AppCompatActivity() {
    private lateinit var binding: ActivityComprobacionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityComprobacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnUser.setOnClickListener {
            val intent = Intent(this, PhoneActivity::class.java)
            startActivity(intent)
        }

    }
}