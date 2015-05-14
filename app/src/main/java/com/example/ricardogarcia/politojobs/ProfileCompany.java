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
    private EditText NameView;
    private Spinner LocationView;
    private EditText AddressView;
    private Spinner IndustryView;
    private EditText DescriptionView;
    private EditText CompanySizeView;
    private EditText WebsiteView;
    private EditText ClientsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_company);

        NameView = (EditText) findViewById(R.id.textName);
        AddressView = (EditText) findViewById(R.id.textAddress);
        LocationView = (Spinner) findViewById(R.id.spinnerLocation);
        ArrayAdapter<String> adapterLocation = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.arrayLocation));
        adapterLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        LocationView.setAdapter(adapterLocation);

        IndustryView = (Spinner) findViewById(R.id.spinnerIndustry);
        ArrayAdapter<String> adapterIndustry = new ArrayAdapter<String>(ProfileCompany.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.arrayIndustry));
        adapterIndustry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        IndustryView.setAdapter(adapterIndustry);

        DescriptionView = (EditText) findViewById(R.id.descriptionText);
        CompanySizeView = (EditText) findViewById(R.id.textSize);
        WebsiteView = (EditText) findViewById(R.id.textWeb);
        ClientsView = (EditText) findViewById(R.id.textClients);

        //TODO
        //Initialize fields with the values found on the table for the current user and display the values to the user
        //without the possibility of changing them. They can be changed only when user clicks the edit button

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
        if (typeUser.equals("Student")) {
            Intent intent = new Intent(this, StudentHome.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, CompanyHome.class);
            startActivity(intent);
        }
    }

    public void goProfile(View view) {
        ParseUser currentUser = ParseUser.getCurrentUser();
        String typeUser = currentUser.getString("TypeUser");
        if (typeUser.equals("Student")) {
            Intent intent = new Intent(this, ProfileStudent.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, ProfileCompany.class);
            startActivity(intent);
        }
    }

    public void saveProfile(View view) {

        final String name = NameView.getText().toString();
        final String address = AddressView.getText().toString();
        final String location = LocationView.getSelectedItem().toString();
        final String industry = IndustryView.getSelectedItem().toString();

        final String description = DescriptionView.getText().toString();
        final Integer companysize = Integer.parseInt(CompanySizeView.getText().toString());
        final String website = WebsiteView.getText().toString();
        final String clients = ClientsView.getText().toString();

        // Set up a progress dialog
        final ProgressDialog dlg = new ProgressDialog(ProfileCompany.this);
        dlg.setTitle("Please wait.");
        dlg.setMessage("Saving Student Profile.  Please wait.");
        dlg.show();
        //save the data to parse.com

        ParseObject student = new ParseObject("Company");

        //TODO
        //This function needs to retrieve the existing object of the table for the specific company and update the values.
        //As it is implemented right now, it is adding a new row which is not the idea

        //student.put("CompanyId", ParseUser.getCurrentUser());
        student.put("Name", name);
        student.put("Location", location);
        student.put("Address", address);
        student.put("Industry", industry);
        student.put("Description", description);
        student.put("Size", companysize);
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


    public void editProfile(View view) {

        //TODO
        //The edit function only set all fields (except name, location and address) as editable

        final View temp = view;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Company");
        query.whereEqualTo("CompanyId", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> company, ParseException e) {
                if (e == null) {
                    for (ParseObject update : company) {

                        update.put("Name", NameView.getText().toString());
                        update.put("Location", AddressView.getText().toString());
                        update.put("Address", LocationView.getSelectedItem().toString());
                        update.put("Industry", IndustryView.getSelectedItem().toString());
                        update.put("Description", DescriptionView.getText().toString());
                        update.put("Size", Integer.parseInt(CompanySizeView.getText().toString()));
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

    public void deleteProfile(View view) {

        //TODO
        //Delete requires not only to delete the row on one table but all the associated data
        //in the other tables

        final View temp = view;
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
}
