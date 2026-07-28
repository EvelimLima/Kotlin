package com.example.app2_eletriccar

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    lateinit var preco: EditText
    lateinit var kmPercorrido: EditText
    lateinit var btnCalcular: Button

    lateinit var resultado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        seupViews()
        setupListeners()

    }

    fun seupViews(){
        preco = findViewById(R.id.et_Preco_Kwh)
        btnCalcular = findViewById(R.id.button_calcular)
        kmPercorrido = findViewById(R.id.et_kmPercorrido)
        resultado = findViewById(R.id.tv_resultado)
    }

    fun setupListeners(){
        btnCalcular.setOnClickListener {
            calcular()
        }
    }

    fun calcular(){
        val preco = preco.text.toString().toFloat()
        val km = kmPercorrido.text.toString().toFloat()

        val result = preco / km

        resultado.text = result.toString()

    }
}