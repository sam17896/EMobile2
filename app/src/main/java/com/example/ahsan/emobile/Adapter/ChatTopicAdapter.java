package com.example.ahsan.emobile.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahsan.emobile.AppConfig;
import com.example.ahsan.emobile.Message;
import com.example.ahsan.emobile.R;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;

public class ChatTopicAdapter extends RecyclerView.Adapter<ChatTopicAdapter.MyViewHolder> {

    private static String TAG = ChatTopicAdapter.class.getSimpleName();
    private static String today;
    private String userId;
    private int SELF = 99999999;
    private Context mContext;
    private ArrayList<Message> messageArrayList;

    private LruCache<String, Bitmap> cache;

    public ChatTopicAdapter(Context mContext, ArrayList<Message> messageArrayList, String userId) {
        this.mContext = mContext;
        this.messageArrayList = messageArrayList;
        this.userId = userId;

        Calendar calendar = Calendar.getInstance();
        today = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

        final int maxMemory = (int) Runtime.getRuntime().maxMemory() / 1024;
        final int cacheSzie = maxMemory / 8;
        cache = new LruCache<>(cacheSzie);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        if (viewType == SELF) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_self, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_other, parent, false);
        }


        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messageArrayList.get(position);
        if (message.getUserid().equals(userId)) {
            return SELF;
        }

        return position;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Message message = messageArrayList.get(position);
        holder.message.setText(message.getText());
        holder.timestamp.setText(message.getUsername());
        if (cache.get(message.getUserpic()) != null) {
            holder.icon.setImageBitmap(cache.get(message.getUserpic()));
        } else {
            DownloadImageTask downloadImageTask = new DownloadImageTask(message, holder.icon);
            downloadImageTask.execute(AppConfig.IMAGESURL + message.getUserpic());
        }

    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView message, timestamp;
        ImageView icon;

        public MyViewHolder(View view) {
            super(view);
            message = (TextView) itemView.findViewById(R.id.message);
            timestamp = (TextView) itemView.findViewById(R.id.timestamp);
            icon = (ImageView) itemView.findViewById(R.id.chat_icon);
        }
    }

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private Message message;
        private WeakReference<ImageView> image;

        public DownloadImageTask(Message msg, ImageView img) {
            message = msg;
            image = new WeakReference<ImageView>(img);
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                mIcon11 = BitmapFactory.decodeStream(in,null,options);
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

            if (image != null) {
                ImageView imageView = image.get();
                if (imageView != null) {
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                        message.setBitmap(bitmap);
                        cache.put(message.getUserpic(), bitmap);
                    } else {

                    }
                }
            }
        }
    }
}