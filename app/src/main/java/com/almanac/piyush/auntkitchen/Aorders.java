package com.almanac.piyush.auntkitchen;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
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

public class Aorders extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ListView ls1;
    private String[] cname;
    private String[] iname;
    private String[] iqty;
    private String[] totalprice;
    private String[] cphone;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aorders);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        requestQueue = Volley.newRequestQueue(Aorders.this);
        ls1=(ListView) findViewById(R.id.todaylist);


        final ProgressDialog p = ProgressDialog.show(Aorders.this,"Fetching All Data","Please Wait",false,false);

        JSONObject params = new JSONObject();
        try {
            params.put("email", loadData());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String load_url = getResources().getString(R.string.fetchtodayorder);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, load_url,params, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {
                p.dismiss();
                try {
                    JSONArray arr=response.getJSONArray("data");
                    cname=new String[arr.length()];
                    cphone=new String[arr.length()];
                    iname=new String[arr.length()];
                    iqty=new String[arr.length()];
                    totalprice=new String[arr.length()];

                    for(int i=0;i<arr.length();i++)
                    {
                        JSONObject ob=arr.getJSONObject(i);
                        cname[i]=ob.getString("cname");
                        cphone[i]=ob.getString("cphone");
                        iname[i]=ob.getString("oitemname");
                        iqty[i]=ob.getString("oitemqty");
                        totalprice[i]=ob.getString("oitemtotalprice");
                    }
                    TodaysOrder adapter = new TodaysOrder(Aorders.this,cname,iname,totalprice,iqty,cphone);
                    ls1.setAdapter(adapter);
                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Aorders.this,getResources().getString(R.string.slownet),Toast.LENGTH_SHORT).show();
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    private String loadData() {
        String URL = "content://com.almanac.piyush.auntkitchen.DBHelper";

        Uri dt = Uri.parse(URL);
        Cursor c = managedQuery(dt, null, null, null, "email DESC");
        c.moveToFirst();
        return c.getString(c.getColumnIndex(DBHelper.EMAIL));
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
        getMenuInflater().inflate(R.menu.aorders, menu);
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
            File file = new File(dir, "auth_auntyemail.txt");
            file.delete();
            startActivity(new Intent(this,AuntLogin.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_aorders) {
            startActivity(new Intent(this,Aorders.class));
            finish();
        } else if (id == R.id.nav_atoday) {
            startActivity(new Intent(this,Atoday.class));
            finish();
        } else if (id == R.id.nav_aprofile) {
            startActivity(new Intent(this,Aprofile.class));
            finish();
        } else {
            startActivity(new Intent(this,Aabout.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
