package com.example.sayantan.stonepaperscissor;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int c_Point=0;
    int h_point=0;
    MediaPlayer play,win,lose;


    public void stone (View view){
        cal(view,"stone");
    }
    public void paper(View view){
        cal(view,"paper");
    }
    public void scissor(View view){
        cal(view,"scissor");
    }
    public void cal(View view,String s){
        ImageView h_Image=(ImageView)findViewById(R.id.man);
        ImageView c_Image=(ImageView)findViewById(R.id.comp);
        Random ran= new Random();
        int ran_No=ran.nextInt(3);
        point(view,s,ran_No);
        if (ran_No==0){
            c_Image.setImageResource(R.drawable.stone);
            c_Image.animate().rotation(180).setDuration(1000);
        }
        else if(ran_No==1){
            c_Image.setImageResource(R.drawable.paper);
            c_Image.animate().rotation(180).setDuration(1000);
        }
        else {
            c_Image.setImageResource(R.drawable.scissor);
            c_Image.animate().rotation(180).setDuration(1000);
        }
        if(s.equals("stone")){
            h_Image.setImageResource(R.drawable.stone);
            h_Image.animate().rotation(-180).setDuration(1000);

        }
        else if(s.equals(("paper"))){
            h_Image.setImageResource(R.drawable.paper);
            h_Image.animate().rotation(-180).setDuration(1000);
        }
        else{
            h_Image.setImageResource(R.drawable.scissor);
            h_Image.animate().rotation(-180).setDuration(1000);
        }
    }

    public void point(View view, String h,int c){
        if(h.equals("stone")){
            if(c==1){
                c_Point+=1;
            }
            else if(c==2){
                h_point+=1;
            }
        }
        else if(h.equals("paper")){
            if(c==2){
                c_Point+=1;
            }
            else if(c==0){
                h_point+=1;
            }
        }
        if(h.equals("scissor")){
            if(c==0){
                c_Point+=1;
            }
            else if(c==1){
                h_point+=1;
            }
        }

        TextView p_Update=(TextView)findViewById(R.id.p_point);
        p_Update.setText(""+c_Point);
        TextView h_Update=(TextView)findViewById(R.id.h_point);
        h_Update.setText(""+h_point);

        if (h_point==5){
            Toast.makeText(this, "Congo!!You win!!!", Toast.LENGTH_LONG).show();
            reset(view);
        }
        else if(c_Point==5){
            Toast.makeText(this, "You lose!!Better luck next time", Toast.LENGTH_LONG).show();
            reset(view);
        }


    }
    public void reset(View view){
        c_Point=0;h_point=0;
        TextView p_Update=(TextView)findViewById(R.id.p_point);
        p_Update.setText(""+c_Point);
        TextView h_Update=(TextView)findViewById(R.id.h_point);
        h_Update.setText(""+h_point);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
