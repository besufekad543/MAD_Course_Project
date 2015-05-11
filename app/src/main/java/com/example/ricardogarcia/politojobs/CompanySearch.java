package com.example.ricardogarcia.politojobs;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.Parse;
import com.parse.ParseUser;

import java.util.HashMap;


public class CompanySearch extends ActionBarActivity {

    public static final String INFO_HASH = "com.example.ricardogarcia.politojobs.HASH";
    public static final String INFO_SEARCHTYPE = "com.example.ricardogarcia.politojobs.SEARCHTYPE";
    public static final String INFO_NAME = "com.example.ricardogarcia.politojobs.NAME";
    public static final String INFO_INDUSTRY = "com.example.ricardogarcia.politojobs.INDUSTRY";
    public static final String INFO_LOCATION = "com.example.ricardogarcia.politojobs.LOCATION";
    public static final String INFO_SIZE = "com.example.ricardogarcia.politojobs.SIZE";


    private ArrayAdapter<String> adapterIndustry;
    private ArrayAdapter<String> adapterLocation;
    private ArrayAdapter<String> adapterSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_search);

        Spinner location = (Spinner) findViewById(R.id.spinnerLocation);
        Spinner industry = (Spinner) findViewById(R.id.spinnerIndustry);
        Spinner size = (Spinner) findViewById(R.id.spinnerCompanySize);

        adapterLocation = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.arrayLocation));
        adapterLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterIndustry = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.arrayIndustry));
        adapterIndustry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterSize = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.arraySize));
        adapterSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        location.setAdapter(adapterLocation);
        industry.setAdapter(adapterIndustry);
        size.setAdapter(adapterSize);

        Parse.initialize(this, "H9NFC1K9LmahxGcCrMOdT0qMaE0lDGT6BgbrSOAc", "4K2VfxRGIyk69KlQJ2B8NMnD71llrlkEPLdTNh9M");


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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_company_search, menu);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        EditText name= (EditText) findViewById(R.id.editTextName);
        Spinner spinnerLocation=(Spinner) findViewById(R.id.spinnerLocation);
        Spinner spinnerIndustry= (Spinner) findViewById(R.id.spinnerIndustry);
        Spinner spinnerSize=(Spinner) findViewById(R.id.spinnerCompanySize);

        outState.putString(INFO_NAME,name.getText().toString());

        if(spinnerLocation.isSelected()){
            outState.putString(INFO_LOCATION,spinnerLocation.getSelectedItem().toString());
        }

        if(spinnerIndustry.isSelected()){
            outState.putString(INFO_INDUSTRY,spinnerIndustry.getSelectedItem().toString());
        }

        if(spinnerSize.isSelected()){
            outState.putString(INFO_SIZE,spinnerSize.getSelectedItem().toString());
        }


        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        EditText name= (EditText) findViewById(R.id.editTextName);
        Spinner spinnerLocation=(Spinner) findViewById(R.id.spinnerLocation);
        Spinner spinnerIndustry= (Spinner) findViewById(R.id.spinnerIndustry);
        Spinner spinnerSize=(Spinner) findViewById(R.id.spinnerCompanySize);

        name.setText(savedInstanceState.getString(INFO_NAME));

        if(savedInstanceState.containsKey(INFO_LOCATION)){
            spinnerLocation.setSelection(adapterLocation.getPosition(savedInstanceState.getString(INFO_LOCATION)));
        }

        if(savedInstanceState.containsKey(INFO_INDUSTRY)){
            spinnerIndustry.setSelection(adapterIndustry.getPosition(savedInstanceState.getString(INFO_INDUSTRY)));
        }

        if(savedInstanceState.containsKey(INFO_SIZE)){
            spinnerSize.setSelection(adapterSize.getPosition(savedInstanceState.getString(INFO_SIZE)));
        }
    }


    public void searchCompanies(View view){
        Intent intent = new Intent(this, CompanySearchResults.class);

        HashMap<String,String> search_filters= new HashMap<String,String>();

        EditText name_filter= (EditText) findViewById(R.id.editTextName);
        Spinner spinnerLocation=(Spinner) findViewById(R.id.spinnerLocation);
        Spinner spinnerIndustry= (Spinner) findViewById(R.id.spinnerIndustry);
        Spinner spinnerSize= (Spinner) findViewById(R.id.spinnerCompanySize);


        search_filters.put(INFO_SEARCHTYPE,"Search");


        if(!name_filter.getText().toString().equals("")){
            search_filters.put(INFO_NAME, name_filter.getText().toString().toLowerCase());
        }

        if(spinnerLocation.isSelected()){
            search_filters.put(INFO_LOCATION,spinnerLocation.getSelectedItem().toString().toLowerCase());
        }

        if(spinnerIndustry.isSelected()){
            search_filters.put(INFO_INDUSTRY,spinnerIndustry.getSelectedItem().toString().toLowerCase());
        }

        if(spinnerSize.isSelected()){
            search_filters.put(INFO_SIZE,spinnerSize.getSelectedItem().toString().toLowerCase());
        }

        Bundle b = new Bundle();
        b.putSerializable(INFO_HASH,search_filters);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void goSavedCompanies(View view){
        Intent intent = new Intent(this, CompanySearchResults.class);

        HashMap<String,String> search_filters= new HashMap<String,String>();
        search_filters.put(INFO_SEARCHTYPE,"Saved Companies");

        Bundle b = new Bundle();
        b.putSerializable(INFO_HASH,search_filters);
        intent.putExtras(b);
        startActivity(intent);

    }
}
