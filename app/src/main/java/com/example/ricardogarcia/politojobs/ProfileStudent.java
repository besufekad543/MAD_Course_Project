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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ProfileStudent extends ActionBarActivity {
    //public static final String APPLICATION_ID = "H9NFC1K9LmahxGcCrMOdT0qMaE0lDGT6BgbrSOAc";
    //public static final String CLIENT_KEY = "4K2VfxRGIyk69KlQJ2B8NMnD71llrlkEPLdTNh9M";

    private EditText NameView;
    private EditText SurnameView;
    private EditText LocationView;
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
    private EditText CurrentCompanyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_student);
        //Parse.enableLocalDatastore(this);

        //Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);

        NameView = (EditText) findViewById(R.id.textName);
        SurnameView = (EditText) findViewById(R.id.textSurname);
        LocationView = (EditText) findViewById(R.id.textLocation);

        IndustryView = (Spinner) findViewById(R.id.spinnerIndustry);
        String[] Industry = getResources().getStringArray(R.array.arrayIndustry);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(ProfileStudent.this, android.R.layout.simple_list_item_1, Industry);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        IndustryView.setAdapter(adapter2);

        DateOfBirthView = (EditText) findViewById(R.id.dateBirthText);
        PlaceOfBirthView = (EditText) findViewById(R.id.placeBirthText);
        DescriptionView  = (EditText) findViewById(R.id.descriptionText);
        TechnicalSkillsView  = (EditText) findViewById(R.id.textTechSkills);

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
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(ProfileStudent.this, android.R.layout.simple_list_item_1, TypeOfDegree);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TypeOfDegreeView.setAdapter(adapter3);

        PhoneNumberView = (EditText) findViewById(R.id.textPhone);
        CurrentCompanyView = (EditText) findViewById(R.id.textCompany);


        // Set up the submit button click handler

        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


                final String name = NameView.getText().toString();
                final String surname = SurnameView.getText().toString();
                final String location = LocationView.getText().toString();
                final String industry = IndustryView.getSelectedItem().toString();

                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date dateofbirth = null;
                try {
                    final String date = DateOfBirthView.getText().toString();
                    dateofbirth = formatter.parse(date);
                } catch (Exception e) {

                }

                final String placeofbirth = PlaceOfBirthView.getText().toString();
                final String description = DescriptionView.getText().toString();

                final String technicalskills1 = TechnicalSkillsView.getText().toString();
                ArrayList<String> technicalskills = new ArrayList<String>();
                technicalskills.add(technicalskills1);


                ArrayList<String> languages = new ArrayList<String>();
                String isChecked = "";
                if(language_eng.isChecked())
                {
                    isChecked = (String) language_eng.getText();
                    languages.add(isChecked);
                }
                if(language_fra.isChecked())
                {
                    isChecked = (String) language_fra.getText();
                    languages.add(isChecked);
                }
                if(language_ita.isChecked())
                {
                    isChecked = (String) language_ita.getText();
                    languages.add(isChecked);
                }
                if(language_ger.isChecked())
                {
                    isChecked = (String) language_ger.getText();
                    languages.add(isChecked);
                }
                if(language_man.isChecked())
                {
                    isChecked = (String) language_man.getText();
                    languages.add(isChecked);
                }
                if(language_por.isChecked())
                {
                    isChecked = (String) language_por.getText();
                    languages.add(isChecked);
                }
                if(language_spa.isChecked())
                {
                    isChecked = (String) language_spa.getText();
                    languages.add(isChecked);
                }


                final String interests1 = InterestsView.getText().toString();
                ArrayList<String> interests = new ArrayList<String>();
                interests.add(interests1);

                final Integer yearofexp = Integer.parseInt(YearOfExpView.getText().toString());
                final String typeofdegree = TypeOfDegreeView.getSelectedItem().toString();

                final Integer phone = Integer.parseInt(PhoneNumberView.getText().toString());

                // Validate the sign up data
                boolean validationError = false;
                StringBuilder validationErrorMessage = new StringBuilder(getResources().getString(R.string.error_intro));
                if (isEmpty(NameView)) {
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_invalid_name));
                }
                if (isEmpty(SurnameView)) {
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_invalid_surname));
                }
                if (isEmpty(LocationView)) {
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_invalid_location));
                }
                if (isEmpty(IndustryView)) {
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_invalid_industry));
                }
                if (isEmpty(DateOfBirthView)) {
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_invalid_DateOfBirth));
                }
                if (isEmpty(PlaceOfBirthView)) {
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_invalid_PlaceOfBirth));
                }

                if (isEmpty(DescriptionView)) {
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_invalid_Description));
                }
                if (isEmpty(TechnicalSkillsView)) {
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_invalid_TechnicalSkills));
                }

                if (isEmpty(YearOfExpView)) {
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_invalid_YearOfExp));
                }
                if (isEmpty(TypeOfDegreeView)) {
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_invalid_Seniority));
                }
                if (isEmpty(PhoneNumberView)) {
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_invalid_PhoneNumber));
                }
                    /*if (isEmpty(CurrentCompanyView)) {
                        validationError = true;
                        validationErrorMessage.append(getResources().getString(R.string.error_invalid_duration));
                    }*/
                validationErrorMessage.append(getResources().getString(R.string.error_end));

                // If there is a validation error, display the error
                if (validationError) {
                    Toast.makeText(ProfileStudent.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                // Set up a progress dialog
                final ProgressDialog dlg = new ProgressDialog(ProfileStudent.this);
                dlg.setTitle("Please wait.");
                dlg.setMessage("Saving Student Profile.  Please wait.");
                dlg.show();
                //save the data to parse.com


                ParseObject student = new ParseObject("Student");

                student.put("StudentId", ParseUser.getCurrentUser().getObjectId());
                student.put("Name", name);
                student.put("Surname", surname);
                student.put("Location", location);
                student.put("Industry", industry);
                student.put("Birthdate", dateofbirth);
                student.put("Birthplace", placeofbirth);
                student.put("Description", description);

                student.put("TechnicalSkills", technicalskills);
                student.put("Languages", languages);
                student.put("Interests", interests);

                student.put("ExperienceYears", yearofexp);
                student.put("TypeOfDegree", typeofdegree);
                student.put("PhoneNumber", phone);
                student.put("CurrentCompany", ParseUser.getCurrentUser());

                student.saveInBackground(new SaveCallback() {
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

        });
        findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                final View temp = v;
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Student");
                query.whereEqualTo("StudentId", ParseUser.getCurrentUser());
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> student, ParseException e) {
                        if (e == null) {
                            for (ParseObject delete : student) {
                                delete.remove(ParseUser.getCurrentUser().getObjectId());
                                delete.deleteInBackground();
                            }
                            Toast.makeText(temp.getContext(), "Student Profile Deleted", Toast.LENGTH_LONG).show();
                        } else {
                            Log.e("Error", e.getMessage());
                            Toast.makeText(temp.getContext(), "Error deleting Student Profile", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
        findViewById(R.id.editButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final View temp = v;
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Student");
                query.whereEqualTo("StudentId", ParseUser.getCurrentUser());
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> student, ParseException e) {
                        if (e == null) {


                            final String technicalskills1 = TechnicalSkillsView.getText().toString();
                            ArrayList<String> technicalskills = new ArrayList<String>();
                            technicalskills.add(technicalskills1);

                            ArrayList<String> languages = new ArrayList<String>();
                            String isChecked = "";
                            if(language_eng.isChecked())
                            {
                                isChecked = (String) language_eng.getText();
                                languages.add(isChecked);
                            }
                            if(language_fra.isChecked())
                            {
                                isChecked = (String) language_fra.getText();
                                languages.add(isChecked);
                            }
                            if(language_ita.isChecked())
                            {
                                isChecked = (String) language_ita.getText();
                                languages.add(isChecked);
                            }
                            if(language_ger.isChecked())
                            {
                                isChecked = (String) language_ger.getText();
                                languages.add(isChecked);
                            }
                            if(language_man.isChecked())
                            {
                                isChecked = (String) language_man.getText();
                                languages.add(isChecked);
                            }
                            if(language_por.isChecked())
                            {
                                isChecked = (String) language_por.getText();
                                languages.add(isChecked);
                            }
                            if(language_spa.isChecked())
                            {
                                isChecked = (String) language_spa.getText();
                                languages.add(isChecked);
                            }

                            final String interests1 = InterestsView.getText().toString();
                            ArrayList<String> interests = new ArrayList<String>();
                            interests.add(interests1);

                            for (ParseObject update : student) {
                                update.put("Name", NameView.getText().toString());
                                update.put("Surname", SurnameView.getText().toString());
                                update.put("Location", LocationView.getText().toString());
                                update.put("Industry", IndustryView.getSelectedItem().toString());
                                update.put("Birthdate", DateOfBirthView.getText().toString());
                                update.put("Birthplace", PlaceOfBirthView.getText().toString());
                                update.put("Description", DescriptionView.getText().toString());
                                update.put("TechnicalSkills", technicalskills.add(technicalskills1));
                                update.put("Languages", isChecked);
                                update.put("Interests", interests.add(interests1));
                                update.put("ExperienceYears", Integer.parseInt(YearOfExpView.getText().toString()));
                                update.put("TypeOfDegree", TypeOfDegreeView.getSelectedItem().toString());
                                update.put("PhoneNumber", Integer.parseInt(PhoneNumberView.getText().toString()));
                                update.put("CurrentCompany", ParseUser.getCurrentUser());
                                update.saveInBackground();
                            }
                            Toast.makeText(temp.getContext(), "Student Profile Updated", Toast.LENGTH_LONG).show();
                        } else {
                            Log.e("Error", e.getMessage());
                            Toast.makeText(temp.getContext(), "Error updating Student Profile", Toast.LENGTH_LONG).show();
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
