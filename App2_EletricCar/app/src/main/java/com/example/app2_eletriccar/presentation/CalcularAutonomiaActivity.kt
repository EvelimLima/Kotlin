package com.example.app2_eletriccar.presentation

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.app2_eletriccar.R

class CalcularAutonomiaActivity : AppCompatActivity() {

    lateinit var preco: EditText
    lateinit var kmPercorrido: EditText

    lateinit var btn_calcular: Button
    lateinit var resultado: TextView

    lateinit var btnClose: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calcular_autonomia)

        seupViews()
        setupListeners()

    }

    fun seupViews(){
        btnClose = findViewById(R.id.img_ButtonClose)

        kmPercorrido = findViewById(R.id.et_kmPercorrido)
        preco = findViewById(R.id.et_Preco_Kwh)
        btn_calcular = findViewById(R.id.button_calcular)
        resultado = findViewById(R.id.tv_resultado)
    }

    fun setupListeners(){
        btn_calcular.setOnClickListener {
            calcular()
        }
        btnClose.setOnClickListener {
            finish()
        }
    }
    fun calcular(){
        val preco = preco.text.toString().toFloat()
        val km = kmPercorrido.text.toString().toFloat()

        val result = preco / km

        resultado.text = result.toString()

    }
}