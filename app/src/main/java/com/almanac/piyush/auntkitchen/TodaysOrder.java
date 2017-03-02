package com.almanac.piyush.auntkitchen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

class TodaysOrder extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] cname;
    private final String[] itemname;
    private final String[] itemprice;
    private final String[] qty;
    private final String[] cphone;

    private TextView tcname;
    private TextView titemname;
    private TextView titemprice;
    private TextView titemqty;
    private TextView tcphone;

    public TodaysOrder(Activity context, String[] cname, String[] itemname, String[] itemprice, String[] qty, String[] cphone) {
        super(context, R.layout.todaysorder, cname);
        this.context = context;
        this.cname = cname;
        this.itemname = itemname;
        this.itemprice = itemprice;
        this.cphone = cphone;
        this.qty=qty;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint({"InflateParams", "ViewHolder"}) View view = inflater.inflate(R.layout.todaysorder,null,true);


       tcname=(TextView) view.findViewById(R.id.customername);
        titemqty=(TextView) view.findViewById(R.id.itemqty);
        titemprice=(TextView) view.findViewById(R.id.totalprice);
        titemname=(TextView) view.findViewById(R.id.itemname);
        tcphone=(TextView) view.findViewById(R.id.customerphone);


        tcname.setText(cname[position]);
        titemqty.setText(qty[position]);
        titemprice.setText(itemprice[position]);
        titemname.setText(itemname[position]);
        tcphone.setText(cphone[position]);
        return view;
    }
}
