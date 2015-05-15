package com.example.ricardogarcia.politojobs;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class UpdateOffer extends ActionBarActivity {
    public static final String APPLICATION_ID = "H9NFC1K9LmahxGcCrMOdT0qMaE0lDGT6BgbrSOAc";
    public static final String CLIENT_KEY = "4K2VfxRGIyk69KlQJ2B8NMnD71llrlkEPLdTNh9M";
    private EditText JobView;
    private Spinner LocationView;
    private EditText DescriptionView;
    private EditText SalaryView;
    private Spinner TypeJobView;
    private EditText DurationView;
    private Spinner IndustryView;
    private Spinner TypeOfContractView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_offer);
        Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);

        JobView = (EditText) findViewById(R.id.textJob);
        LocationView = (Spinner) findViewById(R.id.spinnerLocation);
        DescriptionView = (EditText) findViewById(R.id.textDescription);
        SalaryView = (EditText) findViewById(R.id.textSalary);
        TypeJobView = (Spinner) findViewById(R.id.spinnerJobType);
        DurationView = (EditText) findViewById(R.id.textDuration);
        IndustryView = (Spinner) findViewById(R.id.spinnerJobIndustry);
        TypeOfContractView = (Spinner) findViewById(R.id.spinnerTypeOfContract);

        ParseQuery jobOffer = new ParseQuery("JobOffer");
        jobOffer.whereEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());

        jobOffer.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> arg0, com.parse.ParseException arg1) {
                if (arg1 == null) {
                    for (final ParseObject nameObj : arg0) {


                        JobView.setText(nameObj.getString("Position").toString());
                        ArrayList<String> loc = new ArrayList<String>();
                        String location = nameObj.getString("Location");
                        loc.add(location);
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(UpdateOffer.this, android.R.layout.simple_list_item_1, loc);
                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        LocationView.setAdapter(adapter1);
                        DescriptionView.setText(nameObj.getString("Description"));
                        SalaryView.setText(nameObj.getString("Salary"));

                        ArrayList<String> job = new ArrayList<String>();
                        String jobtype = nameObj.getString("JobType");
                        job.add(jobtype);
                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(UpdateOffer.this, android.R.layout.simple_list_item_1, job);
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        TypeJobView.setAdapter(adapter2);
                        DurationView.setText(nameObj.getString("Duration"));

                        ArrayList<String> indu = new ArrayList<String>();
                        String industry = nameObj.getString("Industry");
                        indu.add(industry);
                        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(UpdateOffer.this, android.R.layout.simple_list_item_1, indu);
                        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        IndustryView.setAdapter(adapter3);


                        ArrayList<String> contract = new ArrayList<String>();
                        String typeofcontract = nameObj.getString("TypeOfContract");
                        contract.add(typeofcontract);
                        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(UpdateOffer.this, android.R.layout.simple_list_item_1, contract);
                        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        TypeOfContractView.setAdapter(adapter4);

                        findViewById(R.id.updateButton).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                nameObj.put("Position", JobView.getText().toString());
                                nameObj.put("Location", LocationView.getSelectedItem().toString());
                                nameObj.put("Description", DescriptionView.getText().toString());
                                nameObj.put("Salary", SalaryView.getText().toString());
                                nameObj.put("JobType", TypeJobView.getSelectedItem().toString());
                                nameObj.put("Duration", Integer.parseInt(DurationView.getText().toString()));
                                nameObj.put("Industry", IndustryView.getSelectedItem().toString());

                                nameObj.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(com.parse.ParseException e) {
                                        if (e != null) {
                                            Log.e("PARSE.COM", "FAILED" + e.getMessage());

                                        } else {
                                            Log.e("PARSE.COM", "SUCCESS");
                                            Intent intent = new Intent(UpdateOffer.this, ProfileCompany.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    }
                                });
                            }
                        });
                    }
                }
            }
        });
        findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                JobView.setText("");
                LocationView.setSelection(0);
                DescriptionView.setText("");
                SalaryView.setText("");
                TypeJobView.setSelection(0);
                DurationView.setText("");
                IndustryView.setSelection(0);
                TypeOfContractView.setSelection(0);
            }
        });

        /*ParseQuery<ParseObject> jobOffer = ParseQuery.getQuery("JobOffer");
        jobOffer.getInBackground("YsHLfJ7tUB", new GetCallback<ParseObject>() {

            @Override
            public void done(ParseObject parseObject, com.parse.ParseException e) {
                if (e == null) {
                    String position = parseObject.getString("Position");
                    String location = parseObject.getString("Location");
                    String description = parseObject.getString("Description");
                    String salary = parseObject.getString("Salary");
                    String typejob = parseObject.getString("TypeJob");
                    String duration = parseObject.getString("Duration");
                    String industry = parseObject.getString("Industry");

                    EditText JobView = (EditText) findViewById(R.id.textJob);
                    JobView.setText(position);
                    EditText LocationView = (EditText) findViewById(R.id.textLocation);
                    LocationView.setText(location);
                    EditText DescriptionView = (EditText) findViewById(R.id.textDescription);
                    DescriptionView.setText(description);
                    EditText SalaryView = (EditText) findViewById(R.id.textSalary);
                    SalaryView.setText(salary);

                    Spinner TypeJobView = (Spinner) findViewById(R.id.spinnerJobType);

                    String[] JobType = getResources().getStringArray(R.array.arrayJobType);
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(UpdateOffer.this, android.R.layout.simple_list_item_1, JobType);
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    TypeJobView.setAdapter(adapter1);


                    EditText DurationView = (EditText) findViewById(R.id.textDuration);
                    DurationView.setText(duration);
                    Spinner IndustryView = (Spinner) findViewById(R.id.spinnerJobIndustry);
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, Integer.parseInt(typejob));
                    IndustryView.setAdapter(adapter2);

                    ParseObject jobOffer2 = new ParseObject("JobOffer");
                    jobOffer2.put("Position", position);
                    jobOffer2.put("Location", location);
                    jobOffer2.put("Description", description);
                    jobOffer2.put("Salary", salary);
                    jobOffer2.put("JobType", typejob);
                    jobOffer2.put("Duration", duration);
                    jobOffer2.put("Industry", industry);

                    jobOffer2.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(com.parse.ParseException e) {
                            if (e != null) {
                                Log.e("PARSE.COM", "FAILED" + e.getMessage());
                            } else {
                                Log.e("PARSE.COM", "SUCCESS");
                            }
                        }
                    });
                } else
                    e.printStackTrace();
            }

        });*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update_offer, menu);
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
}
