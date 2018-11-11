package com.example.sayantan.quotesbemotivated;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String url="https://talaikis.com/api/quotes/";

    private ImageButton next;
    private TextView textView,textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        next=(ImageButton)findViewById(R.id.next);
        textView=(TextView) findViewById(R.id.textView2);
        textView2=(TextView) findViewById(R.id.textView3);

        check();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });







    }

    public void check(){
        if (checkConnection()) {
            go2nxt();
        }
        else {
            next.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            textView2.setVisibility(View.INVISIBLE);
            new AlertDialog.Builder(this)
                    .setTitle("Internet Connection")
                    .setMessage("Please Turn on the Internet and Click Below")
                    .setPositiveButton(android.R.string.yes,null).create().show();
        }
    }

    public void go2nxt(){
        Thread thread = new Thread() {


            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent mainIntent = new Intent(MainActivity.this, MainPage.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        };
        thread.start();
    }


    protected boolean isOnline() {

        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {

            return true;

        } else {

            return false;

        }

    }

    public boolean checkConnection(){

        if(isOnline()){
            return true;

        }else{

            Toast.makeText(MainActivity.this, "You are not connected to Internet", Toast.LENGTH_LONG).show();
            return false;
        }

    }


}
