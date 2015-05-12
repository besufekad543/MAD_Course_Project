package com.example.ricardogarcia.politojobs;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


public class ViewCompany extends ActionBarActivity {

    private static final String NAME = "com.example.ricardogarcia.politojobs.COMPANYNAME";
    private static final String INDUSTRY = "com.example.ricardogarcia.politojobs.COMPANYINDUSTRY";
    private static final String DESCRIPTION = "com.example.ricardogarcia.politojobs.COMPANYDESCRIPTION";
    private static final String LOCATION = "com.example.ricardogarcia.politojobs.COMPANYLOCATION";
    private static final String SIZE = "com.example.ricardogarcia.politojobs.COMPANYSIZE";
    private static final String WEB = "com.example.ricardogarcia.politojobs.COMPANYWEBSITE";
    private static final String CLIENTS = "com.example.ricardogarcia.politojobs.COMPANYCLIENTS";
    public static final String RECEIVER = "com.example.ricardogarcia.politojobs.RECEIVER";
    public static final String RECEIVERTYPE = "com.example.ricardogarcia.politojobs.RECEIVERTYPE";

    private Company company;

    public void saveCompany(View view) {

        ParseQuery<ParseObject> queryStudent = ParseQuery.getQuery("Student");
        queryStudent.whereEqualTo("StudentId", ParseUser.getCurrentUser());
        ParseQuery<ParseObject> queryCompany = ParseQuery.getQuery("Company");
        queryCompany.whereEqualTo("objectId", company.getId());
        try {
            ParseObject student = queryStudent.getFirst();
            ParseObject company = queryCompany.getFirst();
            ParseObject savedCompany = new ParseObject("SavedCompany");
            savedCompany.put("StudentId", student);
            savedCompany.put("CompanyId", company);
            savedCompany.saveInBackground();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, SendMessage.class);
        intent.putExtra(RECEIVER, company.getId());
        intent.putExtra(RECEIVERTYPE, "company");
        startActivity(intent);
    }

    public void backToResults(View view) {
        ViewCompany.this.finish();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_company);

        Intent intent = getIntent();
        company = (Company) intent.getSerializableExtra(CompanyAdapter.COMPANY);

        TextView name = (TextView) findViewById(R.id.companyName);
        name.setText(company.getName());
        TextView industry = (TextView) findViewById(R.id.companyIndustry);
        industry.setText(company.getIndustry());
        TextView description = (TextView) findViewById(R.id.companyDescription);
        description.setText(company.getDescription());
        TextView location = (TextView) findViewById(R.id.companyLocation);
        location.setText(company.getLocation());
        TextView size = (TextView) findViewById(R.id.companySize);
        size.setText(company.getCompany_size());
        TextView website = (TextView) findViewById(R.id.website);
        website.setText(company.getWebsite());
        TextView clients = (TextView) findViewById(R.id.clients);
        clients.setText(clients.getText().toString());

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        TextView name = (TextView) findViewById(R.id.companyName);
        TextView industry = (TextView) findViewById(R.id.companyIndustry);
        TextView description = (TextView) findViewById(R.id.companyDescription);
        TextView location = (TextView) findViewById(R.id.companyLocation);
        TextView size = (TextView) findViewById(R.id.companySize);
        TextView website = (TextView) findViewById(R.id.website);
        TextView clients = (TextView) findViewById(R.id.clients);

        savedInstanceState.putString(NAME, name.getText().toString());
        savedInstanceState.putString(INDUSTRY, industry.getText().toString());
        savedInstanceState.putString(DESCRIPTION, description.getText().toString());
        savedInstanceState.putString(LOCATION, location.getText().toString());
        savedInstanceState.putString(SIZE, size.getText().toString());
        savedInstanceState.putString(WEB, website.getText().toString());
        savedInstanceState.putString(CLIENTS, clients.getText().toString());

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        TextView name = (TextView) findViewById(R.id.companyName);
        TextView industry = (TextView) findViewById(R.id.companyIndustry);
        TextView description = (TextView) findViewById(R.id.companyDescription);
        TextView location = (TextView) findViewById(R.id.companyLocation);
        TextView size = (TextView) findViewById(R.id.companySize);
        TextView website = (TextView) findViewById(R.id.website);
        TextView clients = (TextView) findViewById(R.id.clients);

        name.setText(savedInstanceState.getString(NAME));
        industry.setText(savedInstanceState.getString(INDUSTRY));
        description.setText(savedInstanceState.getString(DESCRIPTION));
        location.setText(savedInstanceState.getString(LOCATION));
        size.setText(savedInstanceState.getString(SIZE));
        website.setText(savedInstanceState.getString(WEB));
        clients.setText(savedInstanceState.getString(CLIENTS));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_company, menu);
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
