package com.example.ricardogarcia.politojobs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Collection;
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
        DescriptionView = (EditText) findViewById(R.id.descriptionText);
        ComapnySizeView = (EditText) findViewById(R.id.textSize);
        WebsiteView = (EditText) findViewById(R.id.textWeb);
        ClientsView = (EditText) findViewById(R.id.textClients);


        ParseQuery query = new ParseQuery("Company");
        query.whereEqualTo("CompanyId", ParseUser.getCurrentUser());

        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> arg0, ParseException arg1) {
                if (arg1 == null) {
                    for (final ParseObject nameObj : arg0) {

                        NameView.setText(nameObj.getString("Name"));
                        NameView.setFocusable(false);
                        NameView.setFocusableInTouchMode(false);
                        NameView.setClickable(false);


                        AddressView.setText(nameObj.getString("Address"));
                        AddressView.setFocusable(false);
                        AddressView.setFocusableInTouchMode(false);
                        AddressView.setClickable(false);

                        LocationView.setText(nameObj.getString("Location"));
                        LocationView.setFocusable(false);
                        LocationView.setFocusableInTouchMode(false);
                        LocationView.setClickable(false);

                        DescriptionView.setText(nameObj.getString("Description"));
                        DescriptionView.setFocusable(false);
                        DescriptionView.setFocusableInTouchMode(false);
                        DescriptionView.setClickable(false);
                        /*ArrayList<String> Indu = new ArrayList<String>();
                        String industry = nameObj.getString("Industry");
                        Indu.add(industry);
                        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(ProfileCompany.this, android.R.layout.simple_list_item_1, Indu);
                        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        IndustryView.setAdapter(adapter4);*/
                        IndustryView.setFocusable(false);
                        IndustryView.setFocusableInTouchMode(false);
                        IndustryView.setClickable(false);

                        Integer bb = nameObj.getInt("Size");
                        ComapnySizeView.setText(bb.toString());

                        ComapnySizeView.setFocusable(false);
                        ComapnySizeView.setFocusableInTouchMode(false);
                        ComapnySizeView.setClickable(false);

                        WebsiteView.setText(nameObj.getString("Website"));
                        WebsiteView.setFocusable(false);
                        WebsiteView.setFocusableInTouchMode(false);
                        WebsiteView.setClickable(false);

                        ClientsView.setText(nameObj.getString("Clients"));
                        ClientsView.setFocusable(false);
                        ClientsView.setFocusableInTouchMode(false);
                        ClientsView.setClickable(false);

                        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {

                            public void onClick(View view) {

                                final ProgressDialog dlg = new ProgressDialog(ProfileCompany.this);
                                dlg.setTitle("Please wait.");
                                dlg.setMessage("Saving Company Profile.  Please wait.");
                                dlg.show();

                                nameObj.put("Industry", IndustryView.getSelectedItem().toString());
                                nameObj.put("Description", DescriptionView.getText().toString());
                                nameObj.put("Size", Integer.parseInt(ComapnySizeView.getText().toString()));
                                nameObj.put("Website", WebsiteView.getText().toString());
                                nameObj.put("Clients", ClientsView.getText().toString());
                                nameObj.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        Intent intent = new Intent(ProfileCompany.this, ProfileCompany.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                });
                            }
                        });
                        findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                nameObj.remove(ParseUser.getCurrentUser().getObjectId());

                                nameObj.deleteInBackground();
                            }
                        });
                    }
                } else {
                    Log.d("Retrival", "Error: " + arg1.getMessage());
                }
            }
        });

        findViewById(R.id.editButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DescriptionView.setFocusable(true);
                DescriptionView.setFocusableInTouchMode(true);
                DescriptionView.setClickable(true);

                IndustryView.setFocusable(true);
                IndustryView.setFocusableInTouchMode(true);
                IndustryView.setClickable(true);

                ComapnySizeView.setFocusable(true);
                ComapnySizeView.setFocusableInTouchMode(true);
                ComapnySizeView.setClickable(true);

                WebsiteView.setFocusable(true);
                WebsiteView.setFocusableInTouchMode(true);
                WebsiteView.setClickable(true);

                ClientsView.setFocusable(true);
                ClientsView.setFocusableInTouchMode(true);
                ClientsView.setClickable(true);
            }
        });
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
