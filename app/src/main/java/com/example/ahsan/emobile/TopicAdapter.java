package com.example.ahsan.emobile;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.MyViewHolder> {

    private Context mContext;
    private List<Topic> topicList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description, id , admin;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            id = (TextView) view.findViewById(R.id.id);
            admin = (TextView) view.findViewById(R.id.admin);

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

    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }
}

