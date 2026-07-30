package com.example.app2_eletriccar.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ListAdapter
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.app2_eletriccar.R
import com.example.app2_eletriccar.presentation.adapter.CarAdapter

class MainActivity : AppCompatActivity() {

    lateinit var btn_calcularAutonomia: Button
    lateinit var rv_lista_car: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        seupViews()
        setupListeners()
        setupList()

    }

    fun seupViews(){
        btn_calcularAutonomia = findViewById(R.id.button_calculeAutonomia)
        rv_lista_car = findViewById(R.id.rv_list_car)
    }


    fun setupList(){
        var dados = arrayOf(
            "uva", "banana", "tangerina", "manga"
        )

        val adapter = CarAdapter(dados)
        rv_lista_car.adapter = adapter

    }

    fun setupListeners(){
        btn_calcularAutonomia.setOnClickListener {
           // calcular()
            startActivity(Intent(this, CalcularAutonomiaActivity::class.java))
        }
    }

}