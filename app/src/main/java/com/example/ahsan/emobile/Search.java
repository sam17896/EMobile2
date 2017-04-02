package com.example.ahsan.emobile;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.example.ahsan.emobile.Adapter.MyExpandableListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Search extends Activity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private SearchManager searchManager;
    private SearchView searchView;
    private MyExpandableListAdapter listAdapter;
    private ExpandableListView mList;
    private ArrayList<Parent_Row> parent_list = new ArrayList<>();
    private ArrayList<Parent_Row> showparent_list = new ArrayList<>();
    private MenuItem searchItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) findViewById(R.id.seacrhview);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnCloseListener(this);
        searchView.setOnQueryTextListener(this);
        searchView.requestFocus();
        mList = (ExpandableListView) findViewById(R.id.searchlist);

        parent_list = new ArrayList<>();
        showparent_list = new ArrayList<>();

        displayList();

    }

    private void displayList() {
        loadData();
    }

    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            mList.expandGroup(i);
        }
    }

    @Override
    public boolean onClose() {
        listAdapter.filterData("");
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        listAdapter.filterData(query);
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        listAdapter.filterData(newText);
        expandAll();
        return false;
    }

    private void loadData() {
        loadAll la = new loadAll();
        la.execute();
    }

    public class loadAll extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            listAdapter = new MyExpandableListAdapter(Search.this, parent_list);
            mList.setAdapter(listAdapter);
            listAdapter.notifyDataSetChanged();

            expandAll();

        }

        @Override
        protected String doInBackground(String... params) {
            String url = AppConfig.URL + "loadall.php";
            HttpHandler sh = new HttpHandler();
            String response = sh.makeServiceCall(url);
            if (response != null) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("user");
                    ArrayList<Child_Row> child_rows = new ArrayList<>();
                    Parent_Row parent_row = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject js = jsonArray.getJSONObject(i);

                        String name = js.getString("name");
                        String id = js.getString("id");
                        String pic = js.getString("pic");
                        System.out.println(name + ":" + id + ":" + pic);

                        Child_Row child_row = new Child_Row(null, name, id, true, pic);
                        child_rows.add(child_row);
                    }
                    if (child_rows.size() > 0) {
                        parent_row = new Parent_Row("Users", child_rows);
                        parent_list.add(parent_row);
                        System.out.println("here parent");
                    }

                    jsonArray = jsonObject.getJSONArray("topic");
                    child_rows = new ArrayList<>();
                    parent_row = null;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject js = jsonArray.getJSONObject(i);

                        String name = js.getString("name");
                        String id = js.getString("id");
                        String pic = js.getString("pic");
                        System.out.println(name + ":" + id + ":" + pic);

                        Child_Row child_row = new Child_Row(null, name, id, false, pic);
                        child_rows.add(child_row);
                    }

                    if (child_rows.size() > 0) {
                        parent_row = new Parent_Row("Topics", child_rows);
                        parent_list.add(parent_row);
                        System.out.println("here pantent topic");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
    }
}
