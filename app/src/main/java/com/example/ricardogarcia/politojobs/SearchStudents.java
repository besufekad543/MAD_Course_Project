package com.example.ricardogarcia.politojobs;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.ParseUser;

import java.util.HashMap;


public class SearchStudents extends ActionBarActivity {

    public static final String HASHMAP = "com.example.ricardogarcia.politojobs.HASHMAP";
    public static final String NAME = "com.example.ricardogarcia.politojobs.STUDENTNAME";
    public static final String SURNAME = "com.example.ricardogarcia.politojobs.STUDENTSURNAME";
    public static final String LOCATION = "com.example.ricardogarcia.politojobs.STUDENTLOCATION";
    public static final String INDUSTRY = "com.example.ricardogarcia.politojobs.STUDENTINDUSTRY";
    public static final String TECHSKILLS = "com.example.ricardogarcia.politojobs.STUDENTTECHSKILLS";
    public static final String EXPERIENCE = "com.example.ricardogarcia.politojobs.STUDENTEXPERIENCE";
    public static final String DEGREE = "com.example.ricardogarcia.politojobs.STUDENTDEGREE";
    public static final String INTERESTS = "com.example.ricardogarcia.politojobs.STUDENTINTERESTS";
    public static final String LANGUAGES = "com.example.ricardogarcia.politojobs.STUDENTLANGUAGES";
    public static final String CHECK = "com.example.ricardogarcia.politojobs.STUDENTCHECK";

    private ArrayAdapter<String> adapterIndustry;
    private ArrayAdapter<String> adapterExperience;
    private ArrayAdapter<String> adapterDegree;
    private ArrayAdapter<String> adapterLanguages;

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

    public void search(View view) {
        HashMap<String, String> filters = new HashMap<>();

        EditText name = (EditText) findViewById(R.id.textName);
        EditText surname = (EditText) findViewById(R.id.textSurname);
        EditText location = (EditText) findViewById(R.id.textLocation);
        Spinner industry = (Spinner) findViewById(R.id.spinnerIndustry);
        EditText techSkills = (EditText) findViewById(R.id.textTechSkills);
        Spinner experience = (Spinner) findViewById(R.id.spinnerExperience);
        Spinner degree = (Spinner) findViewById(R.id.spinnerDegree);
        EditText interests = (EditText) findViewById(R.id.textInterests);
        Spinner languages = (Spinner) findViewById(R.id.spinnerLanguage);
        CheckBox check = (CheckBox) findViewById(R.id.checkBox);


        if(!name.getText().toString().equals("")) {
            filters.put(NAME,name.getText().toString());
        }
        if(!surname.getText().toString().equals("")) {
            filters.put(SURNAME,surname.getText().toString());
        }
        if(!location.getText().toString().equals("")) {
            filters.put(LOCATION,location.getText().toString());
        }
        if(industry.isSelected()) {
            filters.put(INDUSTRY, industry.getSelectedItem().toString());
        }
        if(!techSkills.getText().toString().equals("")) {
            filters.put(TECHSKILLS,techSkills.getText().toString());
        }
        if(experience.isSelected()) {
            filters.put(EXPERIENCE, experience.getSelectedItem().toString());
        }
        if(degree.isSelected()) {
            filters.put(DEGREE, degree.getSelectedItem().toString());
        }
        if(!interests.getText().toString().equals("")) {
            filters.put(INTERESTS,interests.getText().toString());
        }
        if(languages.isSelected()) {
            filters.put(LANGUAGES, languages.getSelectedItem().toString());
        }
        if(check.isSelected()) {
            filters.put(CHECK, "TRUE");
        }

        Intent intent = new Intent(this, ResultStudents.class);
        intent.putExtra(HASHMAP,filters);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_students);

        Spinner industry = (Spinner) findViewById(R.id.spinnerIndustry);
        Spinner experience = (Spinner) findViewById(R.id.spinnerExperience);
        Spinner degree = (Spinner) findViewById(R.id.spinnerDegree);
        Spinner languages = (Spinner) findViewById(R.id.spinnerLanguage);

        adapterIndustry = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.arrayIndustry));
        adapterIndustry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterExperience = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.arrayExperience));
        adapterExperience.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterDegree = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.arrayDegree));
        adapterDegree.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterLanguages = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.arrayLanguages));
        adapterLanguages.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        industry.setAdapter(adapterIndustry);
        experience.setAdapter(adapterExperience);
        degree.setAdapter(adapterDegree);
        languages.setAdapter(adapterLanguages);

        //LLENAR ARRAYS

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        EditText name = (EditText) findViewById(R.id.textName);
        EditText surname = (EditText) findViewById(R.id.textSurname);
        EditText location = (EditText) findViewById(R.id.textLocation);
        Spinner industry = (Spinner) findViewById(R.id.spinnerIndustry);
        EditText techSkills = (EditText) findViewById(R.id.textTechSkills);
        Spinner experience = (Spinner) findViewById(R.id.spinnerExperience);
        Spinner degree = (Spinner) findViewById(R.id.spinnerDegree);
        EditText interests = (EditText) findViewById(R.id.textInterests);
        Spinner languages = (Spinner) findViewById(R.id.spinnerLanguage);
        CheckBox check = (CheckBox) findViewById(R.id.checkBox);

        savedInstanceState.putString(NAME, name.getText().toString());
        savedInstanceState.putString(SURNAME, surname.getText().toString());
        savedInstanceState.putString(LOCATION, location.getText().toString());
        if(industry.isSelected()) {
            savedInstanceState.putString(INDUSTRY, industry.getSelectedItem().toString());
        }
        savedInstanceState.putString(TECHSKILLS, techSkills.getText().toString());
        if(experience.isSelected()) {
            savedInstanceState.putString(EXPERIENCE, experience.getSelectedItem().toString());
        }
        if(degree.isSelected()) {
            savedInstanceState.putString(DEGREE, degree.getSelectedItem().toString());
        }
        savedInstanceState.putString(INTERESTS, interests.getText().toString());
        if(languages.isSelected()) {
            savedInstanceState.putString(LANGUAGES, languages.getSelectedItem().toString());
        }
        if(check.isSelected()) {
            savedInstanceState.putString(CHECK, "TRUE");
        }

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        EditText name = (EditText) findViewById(R.id.textName);
        EditText surname = (EditText) findViewById(R.id.textSurname);
        EditText location = (EditText) findViewById(R.id.textLocation);
        Spinner industry = (Spinner) findViewById(R.id.spinnerIndustry);
        EditText techSkills = (EditText) findViewById(R.id.textTechSkills);
        Spinner experience = (Spinner) findViewById(R.id.spinnerExperience);
        Spinner degree = (Spinner) findViewById(R.id.spinnerDegree);
        EditText interests = (EditText) findViewById(R.id.textInterests);
        Spinner languages = (Spinner) findViewById(R.id.spinnerLanguage);
        CheckBox check = (CheckBox) findViewById(R.id.checkBox);

        name.setText(savedInstanceState.getString(NAME));
        surname.setText(savedInstanceState.getString(SURNAME));
        location.setText(savedInstanceState.getString(LOCATION));
        if(savedInstanceState.containsKey(INDUSTRY)) {
            industry.setSelection(adapterIndustry.getPosition(savedInstanceState.getString(INDUSTRY)));
        }
        techSkills.setText(savedInstanceState.getString(TECHSKILLS));
        if(savedInstanceState.containsKey(EXPERIENCE)) {
            experience.setSelection(adapterExperience.getPosition(savedInstanceState.getString(EXPERIENCE)));
        }
        if(savedInstanceState.containsKey(DEGREE)) {
            degree.setSelection(adapterDegree.getPosition(savedInstanceState.getString(DEGREE)));
        }
        interests.setText(savedInstanceState.getString(INTERESTS));
        if(savedInstanceState.containsKey(LANGUAGES)) {
            languages.setSelection(adapterLanguages.getPosition(savedInstanceState.getString(LANGUAGES)));
        }
        if(savedInstanceState.containsKey(CHECK)) {
            check.setSelected(true);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_students, menu);
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
