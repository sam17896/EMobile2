package com.example.ahsan.emobile.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahsan.emobile.AppConfig;
import com.example.ahsan.emobile.HttpHandler;
import com.example.ahsan.emobile.R;
import com.example.ahsan.emobile.SessionManager;
import com.example.ahsan.emobile.Topic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class DetailFragment extends Fragment {

    SessionManager session;
    TextView title,description,admin;
    ImageView icon;
    String name,ad,desc,pic;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vi = inflater.inflate(R.layout.fragment_detail, container, false);

        session = new SessionManager(getContext().getApplicationContext());

        title = (TextView) vi.findViewById(R.id.topicName);
        description = (TextView) vi.findViewById(R.id.description);
        admin = (TextView) vi.findViewById(R.id.adminName);
        icon = (ImageView) vi.findViewById(R.id.topic_icon);

        myTask myTask = new myTask();
        myTask.execute("");

        return vi;
    }
public class myTask extends AsyncTask<String,String,String>{
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
        description.setText(desc);
        admin.setText(ad);
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
}