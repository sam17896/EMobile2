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
import java.util.ArrayList;

public class PersonalInformationFragment extends Fragment implements View.OnClickListener {

    TextView firstname, lastname, gender, country, dob;
    ImageView imageView;
    String fn,ln,g,c,db,pic;
    ArrayList<String> map;
    boolean s;
    SessionManager session;
    String jsonStr;
    ProgressDialog pd;
    Button addbtn, rmvbtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_pi, container, false);

        session = new SessionManager(getContext().getApplicationContext());

        firstname = (TextView) rootView.findViewById(R.id.user_ofn);
        lastname = (TextView) rootView.findViewById(R.id.user_oln);
        gender = (TextView) rootView.findViewById(R.id.user_og);
        country = (TextView) rootView.findViewById(R.id.user_oc);
        dob = (TextView) rootView.findViewById(R.id.user_odob);

        addbtn = (Button) rootView.findViewById(R.id.addbtn);
        rmvbtn = (Button) rootView.findViewById(R.id.removebtn);

        addbtn.setOnClickListener(this);
        rmvbtn.setOnClickListener(this);


        pd = new ProgressDialog(getContext());
        imageView = (ImageView) rootView.findViewById(R.id.imageView);

        setHasOptionsMenu(true);

        MyTask task = new MyTask();
        task.execute();

        update();

        return rootView;
    }

    private void update() {
        loadstatus tastk = new loadstatus();
        tastk.execute();

    }

    @Override
    public void onClick(View v) {
        Button btn = (Button) v;

        String name = btn.getText().toString();

        switch (name) {
            case "Add":
                myTask2 add = new myTask2();
                add.execute();
                break;

            case "Ignore":
                myTask4 reject = new myTask4();
                reject.execute();

                break;

            case "Cancel":
                myTask5 cancel = new myTask5();
                cancel.execute();
                break;

            case "Approve":
                myTask3 approve = new myTask3();
                approve.execute();
                break;

            case "Remove":
                myTask1 remove = new myTask1();
                remove.execute();
                break;
        }
    }

    private class MyTask extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... s) {

            HttpHandler sh = new HttpHandler();

            String url = AppConfig.URL +"detail.php?id=" + session.getProfile();
            LoadDetail(url, sh);
            return null;
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);

            firstname.setText(fn);
            lastname.setText(ln);
            gender.setText(g);
            country.setText(c);
            dob.setText(db);

            DownloadImageTask dn = new DownloadImageTask();
            dn.execute(AppConfig.IMAGESURL + pic);

        }

        @Override
        protected void onProgressUpdate(String... values) {
        }

        public void LoadDetail(String url, HttpHandler sh){
            jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);


                    JSONArray member = jsonObj.getJSONArray("detail");
                    JSONObject d  = member.getJSONObject(0);
                    fn = d.getString("fn");
                    ln = d.getString("ln");
                    g = d.getString("g");
                    c = d.getString("c");
                    db = d.getString("dob");
                    pic = d.getString("img");



                } catch (final JSONException e) {

                }
            } else {

            }
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
            imageView.setImageBitmap(result);
        }
    }

    private class myTask1 extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            update();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpHandler sh = new HttpHandler();
            String url = AppConfig.URL + "remove_friend.php?id=" + session.getUserID() + "&fid=" + session.getProfile();
            sh.makeServiceCall(url);

            return null;
        }

    }

    private class myTask2 extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            update();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpHandler sh = new HttpHandler();
            String url = AppConfig.URL + "add_friend.php?sid=" + session.getUserID() + "&rid=" + session.getProfile();
            sh.makeServiceCall(url);

            return null;
        }

    }

    private class myTask3 extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            update();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpHandler sh = new HttpHandler();
            String url = AppConfig.URL + "accept_request.php?rid=" + session.getUserID() + "&sid=" + session.getProfile();
            sh.makeServiceCall(url);

            return null;
        }

    }

    private class myTask4 extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            update();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpHandler sh = new HttpHandler();
            String url = AppConfig.URL + "reject_request.php?rid=" + session.getUserID() + "&sid=" + session.getProfile();
            sh.makeServiceCall(url);

            return null;
        }

    }

    private class myTask5 extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            update();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpHandler sh = new HttpHandler();
            String url = AppConfig.URL + "reject_request.php?sid=" + session.getUserID() + "&rid=" + session.getProfile();
            sh.makeServiceCall(url);

            return null;
        }

    }

    private class loadstatus extends AsyncTask<String, String, String> {

        int status;
        boolean admin;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            switch (status) {
                case 0:
                    rmvbtn.setText("Remove");
                    rmvbtn.setEnabled(true);
                    addbtn.setEnabled(false);
                    rmvbtn.setVisibility(View.VISIBLE);
                    addbtn.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    addbtn.setText("Cancel");
                    rmvbtn.setEnabled(false);
                    addbtn.setEnabled(true);
                    rmvbtn.setVisibility(View.INVISIBLE);
                    addbtn.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    addbtn.setText("Approve");
                    rmvbtn.setText("Ignore");
                    rmvbtn.setEnabled(true);
                    addbtn.setEnabled(true);
                    rmvbtn.setVisibility(View.VISIBLE);
                    addbtn.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    addbtn.setText("Add");
                    rmvbtn.setVisibility(View.INVISIBLE);
                    rmvbtn.setEnabled(false);
                    addbtn.setEnabled(true);
                    addbtn.setVisibility(View.VISIBLE);
                    break;



            }
        }

        @Override
        protected String doInBackground(String... params) {

            HttpHandler sh = new HttpHandler();
            String url = AppConfig.URL + "friendstatus.php?id=" + session.getUserID() + "&fid=" + session.getProfile();
            String response = sh.makeServiceCall(url);

            if (response != null) {
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    status = jsonObj.getInt("status");

                } catch (final JSONException e) {

                }
            }

            return null;
        }

    }


}