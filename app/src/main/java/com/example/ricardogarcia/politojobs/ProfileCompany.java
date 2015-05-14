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

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;


public class ProfileCompany extends ActionBarActivity {
    //public static final String APPLICATION_ID = "H9NFC1K9LmahxGcCrMOdT0qMaE0lDGT6BgbrSOAc";
    //public static final String CLIENT_KEY = "4K2VfxRGIyk69KlQJ2B8NMnD71llrlkEPLdTNh9M";

    private EditText NameView;
    private EditText LocationView;
    private EditText AddressView;
    private Spinner IndustryView;
    private EditText DescriptionView;
    private EditText ComapnySizeView;
    private EditText WebsiteView;
    private EditText ClientsView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_company);
            //Parse.enableLocalDatastore(this);
            //Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);
            NameView = (EditText) findViewById(R.id.textName);
            LocationView = (EditText) findViewById(R.id.textLocation);
            AddressView = (EditText) findViewById(R.id.textAddress);

            IndustryView = (Spinner) findViewById(R.id.spinnerIndustry);
            String[] Industry = getResources().getStringArray(R.array.arrayIndustry);
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(ProfileCompany.this, android.R.layout.simple_list_item_1, Industry);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            IndustryView.setAdapter(adapter1);

            DescriptionView  = (EditText) findViewById(R.id.descriptionText);
            ComapnySizeView  = (EditText) findViewById(R.id.textSize);
            WebsiteView  = (EditText) findViewById(R.id.textWeb);
            ClientsView = (EditText) findViewById(R.id.textClients);

            // Set up the Save button click handler
            findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    final String name = NameView.getText().toString();
                    final String adress = AddressView.getText().toString();
                    final String location = LocationView.getText().toString();
                    final String industry = IndustryView.getSelectedItem().toString();


                    final String description = DescriptionView.getText().toString();
                    final Integer comapnysize = Integer.parseInt(ComapnySizeView.getText().toString());
                    final String website = WebsiteView.getText().toString();
                    final String clients = ClientsView.getText().toString();

                    // Validate the sign up data
                    boolean validationError = false;
                    StringBuilder validationErrorMessage = new StringBuilder(getResources().getString(R.string.error_intro));
                    if (isEmpty(NameView)) {
                        validationError = true;
                        validationErrorMessage.append(getResources().getString(R.string.error_invalid_name));
                    }
                    if (isEmpty(LocationView)) {
                        validationError = true;
                        validationErrorMessage.append(getResources().getString(R.string.error_invalid_location));
                    }
                    if (isEmpty(AddressView)) {
                        validationError = true;
                        validationErrorMessage.append(getResources().getString(R.string.error_invalid_address));
                    }
                    if (isEmpty(IndustryView)) {
                        validationError = true;
                        validationErrorMessage.append(getResources().getString(R.string.error_invalid_industry));
                    }
                    if (isEmpty(DescriptionView)) {
                        validationError = true;
                        validationErrorMessage.append(getResources().getString(R.string.error_invalid_Description));
                    }

                    if (isEmpty(ComapnySizeView)) {
                        validationError = true;
                        validationErrorMessage.append(getResources().getString(R.string.error_invalid_Companysize));
                    }

                    if (isEmpty(WebsiteView)) {
                        validationError = true;
                        validationErrorMessage.append(getResources().getString(R.string.error_invalid_Website));
                    }
                    if (isEmpty(ClientsView)) {
                        validationError = true;
                        validationErrorMessage.append(getResources().getString(R.string.error_invalid_Clients));
                    }

                    validationErrorMessage.append(getResources().getString(R.string.error_end));

                    // If there is a validation error, display the error
                    if (validationError) {
                        Toast.makeText(ProfileCompany.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
                                .show();
                        return;
                    }

                    // Set up a progress dialog
                    final ProgressDialog dlg = new ProgressDialog(ProfileCompany.this);
                    dlg.setTitle("Please wait.");
                    dlg.setMessage("Saving Student Profile.  Please wait.");
                    dlg.show();
                    //save the data to parse.com

                    ParseObject student = new ParseObject("Company");

                    //student.put("CompanyId", ParseUser.getCurrentUser());
                    student.put("Name", name);
                    student.put("Location", location);
                    student.put("Address", adress);
                    student.put("Industry", industry);
                    student.put("Description", description);
                    student.put("Size", comapnysize);
                    student.put("Website", website);
                    student.put("Clients", clients);

                    student.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            dlg.dismiss();
                            if (e != null) {

                                Log.e("PARSE.COM", "Building Profile FAILED" + e.getMessage());

                            } else {
                                Log.e("PARSE.COM", "Building Profile SUCCESSFULLY FINISHED");

                            }
                        }
                    });

                }

            });

            findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v){
                    final View temp = v;
                    ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Company");
                    query.whereEqualTo("CompanyId", ParseUser.getCurrentUser());
                    query.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> company, ParseException e) {
                            if (e == null) {
                                for (ParseObject delete : company) {
                                    delete.remove(ParseUser.getCurrentUser().getObjectId());
                                    delete.deleteInBackground();
                                }
                                Toast.makeText(temp.getContext(), "Company Profile Deleted", Toast.LENGTH_LONG).show();
                            } else {
                                Log.e("Error", e.getMessage());
                                Toast.makeText(temp.getContext(), "Error deleting Company Profile", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            });

            findViewById(R.id.editButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v){
                    final View temp = v;
                    ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Company");
                    query.whereEqualTo("CompanyId", ParseUser.getCurrentUser());
                    query.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> company, ParseException e) {
                            if (e == null) {
                                for (ParseObject update : company) {

                                    update.put("Name", NameView.getText().toString());
                                    update.put("Location", AddressView.getText().toString());
                                    update.put("Address", LocationView.getText().toString());
                                    update.put("Industry", IndustryView.getSelectedItem().toString());
                                    update.put("Description", DescriptionView.getText().toString());
                                    update.put("Size", Integer.parseInt(ComapnySizeView.getText().toString()));
                                    update.put("Website", WebsiteView.getText().toString());
                                    update.put("Clients", ClientsView.getText().toString());
                                    update.saveInBackground();
                                }
                                Toast.makeText(temp.getContext(), "Company Profile Deleted", Toast.LENGTH_LONG).show();
                            } else {
                                Log.e("Error", e.getMessage());
                                Toast.makeText(temp.getContext(), "Error deleting Company Profile", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            });


        }
    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }
    private boolean isEmpty(Spinner seltext) {
        if (seltext.getSelectedItem().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_company, menu);
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
