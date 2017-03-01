package com.almanac.piyush.auntkitchen;

import android.annotation.SuppressLint;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class UpcomingFeatures extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
TextView txt,txt2,txt3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_features);
        txt=(TextView) findViewById(R.id.uf1);
        txt2=(TextView) findViewById(R.id.uf2);
        txt3=(TextView) findViewById(R.id.uf3) ;
        getSupportLoaderManager().initLoader(0,null, this);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        FetchData fb=new FetchData(UpcomingFeatures.this,txt,txt2,txt3);
        fb.forceLoad();
        return null;
    }


    private static class FetchData extends AsyncTaskLoader<String> {
        String check=null;
        RequestQueue requestQueue;
      TextView txt1,txttwo,txtthree;
        public FetchData(Context context,TextView txt,TextView txt2,TextView txt3) {
            super(context);
            requestQueue = Volley.newRequestQueue(context);
this.txt1=txt;
            this.txttwo=txt2;
            this.txtthree=txt3;
        }

        @Override
        public String loadInBackground() {
JSONObject params=new JSONObject();
            String load_url = getContext().getResources().getString(R.string.fetchfeatures);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, load_url,params, new Response.Listener<JSONObject>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        JSONArray arr = response.getJSONArray("data");
                        JSONObject a = arr.getJSONObject(0);
                        txt1.setText(a.getString("feature"));
                        txttwo.setText(a.getString("feature2"));
                        txtthree.setText(a.getString("feature3"));

                    }catch (Exception e){

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };
            requestQueue.add(jsonObjectRequest);
            return check;
        }

        @Override
        public void deliverResult(String data) {
            super.deliverResult(data);
        }
    }


}


