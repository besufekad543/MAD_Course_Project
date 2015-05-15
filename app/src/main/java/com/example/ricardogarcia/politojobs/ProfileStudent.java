package com.example.ricardogarcia.politojobs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class ProfileStudent extends ActionBarActivity {

    private EditText NameView;
    private EditText SurnameView;
    private Spinner LocationView;
    private Spinner IndustryView;
    private EditText DateOfBirthView;
    private EditText PlaceOfBirthView;
    private EditText DescriptionView;
    private EditText TechnicalSkillsView;
    private CheckBox language_eng;
    private CheckBox language_fra;
    private CheckBox language_ger;
    private CheckBox language_ita;
    private CheckBox language_man;
    private CheckBox language_por;
    private CheckBox language_spa;
    private EditText InterestsView;
    private EditText YearOfExpView;
    private Spinner TypeOfDegreeView;
    private EditText PhoneNumberView;
    private Spinner CurrentCompanyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_student);

        NameView = (EditText) findViewById(R.id.textName);
        SurnameView = (EditText) findViewById(R.id.textSurname);
        LocationView = (Spinner) findViewById(R.id.spinnerLocation);
        ArrayAdapter<String> adapterLocation = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.arrayLocation));
        adapterLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        LocationView.setAdapter(adapterLocation);


        IndustryView = (Spinner) findViewById(R.id.spinnerIndustry);
        ArrayAdapter<String> adapterIndustry = new ArrayAdapter<String>(ProfileStudent.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.arrayIndustry));
        adapterIndustry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        IndustryView.setAdapter(adapterIndustry);

        DateOfBirthView = (EditText) findViewById(R.id.dateBirthText);
        PlaceOfBirthView = (EditText) findViewById(R.id.placeBirthText);
        DescriptionView = (EditText) findViewById(R.id.descriptionText);
        TechnicalSkillsView = (EditText) findViewById(R.id.textTechSkills);

        language_eng = ((CheckBox) findViewById(R.id.checkbox_english));
        language_fra = ((CheckBox) findViewById(R.id.checkbox_french));
        language_ger = ((CheckBox) findViewById(R.id.checkbox_german));
        language_ita = ((CheckBox) findViewById(R.id.checkbox_italian));
        language_man = ((CheckBox) findViewById(R.id.checkbox_mandarin));
        language_por = ((CheckBox) findViewById(R.id.checkbox_portuguese));
        language_spa = ((CheckBox) findViewById(R.id.checkbox_spanish));

        InterestsView = (EditText) findViewById(R.id.textInterests);
        YearOfExpView = (EditText) findViewById(R.id.textExperience);
        TypeOfDegreeView = (Spinner) findViewById(R.id.spinnerDegree);
        String[] TypeOfDegree = getResources().getStringArray(R.array.arrayDegree);
        ArrayAdapter<String> adapterDegree = new ArrayAdapter<String>(ProfileStudent.this, android.R.layout.simple_spinner_item, TypeOfDegree);
        adapterDegree.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TypeOfDegreeView.setAdapter(adapterDegree);

        PhoneNumberView = (EditText) findViewById(R.id.textPhone);
        CurrentCompanyView = (Spinner) findViewById(R.id.spinnerCurrentCompany);

        ParseQuery companyQuery = new ParseQuery("Company");
        List<String> displayCompanies = new ArrayList<String>();
        displayCompanies.add("-");
        try {
            List<ParseObject> existing_companies = companyQuery.find();
            for (ParseObject p : existing_companies) {
                displayCompanies.add(p.getString("Name"));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapterCurrentCompany = new ArrayAdapter<String>(ProfileStudent.this, android.R.layout.simple_spinner_item, displayCompanies);
        adapterCurrentCompany.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CurrentCompanyView.setAdapter(adapterCurrentCompany);


        ParseQuery query = new ParseQuery("Student");
        query.whereEqualTo("StudentId", ParseUser.getCurrentUser());
        query.include("CurrentCompany");

        try {
            ParseObject student = query.getFirst();

            NameView.setText(student.getString("Name"));
            NameView.setFocusable(false);
            NameView.setFocusableInTouchMode(false);
            NameView.setClickable(false);

            SurnameView.setText(student.getString("Surname"));
            SurnameView.setFocusable(false);
            SurnameView.setFocusableInTouchMode(false);
            SurnameView.setClickable(false);

            if (student.get("Location")==null) {
                LocationView.setSelection(0);
            } else {
                LocationView.setSelection(adapterLocation.getPosition(student.getString("Location")));
            }
            LocationView.setFocusable(false);
            LocationView.setFocusableInTouchMode(false);
            LocationView.setClickable(false);
            LocationView.setEnabled(false);


            if (student.get("Industry")==null) {
                IndustryView.setSelection(0);
            } else {
                IndustryView.setSelection(adapterIndustry.getPosition(student.getString("Industry")));
            }
            IndustryView.setFocusable(false);
            IndustryView.setFocusableInTouchMode(false);
            IndustryView.setClickable(false);
            IndustryView.setEnabled(false);

            if (student.getDate("Birthdate") != null) {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Date birthdate = student.getDate("Birthdate");
                DateOfBirthView.setText(df.format(birthdate));
            }
            DateOfBirthView.setFocusable(false);
            DateOfBirthView.setFocusableInTouchMode(false);
            DateOfBirthView.setClickable(false);

            if(student.getString("Birthplace")!=null) {
                PlaceOfBirthView.setText(student.getString("Birthplace"));
            }
            PlaceOfBirthView.setFocusable(false);
            PlaceOfBirthView.setFocusableInTouchMode(false);
            PlaceOfBirthView.setClickable(false);

            if(student.getString("Description")!=null) {
                DescriptionView.setText(student.getString("Description"));
            }
            DescriptionView.setFocusable(false);
            DescriptionView.setFocusableInTouchMode(false);
            DescriptionView.setClickable(false);

            if(student.get("TechnicalSkills")!=null) {
                ArrayList<String> technical_skills= (ArrayList<String>) student.get("TechnicalSkills");
                TechnicalSkillsView.setText(technical_skills.toString().substring(1,technical_skills.toString().length()-1));
            }
            TechnicalSkillsView.setFocusable(false);
            TechnicalSkillsView.setFocusableInTouchMode(false);
            TechnicalSkillsView.setClickable(false);


            if(student.get("Languages")!=null){
                ArrayList<String> languages = (ArrayList<String>)student.get("Languages");
                if(languages.contains("English")){
                    language_eng.setChecked(true);
                }
                if(languages.contains("French")){
                    language_fra.setChecked(true);
                }
                if(languages.contains("German")){
                    language_ger.setChecked(true);
                }
                if(languages.contains("Italian")){
                    language_ita.setChecked(true);
                }
                if(languages.contains("Mandarin")){
                    language_man.setChecked(true);
                }
                if(languages.contains("Portuguese")){
                    language_por.setChecked(true);
                }
                if(languages.contains("Spanish")){
                    language_spa.setChecked(true);
                }
            }


            language_eng.setClickable(false);
            language_fra.setClickable(false);
            language_ger.setClickable(false);
            language_ita.setClickable(false);
            language_man.setClickable(false);
            language_por.setClickable(false);
            language_spa.setClickable(false);


            if(student.get("Interests")!=null) {
                ArrayList<String> interests= (ArrayList<String>) student.get("Interests");
                InterestsView.setText(interests.toString().substring(1,interests.toString().length()-1));
            }
            InterestsView.setFocusable(false);
            InterestsView.setFocusableInTouchMode(false);
            InterestsView.setClickable(false);

            if(student.get("ExperienceYears")!=null) {
                int yearExperience=student.getInt("ExperienceYears");
                YearOfExpView.setText(String.valueOf(yearExperience));
            }
            YearOfExpView.setFocusable(false);
            YearOfExpView.setFocusableInTouchMode(false);
            YearOfExpView.setClickable(false);

            if (student.get("TypeOfDegree")==null) {
                TypeOfDegreeView.setSelection(0);
            } else {
                TypeOfDegreeView.setSelection(adapterDegree.getPosition(student.getString("TypeOfDegree")));
            }
            TypeOfDegreeView.setFocusable(false);
            TypeOfDegreeView.setFocusableInTouchMode(false);
            TypeOfDegreeView.setClickable(false);
            TypeOfDegreeView.setEnabled(false);

            if(student.get("PhoneNumber")!=null) {
                PhoneNumberView.setText(student.getString("PhoneNumber"));
            }
            PhoneNumberView.setFocusable(false);
            PhoneNumberView.setFocusableInTouchMode(false);
            PhoneNumberView.setClickable(false);


            ParseObject current_company = student.getParseObject("CurrentCompany");
            if (current_company == null) {
                CurrentCompanyView.setSelection(0);
            } else {
                CurrentCompanyView.setSelection(adapterCurrentCompany.getPosition(current_company.getString("Name")));
            }
            CurrentCompanyView.setFocusable(false);
            CurrentCompanyView.setFocusableInTouchMode(false);
            CurrentCompanyView.setClickable(false);
            CurrentCompanyView.setEnabled(false);


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
        getMenuInflater().inflate(R.menu.menu_profile_student, menu);
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

    public void editProfile(View view) {

        LocationView.setFocusable(true);
        LocationView.setFocusableInTouchMode(true);
        LocationView.setClickable(true);
        LocationView.setEnabled(true);

        IndustryView.setFocusable(true);
        IndustryView.setFocusableInTouchMode(true);
        IndustryView.setClickable(true);
        IndustryView.setEnabled(true);

        DateOfBirthView.setFocusable(true);
        DateOfBirthView.setFocusableInTouchMode(true);
        DateOfBirthView.setClickable(true);

        PlaceOfBirthView.setFocusable(true);
        PlaceOfBirthView.setFocusableInTouchMode(true);
        PlaceOfBirthView.setClickable(true);

        DescriptionView.setFocusable(true);
        DescriptionView.setFocusableInTouchMode(true);
        DescriptionView.setClickable(true);

        TechnicalSkillsView.setFocusable(true);
        TechnicalSkillsView.setFocusableInTouchMode(true);
        TechnicalSkillsView.setClickable(true);


        language_eng.setClickable(true);
        language_fra.setClickable(true);
        language_ger.setClickable(true);
        language_ita.setClickable(true);
        language_man.setClickable(true);
        language_por.setClickable(true);
        language_spa.setClickable(true);

        InterestsView.setFocusable(true);
        InterestsView.setFocusableInTouchMode(true);
        InterestsView.setClickable(true);

        YearOfExpView.setFocusable(true);
        YearOfExpView.setFocusableInTouchMode(true);
        YearOfExpView.setClickable(true);

        TypeOfDegreeView.setFocusable(true);
        TypeOfDegreeView.setFocusableInTouchMode(true);
        TypeOfDegreeView.setClickable(true);
        TypeOfDegreeView.setEnabled(true);

        PhoneNumberView.setFocusable(true);
        PhoneNumberView.setFocusableInTouchMode(true);
        PhoneNumberView.setClickable(true);

        CurrentCompanyView.setFocusable(true);
        CurrentCompanyView.setFocusableInTouchMode(true);
        CurrentCompanyView.setClickable(true);
        CurrentCompanyView.setEnabled(true);

    }

    public void saveProfile(View view) {

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dateofbirth = null;
        try {
            final String date = DateOfBirthView.getText().toString();
            dateofbirth = formatter.parse(date);
        } catch (Exception e) {

        }

        ArrayList<String> languages = new ArrayList<String>();
        if (language_eng.isChecked()) {
            languages.add(language_eng.getText().toString());
        }
        if (language_fra.isChecked()) {
            languages.add(language_fra.getText().toString());
        }
        if (language_ita.isChecked()) {
            languages.add(language_ita.getText().toString());
        }
        if (language_ger.isChecked()) {
            languages.add(language_ger.getText().toString());
        }
        if (language_man.isChecked()) {
            languages.add(language_man.getText().toString());
        }
        if (language_por.isChecked()) {
            languages.add(language_por.getText().toString());
        }
        if (language_spa.isChecked()) {
            languages.add(language_spa.getText().toString());
        }

        // Set up a progress dialog
        final ProgressDialog dlg = new ProgressDialog(ProfileStudent.this);
        dlg.setTitle(R.string.saveprofiletitle);
        dlg.setMessage(getString(R.string.saveprofilemessage));
        dlg.show();
        //save the data to parse.com


        ParseQuery studentQuery = new ParseQuery("Student");
        studentQuery.whereEqualTo("StudentId", ParseUser.getCurrentUser());
        try {
            ParseObject student = studentQuery.getFirst();
            if (!LocationView.getSelectedItem().toString().equals("-"))
                student.put("Location", LocationView.getSelectedItem().toString());

            if (!IndustryView.getSelectedItem().toString().equals("-"))
                student.put("Industry", IndustryView.getSelectedItem().toString());

            if (dateofbirth != null)
                student.put("Birthdate", dateofbirth);

            if (!PlaceOfBirthView.getText().toString().equals(""))
                student.put("Birthplace", PlaceOfBirthView.getText().toString());

            if (!DescriptionView.getText().toString().equals(""))
                student.put("Description", DescriptionView.getText().toString());

            if (!TechnicalSkillsView.getText().toString().equals("")) {
                List<String> initial_techskills = Arrays.asList(TechnicalSkillsView.getText().toString().toLowerCase().replaceAll("\\s+", "").split(","));
                student.put("TechnicalSkills", initial_techskills);
            }

            if (languages.size() > 0)
                student.put("Languages", languages);

            if (!InterestsView.getText().toString().equals("")) {
                List<String> interests = Arrays.asList(InterestsView.getText().toString().toLowerCase().replaceAll("\\s+", "").split(","));
                student.put("Interests", interests);
            }

            if (!YearOfExpView.getText().toString().equals("")) {
                student.put("ExperienceYears", Integer.valueOf(YearOfExpView.getText().toString()));
            }

            if (!TypeOfDegreeView.getSelectedItem().toString().equals("-"))
                student.put("TypeOfDegree", TypeOfDegreeView.getSelectedItem().toString());


            if (!PhoneNumberView.getText().toString().equals(""))
                student.put("PhoneNumber", PhoneNumberView.getText().toString());


            if (!CurrentCompanyView.getSelectedItem().toString().equals("-")) {
                ParseQuery companyQuery = new ParseQuery("Company");
                companyQuery.whereEqualTo("Name", CurrentCompanyView.getSelectedItem().toString());
                ParseObject c_company = companyQuery.getFirst();
                student.put("CurrentCompany", c_company);
            }

            student.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    dlg.dismiss();
                    Intent intent = new Intent(ProfileStudent.this, ProfileStudent.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public void deleteProfile(View view) {

        //TODO
        //Delete requires not only to delete the row on one table but all the associated data
        //in the other tables


    }
}
