package com.example.ahsan.emobile.Adapter;

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
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahsan.emobile.AppConfig;
import com.example.ahsan.emobile.Child_Row;
import com.example.ahsan.emobile.Parent_Row;
import com.example.ahsan.emobile.ProfileView;
import com.example.ahsan.emobile.R;
import com.example.ahsan.emobile.SessionManager;
import com.example.ahsan.emobile.TopicView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by AHSAN on 4/2/2017.
 */

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<Parent_Row> parent_rowArrayList;
    private ArrayList<Parent_Row> originalList;
    private SessionManager session;
    private LruCache<String, Bitmap> cache;


    public MyExpandableListAdapter(Context context, ArrayList<Parent_Row> originalList) {
        this.context = context;
        this.parent_rowArrayList = new ArrayList<>();
        this.parent_rowArrayList.addAll(originalList);
        this.originalList = new ArrayList<>();
        this.originalList.addAll(originalList);
        session = new SessionManager(context);
        final int maxMemory = (int) Runtime.getRuntime().maxMemory() / 1024;
        final int cacheSzie = maxMemory / 8;
        cache = new LruCache<>(cacheSzie);

    }

    @Override
    public int getGroupCount() {
        return parent_rowArrayList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return parent_rowArrayList.get(groupPosition).getChilds().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return parent_rowArrayList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return parent_rowArrayList.get(groupPosition).getChilds().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        Parent_Row parent_row = (Parent_Row) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.parent_row, null);

        }

        TextView heading = (TextView) convertView.findViewById(R.id.parent_text);

        heading.setText(parent_row.getName().trim());
        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Child_Row child_row = (Child_Row) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.child_row, null);

        }
        ImageView childIcon = (ImageView) convertView.findViewById(R.id.child_icon);
        if (cache.get(child_row.getId()) != null) {
            childIcon.setImageBitmap(cache.get(child_row.getId()));
        } else {
            DownloadImageTask dn = new DownloadImageTask(child_row, childIcon);
            dn.execute(AppConfig.IMAGESURL + child_row.getPic());
        }
        final TextView childText = (TextView) convertView.findViewById(R.id.child_text);
        childText.setText(child_row.getText().trim());

        childText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (child_row.isUser()) {
                    session.setProfile(child_row.getId().toString());
                    session.setProfileName(((TextView) v).getText().toString());
                    Intent i = new Intent(context, ProfileView.class);
                    context.startActivity(i);
                } else {
                    session.setTopicID(child_row.getId());
                    session.setTopicName(((TextView) v).getText().toString());
                    Intent i = new Intent(context, TopicView.class);
                    context.startActivity(i);
                }
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void filterData(String query) {
        query = query.toLowerCase();
        parent_rowArrayList.clear();

        if (query.isEmpty()) {
            parent_rowArrayList.addAll(originalList);
        } else {
            for (Parent_Row parent_row : originalList) {
                ArrayList<Child_Row> childList = parent_row.getChilds();
                ArrayList<Child_Row> newList = new ArrayList<>();

                for (Child_Row child_row : childList) {
                    if (child_row.getText().toLowerCase().contains(query)) {
                        newList.add(child_row);
                    }
                }
                if (newList.size() > 0) {
                    Parent_Row nparent_row = new Parent_Row(parent_row.getName(), newList);
                    parent_rowArrayList.add(nparent_row);
                }
            }
        }

        notifyDataSetChanged();

    }

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private Child_Row child_row;
        private WeakReference<ImageView> image;

        public DownloadImageTask(Child_Row child_row, ImageView img) {
            this.child_row = child_row;
            image = new WeakReference<ImageView>(img);
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                BitmapFactory.Options options = new BitmapFactory.Options();
                mIcon11 = BitmapFactory.decodeStream(in, null, options);
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
                        child_row.setIcon(bitmap);
                        cache.put(child_row.getId(), bitmap);
                    } else {

                    }
                }
            }
        }
    }
}
