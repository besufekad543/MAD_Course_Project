package com.example.ricardogarcia.politojobs;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.parse.ParseUser;


public class CompanyHome extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_home);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_company, menu);
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

    public void searchStudents(View view){
        Intent intent= new Intent(this,SearchStudents.class);
        startActivity(intent);
    }

    public void viewJobOffers(View view){
        Intent intent= new Intent(this,ListJobs.class);
        startActivity(intent);
    }

    public void viewMessages(View view){
        Intent intent= new Intent(this,Inbox.class);
        startActivity(intent);

    }

    public void logOut(View view){
        ParseUser.logOut();
        Intent intent= new Intent(this,LogIn.class);
        startActivity(intent);
    }


}
