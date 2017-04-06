package com.example.ahsan.emobile.Fragments;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahsan.emobile.AppConfig;
import com.example.ahsan.emobile.HttpHandler;
import com.example.ahsan.emobile.R;
import com.example.ahsan.emobile.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class DetailFragment extends Fragment implements View.OnClickListener {

    SessionManager session;
    TextView title,description,admin;
    ImageView icon;
    String name,ad,desc,pic;
    Button btn;
    ProgressDialog pd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vi = inflater.inflate(R.layout.fragment_detail, container, false);

        session = new SessionManager(getContext().getApplicationContext());

        title = (TextView) vi.findViewById(R.id.topicName);
        description = (TextView) vi.findViewById(R.id.description);
        admin = (TextView) vi.findViewById(R.id.adminName);
        icon = (ImageView) vi.findViewById(R.id.topic_icon);
        btn = (Button) vi.findViewById(R.id.btn);

        btn.setOnClickListener(this);

        setHasOptionsMenu(true);

        myTask myTask = new myTask();
        myTask.execute("");

        update();

        return vi;
    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        String n = b.getText().toString();

        switch (n) {
            case "Leave":
                left l = new left();
                l.execute();
                break;
            case "Join":
                request r = new request();
                r.execute();
                break;

            case "Cancel":
                cancel c = new cancel();
                c.execute();

                break;

        }

    }

    public void update() {
        load task = new load();
        task.execute();
    }


    public class myTask extends AsyncTask<String, String, String> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        HttpHandler sh = new HttpHandler();

        String url = AppConfig.URL + "loadtopicdetails.php?id=" + session.getTopicID();
        final String jsonStr = sh.makeServiceCall(url);
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                JSONArray topic = jsonObj.getJSONArray("detail");

                for (int i = 0; i < topic.length(); i++) {
                    JSONObject c = topic.getJSONObject(i);

                    name = c.getString("name");
                    ad = c.getString("admin");
                    desc = c.getString("desc");
                    pic = c.getString("pic");

                }
            } catch (final JSONException e) {

            }
        } else {

        }

        return null;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        title.setText(name);
        description.setText("Description:  " + desc);
        admin.setText("Created By:  "+ad);
        DownloadImageTask dn = new DownloadImageTask();
        dn.execute(AppConfig.IMAGESURL + pic);
    }
}
    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {

            icon.setImageBitmap(result);
        }
    }

    public class load extends AsyncTask<String, String, String> {

        int status;
        boolean admin;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String URL = AppConfig.URL + "groupstatus.php?id=" + session.getUserID() + "&tid=" + session.getTopicID();
            HttpHandler sh = new HttpHandler();
            String response = sh.makeServiceCall(URL);

            if (response != null) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray array = jsonObject.getJSONArray("status");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject js = array.getJSONObject(i);
                        status = js.getInt("status");
                        admin = js.getBoolean("admin");
                    }

                    Log.d("Group Status", "" + status + " " + admin);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("Group Status", "" + status);
            switch (status) {
                case 0:
                    btn.setText("Leave");
                    btn.setVisibility(View.VISIBLE);
                    Log.d("Group Status", "LEAVE");
                    break;
                case 2:
                    btn.setText("Join");
                    btn.setVisibility(View.VISIBLE);
                    Log.d("Group Status", "ADD");
                    break;
                case 1:
                    btn.setText("Cancel");
                    btn.setVisibility(View.VISIBLE);
                    Log.d("Group Status", "CANCEL");
                    break;

            }
        }
    }

    public class cancel extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = AppConfig.URL + "cancel.php?id=" + session.getTopicID() + "&uid=" + session.getUserID();
            HttpHandler sh = new HttpHandler();
            sh.makeServiceCall(url);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            update();
        }
    }

    public class left extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = AppConfig.URL + "left.php?id=" + session.getTopicID() + "&uid=" + session.getUserID();
            HttpHandler sh = new HttpHandler();
            sh.makeServiceCall(url);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            update();
        }
    }

    public class request extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = AppConfig.URL + "request.php?id=" + session.getTopicID() + "&uid=" + session.getUserID();
            HttpHandler sh = new HttpHandler();
            sh.makeServiceCall(url);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            update();
        }
    }

}