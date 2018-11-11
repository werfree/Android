package com.example.sayantan.quotesbemotivated;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class myprofile extends AppCompatActivity {

    ImageButton call,fb,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        call=(ImageButton)findViewById(R.id.call);
        fb=(ImageButton)findViewById(R.id.fb);
        email=(ImageButton)findViewById(R.id.email);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("https://www.facebook.com/gsayantan01"));
                startActivity(viewIntent);
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+918777812060"));
                startActivity(intent);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","gsayantan01@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Quotes:Be Motivated");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });



    }
}
