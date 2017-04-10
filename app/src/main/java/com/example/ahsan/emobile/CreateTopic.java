package com.example.ahsan.emobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CreateTopic extends AppCompatActivity implements View.OnClickListener  {

    TextView name, description, category, tags;
    Button b;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_topic);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Create New Topic");


        b = (Button) findViewById(R.id.create);
        name = (TextView) findViewById(R.id.name);
        description = (TextView) findViewById(R.id.description);
        category = (TextView) findViewById(R.id.category);
        tags = (TextView) findViewById(R.id.tags);


        session = new SessionManager(getBaseContext().getApplicationContext());


        b.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id){
            case R.id.create:
                myTask task = new myTask();
                task.execute();
            break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class myTask extends AsyncTask<String,String,String>{

        String n, d;
        String[] c, t;
        String id;
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            n = name.getText().toString();
            d = description.getText().toString();

            c = category.getText().toString().split(",");
            t = tags.getText().toString().split(",");

            pd = new ProgressDialog(CreateTopic.this);
            pd.setTitle("Creating Topic.....");
            pd.setCancelable(false);
            pd.show();


        }

        private void showMessage(String s) {
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }
        @Override
        protected String doInBackground(String... params) {
            HttpHandler sh = new HttpHandler();
            String url = null;
            try {
                url = AppConfig.URL + "ct.php?name=" + URLEncoder.encode(n, "UTF-8") + "&description=" + URLEncoder.encode(d, "UTF-8") + "&id=" + session.getUserID();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String response = sh.makeServiceCall(url);

            if(response!=null){
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    JSONObject d = jsonArray.getJSONObject(0);
                    id = d.getString("id");

                    for(int i=0;i<c.length;i++) {
                        url = AppConfig.URL + "tc.php?id=" + id + "&cat=" + URLEncoder.encode(c[i], "UTF-8");
                        sh.makeServiceCall(url);
                    }

                    for(int i=0;i<t.length;i++){
                        url = AppConfig.URL + "tt.php?id=" + id + "&tag=" + URLEncoder.encode(t[i], "UTF-8");
                        sh.makeServiceCall(url);
                    }


                }catch (final JSONException e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showMessage("Json parsing error: " + e.getMessage());
                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showMessage("Couldn't get json from server. Check LogCat for possible errors!");
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (pd.isShowing()) {
                pd.hide();
            }

            Toast.makeText(CreateTopic.this, "Topic Created", Toast.LENGTH_SHORT).show();

            session.setTopicID(id);
            session.setTopicName(n);

            Intent intent = new Intent(CreateTopic.this,TopicView.class);
            startActivity(intent);
            finish();

        }

    }
}
