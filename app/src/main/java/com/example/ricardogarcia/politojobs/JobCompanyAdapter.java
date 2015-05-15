package com.example.ricardogarcia.politojobs;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Besu on 5/15/2015.
 */
public class JobCompanyAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Activity activity;
    private List<Job> listApplications;

    public JobCompanyAdapter(Activity activity, List<Job> listApplications) {
        this.activity = activity;
        this.listApplications = listApplications;
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
        return null;
    }
}
