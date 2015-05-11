package com.example.ricardogarcia.politojobs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class Inbox extends ActionBarActivity {

    public final static String INFO_MESSAGE = "com.example.ricardogarcia.politojobs.MESSAGE";
    public final static String INFO_SUBJECT = "com.example.ricardogarcia.politojobs.SUBJECT";
    public final static String LIST_MESSAGES = "com.example.ricardogarcia.politojobs.LIST";


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
        setContentView(R.layout.activity_inbox);

        Parse.initialize(Inbox.this, "H9NFC1K9LmahxGcCrMOdT0qMaE0lDGT6BgbrSOAc", "4K2VfxRGIyk69KlQJ2B8NMnD71llrlkEPLdTNh9M");
        //ParseUser currentUser= ParseUser.getCurrentUser();
        new RetrieveFromDatabase().execute("rgarcia");

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inbox, menu);
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

    private class RetrieveFromDatabase extends AsyncTask<String,Void,ArrayList<Message>>{

        private ProgressDialog progressDialog= new ProgressDialog(Inbox.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Loading messages");
            if(!progressDialog.isShowing()){
                progressDialog.show();
            }
        }

        @Override
        protected ArrayList<Message> doInBackground(String... params) {

            ArrayList<Message> result_messages=new ArrayList<Message>();
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Message");
            query.whereEqualTo("SenderId",params[0]);
            try {
                List<ParseObject> results=query.find();
                for(ParseObject p:results){
                    Message msg= new Message();
                    msg.setSubject((String) p.get("Subject"));
                    msg.setMessage((String) p.get("Message"));
                    result_messages.add(msg);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return result_messages;
        }

        @Override
        protected void onPostExecute(ArrayList<Message> messages) {
            super.onPostExecute(messages);
            if(progressDialog.isShowing()) {
                progressDialog.hide();
            }

            MessageAdapter mAdapter= new MessageAdapter(Inbox.this,messages);

            ListView list_messages= (ListView) findViewById(R.id.listMessages);
            list_messages.setAdapter(mAdapter);
            list_messages.setEmptyView(findViewById(R.id.textNoResults));

        }
    }
}

