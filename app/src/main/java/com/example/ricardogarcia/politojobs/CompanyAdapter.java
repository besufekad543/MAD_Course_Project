package com.example.ricardogarcia.politojobs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
public class CompanyAdapter extends BaseAdapter implements View.OnClickListener{

    public static final String COMPANY = "com.example.ricardogarcia.politojobs.COMPANY";


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
        return listcompanies.size();
    }

    @Override
    public Object getItem(int position) {
        return listcompanies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder vholder;
        View v=convertView;

        if(listcompanies.size()>0){
            if(v==null){
                v=inflater.inflate(R.layout.company_row, parent, false);
                vholder= new ViewHolder();
                vholder.textName= (TextView) v.findViewById(R.id.textName);
                vholder.textIndustry= (TextView) v.findViewById(R.id.textIndustry);
                vholder.buttonView=(Button) v.findViewById(R.id.buttonView);
                vholder.buttonSave=(Button) v.findViewById(R.id.buttonSave);
                v.setTag(vholder);
            }
            else{
                vholder= (ViewHolder) v.getTag();
            }
            vholder.textName.setText(listcompanies.get(position).getName());
            vholder.textIndustry.setText(listcompanies.get(position).getIndustry());
            vholder.buttonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(activity,ViewCompany.class);
                    intent.putExtra(COMPANY,listcompanies.get(position));
                    activity.startActivity(intent);
                }
            });

            vholder.buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String studentId = ParseUser.getCurrentUser().getObjectId();
                    ParseObject saveJob = new ParseObject("SavedCompany");
                    saveJob.put("StudentId", studentId.toLowerCase());
                    saveJob.put("CompanyId", listcompanies.get(position).getId().toLowerCase());
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
        public TextView textName;
        public TextView textIndustry;
        public Button buttonView;
        public Button buttonSave;
    }
}
