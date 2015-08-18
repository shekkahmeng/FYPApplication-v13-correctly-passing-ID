package com.shekkahmeng.fypapplication;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.shekkahmeng.fypapplication.db.DBAdapter;
import com.shekkahmeng.fypapplication.db.DownloadedEvent;
import com.shekkahmeng.fypapplication.user.UserDetailsActivity;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public final static String ID_EXTRA = "com.shekkahmeng.fypapplication._ID";

    DBAdapter dbAdapter;

    ListView listView;

    MainActivityAdapter adapter;

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

        dbAdapter = new DBAdapter(this);
        dbAdapter.open();

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setContentView(R.layout.activity_main);

        View emptyView = findViewById(R.id.main_emptyview);
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOnlineEventActivity();
            }
        });

        listView = (ListView) findViewById(R.id.main_listview);
        listView.setEmptyView(emptyView);
        listView.setOnItemClickListener(onListClick);
        listView.setOnItemLongClickListener(onListLongClick);

        ((SearchView) findViewById(R.id.main_searchview)).setOnQueryTextListener(this);
    }

    @Override
    protected void onResume() {
        adapter = new MainActivityAdapter(this, dbAdapter.getDownloadedEvents());
        listView.setAdapter(adapter);

        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (dbAdapter != null) {
            dbAdapter.close();
        }

        super.onDestroy();
    }

    private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(MainActivity.this, EventActivity.class);
            intent.putExtra(ID_EXTRA, adapter.getItem(position).getEventRowID());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, view.findViewById(R.id.main_row_image), "image");
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
    };

    private AdapterView.OnItemLongClickListener onListLongClick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Delete")
                    .setMessage("Are you confirm to delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DownloadedEvent iteM = adapter.getItem(position);

                            dbAdapter.deleteRecord(iteM.getEventRowID());

                            adapter.remove(iteM);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();

            return true;
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_add:
                startActivity(new Intent(this, Main2Activity.class));
                return true;
            case R.id.action_account:
                startActivity(new Intent(this, UserDetailsActivity.class));
                return true;
            case R.id.action_events:
                openOnlineEventActivity();
                return true;
//            case R.id.action_login:
//                Intent intentLogin = new Intent(this, LoginActivity.class);
//                startActivity(intentLogin);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openOnlineEventActivity() {
        if (isNetworkAvailable()) {
            startActivity(new Intent(this, OnlineEventActivity.class));
        } else {
            Toast.makeText(getApplicationContext(), "No network available. Please connect to the internet.", Toast.LENGTH_LONG).show();
        }
    }

    //at the beginning use to display with Toast
    public void DisplayRecordLa(Cursor c) {
        Toast.makeText(this, "name:" + c.getString(1) + " date:" + c.getString(2) + " desc:" + c.getString(3)
                + " schedule:" + c.getString(4) + " notes:" + c.getString(5) + " location:" + c.getString(6), Toast.LENGTH_LONG).show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}
