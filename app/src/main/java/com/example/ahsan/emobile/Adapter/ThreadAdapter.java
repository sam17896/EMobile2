package com.example.ahsan.emobile.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahsan.emobile.AppConfig;
import com.example.ahsan.emobile.R;
import com.example.ahsan.emobile.Thread;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ThreadAdapter extends RecyclerView.Adapter<ThreadAdapter.MyViewHolder> {

    private Context mContext;
    private List<String> threads;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, last_message;
        public ImageView iv;
        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            last_message = (TextView) view.findViewById(R.id.last_message);
            iv = (ImageView) view.findViewById(R.id.imageView20);

        }
    }


    public ThreadAdapter(Context mContext, List<String> Threads) {
        this.mContext = mContext;
        this.threads = Threads;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_thread, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        String th = threads.get(position);

        String[] words = th.split(":");

        holder.name.setText(words[1]);
        holder.name.setTag(words[0]);

        holder.last_message.setText(words[4]);
        holder.last_message.setTag(words[2]);

        int count = Integer.parseInt(words[3]);

        if( count > 0){
            holder.name.setText(holder.name.getText() + "(" +count+")");
            holder.name.setTypeface(holder.name.getTypeface(), Typeface.BOLD_ITALIC);
        }


        DownloadImageTask dn = new DownloadImageTask(holder.iv);
        dn.execute(AppConfig.IMAGESURL + words[5]);


    }

    @Override
    public int getItemCount() {
        return threads.size();
    }

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;

        public DownloadImageTask(ImageView imageView) {
            imageViewReference = new WeakReference<>(imageView);
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
                    } else {
                        //  Drawable placeholder = imageView.getContext().getResources().getDrawable(R.drawable.placeholder);
                        // imageView.setImageDrawable(placeholder);
                    }
                }
            }
        }
    }


}

