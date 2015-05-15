package com.example.ricardogarcia.politojobs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class PublishOffer extends ActionBarActivity {
    //public static final String APPLICATION_ID = "H9NFC1K9LmahxGcCrMOdT0qMaE0lDGT6BgbrSOAc";
    //public static final String CLIENT_KEY = "4K2VfxRGIyk69KlQJ2B8NMnD71llrlkEPLdTNh9M";
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
        setContentView(R.layout.activity_publish_offer);


        JobView = (EditText) findViewById(R.id.textJob);

        LocationView = (Spinner) findViewById(R.id.spinnerLocation);
        String[] Location = getResources().getStringArray(R.array.arrayLocation);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PublishOffer.this, android.R.layout.simple_list_item_1, Location);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        LocationView.setAdapter(adapter);

        DescriptionView = (EditText) findViewById(R.id.textDescription);
        SalaryView = (EditText) findViewById(R.id.textSalary);

        TypeJobView = (Spinner) findViewById(R.id.spinnerJobType);
        String[] JobType = getResources().getStringArray(R.array.arrayJobType);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(PublishOffer.this, android.R.layout.simple_list_item_1, JobType);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TypeJobView.setAdapter(adapter1);

        DurationView = (EditText) findViewById(R.id.textDuration);

        IndustryView = (Spinner) findViewById(R.id.spinnerJobIndustry);
        String[] Industry = getResources().getStringArray(R.array.arrayIndustry);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(PublishOffer.this, android.R.layout.simple_list_item_1, Industry);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        IndustryView.setAdapter(adapter2);


        TypeOfContractView = (Spinner) findViewById(R.id.spinnerTypeOfContract);
        String[] TypeOfContract = getResources().getStringArray(R.array.arrayContractType);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(PublishOffer.this, android.R.layout.simple_list_item_1, TypeOfContract);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TypeOfContractView.setAdapter(adapter3);


    }
    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_publish_offer, menu);
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


    public void publishOffer(View view){

        final String position = JobView.getText().toString();
        final String location = LocationView.getSelectedItem().toString();
        final String description = DescriptionView.getText().toString();
        String salary1 = SalaryView.getText().toString();
        final Integer salary = Integer.parseInt(salary1);
        final String typejob = TypeJobView.getSelectedItem().toString();
        final Integer duration = Integer.parseInt(DurationView.getText().toString());
        final String industry = IndustryView.getSelectedItem().toString();
        final String typeofcontract = TypeOfContractView.getSelectedItem().toString();

        // Validate the sign up data
        boolean validationError = false;
        StringBuilder validationErrorMessage = new StringBuilder(getResources().getString(R.string.error_intro));
        if (isEmpty(JobView)) {
            validationError = true;
            validationErrorMessage.append(getResources().getString(R.string.error_invalid_Jobposition));
        }
        if (LocationView.getSelectedItem().toString().equals("-")) {
            validationError = true;
            validationErrorMessage.append(getResources().getString(R.string.error_invalid_location));
        }
        if (isEmpty(DescriptionView)) {
            validationError = true;
            validationErrorMessage.append(getResources().getString(R.string.error_invalid_Description));
        }
        if (isEmpty(SalaryView)) {
            validationError = true;
            validationErrorMessage.append(getResources().getString(R.string.error_invalid_Salary));
        }
        if (TypeJobView.getSelectedItem().toString().equals("-")) {
            validationError = true;
            validationErrorMessage.append(getResources().getString(R.string.error_invalid_typeofjob));
        }
        if (isEmpty(DurationView)) {
            validationError = true;
            validationErrorMessage.append(getResources().getString(R.string.error_invalid_duration));
        }

        if (IndustryView.getSelectedItem().toString().equals("-")) {
            validationError = true;
            validationErrorMessage.append(getResources().getString(R.string.error_invalid_duration));
        }
        validationErrorMessage.append(getResources().getString(R.string.error_end));

        // If there is a validation error, display the error
        if (validationError) {
            Toast.makeText(PublishOffer.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // Set up a progress dialog
        final ProgressDialog dlg = new ProgressDialog(PublishOffer.this);
        dlg.setTitle("Please wait.");
        dlg.setMessage("Publishing Job.  Please wait.");
        dlg.show();

        //save the data to parse.com

        ParseObject jobOffer = new ParseObject("JobOffer");

        jobOffer.put("Position", position);
        jobOffer.put("Location", location);
        jobOffer.put("Description", description);
        jobOffer.put("Salary", salary);
        jobOffer.put("JobType", typejob);
        jobOffer.put("CompanyId", ParseUser.getCurrentUser());
        jobOffer.put("Duration", duration);
        jobOffer.put("Industry", industry);
        jobOffer.put("ContractType", typeofcontract);

        jobOffer.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e) {
                dlg.dismiss();

                if (e != null) {

                    Log.e("PARSE.COM", "FAILED" + e.getMessage());

                } else {
                    Log.e("PARSE.COM", "SUCCESS");
                }
            }
        });

    }

    public void cancel(View view){

        JobView.setText("");
        LocationView.setSelection(0);
        DescriptionView.setText("");
        SalaryView.setText("");
        TypeJobView.setSelection(0);
        DurationView.setText("");
        IndustryView.setSelection(0);
        TypeOfContractView.setSelection(0);
        finish();

    }
}
