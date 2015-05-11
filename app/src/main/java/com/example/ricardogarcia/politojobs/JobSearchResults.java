package com.example.ricardogarcia.politojobs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class JobSearchResults extends ActionBarActivity {

    public static final String JOB = "com.example.ricardogarcia.politojobs.JOB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_search_results);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        new RetrieveFromDatabase().execute((HashMap<String, String>) b.getSerializable(JobSearch.INFO_HASH));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_job_search_results, menu);
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


    private class RetrieveFromDatabase extends AsyncTask<HashMap<String,String>,Void,ArrayList<Job>> {

        private ProgressDialog progressDialog= new ProgressDialog(JobSearchResults.this);



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Loading jobs");
            if(!progressDialog.isShowing()){
                progressDialog.show();
            }
        }

        @Override
        protected ArrayList<Job> doInBackground(HashMap<String, String>... params) {

            ArrayList<Job> jobs= new ArrayList<Job>();
            HashMap<String,String> search_data=params[0];

            if(search_data.get(JobSearch.INFO_SEARCHTYPE).compareTo("Search")==0){
                //Search
                ParseQuery<ParseObject> searchJobQuery = ParseQuery.getQuery("JobOffer");

                //Location Filter
                if(search_data.containsKey(JobSearch.INFO_LOCATION)){
                    searchJobQuery.whereEqualTo("Location",search_data.get(JobSearch.INFO_LOCATION));
                }

                //Industry Filter
                if(search_data.containsKey(JobSearch.INFO_INDUSTRY)){
                    searchJobQuery.whereEqualTo("Industry",search_data.get(JobSearch.INFO_INDUSTRY));
                }

                //Type of Job Filter
                if(search_data.containsKey(JobSearch.INFO_JOBTYPE)){
                    searchJobQuery.whereEqualTo("JobType",search_data.get(JobSearch.INFO_JOBTYPE));
                }

                //Type of contract filter
                if(search_data.containsKey(JobSearch.INFO_CONTRACT_TYPE)){
                    searchJobQuery.whereEqualTo("ContractType",search_data.get(JobSearch.INFO_CONTRACT_TYPE));
                }


                //Duration filter
                if(search_data.containsKey(JobSearch.INFO_LOCATION)){
                    searchJobQuery.whereEqualTo("Location",search_data.get(JobSearch.INFO_LOCATION));
                }

                //Salary filter
                if(search_data.containsKey(JobSearch.INFO_SALARY)){

                }

                //Keywords Filter
                if(search_data.containsKey(JobSearch.INFO_KEYWORDS)){
                    searchJobQuery.whereContains("Position",search_data.get(JobSearch.INFO_KEYWORDS));
                }
                //Company Filter
                if(search_data.containsKey(JobSearch.INFO_COMPANY)){
                    ParseQuery<ParseObject> companyQuery = ParseQuery.getQuery("Company");

                }



            }
            else{
                //Saved Jobs

                //Retrieve rows from SavedJobOffer table with the StudentId equals to the id of the current user
                ParseQuery<ParseObject> savedOfferQuery = ParseQuery.getQuery("SavedJobOffer");
                savedOfferQuery.whereEqualTo("StudentId", ParseUser.getCurrentUser().getObjectId());

                try {
                    List<ParseObject> results=savedOfferQuery.find();
                    for(ParseObject p:results){
                        //For each offer in the SavedJobOffer, retrieve the data contained in the JobOffer table with all the information
                        // of the specific job offer

                        ParseQuery<ParseObject> jobOfferQuery = ParseQuery.getQuery("JobOffer");
                        jobOfferQuery.whereEqualTo("objectId",p.get("OfferId"));
                        ParseObject job_result=jobOfferQuery.getFirst();
                        Job job= new Job();
                        job.setId(job_result.getObjectId());
                        job.setPosition(job_result.get("Position").toString());
                        job.setIndustry(job_result.get("Industry").toString());
                        job.setDescription(job_result.get("Description").toString());
                        job.setLocation(job_result.get("Location").toString());
                        job.setSalary(job_result.get("Salary").toString());
                        job.setTypeJob(job_result.get("JobType").toString());
                        job.setDuration(job_result.get("Duration").toString());
                        job.setDate(job_result.get("Date").toString());

                        //Each job offer is related with a company through the companyId. Retrieve the company related
                        //by searching it on the Company table
                        ParseQuery<ParseObject> companyQuery = ParseQuery.getQuery("Company");
                        companyQuery.whereEqualTo("objectId",job_result.get("CompanyId"));
                        ParseObject company_result=companyQuery.getFirst();
                        Company company=new Company();
                        company.setId(company_result.get("CompanyId").toString());
                        company.setName(company_result.get("Name").toString());
                        company.setLocation(company_result.get("Location").toString());
                        company.setAddress(company_result.get("Address").toString());
                        company.setIndustry(company_result.get("Industry").toString());
                        company.setDescription(company_result.get("Description").toString());
                        company.setCompany_size(company_result.get("Size").toString());
                        company.setWebsite(company_result.get("Website").toString());
                        company.setClients(company_result.get("Clients").toString());

                        job.setCompany(company);
                        job.setContractType(company_result.get("ContractType").toString());

                        jobs.add(job);

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            return jobs;
        }

        @Override
        protected void onPostExecute(ArrayList<Job> jobs) {
            super.onPostExecute(jobs);
            if(progressDialog.isShowing()) {
                progressDialog.hide();
            }

            JobAdapter jAdapter= new JobAdapter(JobSearchResults.this,jobs);

            ListView list_messages= (ListView) findViewById(R.id.listResults);
            list_messages.setAdapter(jAdapter);
            list_messages.setEmptyView(findViewById(R.id.textNoResults));

        }
    }
}
