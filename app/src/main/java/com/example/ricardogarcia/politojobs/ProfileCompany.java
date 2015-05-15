package com.example.ricardogarcia.politojobs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;


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

        ParseQuery query = new ParseQuery("Company");
        query.whereEqualTo("CompanyId", ParseUser.getCurrentUser());

        try {
            ParseObject company = query.getFirst();
            NameView.setText(company.getString("Name"));
            NameView.setFocusable(false);
            NameView.setFocusableInTouchMode(false);
            NameView.setClickable(false);

            AddressView.setText(company.getString("Address"));
            AddressView.setFocusable(false);
            AddressView.setFocusableInTouchMode(false);
            AddressView.setClickable(false);

            LocationView.setSelection(adapterLocation.getPosition(company.getString("Location")));
            LocationView.setFocusable(false);
            LocationView.setFocusableInTouchMode(false);
            LocationView.setClickable(false);
            LocationView.setEnabled(false);

            if(company.get("Description")!=null) {
                DescriptionView.setText(company.getString("Description"));
            }
            DescriptionView.setFocusable(false);
            DescriptionView.setFocusableInTouchMode(false);
            DescriptionView.setClickable(false);

            if (company.get("Industry")==null) {
                IndustryView.setSelection(0);
            } else {
                IndustryView.setSelection(adapterIndustry.getPosition(company.getString("Industry")));
            }
            IndustryView.setFocusable(false);
            IndustryView.setFocusableInTouchMode(false);
            IndustryView.setClickable(false);
            IndustryView.setEnabled(false);

            if(company.get("Size")!=null){
                int csize=company.getInt("Size");
                CompanySizeView.setText(String.valueOf(csize));
            }
            CompanySizeView.setFocusable(false);
            CompanySizeView.setFocusableInTouchMode(false);
            CompanySizeView.setClickable(false);

            if(company.getString("Website")!=null) {
                WebsiteView.setText(company.getString("Website"));
            }
            WebsiteView.setFocusable(false);
            WebsiteView.setFocusableInTouchMode(false);
            WebsiteView.setClickable(false);

            if(company.getString("Clients")!=null) {
                ClientsView.setText(company.getString("Clients"));
            }
            ClientsView.setFocusable(false);
            ClientsView.setFocusableInTouchMode(false);
            ClientsView.setClickable(false);

        } catch (ParseException e) {
            e.printStackTrace();
        }
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

        // Set up a progress dialog
        final ProgressDialog dlg = new ProgressDialog(ProfileCompany.this);
        dlg.setTitle(R.string.saveprofiletitle);
        dlg.setMessage(getString(R.string.saveprofilemessage));
        dlg.show();
        //save the data to parse.com

        try {
            ParseQuery query = new ParseQuery("Company");
            query.whereEqualTo("CompanyId", ParseUser.getCurrentUser());
            ParseObject company = query.getFirst();

            if(!IndustryView.getSelectedItem().toString().equals("-"))
            company.put("Industry", IndustryView.getSelectedItem().toString());

            if(!DescriptionView.getText().toString().equals(""))
            company.put("Description", DescriptionView.getText().toString());

            if(!CompanySizeView.getText().toString().equals(""))
            company.put("Size", Integer.valueOf(CompanySizeView.getText().toString()));

            if(!WebsiteView.getText().toString().equals(""))
            company.put("Website", WebsiteView.getText().toString());

            if(!ClientsView.getText().toString().equals(""))
            company.put("Clients", ClientsView.getText().toString());

            company.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    Intent intent = new Intent(ProfileCompany.this, ProfileCompany.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });


        } catch (ParseException e) {
            e.printStackTrace();
        }


    }


    public void editProfile(View view) {

        DescriptionView.setFocusable(true);
        DescriptionView.setFocusableInTouchMode(true);
        DescriptionView.setClickable(true);

        LocationView.setFocusable(true);
        LocationView.setFocusableInTouchMode(true);
        LocationView.setClickable(true);
        LocationView.setEnabled(true);

        IndustryView.setFocusable(true);
        IndustryView.setFocusableInTouchMode(true);
        IndustryView.setClickable(true);
        IndustryView.setEnabled(true);

        CompanySizeView.setFocusable(true);
        CompanySizeView.setFocusableInTouchMode(true);
        CompanySizeView.setClickable(true);

        WebsiteView.setFocusable(true);
        WebsiteView.setFocusableInTouchMode(true);
        WebsiteView.setClickable(true);

        ClientsView.setFocusable(true);
        ClientsView.setFocusableInTouchMode(true);
        ClientsView.setClickable(true);

    }

    public void deleteProfile(View view) {

        //TODO
        //Delete requires not only to delete the row on one table but all the associated data
        //in the other tables


    }
}
