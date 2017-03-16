package com.example.ahsan.emobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NavigationDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
        ,View.OnClickListener, AdapterView.OnItemClickListener{

    ArrayList<Topic> topicList;
    ArrayList<String> friends;
    ArrayList<String> groups;
    RecyclerView TopicList;
    DrawerLayout drawer;
    TextView username;
    NavigationView navigationView;
    ProgressDialog pDialog;
    SessionManager session;
    Menu menu;
    private static LayoutInflater inflater=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        TopicList = (RecyclerView) findViewById(R.id.topiclist);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        TopicList.setLayoutManager(mLayoutManager);
        TopicList.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        TopicList.setItemAnimator(new DefaultItemAnimator());
        TopicList.addOnItemTouchListener(
                new RecyclerItemClickListener(this, TopicList ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        if(view==null)
                            view = inflater.inflate(R.layout.listview, null);

                        session.setTopicID((String)view.findViewById(R.id.id).getTag());
                        TextView tt = (TextView) view.findViewById(R.id.title);
                        TextView td = (TextView) view.findViewById(R.id.description);
                        TextView ta = (TextView)  view.findViewById(R.id.admin);


                        session.setTopicName(tt.getText().toString());
                        session.setTopicDescription(td.getText().toString());
                        session.setTopicAdmin(ta.getText().toString());

                        Intent i = new Intent(getApplicationContext(), TopicView.class);
                        startActivity(i);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle (this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        username = (TextView) header.findViewById(R.id.username);

        session = new SessionManager(getApplicationContext());
        username.setText(session.getUsername());

        menu = navigationView.getMenu();

        MyTask task = new MyTask();
        topicList = new ArrayList<>();
        task.execute("topic");

        MyTask1 task1 = new MyTask1();
        friends = new ArrayList<>();
        task1.execute("friend");

        MyTask2 task2 = new MyTask2();
        groups = new ArrayList<>();
        task2.execute("group");



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {

            session.setLogin(false);
            session.setUserId("");
            session.setTopicID("");
            session.setTopicAdmin("");
            session.setTopicDescription("");
            session.setTopicName("");
            session.setProfile("");

            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(view==null)
            view = inflater.inflate(R.layout.listview, null);

        session.setTopicID((String)view.findViewById(R.id.id).getTag());
/*        TextView tt = (TextView) view.findViewById(R.id.title);
        TextView td = (TextView) view.findViewById(R.id.description);
        TextView ta = (TextView)  view.findViewById(R.id.admin);


        session.setTopicName(tt.getText().toString());
        session.setTopicDescription(td.getText().toString());
        session.setTopicAdmin(ta.getText().toString());
*/
        Intent i = new Intent(this, TopicView.class);
        startActivity(i);

    }

    private class MyTask extends AsyncTask<String,String,String> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(NavigationDrawer.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... s) {

        HttpHandler sh = new HttpHandler();
        String url = AppConfig.URL +"loadtopic.php?id=" + session.getUserID();
        LoadTopic(url, sh);

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (pDialog.isShowing())
            pDialog.dismiss();
        /**
         * Updating parsed JSON data into ListView
         * */
        TopicAdapter adapter = new TopicAdapter(getApplicationContext() , topicList);
        TopicList.setAdapter(adapter);
    }

    @Override
    protected void onProgressUpdate(String... values) {
    }
}
    private class MyTask1 extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... s) {

            HttpHandler sh = new HttpHandler();

            String url = AppConfig.URL +"userfriends.php?id=" + session.getUserID();
            LoadFriend(url, sh);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            int k=0;
            for(String n : friends){
                String words[] = n.split(":");
                menu.findItem(R.id.friends).getSubMenu().add(R.id.friends, Integer.parseInt(words[1]),k,words[0]);
                MenuItem myMenuItem = menu.findItem(R.id.friends).getSubMenu().findItem(Integer.parseInt(words[1]));
                try {
                    myMenuItem.setIcon(drawableFromUrl(AppConfig.IMAGESURL + words[2]));
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(NavigationDrawer.this, "Image not loaded", Toast.LENGTH_SHORT).show();
                }
                myMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        session.setProfile(""+id);

                        Intent i = new Intent(NavigationDrawer.this, ProfileView.class);
                        startActivity(i);
                        return false;
                    }
                });
                k++;
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
        }
    }

    private class MyTask2 extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... s) {

            HttpHandler sh = new HttpHandler();
            String url = AppConfig.URL +"usertopic.php?id=" + session.getUserID();
            LoadGroup(url, sh);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            int k=0;
            for(String n : groups){
                String words[] = n.split(":");
                menu.findItem(R.id.groups).getSubMenu().add(R.id.groups, Integer.parseInt(words[1]),k,words[0]);
                MenuItem myMenuItem = menu.findItem(R.id.groups).getSubMenu().findItem(Integer.parseInt(words[1]));
                try {
                    myMenuItem.setIcon(drawableFromUrl(AppConfig.IMAGESURL + words[2]));
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(NavigationDrawer.this, "Image not loaded", Toast.LENGTH_SHORT).show();
                }
                myMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();

                        // LOAD ACTIVITY TOPIC


                        return false;
                    }
                });
                k++;
            }

        }

        @Override
        protected void onProgressUpdate(String... values) {
        }
    }

    private void showMessage(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    private void LoadFriend(String url, HttpHandler sh){
        String jsonStr = sh.makeServiceCall(url);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                JSONArray friend = jsonObj.getJSONArray("friend");

                for (int i = 0; i < friend.length(); i++) {
                    JSONObject c = friend.getJSONObject(i);
                    friends.add(c.getString("name")+":" + c.getString("userid")+":"+c.getString("pic"));
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

    }

    private void LoadGroup(String url, HttpHandler sh){
        final String jsonStr = sh.makeServiceCall(url);
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                JSONArray group = jsonObj.getJSONArray("group");

                for (int i = 0; i < group.length(); i++) {
                    JSONObject c = group.getJSONObject(i);
                    groups.add(c.getString("name")+":"+c.getString("id")+":"+c.getString("img"));
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

    }

    private void LoadTopic(String url, HttpHandler sh){

        final String jsonStr = sh.makeServiceCall(url);
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                JSONArray topic = jsonObj.getJSONArray("topic");

                for (int i = 0; i < topic.length(); i++) {
                    JSONObject c = topic.getJSONObject(i);

                    String id = c.getString("id");
                    String name = c.getString("name");
                    String admin = c.getString("admin");
                    String description = c.getString("description");

                    Topic topic1 = new Topic(name, description , admin , id);

                    topicList.add(topic1);

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
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    public Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(x);
    }
}

