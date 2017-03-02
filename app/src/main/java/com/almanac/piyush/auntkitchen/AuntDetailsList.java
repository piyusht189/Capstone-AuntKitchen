package com.almanac.piyush.auntkitchen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

class AuntDetailsList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] aname;
    private final String[] itemname;
    private final String[] itemprice;
    private final String[] itemmenu;
    private final String[] aaddress;

    private TextView taname;
    private TextView titemname;
    private TextView titemprice;
    private TextView titemmenu;
    private TextView taaddress;

    public AuntDetailsList(Activity context, String[] aname, String[] itemname, String[] itemprice, String[] itemmenu, String[] aaddress) {
        super(context, R.layout.auntdetailslist, aname);
        this.context = context;
        this.aname = aname;
        this.itemmenu = itemmenu;
        this.itemname = itemname;
        this.itemprice = itemprice;
        this.aaddress = aaddress;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
       @SuppressLint({"InflateParams", "ViewHolder"}) View view = inflater.inflate(R.layout.auntdetailslist,null,true);

       taname=(TextView) view.findViewById(R.id.auntyname);
        taaddress=(TextView) view.findViewById(R.id.auntaddress);
        titemprice=(TextView) view.findViewById(R.id.price);
        titemname=(TextView) view.findViewById(R.id.itemname);
        titemmenu=(TextView) view.findViewById(R.id.itemmenu);
        taaddress.setText(aaddress[position]);
        taname.setText(aname[position]);
        titemmenu.setText(itemmenu[position]);
        titemprice.setText(itemprice[position]);
        titemname.setText(itemname[position]);

        return view;
    }
}
