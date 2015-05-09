package com.example.ricardogarcia.politojobs;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseUser;


public class ViewJob extends ActionBarActivity {

    private static final String POSITION = "com.example.ricardogarcia.politojobs.POSITION";
    private static final String COMPANY = "com.example.ricardogarcia.politojobs.COMPANY";
    private static final String INDUSTRY = "com.example.ricardogarcia.politojobs.INDUSTRY";
    private static final String DATE = "com.example.ricardogarcia.politojobs.DATE";
    private static final String DESCRIPTION = "com.example.ricardogarcia.politojobs.DESCRIPTION";
    private static final String SALARY = "com.example.ricardogarcia.politojobs.SALARY";
    private static final String LOCATION = "com.example.ricardogarcia.politojobs.LOCATION";
    private static final String TYPE = "com.example.ricardogarcia.politojobs.TYPE";
    private static final String DURATION = "com.example.ricardogarcia.politojobs.DURATION";

    private Job job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_job);

        Intent intent = getIntent();
        job = (Job) intent.getSerializableExtra(JobSearchResults.JOB);

        TextView position = (TextView) findViewById(R.id.jobPosition);
        position.setText(job.getName());
        TextView company = (TextView) findViewById(R.id.companyName);
        company.setText(job.getCompany().getName());
        TextView industry = (TextView) findViewById(R.id.companyIndustry);
        industry.setText(job.getIndustry());
        TextView date = (TextView) findViewById(R.id.datePosted);
        date.setText(job.getDate());
        TextView description = (TextView) findViewById(R.id.jobDescription);
        description.setText(job.getDescription());
        TextView salary = (TextView) findViewById(R.id.salary);
        salary.setText(job.getSalary());
        TextView location = (TextView) findViewById(R.id.jobLocation);
        location.setText(job.getLocation());
        TextView type = (TextView) findViewById(R.id.jobType);
        type.setText(job.getTypeJob());
        TextView duration = (TextView) findViewById(R.id.jobDuration);
        duration.setText(job.getDuration());

    }

    public void applyNow(View view) {
        String studentId = ParseUser.getCurrentUser().getObjectId();
        ParseObject applyJob = new ParseObject("ApplyJob");
        applyJob.put("StudentId", studentId);
        applyJob.put("JobId", job.getId());
        applyJob.saveInBackground();
    }

    public void saveJob(View view) {
        String studentId = ParseUser.getCurrentUser().getObjectId();
        ParseObject saveJob = new ParseObject("SavedJobOffer");
        saveJob.put("StudentId", studentId);
        saveJob.put("JobId", job.getId());
        saveJob.saveInBackground();
    }

    public void goBack(View view) {
        ViewJob.this.finish();
    }

    public void goHome(View view) {
        ParseUser currentUser = ParseUser.getCurrentUser();
        String typeUser = currentUser.getString("TypeUser");
        if(typeUser.equals("Student")){
            Intent intent = new Intent(this, StudentHome.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, CompanyHome.class);
            startActivity(intent);
        }
    }

    public void goProfile(View view) {
        ParseUser currentUser = ParseUser.getCurrentUser();
        String typeUser = currentUser.getString("TypeUser");
        if(typeUser.equals("Student")){
            Intent intent = new Intent(this, ProfileStudent.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, ProfileCompany.class);
            startActivity(intent);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        TextView position = (TextView) findViewById(R.id.jobPosition);
        TextView company = (TextView) findViewById(R.id.companyName);
        TextView industry = (TextView) findViewById(R.id.companyIndustry);
        TextView date = (TextView) findViewById(R.id.datePosted);
        TextView description = (TextView) findViewById(R.id.jobDescription);
        TextView salary = (TextView) findViewById(R.id.salary);
        TextView location = (TextView) findViewById(R.id.jobLocation);
        TextView type = (TextView) findViewById(R.id.jobType);
        TextView duration = (TextView) findViewById(R.id.jobDuration);

        savedInstanceState.putString(POSITION, position.toString());
        savedInstanceState.putString(COMPANY, company.toString());
        savedInstanceState.putString(INDUSTRY, industry.toString());
        savedInstanceState.putString(DATE, date.toString());
        savedInstanceState.putString(DESCRIPTION, description.toString());
        savedInstanceState.putString(SALARY, salary.toString());
        savedInstanceState.putString(LOCATION, location.toString());
        savedInstanceState.putString(TYPE, type.toString());
        savedInstanceState.putString(DURATION, duration.toString());

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        TextView position = (TextView) findViewById(R.id.jobPosition);
        TextView company = (TextView) findViewById(R.id.companyName);
        TextView industry = (TextView) findViewById(R.id.companyIndustry);
        TextView date = (TextView) findViewById(R.id.datePosted);
        TextView description = (TextView) findViewById(R.id.jobDescription);
        TextView salary = (TextView) findViewById(R.id.salary);
        TextView location = (TextView) findViewById(R.id.jobLocation);
        TextView type = (TextView) findViewById(R.id.jobType);
        TextView duration = (TextView) findViewById(R.id.jobDuration);

        position.setText(savedInstanceState.getString(POSITION));
        company.setText(savedInstanceState.getString(COMPANY));
        industry.setText(savedInstanceState.getString(INDUSTRY));
        date.setText(savedInstanceState.getString(DATE));
        description.setText(savedInstanceState.getString(DESCRIPTION));
        salary.setText(savedInstanceState.getString(SALARY));
        location.setText(savedInstanceState.getString(LOCATION));
        type.setText(savedInstanceState.getString(TYPE));
        duration.setText(savedInstanceState.getString(DURATION));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_job, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
