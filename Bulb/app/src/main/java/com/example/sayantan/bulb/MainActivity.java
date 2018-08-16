package com.example.sayantan.bulb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public void blueTap(View view){
        ImageView blue= (ImageView) findViewById(R.id.blueBulb);
        ImageView green= (ImageView) findViewById(R.id.greenBulb);
        blue.animate().alpha(1).setDuration(2000);
        green.animate().alpha(0).setDuration(2000);
    }
    public void greenTap(View view){
        ImageView green=(ImageView)findViewById(R.id.greenBulb);
        ImageView blue=(ImageView)findViewById(R.id.blueBulb);
        green.animate().alpha(1).setDuration(2000);
        blue.animate().alpha(0).setDuration(2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
