package com.example.sayantan.quotesbemotivated;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ThreadLocalRandom;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuotesFragment extends Fragment {


    public QuotesFragment() {
        // Required empty public constructor
    }
    TextView quoteText,quoteAuthor;

    ImageButton profile;

    CardView cardView;
    View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View qView=inflater.inflate(R.layout.fragment_quotes, container, false);



        cardView=(CardView)qView.findViewById(R.id.cardView);



        ImageButton imgbtn=(ImageButton)qView.findViewById(R.id.imageButton);
        ImageButton ss=(ImageButton)qView.findViewById(R.id.imageView3);

        profile=(ImageButton)qView.findViewById(R.id.profile);

        quoteText=(TextView)qView.findViewById(R.id.quotes);
        quoteAuthor=(TextView)qView.findViewById(R.id.author);

        final String quote=getArguments().getString("quote");
        final String author=getArguments().getString("author");
        quoteText.setText(quote);
        quoteAuthor.setText(author);

        int colors[]=new int[]{R.color.blue_500,R.color.pink_900,R.color.green_400,R.color.lime_400,R.color.orange_400,R.color.amber_800,R.color.grey_700};

        cardView.setBackgroundResource(getRandom(colors));

        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Quotes:Be Motivated");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, quote+" -By @"+author);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });


        ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copied Text",quote+" -By @"+author);
                clipboard.setPrimaryClip(clip);

                Toast.makeText(getContext(),"Quote copied",Toast.LENGTH_SHORT).show();

            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a=new Intent(getActivity(),myprofile.class);
                startActivity(a);
            }
        });


        return qView;
    }





    public static final QuotesFragment newInstance(String quote,String author){
        QuotesFragment fragment=new QuotesFragment();
        Bundle bundle=new Bundle();
        bundle.putString("quote",quote);
        bundle.putString("author",author);
        fragment.setArguments(bundle);
        return fragment;
    }

    int getRandom(int[] colorArray){
        int color;
        int quoteArrayLen=colorArray.length;
        int rand= ThreadLocalRandom.current().nextInt(quoteArrayLen);
        color=colorArray[rand];
        return color;
    }

}
