package com.example.ricardogarcia.politojobs;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
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
public class JobAdapter extends BaseAdapter implements View.OnClickListener{

    private LayoutInflater inflater;
    private Activity activity;
    private List<Job> listjobs;


    public JobAdapter(Activity activity,ArrayList list){
        inflater= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity=activity;
        this.listjobs =list;

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

        if(listjobs.size()>0){
            if(v==null){
                v=inflater.inflate(R.layout.job_row, parent, false);
                vholder= new ViewHolder();
                vholder.textJob= (TextView) v.findViewById(R.id.textJobPosition);
                vholder.textCompany= (TextView) v.findViewById(R.id.textCompany);
                vholder.textLocation= (TextView) v.findViewById(R.id.textLocation);
                vholder.textDate= (TextView) v.findViewById(R.id.textDatePosted);
                v.setTag(vholder);
            }
            else{
                vholder= (ViewHolder) v.getTag();
            }
            vholder.textJob.setText(listjobs.get(position).getName());
            vholder.textCompany.setText(listjobs.get(position).getCompany().getName());
            vholder.textLocation.setText(listjobs.get(position).getLocation());
            vholder.textDate.setText(listjobs.get(position).getDate());
        }

        return v;
    }

    @Override
    public void onClick(View v) {

    }


    public static class ViewHolder {
        public TextView textJob;
        public TextView textCompany;
        public TextView textLocation;
        public TextView textDate;
    }
}
