package com.example.sayantan.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String player = "cross";
    String[] myGameState = {"0","1","2","3","4","5","6","7","8"};

    public void tap(View view) {
        ImageView myTapped = (ImageView) view;
        int imgTag = Integer.parseInt(myTapped.getTag().toString());

        if (!(myGameState[imgTag].equals("cross") || myGameState[imgTag].equals("dot"))){
            myGameState[imgTag] = player;
            if (player.equals("cross")) {
                myTapped.setImageResource(R.drawable.cross);
                player = "dot";
            } else  {
                myTapped.setImageResource(R.drawable.circle);
                player = "cross";
            }
            myTapped.animate().rotation(360).setDuration(1000);
            int s=check();
            if(s==1){
                if (player=="cross"){
                    Toast.makeText(getApplicationContext(), "Circle is the winner", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Cross is the winner", Toast.LENGTH_LONG).show();
                }
            }
            else if(s==3){

                Toast.makeText(getApplicationContext(), "MATCH DRAW", Toast.LENGTH_LONG).show();
            }

        }
        else{
            Toast.makeText(getApplicationContext(), "Place is Already Filled", Toast.LENGTH_SHORT).show();
        }


    }


    public void reset(View view) {

        player = "cross";
        for (int i = 0; i < myGameState.length; i++) {
            myGameState[i] =Integer.toString(i);
        }
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.Line1);
        //linearLayout.getChildCount()to get total my of image in the layout
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            ((ImageView) linearLayout.getChildAt(i)).setImageResource(R.mipmap.ic_launcher);
            ((ImageView) linearLayout.getChildAt(i)).animate().rotation(360).setDuration(1000);
        }

        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.Line2);
        //linearLayout.getChildCount()to get total my of image in the layout
        for (int i = 0; i < linearLayout2.getChildCount(); i++) {
            ((ImageView) linearLayout2.getChildAt(i)).setImageResource(R.mipmap.ic_launcher);
            ((ImageView) linearLayout2.getChildAt(i)).animate().rotation(360).setDuration(1000);
        }

        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.Line3);
        //linearLayout.getChildCount()to get total my of image in the layout
        for (int i = 0; i < linearLayout3.getChildCount(); i++) {
            ((ImageView) linearLayout3.getChildAt(i)).setImageResource(R.mipmap.ic_launcher);
            ((ImageView) linearLayout3.getChildAt(i)).animate().rotation(360).setDuration(1000);
        }
    }
    public int check(){
        if((myGameState[0].equals(myGameState[1]) && myGameState[1].equals(myGameState[2]))||
                (myGameState[3].equals(myGameState[4])&&myGameState[4].equals(myGameState[5]))||
                (myGameState[6].equals(myGameState[7])&&myGameState[7].equals(myGameState[8]))||
                (myGameState[0].equals(myGameState[4])&&myGameState[4].equals(myGameState[8]))||
                (myGameState[2].equals(myGameState[4])&&myGameState[4].equals(myGameState[6]))||
                (myGameState[0].equals(myGameState[3])&&myGameState[3].equals(myGameState[6]))||
                (myGameState[1].equals(myGameState[4])&&myGameState[4].equals(myGameState[7]))||
                (myGameState[2].equals(myGameState[5])&&myGameState[5].equals(myGameState[8]))){
            for(int i=0;i<myGameState.length;i++){
                myGameState[i]=player;
            }

            return 1;
        }
        else{
            for(int i=0;i<myGameState.length;i++){
                if (!(myGameState[i].equals("cross") || myGameState[i].equals("dot"))){
                    return 2;
                }
            }
            return 3;
        }

    }







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
