package com.example.sayantan.quotesbemotivated;

import android.app.ProgressDialog;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyQuotesData {
    ArrayList<MyQuotes> quoteArrayList = new ArrayList<>();





    public void getQuotes(final QuoteListAsyncyResponse callBack) {
        String url = "https://raw.githubusercontent.com/pdichone/UIUX-Android-Course/master/Quotes.json%20";





        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(Request.Method.GET,
                        url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject quoteObject = response.getJSONObject(i);
                                MyQuotes quote = new MyQuotes();
                                quote.setQuote(quoteObject.getString("quote"));
                                quote.setAuthor(quoteObject.getString("name"));

                                Log.d("STUFFF::", quoteObject.getString("name"));

                                quoteArrayList.add(quote);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        if (null != callBack){
                            callBack.processFinished(quoteArrayList);
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

       MySingleton.getInstance().addToRequestQueue(jsonArrayRequest);

    }
}