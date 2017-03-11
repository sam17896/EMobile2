package com.example.ahsan.emobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tv;
    Button btn;
    Button btn2;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btn = (Button) findViewById(R.id.button);
        tv = (TextView) findViewById(R.id.ID);
        btn2 = (Button) findViewById(R.id.button2);


        session = new SessionManager(getApplicationContext());
        tv.setText(session.getUserID());

        btn2.setOnClickListener(this);
        btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent i;
        switch (id){
            case R.id.button:
                i = new Intent(this,LoginActivity.class);
                startActivity(i);
                finish();
                break;

            case R.id.button2:
                session.setLogin(false);
                session.setUserId("");

                i = new Intent(this, LoginActivity.class);
                startActivity(i);
                finish();

                break;
        }

    }
}
