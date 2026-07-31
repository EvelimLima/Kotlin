package com.example.app2_eletriccar.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.app2_eletriccar.R
import com.example.app2_eletriccar.data.CarFactory
import com.example.app2_eletriccar.ui.adapter.CarAdapter

class MainActivity : AppCompatActivity() {

    lateinit var btn_calcularAutonomia: Button
    lateinit var rv_lista_car: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        setupViews()
        setupListeners()
        setupList()

    }

    fun setupViews(){  //chama as views
        btn_calcularAutonomia = findViewById(R.id.button_calculeAutonomia)
        rv_lista_car = findViewById(R.id.rv_list_car)
    }

    fun setupListeners() { // os cliques
        btn_calcularAutonomia.setOnClickListener {
            // calcular()
            startActivity(Intent(this, CalcularAutonomiaActivity::class.java))
        }
    }
    fun setupList(){ // conecta os adapter
        val adapter = CarAdapter(CarFactory.list)
        rv_lista_car.adapter = adapter

    }

}