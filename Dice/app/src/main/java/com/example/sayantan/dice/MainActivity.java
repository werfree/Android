package com.example.sayantan.dice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public void rollIt(View view){
        Random ran= new Random();
        int ranNo = ran.nextInt(6);
        ImageView img=(ImageView)findViewById(R.id.dice);
        if (ranNo==1){

            img.setImageResource(R.drawable.one);


        }
        else if (ranNo==1){

            img.setImageResource((R.drawable.two));

        }
        else if (ranNo==2){

            img.setImageResource((R.drawable.three));

        }
        else if (ranNo==3){

            img.setImageResource((R.drawable.four));

        }
        else if (ranNo==4){

            img.setImageResource((R.drawable.five));

        }
        else{

            img.setImageResource((R.drawable.six));


        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
