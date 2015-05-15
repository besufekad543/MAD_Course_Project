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
    private EditText CurrentCompanyView;

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
        ArrayAdapter<String> adapterDegree = new ArrayAdapter<String>(ProfileStudent.this, android.R.layout.simple_spinner_item, TypeOfDegree);
        adapterDegree.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TypeOfDegreeView.setAdapter(adapterDegree);

        PhoneNumberView = (EditText) findViewById(R.id.textPhone);
        CurrentCompanyView = (EditText) findViewById(R.id.textCompany);

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

    public void editProfile(View view){

        //TODO
        //The edit function only set all fields (except name, location and address) as editable

        final View temp = view;
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
                        update.put("Location", LocationView.getSelectedItem().toString());
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

    public void saveProfile(View view){

        //TODO
        //This function needs to retrieve the existing object of the table for the specific student and update the values.
        //As it is implemented right now, it is adding a new row which is not the idea

        final String name = NameView.getText().toString();
        final String surname = SurnameView.getText().toString();
        final String location = LocationView.getSelectedItem().toString();
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

    public void deleteProfile(View view){

        try {
            //
                /*Parse.initialize(this, "H9NFC1K9LmahxGcCrMOdT0qMaE0lDGT6BgbrSOAc", "4K2VfxRGIyk69KlQJ2B8NMnD71llrlkEPLdTNh9M");
                ParseQuery<ParseUser> query = ParseUser.getQuery();
                query.whereEqualTo("objectId", "2AM7fmxH5Sk");
                ParseUser user = query.getFirst();*/
            //

            ParseQuery<ParseObject> queryStudent = ParseQuery.getQuery("Student");
            queryStudent.include("StudentId");
            queryStudent.whereEqualTo("StudentId", ParseUser.getCurrentUser());

            ParseObject student = queryStudent.getFirst();

            ParseQuery<ParseObject> queryApplyJob = ParseQuery.getQuery("ApplyJob");
            queryApplyJob.include("StudentId");
            queryApplyJob.whereEqualTo("StudentId", student);

            List<ParseObject> resultsApplyJob=queryApplyJob.find();
            for(ParseObject p:resultsApplyJob){
                p.delete();
            }

            ParseQuery<ParseObject> querySavedCompany = ParseQuery.getQuery("SavedCompany");
            querySavedCompany.include("StudentId");
            querySavedCompany.whereEqualTo("StudentId", student);

            List<ParseObject> resultsSavedCompany=querySavedCompany.find();
            for(ParseObject p:resultsSavedCompany){
                p.delete();
            }

            ParseQuery<ParseObject> querySavedJobOffer = ParseQuery.getQuery("SavedJobOffer");
            querySavedJobOffer.include("StudentId");
            querySavedJobOffer.whereEqualTo("StudentId", student);

            List<ParseObject> resultsSavedJobOffer=querySavedJobOffer.find();
            for(ParseObject p:resultsSavedJobOffer){
                p.delete();
            }

            ParseQuery<ParseObject> querySavedStudent = ParseQuery.getQuery("SavedStudent");
            querySavedStudent.include("StudentId");
            querySavedStudent.whereEqualTo("StudentId", student);

            List<ParseObject> resultsSavedStudent=querySavedStudent.find();
            for(ParseObject p:resultsSavedStudent){
                p.delete();
            }

            ParseQuery<ParseObject> queryMessage = ParseQuery.getQuery("Message");
            queryMessage.whereEqualTo("SenderId", ParseUser.getCurrentUser().getObjectId());
            List<ParseObject> resultsMessage=queryMessage.find();
            for(ParseObject p:resultsMessage){
                p.delete();
            }

            queryMessage = ParseQuery.getQuery("Message");
            queryMessage.whereEqualTo("ReceiverId", ParseUser.getCurrentUser().getObjectId());
            resultsMessage=queryMessage.find();
            for(ParseObject p:resultsMessage){
                p.delete();
            }

            student.delete();
            ParseUser.getCurrentUser().delete();
            //String objectId = ParseUser.getCurrentUser().getObjectId();
            ParseUser.logOut();



            /*ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereEqualTo("objectId", objectId);
            ParseUser user = query.getFirst();
            user.delete();*/

            startActivity(new Intent(this, ManageSession.class));


        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
