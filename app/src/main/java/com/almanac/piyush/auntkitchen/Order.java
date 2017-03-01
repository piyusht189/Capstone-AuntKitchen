package com.almanac.piyush.auntkitchen;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Order extends AppCompatActivity {
    EditText iqty;
    int tp;
    RequestQueue requestQueue;
    TextView aname, aaddress, aphone, iname, imenu, iprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        requestQueue = Volley.newRequestQueue(Order.this);
        iqty = (EditText) findViewById(R.id.iqty);
        aname = (TextView) findViewById(R.id.auntyname);
        aaddress = (TextView) findViewById(R.id.auntaddress);
        aphone = (TextView) findViewById(R.id.auntyphone);
        iname = (TextView) findViewById(R.id.iname);
        imenu = (TextView) findViewById(R.id.imenu);
        iprice = (TextView) findViewById(R.id.iprice);

        Intent i = getIntent();

        final ProgressDialog p = ProgressDialog.show(Order.this, getResources().getString(R.string.fetching), getResources().getString(R.string.pleasewait), false, false);

        JSONObject params = new JSONObject();
        try {
            params.put("aname", i.getStringExtra("aname"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String load_url = getResources().getString(R.string.getauntydetailorder);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, load_url, params, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {
                p.dismiss();
                try {
                    JSONArray arr = response.getJSONArray("data");
                    JSONObject ob = arr.getJSONObject(0);
                    aname.setText(ob.getString("aname"));
                    aphone.setText(ob.getString("aphone"));
                    aaddress.setText(ob.getString("aaddress"));
                    iname.setText(ob.getString("atodayitemname"));
                    imenu.setText(ob.getString("atodayitemmenu"));
                    iprice.setText(ob.getString("atodayitemprice"));
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Order.this, getResources().getString(R.string.slownet), Toast.LENGTH_SHORT).show();
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


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String check;
                final ProgressDialog pDialog = ProgressDialog.show(Order.this, getResources().getString(R.string.ordering), getResources().getString(R.string.pleasewait), false, false);
                tp = Integer.parseInt(iqty.getText().toString()) * Integer.parseInt(iprice.getText().toString());
                try {
                    int num = Integer.parseInt(iqty.getText().toString());
                    check = "yes";
                } catch (NumberFormatException e) {
                    check = "no";
                }
                if (!iqty.getText().toString().equals("") && check.equals("yes"))

                {
                    Toast.makeText(Order.this, "Total Price:" + String.valueOf(tp), Toast.LENGTH_SHORT).show();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.setorder), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(Order.this, response, Toast.LENGTH_SHORT).show();
                            if (response.equals(getResources().getString(R.string.success))) {
                                pDialog.dismiss();
                                Toast.makeText(Order.this, getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Order.this, Chome.class));
                                finish();
                            } else {
                                pDialog.dismiss();
                                Toast.makeText(Order.this, getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pDialog.dismiss();
                            Toast.makeText(Order.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            //Creating parameters
                            Map<String, String> params = new Hashtable<>();

                            //Adding parameters
                            params.put("aname", aname.getText().toString());
                            params.put("cemail", loadData());
                            params.put("qty", iqty.getText().toString());
                            params.put("totalprice", String.valueOf(tp));
                            // params.put("macid", loadData3());

                            //returning parameters
                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);
                } else {
                    Toast.makeText(Order.this, "Kindly enter Quantity in number!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected String loadData() {
        String FILENAME = "auth_custemail.txt";
        String out = "";

        /*try {
            FileInputStream fis1 = getApplication().openFileInput(FILENAME);
            BufferedReader br1 = new BufferedReader(new InputStreamReader(fis1));
            String sLine1;
            while (((sLine1 = br1.readLine()) != null)) {
                out += sLine1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }*/
        DBHelper db=new DBHelper(getApplicationContext());
        Cursor c=db.getData();
        c.moveToFirst();
        return c.getString(1);
    }

}
