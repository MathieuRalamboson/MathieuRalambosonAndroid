package com.example.mathieuralambosonandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mathieuralambosonandroid.Controller.DetailapiService;
import com.example.mathieuralambosonandroid.Model.Detail;
import com.example.mathieuralambosonandroid.Model.DetailRespuesta;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "SecondActivity";

    private Retrofit retrofit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.d(TAG,"onCreate started.");
        getIncomingIntent();

        //Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        obtenerDetail();

    }

    private void obtenerDetail(){

        DetailapiService service = retrofit.create(DetailapiService.class);
         Call<DetailRespuesta> detailRespuestaCall = service.obtenerDetailPokemon();

         detailRespuestaCall.enqueue(new Callback<DetailRespuesta>() {
             @Override
             public void onResponse(Call<DetailRespuesta> call, Response<DetailRespuesta> response) {
                 if(response.isSuccessful()){

                     DetailRespuesta detailRespuesta = response.body();
                     ArrayList<Detail> listaDetail = detailRespuesta.getTypes();

                     for(int i = 0; i < listaDetail.size(); i++){
                         Detail p = listaDetail.get(i);
                         Log.i(TAG,"Detail : " + p.getSlot());
                     }

                 } else{
                     Log.e(TAG,"Detail onResponse: " + response.errorBody());
                 }
             }

             @Override
             public void onFailure(Call<DetailRespuesta> call, Throwable t) {
                 Log.e(TAG,"Detail onFailure: " + t.getMessage());

             }
         });

    }

    private void getIncomingIntent(){
        Log.d(TAG,"getIncomingIntent: checking for incoming intents.");
        //Si ca ne fonctionne pas App crash

        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("image_name")){
            Log.d(TAG,"getIncomingIntent: found intent extras.");
            String imageUrl = getIntent().getStringExtra("image_url");
            String imageName = getIntent().getStringExtra("image_name");

            setImage(imageUrl,imageName);

        }
    }

    private void setImage(String imageUrl,String imageName){
        Log.d(TAG, "setImage: setting the image.");

        TextView name = findViewById(R.id.nombreTextView);
        name.setText(imageName);

        ImageView imageView = findViewById(R.id.fotoImageView);
        Glide.with(this)
                .load("https://pokeres.bastionbot.org/images/pokemon/" + imageUrl + ".png")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);


    }
}
