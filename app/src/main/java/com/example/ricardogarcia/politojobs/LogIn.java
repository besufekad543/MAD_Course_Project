package com.example.ricardogarcia.politojobs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;


public class LogIn extends ActionBarActivity
{
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        init();
    }

    public void onRegisterClick(View v)
    {
        startActivity(new Intent(this,Registration.class));
    }
    public void onLoginClick(View v)
    {
        // Check for input data validation error, display the error
        if(validateRegisterInput()) {
            // Set up a progress dialog
            final ProgressDialog dlg = new ProgressDialog(LogIn.this);
            dlg.setTitle("Please wait.");
            dlg.setMessage("Logging in.  Please wait.");
            dlg.show();
            // Call the Parse login method
            ParseUser.logInInBackground(username.getText().toString(), password.getText()
                    .toString(), new LogInCallback() {

                @Override
                public void done(ParseUser user, ParseException e) {
                    dlg.dismiss();
                    if (e != null) {
                        // Show the error message
                        Toast.makeText(LogIn.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        // Start an intent for the dispatch activity
                        Intent intent = new Intent(LogIn.this, ManageSession.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    private void init()
    {
        username = (EditText) findViewById(R.id.loginUsernameTxt);
        password = (EditText) findViewById(R.id.loginPasswordTxt);
    }

    //use type value = true for student and false for company data
    private boolean validateRegisterInput()
    {
        // Validate the sign up data
        boolean validationError = false;
        StringBuilder validationErrorMessage =
                new StringBuilder(getResources().getString(R.string.error_intro));
        if (isEmpty(username)) {
            validationError = true;
            validationErrorMessage.append(getResources().getString(R.string.error_blank_username));
        }
        if (isEmpty(password)) {
            if (validationError) {
                validationErrorMessage.append(getResources().getString(R.string.error_join));
            }
            validationError = true;
            validationErrorMessage.append(getResources().getString(R.string.error_blank_password));
        }
        validationErrorMessage.append(getResources().getString(R.string.error_end));

        // If there is a validation error, display the error
        if (validationError) {
            Toast.makeText(getBaseContext(), validationErrorMessage.toString(), Toast.LENGTH_LONG).show();
            return false;//invalid input
        }
        return true;//valid input
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

}
