package com.example.ahsan.emobile.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.ahsan.emobile.Adapter.ChatTopicAdapter;
import com.example.ahsan.emobile.AppConfig;
import com.example.ahsan.emobile.HttpHandler;
import com.example.ahsan.emobile.Message;
import com.example.ahsan.emobile.R;
import com.example.ahsan.emobile.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class ChatFragment extends Fragment {

    String selfUserId;
    private String TAG = ChatFragment.class.getSimpleName();
    private String chatRoomId;
    private RecyclerView recyclerView;
    private ChatTopicAdapter mAdapter;
    private ArrayList<Message> messageArrayList;
    private EditText inputMessage;
    private ImageButton btnSend;
    private SessionManager session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);

        inputMessage = (EditText) rootView.findViewById(R.id.message);
        btnSend = (ImageButton) rootView.findViewById(R.id.btn_send);

        session = new SessionManager(getContext().getApplicationContext());
        setHasOptionsMenu(true);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        messageArrayList = new ArrayList<>();

        // self user id is to identify the message owner
        selfUserId = session.getUserID();



        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext().getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        loadMessage lm = new loadMessage();
        lm.execute();

        return rootView;
    }

    private void sendMessage() {
        String message = inputMessage.getText().toString().trim();

        if(!message.equals("")){
            sendMessage sm = new sendMessage(message);
            sm.execute();
        }

        inputMessage.setText("");


    }


    public class sendMessage extends AsyncTask<String,String,String>{

        String message;

        public sendMessage(String message){
            this.message = message;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = null;
            try {
                url = AppConfig.URL + "sendmessage.php?tid=" + session.getTopicID() + "&id=" + session.getUserID() + "&message=" + URLEncoder.encode(message, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            HttpHandler sh = new HttpHandler();
            sh.makeServiceCall(url);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }

    }

    public class loadMessage extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = AppConfig.URL + "loadmessage.php?id=" + session.getTopicID();
            HttpHandler sh = new HttpHandler();
            String response = sh.makeServiceCall(url);

            if(response!=null){
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray message = jsonObject.getJSONArray("messages");

                    for(int i=0;i<message.length();i++){
                        JSONObject jso = message.getJSONObject(i);

                        Message msg = new Message();

                        msg.setText(jso.getString("text"));
                        msg.setTime(jso.getString("time"));
                        msg.setUsername(jso.getString("username"));
                        msg.setUserid(jso.getString("userid"));
                        msg.setUserpic(jso.getString("pic"));

                        messageArrayList.add(msg);
                    }



                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            mAdapter = new ChatTopicAdapter(getContext().getApplicationContext(), messageArrayList, selfUserId);
            recyclerView.setAdapter(mAdapter);
            recyclerView.scrollToPosition(messageArrayList.size() -1);

            updatemessage  up = new updatemessage();
            up.execute();


        }
    }

    public class updatemessage extends AsyncTask<String,String,String>{
        boolean newmsg = false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = AppConfig.URL + "updatetopicmessage.php?id=" + session.getTopicID();
            HttpHandler sh = new HttpHandler();

            String response = sh.makeServiceCall(url);

            if(response!=null){
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray message = jsonObject.getJSONArray("messages");
                    //         messageArrayList.clear();


                    for(int i=0;i<message.length();i++){
                        JSONObject jso = message.getJSONObject(i);
                        newmsg = true;

                        Message msg = new Message();

                        msg.setText(jso.getString("text"));
                        msg.setTime(jso.getString("time"));
                        msg.setUsername(jso.getString("username"));
                        msg.setUserid(jso.getString("userid"));
                        msg.setUserpic(jso.getString("pic"));

                        messageArrayList.add(msg);
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(newmsg) {
                mAdapter.notifyDataSetChanged();
                newmsg = false;
                recyclerView.scrollToPosition(messageArrayList.size() - 1);
            }
            //   Toast.makeText(MessageActivity.this, "Hogya", Toast.LENGTH_SHORT).show();
            updatemessage  up = new updatemessage();
            up.execute();

        }
    }
}