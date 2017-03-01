package com.almanac.piyush.auntkitchen;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyOrderList extends ArrayAdapter<String> {

    Activity context;
    String[] aname,itemname,itemprice,qty,aphone;

    TextView taname,titemname,titemprice,titemqty,taphone;

    public MyOrderList(Activity context, String[] aname, String[] itemname, String[] itemprice, String[] qty, String[] aphone) {
        super(context, R.layout.myorderrow, aname);
        this.context = context;
        this.aname = aname;
        this.itemname = itemname;
        this.itemprice = itemprice;
        this.aphone = aphone;
        this.qty=qty;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.myorderrow,null,true);


       taname=(TextView) view.findViewById(R.id.auntyname);
        titemqty=(TextView) view.findViewById(R.id.itemqty);
        titemprice=(TextView) view.findViewById(R.id.totalprice);
        titemname=(TextView) view.findViewById(R.id.itemname);
        taphone=(TextView) view.findViewById(R.id.auntyphone);


        taname.setText(aname[position]);
        titemqty.setText(qty[position]);
        titemprice.setText(itemprice[position]);
        titemname.setText(itemname[position]);
        taphone.setText(aphone[position]);
        return view;
    }
}
