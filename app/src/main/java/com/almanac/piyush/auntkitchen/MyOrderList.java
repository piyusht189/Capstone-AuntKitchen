package com.almanac.piyush.auntkitchen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

class MyOrderList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] aname;
    private final String[] itemname;
    private final String[] itemprice;
    private final String[] qty;
    private final String[] aphone;

    private TextView taname;
    private TextView titemname;
    private TextView titemprice;
    private TextView titemqty;
    private TextView taphone;

    public MyOrderList(Activity context, String[] aname, String[] itemname, String[] itemprice, String[] qty, String[] aphone) {
        super(context, R.layout.myorderrow, aname);
        this.context = context;
        this.aname = aname;
        this.itemname = itemname;
        this.itemprice = itemprice;
        this.aphone = aphone;
        this.qty=qty;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder") View view = inflater.inflate(R.layout.myorderrow,null,true);


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
