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


        //Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/Biuni/PokemonGO-Pokedex/master/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        obtenerDetail();

        getIncomingIntent();

    }

    private void obtenerDetail(){

        DetailapiService service = retrofit.create(DetailapiService.class);
         Call<DetailRespuesta> detailRespuestaCall = service.obtenerDetailPokemon();

         detailRespuestaCall.enqueue(new Callback<DetailRespuesta>() {
             @Override
             public void onResponse(Call<DetailRespuesta> call, Response<DetailRespuesta> response) {
                 if(response.isSuccessful()){

                     DetailRespuesta detailRespuesta = response.body();
                     ArrayList<Detail> listaDetail = detailRespuesta.getPokemon();

                     for(int i = 0; i < listaDetail.size(); i++){
                         Detail d = listaDetail.get(i);
                         Log.i(TAG,"Pokemon: " + d.getName()+" Detail : " + d.getWeight());
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

            String imageHeight = getIntent().getStringExtra("image_height");
            String imageWeight = getIntent().getStringExtra("image_weight");
            String imageType = getIntent().getStringExtra("image_type");
            String imageWeakness = getIntent().getStringExtra("image_weakness");


            setImage(imageUrl,imageName,imageHeight,imageWeight,imageType,imageWeakness);

        }
    }

    private void setImage(String imageUrl,String imageName,String imageHeight,String imageWeight,String imageType,String imageWeakness){
        Log.d(TAG, "setImage: setting the image.");

        TextView name = findViewById(R.id.nombreTextView);
        name.setText(imageName);

        TextView height = findViewById(R.id.height);
        height.setText(imageHeight);

        TextView weight = findViewById(R.id.weight);
        weight.setText(imageWeight);

        TextView type = findViewById(R.id.type);
        type.setText(imageType);

        TextView weakness = findViewById(R.id.weakness);
        weakness.setText(imageWeakness);

        ImageView imageView = findViewById(R.id.fotoImageView);
        Glide.with(this)
                .load("https://pokeres.bastionbot.org/images/pokemon/" + imageUrl + ".png")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);


    }
}
