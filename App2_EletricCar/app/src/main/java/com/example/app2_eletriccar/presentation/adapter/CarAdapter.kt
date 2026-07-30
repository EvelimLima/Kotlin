package com.example.app2_eletriccar.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.motion.widget.KeyPosition
import androidx.recyclerview.widget.RecyclerView
import com.example.app2_eletriccar.R
import com.google.android.material.animation.Positioning
import java.sql.Types

class CarAdapter(private val carros: Array<String>):
    RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewTypes: Int): ViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.carro_item, parent, false)
        return ViewHolder(view)
    }

    //pega o conteudo da view e troca pela informação de item de uma lista
    override fun onBindViewHolder( holder: ViewHolder, position: Int) {
        holder.textView.text = carros[position]
    }


    override fun getItemCount(): Int = carros.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val textView: TextView

        init {
            textView = view.findViewById(R.id.tv_preco_value)
        }
    }

}