package com.example.ricardogarcia.politojobs;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by aRosales on 11/05/2015.
 */
public class StudentAdapter extends BaseAdapter{

    private ArrayList<Student> listStudents;
    private Activity activity;
    public static final String SEARCH_TYPE = "com.example.ricardogarcia.politojobs.SEARCH_TYPE";
    public static final String STUDENT = "com.example.ricardogarcia.politojobs.STUDENT";

    public StudentAdapter(Activity activity, ArrayList<Student> listStudents) {
        this.listStudents = listStudents;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listStudents.size();
    }

    @Override
    public Student getItem(int position) {
        return listStudents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_students_item, parent, false);
        }

        final Student item = getItem(position);

        TextView name = (TextView) convertView.findViewById(R.id.studentName);
        TextView industry = (TextView) convertView.findViewById(R.id.studentIndustry);
        TextView description = (TextView) convertView.findViewById(R.id.studentDescription);

        name.setText(item.getName() + " " + item.getSurname());
        industry.setText(item.getIndustry());
        description.setText(item.getDescription());

        Button view = (Button) convertView.findViewById(R.id.viewButton);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ViewStudent.class);
                intent.putExtra(STUDENT,item);
                activity.startActivity(intent);
            }
        });

        Button save = (Button) convertView.findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }
}
