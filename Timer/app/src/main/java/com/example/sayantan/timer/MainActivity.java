package com.example.sayantan.timer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    int timeleft=0,busy=0;
    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    CountDownTimer countDownTimer;
    Button btn;

    public void check(View view){
        if (busy==0){
            timerPlay(view);
        }
    }

    public void timerPlay(View view) {

        btn=(Button)findViewById(R.id.button2);


        int p = 0;
        final TextView timeUpdate = (TextView) findViewById(R.id.timeleft);
        if(busy==0){
            mediaPlayer=MediaPlayer.create(MainActivity.this,R.raw.timer);
            audioManager=(AudioManager)getSystemService(AUDIO_SERVICE);
            int maxVol=audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
            audioManager.setStreamVolume(AudioManager.STREAM_ALARM,maxVol,0);

        EditText hour = (EditText) findViewById(R.id.hr);
        EditText minute = (EditText) findViewById(R.id.min);
        EditText second = (EditText) findViewById(R.id.sec);

        String hr = hour.getText().toString();
        String min = minute.getText().toString();
        String sec = second.getText().toString();
        if (TextUtils.isEmpty(hr)) {
            p += 1;
        } else {
            timeleft += ((Integer.parseInt(hr)) * 3600);
        }
        if (TextUtils.isEmpty(min)) {
            p += 1;
        } else {
            timeleft += ((Integer.parseInt(min)) * 60);
        }
        if (TextUtils.isEmpty(sec)) {
            p += 1;
        } else {
            timeleft += ((Integer.parseInt(sec)));
        }
        timeleft *= 1000;
        busy=1;
        }
        else{
            Toast.makeText(getApplicationContext(),"You timer is running",Toast.LENGTH_SHORT).show();
        }


        if (p >= 3) {
            Toast.makeText(getApplicationContext(), "Enter Valid Time", Toast.LENGTH_SHORT).show();
            busy=0;
        } else {
            countDownTimer=new CountDownTimer(timeleft, 1000) {
                public void onTick(long millisUntilFinished)
                {
                    long a=(millisUntilFinished/1000);

                    timeUpdate.setText("Remaining:" + a);
                    btn.setEnabled(true);

                }

                @Override
                public void onFinish() {
                    timeUpdate.setText("DONE!!");
                    timeleft=0;
                    busy=0;
                    mediaPlayer.start();
                }
            }.start();
        }
    }

    public void reset(View view){
        TextView timeUpdate=(TextView)findViewById(R.id.timeleft);
        timeUpdate.setText("TIME IS PRECIOUS");
        EditText hour = (EditText) findViewById(R.id.hr);
        EditText minute = (EditText) findViewById(R.id.min);
        EditText second = (EditText) findViewById(R.id.sec);
        btn=(Button)findViewById(R.id.button2);
        hour.setText("");
        minute.setText("");
        second.setText("");
        busy=0;
        mediaPlayer.stop();
        timeleft=0;
        countDownTimer.cancel();
        btn.setEnabled(false);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=(Button)findViewById(R.id.button2);
        btn.setEnabled(false);

    }
}
