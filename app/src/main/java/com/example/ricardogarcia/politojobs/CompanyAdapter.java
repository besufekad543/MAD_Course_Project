package com.example.ricardogarcia.politojobs;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ricardogarcia on 26/04/15.
 */
public class CompanyAdapter extends BaseAdapter implements View.OnClickListener{

    private LayoutInflater inflater;
    private Activity activity;
    private List<Company> listcompanies;


    public CompanyAdapter(Activity activity,ArrayList list){
        inflater= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity=activity;
        this.listcompanies =list;

    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vholder;
        View v=convertView;

        if(listcompanies.size()>0){
            if(v==null){
                v=inflater.inflate(R.layout.company_row, parent, false);
                vholder= new ViewHolder();
                vholder.textName= (TextView) v.findViewById(R.id.textName);
                vholder.textIndustry= (TextView) v.findViewById(R.id.textIndustry);
                v.setTag(vholder);
            }
            else{
                vholder= (ViewHolder) v.getTag();
            }
            vholder.textName.setText(listcompanies.get(position).getName());
            vholder.textIndustry.setText(listcompanies.get(position).getIndustry());
        }
        else{
            if(v==null){
                v=inflater.inflate(R.layout.no_results_row, parent, false);
                vholder= new ViewHolder();
                vholder.textName= (TextView) v.findViewById(R.id.textName);
                v.setTag(vholder);
            }
            else{
                vholder= (ViewHolder) v.getTag();
            }
            vholder.textName.setText(activity.getText(R.string.no_results_text));
        }

        return v;
    }

    @Override
    public void onClick(View v) {

    }


    public static class ViewHolder {
        public TextView textName;
        public TextView textIndustry;
    }
}
