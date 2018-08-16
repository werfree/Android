package com.example.sayantan.weatherapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {



    Button btn;
    EditText city;
    TextView te;
    TextView sky;
    TextView humidity;
    TextView pressure;
    TextView hr;
    Date now;
    String finalResult="";
    String s_temp;
    String s_sky;
    TextToSpeech textToSpeech;
    int results;
    //http://api.openweathermap.org/data/2.5/weather?q=kol&appid=84c06daf2094e0c19fb553e6227b12f6
    //http://api.openweathermap.org/data/2.5/forecast?q=kol&appid=84c06daf2094e0c19fb553e6227b12f6

    String baseUrl="http://api.openweathermap.org/data/2.5/weather?q=";
    String api="&appid=84c06daf2094e0c19fb553e6227b12f6";
    String hUrl="http://api.openweathermap.org/data/2.5/forecast?q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=(Button)findViewById(R.id.button);
        city=(EditText)findViewById(R.id.getCity);
        sky=(TextView)findViewById(R.id.sky);
        te=(TextView)findViewById(R.id.tem);
        humidity=(TextView)findViewById(R.id.hum);
        pressure=(TextView)findViewById(R.id.pre);

        textToSpeech=new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status==TextToSpeech.SUCCESS){
                    textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cit=(city.getText().toString());
                cit=cit.replaceAll("\\s","");


                if (cit.isEmpty()){
                    Toast.makeText(MainActivity.this,"City name is empty",Toast.LENGTH_SHORT).show();
                    sky.setText("Please Enter");
                    te.setText("The CITY.");
                    humidity.setText("");
                    pressure.setText("");

                }
                else{
                    sky.setText("Please wait");
                    te.setText(". . . . ");
                    humidity.setText("");
                    pressure.setText("");

                    String url=baseUrl+cit+api;
                    final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    try {
                                        String weather=response.getString("weather");
                                        String mainTemp=response.getString("main");
                                        JSONObject ob1=new JSONObject(mainTemp);
                                        int temp=(ob1.getInt("temp"));
                                        temp=temp-273;
                                        String f_temp=Integer.toString(temp);
                                        s_temp=f_temp;
                                        f_temp=f_temp+"°C";
                                        te.setText(f_temp);
                                        if(te.getText().length()>5){
                                            Toast.makeText(MainActivity.this,"Check the City name and the internet connection",Toast.LENGTH_LONG).show();
                                        }
                                        else{

                                            String pressur=ob1.getString("pressure");
                                            String hum= ob1.getString("humidity");
                                            pressure.setText(pressur+"hpa");
                                            humidity.setText(hum+"%");
                                        }


                                        //int s=Integer.valueOf(temp);
                                        JSONArray ar=new JSONArray(weather);
                                        for (int i=0;i<ar.length();i++){
                                            JSONObject ob=ar.getJSONObject(i);
                                            String myWeather=ob.getString("main");
                                            sky.setText("Sky: "+myWeather);
                                            s_sky=myWeather;
                                            }

                                        speakout(s_temp,s_sky);

                                    } catch (JSONException e) {
                                        sky.setText("Sorry city not found");
                                        Toast.makeText(MainActivity.this,"Check the internet connection",Toast.LENGTH_LONG).show();
                                        e.printStackTrace();

                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.i("Error","Error"+error);
                                    String s;
                                    s="Please enter a valid city or check the internet connection";
                                    textToSpeech.speak(s,TextToSpeech.QUEUE_FLUSH,null);
                                    Toast toast= Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER,0,0);
                                    toast.show();
                                }
                            }
                    );
                    MySingleton.getInstance(MainActivity.this).addToRequestQue(jsonObjectRequest);




                }


            }
        });


    }
    public void speakout(String temp,String sky){
        String s;
            s = "The present temperature in " + city.getText() + "is " + temp + " with " + sky + "y sky";
        textToSpeech.speak(s,TextToSpeech.QUEUE_FLUSH,null);

    }
    public void onStop() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    super.onStop();
    }
    public void onmickclick(View view){
        if(view.getId()==R.id.mice){
            s2t();
        }
    }
    public void s2t(){
        Intent i= new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say your City Name");


        try{
            startActivityForResult(i,100);
        }
        catch (ActivityNotFoundException e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }



    }
    public void onActivityResult(int request_code,int result_code,Intent i){

        super.onActivityResult(request_code,result_code,i);
        switch (request_code)
        {
            case 100:if(result_code==RESULT_OK && i!=null){
                ArrayList<String> result=i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                city.setText(result.get(0));
                btn.callOnClick();
            }
            break;
        }



    }
}
