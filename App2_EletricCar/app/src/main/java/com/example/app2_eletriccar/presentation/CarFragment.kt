package com.example.app2_eletriccar.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.app2_eletriccar.R
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.app2_eletriccar.data.CarFactory
import com.example.app2_eletriccar.ui.adapter.CarAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CarFragment: Fragment() {

    lateinit var fab_calc: FloatingActionButton
    lateinit var rv_lista_car: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.car_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews(view)
        setupList()
        setupListeners()

    }


    fun setupViews(view: View) {  //chama as views
        view.apply {
            fab_calc = findViewById(R.id.fab_calcular)
            rv_lista_car = findViewById(R.id.rv_list_car)
        }

    }

    fun setupList(){ // conecta os adapter
        val adapter = CarAdapter(CarFactory.list)
        rv_lista_car.adapter = adapter

    }

    fun setupListeners() { // os cliques
        fab_calc.setOnClickListener {
            // calcular()
            startActivity(Intent(context, CalcularAutonomiaActivity::class.java))
        }
    }
}