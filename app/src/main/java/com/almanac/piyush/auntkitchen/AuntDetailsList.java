package com.almanac.piyush.auntkitchen;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AuntDetailsList extends ArrayAdapter<String> {

    Activity context;
    String[] aname,itemname,itemprice,itemmenu,aaddress;

    TextView taname,titemname,titemprice,titemmenu,taaddress;

    public AuntDetailsList(Activity context, String[] aname, String[] itemname, String[] itemprice, String[] itemmenu, String[] aaddress) {
        super(context, R.layout.auntdetailslist, aname);
        this.context = context;
        this.aname = aname;
        this.itemmenu = itemmenu;
        this.itemname = itemname;
        this.itemprice = itemprice;
        this.aaddress = aaddress;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.auntdetailslist,null,true);

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
