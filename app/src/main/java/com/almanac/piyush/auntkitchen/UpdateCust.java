package com.almanac.piyush.auntkitchen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Hashtable;
import java.util.Map;

public class UpdateCust extends AppCompatActivity {
private EditText nm;
    private EditText ph;
    private EditText add;


    private RequestQueue requestQueue;
    private String n;
    private String p;
    private String a;
    private String e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatecust);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i=getIntent();
nm=(EditText) findViewById(R.id.name);
        ph=(EditText) findViewById(R.id.phone);
        add=(EditText) findViewById(R.id.address);
        requestQueue = Volley.newRequestQueue(this);

        nm.setText(i.getStringExtra("name"));
        ph.setText(i.getStringExtra("phone"));
        add.setText(i.getStringExtra("address"));
        n=i.getStringExtra("name");
        p=i.getStringExtra("phone");
        a=i.getStringExtra("address");
        e=i.getStringExtra("email");



    }
    public void update(View view){

        final ProgressDialog pDialog = ProgressDialog.show(this,getResources().getString(R.string.logging),getResources().getString(R.string.pleasewait),false,false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,getResources().getString(R.string.updatecust), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals(getResources().getString(R.string.success))){
                    pDialog.dismiss();

                    Toast.makeText(UpdateCust.this, getResources().getString(R.string.updatedsuccess), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UpdateCust.this,Cprofile.class));
                    finish();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Toast.makeText(UpdateCust.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Creating parameters
                Map<String,String> params = new Hashtable<>();

                //Adding parameters
                params.put("email",e );
                params.put("name", nm.getText().toString());
                params.put("phone",ph.getText().toString());
                params.put("address",add.getText().toString());
                // params.put("macid", loadData3());

                //returning parameters
                return params;
            }
        };
        requestQueue.add(stringRequest);



    }

}
