package com.example.mathieuralambosonandroid.pokeapi;

import com.example.mathieuralambosonandroid.model.PokemonRespuesta;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PokeapiService {
        @GET ("pokemon")
    Call<PokemonRespuesta> obtenerListaPokemon();

}
