package com.shekkahmeng.fypapplication;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.shekkahmeng.fypapplication.db.DBAdapter;
import com.shekkahmeng.fypapplication.rest.JSONParser;
import com.shekkahmeng.fypapplication.rest.OnlineEvent;
import com.shekkahmeng.fypapplication.rest.RestAPI;
import com.shekkahmeng.fypapplication.user.UserUtility;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class OnlineEventActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ListView listView;
    private View progressBarView, errorView;

    private OnlineEventActivityAdapter adapter;

    private Set<Long> downloadedEventIDs = new HashSet<Long>();

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setElevation(0);

        setContentView(R.layout.activity_online_event);

        DBAdapter dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        downloadedEventIDs = dbAdapter.getDownloadedEventIDs();
        dbAdapter.close();

        progressBarView = findViewById(R.id.online_event_progressbar);
        errorView = findViewById(R.id.online_event_error);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncLoadOnlineEvent().execute();
            }
        });

        listView = (ListView) findViewById(R.id.online_event_listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                final Spinner feeSpinner = new Spinner(OnlineEventActivity.this);
//                feeSpinner.setAdapter(new ArrayAdapter<String>(OnlineEventActivity.this, android.R.layout.simple_dropdown_item_1line, FeeOptions));
//                final Spinner userTypeSpinner = new Spinner(OnlineEventActivity.this);
//                userTypeSpinner.setAdapter(new ArrayAdapter<String>(OnlineEventActivity.this, android.R.layout.simple_dropdown_item_1line, UserTypeOptions));
//                LinearLayout linearLayout = new LinearLayout(OnlineEventActivity.this);
//                linearLayout.setOrientation(LinearLayout.VERTICAL);
//                linearLayout.addView(feeSpinner);
//                ScrollView scrollView = new ScrollView(OnlineEventActivity.this);
//                scrollView.addView(linearLayout);
                WebView webView = new WebView(OnlineEventActivity.this);
                webView.loadData(adapter.getEvent(position).getEventHTML(), "text/html; charset=utf-8", "UTF-8");

                new AlertDialog.Builder(OnlineEventActivity.this)
                        .setView(webView)
                        .setCancelable(false)
                        .setPositiveButton("Download", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DBAdapter dbAdapter = new DBAdapter(OnlineEventActivity.this);
                                dbAdapter.open();
                                long x = dbAdapter.insertRecord(adapter.getEvent(position));
                                dbAdapter.close();

                                downloadedEventIDs.add(adapter.getEvent(position).getEventId());

                                ((OnlineEventActivityAdapter)listView.getAdapter()).notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        ((SearchView) findViewById(R.id.online_event_searchview)).setOnQueryTextListener(this);

        new AsyncLoadOnlineEvent().execute();
    }

    protected class AsyncLoadOnlineEvent extends AsyncTask<Void, JSONObject, ArrayList<OnlineEvent>> {
        @Override
        protected void onPreExecute() {
            progressBarView.setVisibility(View.VISIBLE);
            errorView.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<OnlineEvent> doInBackground(Void... params) {
            try {
                JSONObject jsonObject = RestAPI.getEvents();
                return JSONParser.parseEvents(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<OnlineEvent> onlineEventTables) {
            progressBarView.setVisibility(View.GONE);

            if(onlineEventTables != null && onlineEventTables.size() > 0) {
                listView.setAdapter(adapter = new OnlineEventActivityAdapter(OnlineEventActivity.this, onlineEventTables, downloadedEventIDs));
                errorView.setVisibility(View.GONE);
            }
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_online_event, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
