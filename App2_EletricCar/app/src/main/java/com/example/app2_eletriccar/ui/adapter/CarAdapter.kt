package com.example.app2_eletriccar.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.motion.widget.KeyPosition
import androidx.recyclerview.widget.RecyclerView
import com.example.app2_eletriccar.R
import com.example.app2_eletriccar.dominio.Carro


class CarAdapter(private val carros: List<Carro>):
    RecyclerView.Adapter<CarAdapter.ViewHolder>() {

        // cria cartao vazio
    override fun onCreateViewHolder(parent: ViewGroup, viewTypes: Int): ViewHolder{
        Log.d("CarAdapter", "onCreateViewHolder chamado")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.carro_item, parent, false)
        return ViewHolder(view)
    }

    //pega o conteudo da view e troca pela informação de item de uma lista
    override fun onBindViewHolder( holder: ViewHolder, position: Int) {
        Log.d("CarAdapter", "onBindViewHolder chamado para a posição $position")
        holder.preco.text = carros[position].preco
        holder.bateria.text = carros[position].bateria
        holder.potencia.text = carros[position].potencia
        holder.recarga.text = carros[position].recarga
    }

    override fun getItemCount(): Int = carros.size




    //construtor
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val preco: TextView
        val bateria: TextView
        val potencia: TextView
        val recarga: TextView

        init {
            view.apply {
                preco = findViewById(R.id.tv_preco_value)
                bateria = findViewById(R.id.tv_bateria_value)
                potencia = findViewById(R.id.tv_potencia_value)
                recarga = findViewById(R.id.tv_recarga_value)
            }
        }
    }

}