package com.example.mathieuralambosonandroid.Controller;

import com.example.mathieuralambosonandroid.Model.PokemonRespuesta;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokeapiService {
    @GET("pokemon")
    Call<PokemonRespuesta> obtenerListaPokemon(@Query("limit") int limit,@Query("offset") int offset);

}