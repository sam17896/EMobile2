package com.example.ahsan.emobile.Adapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatTopicAdapter extends RecyclerView.Adapter<ChatTopicAdapter.MyViewHolder> {

    private static String TAG = ChatTopicAdapter.class.getSimpleName();

    private String userId;
    private int SELF = 99999999;
    private static String today;

    private Context mContext;
    private ArrayList<Message> messageArrayList;

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


    public ChatTopicAdapter(Context mContext, ArrayList<Message> messageArrayList, String userId) {
        this.mContext = mContext;
        this.messageArrayList = messageArrayList;
        this.userId = userId;

        Calendar calendar = Calendar.getInstance();
        today = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
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

        DownloadImageTask downloadImageTask = new DownloadImageTask(holder.icon);
        downloadImageTask.execute(AppConfig.IMAGESURL + message.getUserpic());

    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
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

            if (imageViewReference != null) {
                ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    } else {

                    }
                }
            }
        }
    }

}