package com.example.ricardogarcia.politojobs;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.parse.ParseObject;
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

    public void sendMessage(View view){
        Intent intent = new Intent(this, SendMessage.class);
        intent.putExtra(RECEIVER, student.getId());
        intent.putExtra(RECEIVERTYPE, "student");
        startActivity(intent);
    }

    public void saveStudent(View view){
        String companyId = ParseUser.getCurrentUser().getObjectId();
        ParseObject savedCompany = new ParseObject("SavedCompany");
        savedCompany.put("CompanyId", companyId);
        savedCompany.put("StudentId", student.getId());
        savedCompany.saveInBackground();
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
        student = (Student) intent.getSerializableExtra(ResultStudents.STUDENT);

        TextView name = (TextView) findViewById(R.id.studentName);
        name.setText(student.getName()+" "+student.getSurname());
        TextView description = (TextView) findViewById(R.id.studentDescription);
        description.setText(student.getDescription());
        TextView industry = (TextView) findViewById(R.id.studentIndustry);
        industry.setText(student.getIndustry());
        TextView techSkills = (TextView) findViewById(R.id.techSkills);
        techSkills.setText("Technical Skills");
        for(int i=0; i<student.getSkills().size(); i++){
            techSkills.setText(techSkills.getText().toString()+"\n"+student.getSkills().get(i));
        }
        TextView experience = (TextView) findViewById(R.id.yearsExperience);
        experience.setText(student.getExperienceyears());
        TextView degree = (TextView) findViewById(R.id.typeOfDegree);
        degree.setText(student.getDegree());
        TextView interests = (TextView) findViewById(R.id.interests);
        interests.setText("Interests");
        for(int i=0; i<student.getInterests().size(); i++){
            interests.setText(interests.getText().toString()+"\n"+student.getInterests().get(i));
        }
        TextView company = (TextView) findViewById(R.id.currentCompany);
        company.setText(student.getCurrent_company().getName());
        TextView languages = (TextView) findViewById(R.id.languages);
        languages.setText("Languages");
        for(int i=0; i<student.getLanguages().size(); i++){
            languages.setText(languages.getText().toString()+"\n"+student.getLanguages().get(i));
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
