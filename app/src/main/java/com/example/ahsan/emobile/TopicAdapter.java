package com.example.ahsan.emobile;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class TopicAdapter extends ArrayAdapter{

    private Activity activity;
    private ArrayList<Topic> topics;
    private static LayoutInflater inflater=null;

    public TopicAdapter(Activity activity, int resource, ArrayList<Topic> objects) {
        super(activity, resource, objects);

        this.activity = activity;
        this.topics = objects;

        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return topics.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.listview, null);

        TextView title = (TextView) vi.findViewById(R.id.title);
        TextView description = (TextView) vi.findViewById(R.id.description);
        TextView id = (TextView) vi.findViewById(R.id.id);


        Topic topic;

        topic = topics.get(position);

        title.setText(topic.getTitle());
        description.setText(topic.getDescription());
        id.setTag(topic.getId());

        return vi;
    }
}

