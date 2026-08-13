package com.example.app2_eletriccar.presentation

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.app2_eletriccar.R
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.app2_eletriccar.data.CarFactory
import com.example.app2_eletriccar.data.CarsApi
import com.example.app2_eletriccar.dominio.Carro
import com.example.app2_eletriccar.ui.adapter.CarAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.channels.ReceiveChannel
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class CarFragment: Fragment() {

    lateinit var fab_calc: FloatingActionButton
    lateinit var rcv_lista_car: RecyclerView
    var carrosArray: ArrayList<Carro> = ArrayList()
    lateinit var progress: ProgressBar
    lateinit var noInternetImage: ImageView
    lateinit var noInternetText: TextView

    lateinit var carsAPI : CarsApi

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.car_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // chamadas das fun

        setupRetrofit()
        setupViews(view)
        setupListeners()

    }

    override fun onResume() {
        super.onResume()
        val checkInternet = checkForInternet(context)
        Log.d("Mytask Conexao Internet", checkInternet.toString())
        if (checkInternet){
            callService()
        }
        else {
            emptyState()
        }
    }


    fun setupRetrofit(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/igorbag/cars-api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        carsAPI = retrofit.create(CarsApi::class.java)

    }


    fun getAllCars(){
        carsAPI.getAllCars().enqueue(object : Callback<List<Carro>> {  // parada aqui

        })
    }

    fun emptyState(){
        progress.isVisible = false
        rcv_lista_car.isVisible = false
        noInternetImage.isVisible = true
        noInternetText.isVisible = true
    }


    fun setupViews(view: View) {  //chama as views
        view.apply {
            fab_calc = findViewById(R.id.fab_calcular)
            rcv_lista_car = findViewById(R.id.rcv_list_car)
            progress = findViewById(R.id.pb_loader)
            noInternetImage = findViewById(R.id.iv_empty_state)
            noInternetText = findViewById(R.id.tv_noInternet)
        }

    }

    fun setupList(){ // conecta os adapter
        val carroAdapter = CarAdapter(carrosArray)

        rcv_lista_car.apply{
            isVisible = true
            adapter = carroAdapter
        }

    }

    fun setupListeners() { // os cliques
        fab_calc.setOnClickListener {
            startActivity(Intent(context, CalcularAutonomiaActivity::class.java))
        }
    }

    fun callService (){
        var urlBase = "https://raw.githubusercontent.com/igorbag/cars-api/main/cars.json"
        Mytask().execute(urlBase)

    }

    fun checkForInternet(context: Context?): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            val network = connectivityManager.activeNetwork?: return false


            val activityNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activityNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activityNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        }
        else {
            @Suppress("DEPRECATION")
            val networkinfo = connectivityManager.activeNetworkInfo?: return false
            @Suppress("DEPRECATION")
            return networkinfo.isConnected
        }
    }

    inner class Mytask: AsyncTask<String, String, String>(){

        override fun onPreExecute() {
            super.onPreExecute()
            Log.d("Mytask", "iniciando")
            progress.isVisible = true

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

                progress.isVisible = false
                noInternetImage.isVisible = false
                noInternetText.isVisible = false

                //setupList()

            } catch (ex: Exception){
                Log.e("Erro", ex.toString())
            }
        }
    }
}