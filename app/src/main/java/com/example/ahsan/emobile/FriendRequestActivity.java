package com.example.ahsan.emobile;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.ahsan.emobile.Adapter.FRAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FriendRequestActivity extends AppCompatActivity{

   SessionManager session;
    ArrayList<String> map;
    ListView lv;
    ProgressDialog pd;
    SwipeRefreshLayout srl;
    boolean refresh = false;
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_request);
        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Friend Request");

        pd = new ProgressDialog(this);
        lv  = (ListView) findViewById(R.id.requests);
        session = new SessionManager(getApplicationContext());
        srl = (SwipeRefreshLayout) findViewById(R.id.refresh);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                map = new ArrayList<>();
                refresh = true;
                mytask task = new mytask();
                task.execute();
            }
        });


        map = new ArrayList<>();
        mytask task = new mytask();
        task.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class mytask extends AsyncTask<String, String , String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd.setMessage("Loading");
            pd.setCancelable(false);
            pd.show();

        }

        @Override
        protected String doInBackground(String... params) {
            HttpHandler sh = new HttpHandler();
            String url = AppConfig.URL + "friendrequests.php?id=" +  session.getUserID();
            String response = sh.makeServiceCall(url);

            if(response != null){
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray array = jsonObject.getJSONArray("requests");

                    for(int i=0;i<array.length();i++){
                        JSONObject js = array.getJSONObject(i);

                        String str = js.getString("id");
                        str += ":" + js.getString("username");
                        str += ":" + js.getString("sid");
                        str += ":" + js.getString("pic");

                        map.add(str);
                    }

                } catch (JSONException e){

                }
            } else {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(pd.isShowing()){
                pd.hide();
            }


            if(refresh){
                refresh = false;
                srl.setRefreshing(false);
            }

            ListAdapter la = new FRAdapter(FriendRequestActivity.this, R.layout.fr , map, false);
            lv.setAdapter(la);

        }
    }
}
