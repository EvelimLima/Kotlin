package com.example.app2_eletriccar.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.app2_eletriccar.R

class MainActivity : AppCompatActivity() {

    lateinit var btn_calcularAutonomia: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        seupViews()
        setupListeners()

    }

    fun seupViews(){
        btn_calcularAutonomia = findViewById(R.id.button_calculeAutonomia)
    }

    fun setupListeners(){
        btn_calcularAutonomia.setOnClickListener {
           // calcular()
            startActivity(Intent(this, CalcularAutonomiaActivity::class.java))
        }
    }

}