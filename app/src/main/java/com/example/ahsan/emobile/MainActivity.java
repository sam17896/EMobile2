package com.example.ahsan.emobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tv, ip;
    Button lg, rg, hm, pr , tp,setip;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tv = (TextView) findViewById(R.id.ID);
        ip = (TextView) findViewById(R.id.ip);
        rg = (Button) findViewById(R.id.rgtr);
        lg = (Button) findViewById(R.id.lg);
        hm = (Button) findViewById(R.id.hm);
        pr = (Button) findViewById(R.id.pr);
        tp = (Button) findViewById(R.id.tp);
        setip = (Button) findViewById(R.id.setip);



        session = new SessionManager(getApplicationContext());
        tv.setText(session.getUserID());

        lg.setOnClickListener(this);
        rg.setOnClickListener(this);
        hm.setOnClickListener(this);
        pr.setOnClickListener(this);
        tp.setOnClickListener(this);
        setip.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent i;
        switch (id){
            case R.id.lg:
                session.setLogin(false);
                session.setUserId("");

                i = new Intent(this,LoginActivity.class);
                startActivity(i);
                finish();
                break;

            case R.id.rgtr:
                session.setLogin(false);
                session.setUserId("");

                i = new Intent(this, RegisterActivity.class);
                startActivity(i);
                finish();

                break;

            case R.id.hm:

                i = new Intent(this, NavigationDrawer.class);
                startActivity(i);
                finish();
                break;

            case R.id.pr:

                i = new Intent(this, ProfileView.class);
                startActivity(i);
                finish();
                break;

            case R.id.tp:

                i = new Intent(this, TopicView.class);
                startActivity(i);
                finish();
                break;
            case R.id.setip:
                String ipAdd = ip.getText().toString().trim();
                AppConfig.URL = "http://" + ipAdd + ":84/e-discussionmobile/";
                AppConfig.IMAGESURL = "http://" + ipAdd + ":84/e-discussionmobile/uploaded_files/";

                break;


        }

    }
}
