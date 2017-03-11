package com.example.ahsan.emobile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends Activity implements View.OnClickListener{

    EditText email;
    EditText username;
    EditText password;
    ProgressDialog pd;
    Button btnrgstr;
    Button signin;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        session = new SessionManager(getApplicationContext());

        pd = new ProgressDialog(this);
        pd.setCancelable(false);

        email = (EditText) findViewById(R.id.Email);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        btnrgstr = (Button) findViewById(R.id.rgstrbtn);
        signin = (Button) findViewById(R.id.buttonLgn);

        btnrgstr.setOnClickListener(this);
        signin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.buttonLgn:
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                finish();
                break;

            case R.id.rgstrbtn:

                String em , us , pass;

                em = email.getText().toString();
                us = username.getText().toString();
                pass = password.getText().toString();

                registerUser(em,us,pass);
                break;
        }

    }

    public void registerUser(final String Email, final String Username, final String Password){
        pd.setMessage("Registering");
        showDialog();
        String tag_string_req = "req_register";
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL + "index.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if(!error){
                        String userid = jObj.getString("userid");
                        String username = jObj.getString("username");
                        session.setLogin(true);
                        session.setUserId(userid);
                        session.setUsername(username);

                        Intent intent = new Intent(RegisterActivity.this, NavigationDrawer.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                      showMessage(jObj.getString("error_msg"));
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                    showMessage("JSON ERROR: " + e.getMessage());
                    hideDialog();
                }


                }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                showMessage(error.getMessage());
                hideDialog();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap();
                params.put("username", Username);
                params.put("password", Password);
                params.put("email", Email);
                params.put("register","true");

                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pd.isShowing())
            pd.show();
    }

    private void hideDialog() {
        if (pd.isShowing())
            pd.dismiss();
    }


    public void showMessage(String s){
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}
