package com.example.app2_eletriccar.data

import com.example.app2_eletriccar.dominio.Carro
import retrofit2.Call
import retrofit2.http.GET

interface CarsApi {

    @GET("cars.json")
    fun getAllCars(): Call<List<Carro>>
}