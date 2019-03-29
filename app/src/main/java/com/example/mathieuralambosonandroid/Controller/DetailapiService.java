package com.example.mathieuralambosonandroid.Controller;

import com.example.mathieuralambosonandroid.Model.DetailRespuesta;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DetailapiService {
   @GET("pokemon/2")
   Call<DetailRespuesta> obtenerDetailPokemon();
}
