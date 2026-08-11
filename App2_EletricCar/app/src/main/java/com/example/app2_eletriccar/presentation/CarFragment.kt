package com.example.app2_eletriccar.presentation

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import com.example.app2_eletriccar.R
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.app2_eletriccar.data.CarFactory
import com.example.app2_eletriccar.dominio.Carro
import com.example.app2_eletriccar.ui.adapter.CarAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class CarFragment: Fragment() {

    lateinit var fab_calc: FloatingActionButton
    lateinit var rv_lista_car: RecyclerView

    var carrosArray: MutableList<Carro> = ArrayList()

    lateinit var progress: ProgressBar

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
        setupListeners()
        callService()

    }


    fun setupViews(view: View) {  //chama as views
        view.apply {
            fab_calc = findViewById(R.id.fab_calcular)
            rv_lista_car = findViewById(R.id.rv_list_car)
            progress = findViewById(R.id.pb_loader) // continuar aquii
        }

    }

    fun setupList(){ // conecta os adapter
        val adapter = CarAdapter(carrosArray)
        // parou aqui
        rv_lista_car.adapter = adapter



    }

    fun setupListeners() { // os cliques
        fab_calc.setOnClickListener {
            startActivity(Intent(context, CalcularAutonomiaActivity::class.java))
        }
    }

    fun callService (){
        var urlBase = "https://raw.githubusercontent.com/igorbag/cars-api/main/cars.json"
        Mytask().execute(urlBase)

        progress.visibility = View.VISIBLE // <-
    }

    inner class Mytask: AsyncTask<String, String, String>(){

        override fun onPreExecute() {
            super.onPreExecute()
            Log.d("Mytask", "iniciando")
        }
        override fun doInBackground(vararg url: String?): String? {
            Log.d("Mytask", "Tentando conectar em: ${url[0]}")

            var urlConnection: HttpURLConnection? = null

            try {
                val urlBse = URL(url[0])
                urlConnection = urlBse.openConnection() as HttpURLConnection
                urlConnection.connectTimeout= 6000
                urlConnection.readTimeout=6000
                urlConnection.setRequestProperty(
                    "Accept",
                    "application/json"
                )

                Log.d("Mytask", "Conexao aberta, eperando resposta...")


                val responseCode = urlConnection.responseCode
                Log.d("Mytask", "resposta HTTP: $responseCode")

                if (responseCode == HttpURLConnection.HTTP_OK){
                    var response = urlConnection.inputStream.bufferedReader().use {it.readText()}
                    Log.d("Mytask", "resposta recebida, tm: ${response.length} caracteres")

                    publishProgress(response)
                }else {
                    Log.d("Mytask", "servidor indisponivel no momento...")
                }


            } catch (ex: Exception){
                Log.e("Erro", "Erro ao realizar processamento...")
            }finally {
                urlConnection?.disconnect()
            }

            return " "
        }

        override fun onProgressUpdate(vararg values: String?) {
            Log.d("Mytask", "onProgressUpdate chamado, ${values[0]}")
            try {
                var jsonArray = JSONTokener(values[0]).nextValue() as JSONArray

                for ( i in 0 until jsonArray.length()) {
                    val id = jsonArray.getJSONObject(i).getString("id")
                    Log.d("ID ->", id)

                    val preco = jsonArray.getJSONObject(i).getString("preco")
                    Log.d("Preco ->", preco)

                    val bateria = jsonArray.getJSONObject(i).getString("bateria")
                    Log.d("Bateria ->", bateria)

                    val potencia = jsonArray.getJSONObject(i).getString("potencia")
                    Log.d("Potencia ->", potencia)

                    val recarga = jsonArray.getJSONObject(i).getString("recarga")
                    Log.d("Recarga ->", recarga)

                    val urlPhoto = jsonArray.getJSONObject(i).getString("urlPhoto")
                    Log.d("Url ->", urlPhoto)

                    val model = Carro(
                        id = id.toInt(),
                        preco = preco,
                        bateria = bateria,
                        potencia = potencia,
                        recarga = recarga,
                        urlPhoto = urlPhoto
                    )
                    carrosArray.add(model)
            }

                progress.visibility = View.GONE // <-
                setupList()

            } catch (ex: Exception){
                Log.e("Erro", ex.toString())
            }
        }
    }
}