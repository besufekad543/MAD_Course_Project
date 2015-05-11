package com.example.ricardogarcia.politojobs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ricardogarcia on 26/04/15.
 */
public class JobAdapter extends BaseAdapter implements View.OnClickListener{

    public static final String JOB = "com.example.ricardogarcia.politojobs.JOB";

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
        return listjobs.size();
    }

    @Override
    public Object getItem(int position) {
        return listjobs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

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
                vholder.buttonView=(Button) v.findViewById(R.id.buttonView);
                vholder.buttonSave=(Button) v.findViewById(R.id.buttonSave);
                v.setTag(vholder);
            }
            else{
                vholder= (ViewHolder) v.getTag();
            }
            vholder.textJob.setText(listjobs.get(position).getPosition());
            vholder.textCompany.setText(listjobs.get(position).getCompany().getName());
            vholder.textLocation.setText(listjobs.get(position).getLocation());
            vholder.textDate.setText(listjobs.get(position).getDate());
            vholder.buttonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(activity,ViewJob.class);
                    intent.putExtra(JOB,listjobs.get(position));
                    activity.startActivity(intent);
                }
            });

            vholder.buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String studentId = ParseUser.getCurrentUser().getObjectId();
                    ParseObject saveJob = new ParseObject("SavedJobOffer");
                    saveJob.put("StudentId", studentId);
                    saveJob.put("JobId", listjobs.get(position).getId());
                    saveJob.saveInBackground();
                }
            });

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
        public Button buttonView;
        public Button buttonSave;
    }
}
