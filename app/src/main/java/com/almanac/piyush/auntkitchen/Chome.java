package com.almanac.piyush.auntkitchen;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Chome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private Spinner category;

    private RequestQueue requestQueue;
    private ListView ls;
    private String[] iname;
    private String[] iprice;
    private String[] imenu;
    private String[] aname;
    private String[] aadd;
    private String[] arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        arr=getResources().getStringArray(R.array.menu);
        ls= (ListView)findViewById(R.id.auntylist);
        category=(Spinner) findViewById(R.id.selectcategoryspinner);

        requestQueue = Volley.newRequestQueue(Chome.this);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Chome.this, android.R.layout.simple_spinner_item, arr);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        category.setAdapter(adapter);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                  fetch(category.getSelectedItem().toString());


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
                // Getting the Container Layout of the ListView
                RelativeLayout linearLayoutParent = (RelativeLayout) container;

                // Getting the inner Linear Layout
                RelativeLayout linearLayoutChild = (RelativeLayout) linearLayoutParent.getChildAt(0);

                // Getting the Country TextView
                TextView tvCountry = (TextView) linearLayoutChild.getChildAt(3);
Toast.makeText(Chome.this,tvCountry.getText().toString(),Toast.LENGTH_LONG).show();
                Intent i=new Intent(Chome.this,Order.class);
                i.putExtra("aname",tvCountry.getText().toString());
                startActivity(i);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
private void fetch(String cat){

    final ProgressDialog p = ProgressDialog.show(Chome.this,getResources().getString(R.string.fetching),getResources().getString(R.string.pleasewait),false,false);

    JSONObject params = new JSONObject();
    try {
        params.put("category", cat);
    } catch (JSONException e) {
        e.printStackTrace();
    }

    String load_url = getResources().getString(R.string.getlist);

    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, load_url,params, new Response.Listener<JSONObject>() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onResponse(JSONObject response) {
            p.dismiss();
            try {
                JSONArray arr = response.getJSONArray("data");
                iname=new String[arr.length()];
                imenu=new String[arr.length()];
                iprice=new String[arr.length()];
                aadd=new String[arr.length()];
                aname=new String[arr.length()];

                for(int i=0;i<arr.length();i++){
                    JSONObject ob=arr.getJSONObject(i);
                    iname[i]=ob.getString("atodayitemname");
                    imenu[i]=ob.getString("atodayitemmenu");
                    iprice[i]=ob.getString("atodayitemprice");
                    aname[i]=ob.getString("aname");
                    aadd[i]=ob.getString("aaddress");
                }

                AuntDetailsList adapter = new AuntDetailsList(Chome.this,aname,iname,iprice,imenu,aadd);
                ls.setAdapter(adapter);
            }catch (Exception e){

            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(Chome.this,getResources().getString(R.string.slownet),Toast.LENGTH_SHORT).show();
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




}

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_feedback) {
            startActivity(new Intent(this,Feedback.class));

        }else if(id == R.id.action_aboutdev){
            startActivity(new Intent(this,AboutDev.class));
        }else{
            File dir = getFilesDir();
            File file = new File(dir, "auth_custemail.txt");
            file.delete();
            startActivity(new Intent(this,CustLogin.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_chome) {
            startActivity(new Intent(this,Chome.class));
            finish();
        } else if (id == R.id.nav_corders) {
            startActivity(new Intent(this,CmyOrders.class));
            finish();
        } else if (id == R.id.nav_cprofile) {
            startActivity(new Intent(this,Cprofile.class));
            finish();
        } else {
            startActivity(new Intent(this,Cabout.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
