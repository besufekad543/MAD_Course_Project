package com.example.ricardogarcia.politojobs;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.parse.ParseUser;


public class StudentHome extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_student_home, menu);
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

    public void logoutCurrentUser()
    {
        ParseUser.logOut();
        startActivity(new Intent(this,ManageSession.class));
    }

    public void viewApplications(View view){
        Intent intent= new Intent(this,ListApplication.class);
        startActivity(intent);
    }

    public void onSearchCompaniesClick(View v)
    {
        startActivity(new Intent(this,CompanySearch.class));
    }

    public void onSearchJobOffersClick(View v)
    {
        startActivity(new Intent(this, JobSearch.class));
    }

    public void onViewMessageClick(View v)
    {
        startActivity(new Intent(this, Message.class));
    }

    public void onStudentLogoutClick(View v)
    {
        logoutCurrentUser();
    }

}
