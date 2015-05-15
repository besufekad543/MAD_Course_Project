package com.example.ricardogarcia.politojobs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class ListJobs extends ActionBarActivity {
public static final String APPLICATION_ID = "H9NFC1K9LmahxGcCrMOdT0qMaE0lDGT6BgbrSOAc";
public static final String CLIENT_KEY = "4K2VfxRGIyk69KlQJ2B8NMnD71llrlkEPLdTNh9M";

@Override
protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_list_jobs);

    /*Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);

    findViewById(R.id.publish_button).setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ListJobs.this, PublishOffer.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    });

    ParseQuery query = new ParseQuery("JobOffer");
    query.whereEqualTo("objectId", ParseUser.getCurrentUser().getObjectId()); //objectId
    query.findInBackground(new FindCallback<ParseObject>() {

        @Override
        public void done(List<ParseObject> arg0, ParseException arg1) {
            if (arg1 == null) {
                for (final ParseObject nameObj : arg0) {

                    ListView listView = (ListView) findViewById(R.id.listResult);
                    ArrayList<String> jobs = new ArrayList<String>();

                    String position = nameObj.getString("Position").toString();
                    String location = nameObj.getString("Location").toString();
                    jobs.add(position);
                    jobs.add(location);
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(ListJobs.this, android.R.layout.simple_list_item_1, jobs);
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    listView.setAdapter(adapter1);

                    findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            nameObj.remove(ParseUser.getCurrentUser().getObjectId());

                            nameObj.deleteInBackground();
                        }
                    });
                    findViewById(R.id.editButton).setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(ListJobs.this, UpdateOffer.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });


                }
            }
        }
                });
    */

        try {
            ParseQuery<ParseObject> queryStudent = ParseQuery.getQuery("Company");
            queryStudent.include("CompanyId");
            queryStudent.whereEqualTo("CompanyId", ParseUser.getCurrentUser());

            ParseObject company = queryStudent.getFirst();


            new RetrieveFromDatabase().execute(company);

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_jobs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
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

    private class RetrieveFromDatabase extends AsyncTask<ParseObject,Void,ArrayList<Job>> {

        private ProgressDialog progressDialog= new ProgressDialog(ListJobs.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Loading job offers");
            if(!progressDialog.isShowing()){
                progressDialog.show();
            }
        }

        @Override
        protected ArrayList<Job> doInBackground(ParseObject... params) {
            ArrayList<Job> result_jobs=new ArrayList<Job>();
            ParseQuery<ParseObject> query = ParseQuery.getQuery("JobOffer");
            query.include("CompanyId");
            query.whereEqualTo("CompanyId", params[0]);
            try {
                List<ParseObject> results=query.find();
                for(ParseObject p:results){
                    Job job = new Job();
                    job.setId(p.getObjectId());
                    if(p.getString("Position")!=null)
                        job.setPosition(p.getString("Position"));
                    if(p.getString("Industry")!=null)
                        job.setIndustry(p.getString("Industry"));
                    if(p.getString("Description")!=null)
                        job.setDescription(p.getString("Description"));
                    if(p.getString("Location")!=null)
                        job.setLocation(p.getString("Location"));
                    if (p.getNumber("Salary")!=null)
                        job.setSalary(p.getNumber("Salary").toString());
                    if(p.getString("JobType")!=null)
                        job.setTypeJob(p.getString("JobType"));
                    if(p.getString("ContractType")!=null)
                        job.setContractType(p.getString("ContractType"));
                    if(p.getNumber("Duration")!=null)
                        job.setDuration(p.getNumber("Duration").intValue());
                    if(p.getDate("createdAt")!=null)
                        job.setDate(p.getDate("createdAt").toString());
                    ParseObject company = p.getParseObject("CompanyId");
                    if(company!=null){

                    }


                    result_jobs.add(job);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return result_jobs;
        }

        @Override
        protected void onPostExecute(ArrayList<Job> jobs) {
            super.onPostExecute(jobs);
            if(progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            JobCompanyAdapter jAdapter= new JobCompanyAdapter(ListJobs.this,jobs);

            ListView list_jobs= (ListView) findViewById(R.id.listResult);
            list_jobs.setAdapter(jAdapter);
            list_jobs.setEmptyView(findViewById(R.id.emptyView));

        }
    }
}
