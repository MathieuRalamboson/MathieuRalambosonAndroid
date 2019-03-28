package com.example.mathieuralambosonandroid.pokeapi;

import retrofit2.Call;

public interface PokeapiService {

    Call<PokemonRespuesta> obtenerListaPokemon();

}
