package com.example.ricardogarcia.politojobs;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


public class SendMessage extends ActionBarActivity {

    private static final String NAME = "com.example.ricardogarcia.politojobs.COMPANYNAME";
    private static final String SUBJECT = "com.example.ricardogarcia.politojobs.SUBJECT";
    private static final String MESSAGE = "com.example.ricardogarcia.politojobs.MESSAGE";

    private String companyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        Intent intent = getIntent();
        companyId = intent.getStringExtra(ViewCompany.ID);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Company");
        query.getInBackground(companyId, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                /*if (e == null) {
                    // object will be your game score
                } else {
                    // something went wrong
                }*/
                if (e == null){
                    TextView name = (TextView) findViewById(R.id.companyName);
                    name.setText(object.getString("Name"));
                }
            }
        });

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

    public void sendMessage(View view) {
        String studentId = ParseUser.getCurrentUser().getObjectId();
        ParseObject message = new ParseObject("Message");
        message.put("SenderId", studentId);
        message.put("ReceiverId", companyId);
        EditText subjectText = (EditText) findViewById(R.id.textSubject);
        String subject = subjectText.getText().toString();
        message.put("Subject", subject);
        EditText messageText = (EditText) findViewById(R.id.textMessage);
        String mess = messageText.getText().toString();
        message.put("Message", mess);
        message.saveInBackground();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        TextView name = (TextView) findViewById(R.id.companyName);
        EditText subjectText = (EditText) findViewById(R.id.textSubject);
        EditText messageText = (EditText) findViewById(R.id.textMessage);

        savedInstanceState.putString(NAME, name.toString());
        savedInstanceState.putString(SUBJECT, subjectText.toString());
        savedInstanceState.putString(MESSAGE, messageText.toString());

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        TextView name = (TextView) findViewById(R.id.companyName);
        EditText subjectText = (EditText) findViewById(R.id.textSubject);
        EditText messageText = (EditText) findViewById(R.id.textMessage);

        name.setText(savedInstanceState.getString(NAME));
        subjectText.setText(savedInstanceState.getString(SUBJECT));
        messageText.setText(savedInstanceState.getString(MESSAGE));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_send_message, menu);
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
