package com.example.ricardogarcia.politojobs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


public class ViewStudent extends ActionBarActivity {

    private static final String NAME = "com.example.ricardogarcia.politojobs.STUDENTNAME";
    private static final String INDUSTRY = "com.example.ricardogarcia.politojobs.STUDENTINDUSTRY";
    private static final String DESCRIPTION = "com.example.ricardogarcia.politojobs.STUDENTDESCRIPTION";
    private static final String TECHSKILLS = "com.example.ricardogarcia.politojobs.STUDENTTECHSKILLS";
    private static final String EXPERIENCE = "com.example.ricardogarcia.politojobs.STUDENTEXPERIENCE";
    private static final String DEGREE = "com.example.ricardogarcia.politojobs.STUDENTDEGREE";
    private static final String INTERESTS = "com.example.ricardogarcia.politojobs.STUDENTINTERESTS";
    private static final String COMPANY = "com.example.ricardogarcia.politojobs.STUDENTCOMPANY";
    private static final String LANGUAGES = "com.example.ricardogarcia.politojobs.STUDENTLANGUAGES";
    public static final String RECEIVER = "com.example.ricardogarcia.politojobs.RECEIVER";
    public static final String RECEIVERTYPE = "com.example.ricardogarcia.politojobs.RECEIVERTYPE";

    private Student student;
    private String save_delete_type;

    public void sendMessage(View view){
        Intent intent = new Intent(this, SendMessage.class);
        intent.putExtra(RECEIVER, student.getId());
        intent.putExtra(RECEIVERTYPE, "Student");
        startActivity(intent);
    }

    public void saveStudent(View view){

        try {
            //
                /*ParseQuery<ParseUser> query = ParseUser.getQuery();
                query.whereEqualTo("objectId", "y7djJxGraH");
                ParseUser user = query.getFirst();*/
            //

            ParseQuery<ParseObject> queryCompany = ParseQuery.getQuery("Company");
            queryCompany.include("CompanyId");
            queryCompany.whereEqualTo("CompanyId", ParseUser.getCurrentUser());

            ParseQuery<ParseObject> queryStudent = ParseQuery.getQuery("Student");
            queryStudent.include("StudentId");
            queryStudent.include("CurrentCompany");
            queryStudent.whereEqualTo("objectId", student.getId());

            ParseObject student = queryStudent.getFirst();
            ParseObject company = queryCompany.getFirst();

            ParseQuery<ParseObject> querySavedStudent = ParseQuery.getQuery("SavedStudent");
            querySavedStudent.whereEqualTo("StudentId", student);
            querySavedStudent.whereEqualTo("CompanyId", company);

            if(save_delete_type.equals("Search")) {
                String message = null;

                if (querySavedStudent.count() == 0) {
                    ParseObject savedStudent = new ParseObject("SavedStudent");
                    savedStudent.put("StudentId", student);
                    savedStudent.put("CompanyId", company);
                    savedStudent.saveInBackground();
                    message = getString(R.string.addedSavedStudentMessage);

                } else {
                    message = getString(R.string.existingSavedStudentMessage);

                }

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Save students");
                builder.setMessage(message);
                builder.setCancelable(true);
                builder.setNeutralButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
            }
            else{
                querySavedStudent.getFirst().deleteInBackground();
                ViewStudent.this.finish();

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void backToResults(View view) {
        ViewStudent.this.finish();
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
        setContentView(R.layout.activity_view_student);

        Intent intent = getIntent();
        student = (Student) intent.getSerializableExtra(StudentAdapter.STUDENT);
        save_delete_type = (String) intent.getSerializableExtra(StudentAdapter.SEARCH_TYPE);
        //
        /*Parse.initialize(this, "H9NFC1K9LmahxGcCrMOdT0qMaE0lDGT6BgbrSOAc", "4K2VfxRGIyk69KlQJ2B8NMnD71llrlkEPLdTNh9M");
        save_delete_type = "Save";
        student = new Student();
        student.setId("2qeSmSVRGX");
        student.setName("Andrea");
        student.setSurname("Rosales");*/
        //

        TextView name = (TextView) findViewById(R.id.studentName);
        if(student.getName()!=null && student.getSurname()!=null)
            name.setText(student.getName() + " " + student.getSurname());
        TextView description = (TextView) findViewById(R.id.studentDescription);
        if(student.getDescription()!=null)
            description.setText(student.getDescription());
        TextView industry = (TextView) findViewById(R.id.studentIndustry);
        if(student.getIndustry()!=null)
            industry.setText(student.getIndustry());
        TextView techSkills = (TextView) findViewById(R.id.techSkills);
        techSkills.setText("Technical Skills");
        if(student.getSkills()!=null)
            for(int i=0; i<student.getSkills().size(); i++){
                techSkills.setText(techSkills.getText().toString()+"\n"+student.getSkills().get(i));
            }
        TextView experience = (TextView) findViewById(R.id.yearsExperience);
        if(student.getExperienceyears()>0)
            experience.setText(student.getExperienceyears());
        TextView degree = (TextView) findViewById(R.id.typeOfDegree);
        if(student.getDegree()!=null)
            degree.setText(student.getDegree());
        TextView interests = (TextView) findViewById(R.id.interests);
        interests.setText("Interests");
        if(student.getInterests()!=null)
            for(int i=0; i<student.getInterests().size(); i++){
                interests.setText(interests.getText().toString()+"\n"+student.getInterests().get(i));
            }
        TextView company = (TextView) findViewById(R.id.currentCompany);
        if(student.getCurrent_company()!=null)
            company.setText(student.getCurrent_company().getName());
        TextView languages = (TextView) findViewById(R.id.languages);
        languages.setText("Languages");
        if(student.getLanguages()!=null)
            for(int i=0; i<student.getLanguages().size(); i++){
                languages.setText(languages.getText().toString()+"\n"+student.getLanguages().get(i));
            }

        Button save_delete = (Button) findViewById(R.id.saveButton);
        if(save_delete_type.equals("Search")){
            save_delete.setText(getResources().getString(R.string.save_student_button));
        }
        else {
            save_delete.setText(getResources().getString(R.string.remove_student_button));
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        TextView name = (TextView) findViewById(R.id.studentName);
        TextView description = (TextView) findViewById(R.id.studentDescription);
        TextView industry = (TextView) findViewById(R.id.studentIndustry);
        TextView techSkills = (TextView) findViewById(R.id.techSkills);
        TextView experience = (TextView) findViewById(R.id.yearsExperience);
        TextView degree = (TextView) findViewById(R.id.typeOfDegree);
        TextView interests = (TextView) findViewById(R.id.interests);
        TextView company = (TextView) findViewById(R.id.currentCompany);
        TextView languages = (TextView) findViewById(R.id.languages);

        savedInstanceState.putString(NAME, name.getText().toString());
        savedInstanceState.putString(INDUSTRY, description.getText().toString());
        savedInstanceState.putString(DESCRIPTION, industry.getText().toString());
        savedInstanceState.putString(TECHSKILLS, techSkills.getText().toString());
        savedInstanceState.putString(EXPERIENCE, experience.getText().toString());
        savedInstanceState.putString(DEGREE, degree.getText().toString());
        savedInstanceState.putString(INTERESTS, interests.getText().toString());
        savedInstanceState.putString(COMPANY, company.getText().toString());
        savedInstanceState.putString(LANGUAGES, languages.getText().toString());

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        TextView name = (TextView) findViewById(R.id.studentName);
        TextView description = (TextView) findViewById(R.id.studentDescription);
        TextView industry = (TextView) findViewById(R.id.studentIndustry);
        TextView techSkills = (TextView) findViewById(R.id.techSkills);
        TextView experience = (TextView) findViewById(R.id.yearsExperience);
        TextView degree = (TextView) findViewById(R.id.typeOfDegree);
        TextView interests = (TextView) findViewById(R.id.interests);
        TextView company = (TextView) findViewById(R.id.currentCompany);
        TextView languages = (TextView) findViewById(R.id.languages);

        name.setText(savedInstanceState.getString(NAME));
        description.setText(savedInstanceState.getString(DESCRIPTION));
        industry.setText(savedInstanceState.getString(INDUSTRY));
        techSkills.setText(savedInstanceState.getString(TECHSKILLS));
        experience.setText(savedInstanceState.getString(EXPERIENCE));
        degree.setText(savedInstanceState.getString(DEGREE));
        interests.setText(savedInstanceState.getString(INTERESTS));
        company.setText(savedInstanceState.getString(COMPANY));
        languages.setText(savedInstanceState.getString(LANGUAGES));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_student, menu);
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
