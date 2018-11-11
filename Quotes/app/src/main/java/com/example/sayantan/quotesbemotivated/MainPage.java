package com.example.sayantan.quotesbemotivated;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainPage extends AppCompatActivity {

    private  QuotesViewpagerAdapter quotesViewpagerAdapter;
    private ViewPager viewPager;

    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        progressDialog=new ProgressDialog(this);

        progressDialog.setTitle("Loading The Quotes");
        progressDialog.setMessage("Waiting is the Sign of True Love");

        progressDialog.show();



        quotesViewpagerAdapter=new QuotesViewpagerAdapter(getSupportFragmentManager(),getFragment());
        viewPager=findViewById(R.id.viewpager);
        viewPager.setAdapter(quotesViewpagerAdapter);








    }

    private List<Fragment>getFragment(){

        final List<Fragment>fragmentList=new ArrayList<>();



        new  MyQuotesData().getQuotes(new QuoteListAsyncyResponse() {
            @Override
            public void processFinished(ArrayList<MyQuotes> quotes) {

                progressDialog.dismiss();
                Toast toast = new Toast(MainPage.this);
                ImageView view = new ImageView(MainPage.this);
                view.setImageResource(R.drawable.swipe);
                toast.setView(view);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
                Toast toast1=Toast.makeText(MainPage.this,"Swipe left",Toast.LENGTH_LONG);
                toast1.setGravity(Gravity.CENTER,0,0);
                toast1.show();



                for(int  i=0;i<quotes.size();i++){



                    QuotesFragment quotesFragment=QuotesFragment.newInstance(quotes.get(i).getQuote(),quotes.get(i).getAuthor());
                    fragmentList.add(quotesFragment);
                    Collections.shuffle(fragmentList);
                }

                Collections.shuffle(fragmentList);

                quotesViewpagerAdapter.notifyDataSetChanged();

            }
        });








        return fragmentList;



    }




}
