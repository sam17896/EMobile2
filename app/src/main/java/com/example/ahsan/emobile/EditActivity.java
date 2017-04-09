package com.example.ahsan.emobile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.ahsan.emobile.Adapter.ContactAdapter;
import com.example.ahsan.emobile.Adapter.EducationAdapter;
import com.example.ahsan.emobile.Adapter.InterestAdapter;
import com.example.ahsan.emobile.Adapter.LinkAdapter;
import com.example.ahsan.emobile.Adapter.SkillAdapter;
import com.example.ahsan.emobile.Adapter.WorkAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;


public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView iv;
    ImageButton ib;
    EditText firstname, lastname, dob, gender, country, ed_link, ed_contact, ed_interest, ed_skill, ed_education, ed_ef, ed_et, ed_work, ed_wf, ed_wt;
    Button save, ss, cs, is, sks, es, ws;
    Spinner linktype, contactype;
    ListView link, skill, work, education, contact, interest;
    RelativeLayout rl;

    SessionManager session;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        session = new SessionManager(getApplicationContext());
        configure();
        prepare();
        populate();


    }

    private void populate() {
        // Decalare
        Education ed = new Education();
        Detail dt = new Detail();
        Interest it = new Interest();
        Skill sk = new Skill();
        SocialLink sl = new SocialLink();
        Contacts c = new Contacts();
        Work w = new Work();

        // Load data
        ed.execute();
        dt.execute();
        it.execute();
        sk.execute();
        sl.execute();
        c.execute();
        w.execute();

    }

    private void prepare() {
        rl = (RelativeLayout) findViewById(R.id.relativeLayout);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.linktype, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        linktype.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.contacttype, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        contactype.setAdapter(adapter1);

        save.setOnClickListener(this);
        ss.setOnClickListener(this);
        cs.setOnClickListener(this);
        is.setOnClickListener(this);
        sks.setOnClickListener(this);
        es.setOnClickListener(this);
        ws.setOnClickListener(this);

    }

    private void configure() {
        iv = (ImageView) findViewById(R.id.imageView);
        ib = (ImageButton) findViewById(R.id.imageButton);

        //Edit Texts
        firstname = (EditText) findViewById(R.id.ed_fn);
        lastname = (EditText) findViewById(R.id.en_ln);
        dob = (EditText) findViewById(R.id.en_dob);
        gender = (EditText) findViewById(R.id.en_g);
        country = (EditText) findViewById(R.id.en_c);
        ed_link = (EditText) findViewById(R.id.ll);
        ed_contact = (EditText) findViewById(R.id.cc);
        ed_interest = (EditText) findViewById(R.id.li);
        ed_skill = (EditText) findViewById(R.id.sl);
        ed_education = (EditText) findViewById(R.id.einst);
        ed_ef = (EditText) findViewById(R.id.ef);
        ed_et = (EditText) findViewById(R.id.et);
        ed_work = (EditText) findViewById(R.id.winst);
        ed_wf = (EditText) findViewById(R.id.wf);
        ed_wt = (EditText) findViewById(R.id.wt);


        // ListView

        link = (ListView) findViewById(R.id.l);
        skill = (ListView) findViewById(R.id.s);
        work = (ListView) findViewById(R.id.w);
        education = (ListView) findViewById(R.id.e);
        contact = (ListView) findViewById(R.id.c);
        interest = (ListView) findViewById(R.id.i);

        //Spinner
        linktype = (Spinner) findViewById(R.id.ltype);
        contactype = (Spinner) findViewById(R.id.ctype);

        // Buttons
        save = (Button) findViewById(R.id.save);
        ss = (Button) findViewById(R.id.ls);
        cs = (Button) findViewById(R.id.cs);
        is = (Button) findViewById(R.id.is);
        sks = (Button) findViewById(R.id.ss);
        es = (Button) findViewById(R.id.es);
        ws = (Button) findViewById(R.id.ws);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            // SAVE
            case R.id.save:
                Snackbar snackbar = Snackbar
                        .make(rl, "To be implemented", Snackbar.LENGTH_LONG);

                snackbar.show();
                break;
            //Link Save
            case R.id.ls:
                snackbar = Snackbar
                        .make(rl, "To be implemented", Snackbar.LENGTH_LONG);

                snackbar.show();

                break;
            //Contact Save
            case R.id.cs:
                snackbar = Snackbar
                        .make(rl, "To be implemented", Snackbar.LENGTH_LONG);

                snackbar.show();
                break;
            //Interest Save
            case R.id.is:
                snackbar = Snackbar
                        .make(rl, "To be implemented", Snackbar.LENGTH_LONG);

                snackbar.show();

                break;
            //Skill save
            case R.id.ss:
                snackbar = Snackbar
                        .make(rl, "To be implemented", Snackbar.LENGTH_LONG);

                snackbar.show();

                break;
            //Education Save;
            case R.id.es:
                snackbar = Snackbar
                        .make(rl, "To be implemented", Snackbar.LENGTH_LONG);

                snackbar.show();

                break;
            //Work Save
            case R.id.ws:
                snackbar = Snackbar
                        .make(rl, "To be implemented", Snackbar.LENGTH_LONG);

                snackbar.show();
                break;
        }

    }


    // Education
    private class Education extends AsyncTask<String, String, String> {

        ArrayList<String> map;

        @Override
        protected void onPreExecute() {
            map = new ArrayList<>();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... s) {

            HttpHandler sh = new HttpHandler();

            String url = AppConfig.URL + "loadeducation.php?id=" + session.getProfile();
            LoadEducation(url, sh);
            return null;
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);

            ListAdapter adapter = new EducationAdapter(EditActivity.this, R.layout.education, map, false);
            education.setAdapter(adapter);


        }

        @Override
        protected void onProgressUpdate(String... values) {
        }

        public void LoadEducation(String url, HttpHandler sh) {
            String jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray member = jsonObj.getJSONArray("education");

                    for (int i = 0; i < member.length(); i++) {
                        JSONObject c = member.getJSONObject(i);
                        String str = c.getString("in") + ":" + c.getString("yearf") + ":" + c.getString("yeart");
                        map.add(str);
                    }
                } catch (final JSONException e) {

                }
            } else {

            }
        }
    }


    //Interest
    private class Interest extends AsyncTask<String, String, String> {
        ArrayList<String> map;

        @Override
        protected void onPreExecute() {
            map = new ArrayList<>();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... s) {

            HttpHandler sh = new HttpHandler();

            String url = AppConfig.URL + "loadinterest.php?id=" + session.getProfile();
            LoadInterest(url, sh);
            return null;
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);

            ListAdapter adapter = new InterestAdapter(EditActivity.this, R.layout.interest, map, false);
            interest.setAdapter(adapter);


        }

        @Override
        protected void onProgressUpdate(String... values) {
        }

        public void LoadInterest(String url, HttpHandler sh) {
            String jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray member = jsonObj.getJSONArray("interest");

                    for (int i = 0; i < member.length(); i++) {
                        JSONObject c = member.getJSONObject(i);
                        String str = c.getString("in");
                        map.add(str);
                    }
                } catch (final JSONException e) {

                }
            } else {

            }
        }
    }

    //Detail
    private class Detail extends AsyncTask<String, String, String> {
        ArrayList<String> map;
        String fn, c, db, ln, g, pic;

        @Override
        protected void onPreExecute() {
            map = new ArrayList<>();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... s) {

            HttpHandler sh = new HttpHandler();

            String url = AppConfig.URL + "detail.php?id=" + session.getProfile();
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

        public void LoadDetail(String url, HttpHandler sh) {
            String jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);


                    JSONArray member = jsonObj.getJSONArray("detail");
                    JSONObject d = member.getJSONObject(0);
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
            iv.setImageBitmap(result);
        }
    }

    // Skill
    private class Skill extends AsyncTask<String, String, String> {
        ArrayList<String> map;

        @Override
        protected void onPreExecute() {
            map = new ArrayList<>();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... s) {

            HttpHandler sh = new HttpHandler();

            String url = AppConfig.URL + "loadskill.php?id=" + session.getProfile();
            LoadSkills(url, sh);
            return null;
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);

            ListAdapter adapter = new SkillAdapter(EditActivity.this, R.layout.skills, map, false);
            skill.setAdapter(adapter);

        }

        @Override
        protected void onProgressUpdate(String... values) {
        }

        public void LoadSkills(String url, HttpHandler sh) {
            String jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray member = jsonObj.getJSONArray("skills");

                    for (int i = 0; i < member.length(); i++) {
                        JSONObject c = member.getJSONObject(i);
                        String str = c.getString("skill");
                        map.add(str);
                    }
                } catch (final JSONException e) {

                }
            } else {

            }
        }
    }

    //Social Link

    private class SocialLink extends AsyncTask<String, String, String> {
        ArrayList<String> map;

        @Override
        protected void onPreExecute() {
            map = new ArrayList<>();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... s) {

            HttpHandler sh = new HttpHandler();

            String url = AppConfig.URL + "loadlinks.php?id=" + session.getProfile();
            LoadLink(url, sh);
            return null;
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);

            ListAdapter adapter = new LinkAdapter(EditActivity.this, R.layout.link, map, false);
            link.setAdapter(adapter);


        }

        @Override
        protected void onProgressUpdate(String... values) {
        }

        public void LoadLink(String url, HttpHandler sh) {
            String jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray member = jsonObj.getJSONArray("links");

                    for (int i = 0; i < member.length(); i++) {
                        JSONObject c = member.getJSONObject(i);
                        String str = c.getString("name") + ":" + c.getString("link");
                        map.add(str);
                    }
                } catch (final JSONException e) {

                }
            } else {

            }
        }
    }


    // WORK
    private class Work extends AsyncTask<String, String, String> {
        ArrayList<String> map;

        @Override
        protected void onPreExecute() {
            map = new ArrayList<>();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... s) {

            HttpHandler sh = new HttpHandler();

            String url = AppConfig.URL + "loadwork.php?id=" + session.getProfile();
            LoadWork(url, sh);
            return null;
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);
            ListAdapter adapter = new WorkAdapter(EditActivity.this, R.layout.work, map, false);
            work.setAdapter(adapter);


        }

        @Override
        protected void onProgressUpdate(String... values) {
        }

        public void LoadWork(String url, HttpHandler sh) {
            String jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);


                    JSONArray member = jsonObj.getJSONArray("work");

                    for (int i = 0; i < member.length(); i++) {
                        JSONObject c = member.getJSONObject(i);
                        String str = c.getString("in") + ":" + c.getString("yearf") + ":" + c.getString("yeart");
                        map.add(str);
                    }
                } catch (final JSONException e) {

                }
            } else {

            }
        }
    }


    // CONTACTS 

    private class Contacts extends AsyncTask<String, String, String> {
        ArrayList<String> map;

        @Override
        protected void onPreExecute() {
            map = new ArrayList<>();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... s) {

            HttpHandler sh = new HttpHandler();

            String url = AppConfig.URL + "loadphone.php?id=" + session.getProfile();
            LoadContact(url, sh);
            return null;
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);

            ListAdapter adapter = new ContactAdapter(EditActivity.this, R.layout.contact, map, false);
            contact.setAdapter(adapter);


        }

        @Override
        protected void onProgressUpdate(String... values) {
        }

        public void LoadContact(String url, HttpHandler sh) {
            String jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray member = jsonObj.getJSONArray("phone");

                    for (int i = 0; i < member.length(); i++) {
                        JSONObject c = member.getJSONObject(i);
                        String str = c.getString("type") + ":" + c.getString("phone");
                        map.add(str);
                    }
                } catch (final JSONException e) {

                }
            } else {

            }
        }
    }

}
