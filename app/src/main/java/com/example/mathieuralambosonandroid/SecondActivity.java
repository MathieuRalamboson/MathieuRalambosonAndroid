package com.example.mathieuralambosonandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "SecondActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.d(TAG,"onCreate started.");
        getIncomingIntent();

    }

    private void getIncomingIntent(){
        Log.d(TAG,"getIncomingIntent: checking for incoming intents.");
        //Si ca ne fonctionne pas App crash

        if(getIntent().hasExtra("image_url")){
            Log.d(TAG,"getIncomingIntent: found intent extras.");
            String imageUrl = getIntent().getStringExtra("image_url");

            setImage(imageUrl);

        }
    }

    private void setImage(String imageUrl){
        Log.d(TAG, "setImage: setting the image.");

        ImageView imageView = findViewById(R.id.fotoImageView);
        Glide.with(this)
                .load("https://pokeres.bastionbot.org/images/pokemon/" + imageUrl + ".png")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

    }
}
