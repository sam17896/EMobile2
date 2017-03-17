package com.example.ahsan.emobile;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.MyViewHolder> {

    private Context mContext;
    private List<Topic> topicList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description, id , admin;
        public ImageView iv;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            id = (TextView) view.findViewById(R.id.id);
            admin = (TextView) view.findViewById(R.id.admin);
            iv = (ImageView) view.findViewById(R.id.topic_icon);

        }
    }


    public TopicAdapter(Context mContext, List<Topic> topicList) {
        this.mContext = mContext;
        this.topicList = topicList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Topic topic = topicList.get(position);
        holder.title.setText(topic.getTitle());
        holder.description.setText(topic.getDescription());
        holder.id.setTag(topic.getId());
        holder.admin.setText(topic.getAdminId());

        DownloadImageTask dn = new DownloadImageTask(holder.iv);
        dn.execute(AppConfig.IMAGESURL + topic.getImage());


    }

    @Override
    public int getItemCount() {
        return topicList.size();
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

