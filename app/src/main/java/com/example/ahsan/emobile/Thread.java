package com.example.ahsan.emobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahsan.emobile.Adapter.ThreadAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Thread extends AppCompatActivity {

    private static LayoutInflater inflater=null;
    RecyclerView thread;
    SessionManager session;
    ProgressDialog pDialog;
    ArrayList<String> Threads;
    SwipeRefreshLayout tsr;
    boolean referesh  = false;
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Chats");

        session = new SessionManager(getBaseContext().getApplicationContext());
        thread = (RecyclerView) findViewById(R.id.threads);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        thread.setLayoutManager(mLayoutManager);
        thread.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        thread.setItemAnimator(new DefaultItemAnimator());
        thread.addOnItemTouchListener(
                new RecyclerItemClickListener(this, thread ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        if(view==null)
                            view = inflater.inflate(R.layout.layout_thread, null);
                        TextView name = (TextView) view.findViewById(R.id.name);
                        session.setProfile(name.getTag().toString());
                        session.setProfileName(name.getText().toString());

                        Intent k = new Intent(Thread.this, MessageActivity.class);
                        startActivity(k);

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        tsr = (SwipeRefreshLayout) findViewById(R.id.threadreferesh);
        Threads = new ArrayList<>();
        tsr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Threads = new ArrayList<>();
                MyTask task = new MyTask();
                task.execute();
                referesh = true;
            }


        });
        MyTask task = new MyTask();
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

    private void showMessage(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private class MyTask extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Thread.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... s) {

            HttpHandler sh = new HttpHandler();
            String url = AppConfig.URL + "thread.php?id=" + session.getUserID();

            String jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray friend = jsonObj.getJSONArray("thread");

                    for (int i = 0; i < friend.length(); i++) {
                        JSONObject c = friend.getJSONObject(i);
                        Threads.add(c.getString("friendid")+":" + c.getString("username")+":"+c.getString("id")+":"+c.getString("msg")+":"+c.getString("message")+":"+c.getString("pic"));
                    }
                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showMessage("Json parsing error: " + e.getMessage());
                        }
                    });
                }
            } else {
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
            if (pDialog.isShowing())
                pDialog.dismiss();

            if(referesh){
                referesh =false;
                tsr.setRefreshing(false);
            }

            ThreadAdapter adapter = new ThreadAdapter(Thread.this, Threads );
            thread.setAdapter(adapter);
        }
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }


    }
}
