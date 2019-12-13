package com.example.pixquest;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomShopView extends ArrayAdapter<String> {

    private String[] skinname;
    private String[] skinprice;
    private Integer[] imgid;
    private Activity context;
    private String username;

    public CustomShopView(Activity context, String[] skinname,String[] skinprice,Integer[] imgid) {
        super(context, R.layout.shoplistview,skinname);

        this.context=context;
        this.skinname=skinname;
        this.skinprice=skinprice;
        this.imgid=imgid;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View r = convertView;
        ViewHolder viewHolder = null;
        if(r==null){
            LayoutInflater layoutInflater= context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.shoplistview,null,true);
            viewHolder=new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else{
            viewHolder= (ViewHolder) r.getTag();
        }
        viewHolder.imgview.setImageResource(imgid[position]);
        viewHolder.txtview1.setText(skinname[position]);
        viewHolder.txtview2.setText(skinprice[position]);
        return r;
    }

    class ViewHolder{
        TextView txtview1;
        TextView txtview2;
        ImageView imgview;
        ViewHolder(View v){
            txtview1=v.findViewById(R.id.skinname);
            txtview2=v.findViewById(R.id.skinprice);
            imgview=v.findViewById(R.id.skinimage);
        }
    }
}
