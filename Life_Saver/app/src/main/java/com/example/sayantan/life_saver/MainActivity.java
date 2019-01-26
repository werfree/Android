package com.example.sayantan.life_saver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread thread=new Thread(){


            @Override
            public void run(){
                try{
                    sleep(5000);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    mAuth = FirebaseAuth.getInstance();
                    FirebaseUser currentUser = mAuth.getCurrentUser();

                    if (mAuth.getUid() == null) {

                        Intent intent = new Intent(MainActivity.this,SignUp.class);
                        //Toast.makeText(MainPage.this,"All Done",Toast.LENGTH_LONG).show();
                        startActivity(intent);
                        finish();



                    }
                    else {

                        Intent mainIntent = new Intent(MainActivity.this, MainPage.class);
                        startActivity(mainIntent);
                        finish();
                    }
                }
            }
        };
        thread.start();
    }
}
