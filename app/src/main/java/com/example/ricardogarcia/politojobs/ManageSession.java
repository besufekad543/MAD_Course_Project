package com.example.ricardogarcia.politojobs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.parse.ParseUser;

/**
 * Created by POLI on 5/12/2015.
 */
public class ManageSession extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // Check if there is current user info
        manageUserSession();
    }

    private void manageUserSession() {
        // Check if there is current user info
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // Start an intent for the logged in activity
            String userType = currentUser.getString("TypeUser");//true for student //false for company
            if(userType.equals("Student")) {
                startActivity(new Intent(this, StudentHome.class));
            }else if(userType.equals("Company")) {
                   startActivity(new Intent(this, CompanyHome.class));
            }else
                Toast.makeText(getBaseContext(), "Unknown user type!", Toast.LENGTH_LONG).show();
        } else {
            // Start and intent for the logged out activity(home activity of the app)
            startActivity(new Intent(this, LogIn.class));
        }
    }
}
