package com.example.ahsan.emobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {


    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        session = new SessionManager(getApplicationContext());

        load l = new load();
        l.execute();
    }

    private class load extends AsyncTask<String, String, String> {

        Boolean loggedin = false;

        @Override
        protected String doInBackground(String... params) {

            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                loggedin = !session.getUserID().equals("-1");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (loggedin) {
                Intent i = new Intent(MainActivity.this, NavigationDrawer.class);
                startActivity(i);
            } else {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        }
    }
}