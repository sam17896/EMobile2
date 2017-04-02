package com.example.ahsan.emobile.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahsan.emobile.AppConfig;
import com.example.ahsan.emobile.HttpHandler;
import com.example.ahsan.emobile.ProfileView;
import com.example.ahsan.emobile.R;
import com.example.ahsan.emobile.SessionManager;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class RequestAdapter extends ArrayAdapter implements View.OnClickListener{

    private static LayoutInflater inflater = null;
    ImageButton b , r;
    SessionManager session;
    ImageView iv;
    boolean s;
    LruCache<String, Bitmap> cache;
    private Activity activity;
    private ArrayList<String> names;
    public RequestAdapter (Activity activity, int resource, ArrayList<String> names, boolean s) {
        super(activity, resource,  names);

        this.activity = activity;
        this.names = names;
        this.s = s;
        session = new SessionManager(getContext().getApplicationContext());
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int maxmemory = (int) Runtime.getRuntime().maxMemory();
        int cahcesize = maxmemory / 8;
        cache = new LruCache<>(cahcesize);
    }

    public int getCount() {
        return names.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(convertView == null)
            vi = inflater.inflate(R.layout.request, null);

        TextView name = (TextView) vi.findViewById(R.id.name);
        b = (ImageButton) vi.findViewById(R.id.add);
        r = (ImageButton) vi.findViewById(R.id.reject);
        iv = (ImageView) vi.findViewById(R.id.imageView2);

        String[] words = names.get(position).split(":");

        name.setText(words[0]);
        name.setTag(words[1]);
        name.setOnClickListener(this);

        if(!s){
            b.setVisibility(View.GONE);
            r.setVisibility(View.GONE);
        }else {
            b.setTag(words[1]+":"+position);
            b.setOnClickListener(this);
            r.setTag(words[1]+":"+position);
            r.setOnClickListener(this);
        }

        if (cache.get(words[1]) != null) {
            iv.setImageBitmap(cache.get(words[1]));
        } else {
            DownloadImageTask dn = new DownloadImageTask(iv, words[1]);
            dn.execute(AppConfig.IMAGESURL + words[2]);
        }

        return vi;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.add:
                b = (ImageButton) v;
                myTask task = new myTask();
                task.execute(v.getTag().toString());
                break;

            case R.id.reject:
                b = (ImageButton) v;

                myTask1 task1 = new myTask1();
                task1.execute(v.getTag().toString());
                break;

            case R.id.name:
                session.setProfile(v.getTag().toString());

                Intent i = new Intent(getContext(), ProfileView.class);
                getContext().startActivity(i);
                break;
        }
    }
    private class myTask extends AsyncTask<String, String , String> {

        String id;
        int position;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            names.remove(position);
            notifyDataSetChanged();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpHandler sh = new HttpHandler();

            String[] para = params[0].split(":");
            id = para[0];
            position = Integer.parseInt(para[1]);

            String url = AppConfig.URL + "add_user.php?id=" + session.getTopicID() + "&userid=" + id;
            sh.makeServiceCall(url);

            return null;
        }

    }
    private class myTask1 extends AsyncTask<String, String , String> {

        String id;
        int position;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            names.remove(position);
            notifyDataSetChanged();

        }

        @Override
        protected String doInBackground(String... params) {

            HttpHandler sh = new HttpHandler();
            String[] para = params[0].split(":");
            id = para[0];
            position = Integer.parseInt(para[1]);

            String url = AppConfig.URL + "remove.php?id=" + session.getTopicID() + "&userid=" + id;
            sh.makeServiceCall(url);

            return null;
        }

    }

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        String id;

        public DownloadImageTask(ImageView imageView, String id) {
            imageViewReference = new WeakReference<>(imageView);
            this.id = id;
        }

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

        protected void onPostExecute(Bitmap bitmap) {

            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null) {
                ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                        cache.put(id, bitmap);
                    } else {
                        //  Drawable placeholder = imageView.getContext().getResources().getDrawable(R.drawable.placeholder);
                        // imageView.setImageDrawable(placeholder);
                    }
                }
            }
        }
    }


}

