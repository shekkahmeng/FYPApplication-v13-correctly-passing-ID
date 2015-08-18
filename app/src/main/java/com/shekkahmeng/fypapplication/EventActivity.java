package com.shekkahmeng.fypapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.shekkahmeng.fypapplication.db.DBAdapter;
import com.shekkahmeng.fypapplication.db.DownloadedEvent;
import com.shekkahmeng.fypapplication.rest.RestAPI;
import com.shekkahmeng.fypapplication.user.LoginActivity;
import com.shekkahmeng.fypapplication.user.SignUpActivity;
import com.shekkahmeng.fypapplication.user.UserUtility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

public class EventActivity extends AppCompatActivity {

    DBAdapter dbAdapter;

    DownloadedEvent downloadedEvent;

    public static final String IMAGE_ID = "IMG_ID";
    public static final String IMAGE_BITMAP = "imagebitmap";
//    public byte[] img = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbAdapter = new DBAdapter(this);
        dbAdapter.open();

        downloadedEvent = dbAdapter.getDownloadedEvent(getIntent().getLongExtra(MainActivity.ID_EXTRA, -1));
        if (downloadedEvent == null) {
            Toast.makeText(this, "Event not found", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        getSupportActionBar().setTitle(downloadedEvent.getEventName());

        setContentView(R.layout.activity_event);

        TextView tvName = (TextView) findViewById(R.id.event_name);
        tvName.setText(downloadedEvent.getEventName());
        TextView tvWebsite = (TextView) findViewById(R.id.event_website);
        tvWebsite.setText(downloadedEvent.getEventWebsite());
        TextView tvDate = (TextView) findViewById(R.id.event_date);
        tvDate.setText(downloadedEvent.getEventDate());
        TextView tvContactName = (TextView) findViewById(R.id.event_contact_name);
        tvContactName.setText(downloadedEvent.getEventContactName());
        TextView tvContact = (TextView) findViewById(R.id.event_contact);
        tvContact.setText(downloadedEvent.getEventContact());
        TextView tvShortName = (TextView) findViewById(R.id.short_name);
        tvShortName.setText(downloadedEvent.getShortName());
        TextView tvChairmanName = (TextView) findViewById(R.id.chairman_name);
        tvChairmanName.setText(downloadedEvent.getChairmanName());
        TextView tvChairmanEmail = (TextView) findViewById(R.id.chairman_email);
        tvChairmanEmail.setText(downloadedEvent.getChairmanEmail());
        TextView tvConferencePhone = (TextView) findViewById(R.id.conference_phone);
        tvConferencePhone.setText(downloadedEvent.getConferencePhone());
        TextView tvSystemEmail = (TextView) findViewById(R.id.system_email);
        tvSystemEmail.setText(downloadedEvent.getSystemEmail());
        TextView tvSecretaristAddress = (TextView) findViewById(R.id.secretaristAddress);
        tvSecretaristAddress.setText(downloadedEvent.getSecretaristAddress());
        TextView tvlocation = (TextView) findViewById(R.id.event_location);
        tvlocation .setText(downloadedEvent.getEventLocation());
        ImageView imgV = (ImageView) findViewById(R.id.event_image);
//        imgV.setImageBitmap(downloadedEvent.getEventImage());
        byte[] imageAsBytes = Base64.decode(downloadedEvent.getEventImage().getBytes(), Base64.DEFAULT);
        imgV.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));

        //new LoadImageFromDatabaseTask().execute(0);

        invalidateJoinOption();
    }

    @Override
    protected void onDestroy() {
        if (dbAdapter != null) {
            dbAdapter.close();
        }
        super.onDestroy();
    }

    private void invalidateJoinOption() {
        if (registerMenuItem == null) return;

        registerMenuItem.setVisible(false);

        Cursor cursor = dbAdapter.getRecord(downloadedEvent.getEventRowID());
        if (cursor.moveToFirst()) {
            if (cursor.getInt(cursor.getColumnIndex(DBAdapter.EVENT_JOINED)) == 1) {
                registerMenuItem.setVisible(false);
            } else {
                registerMenuItem.setVisible(true);
            }
        }
    }
/*
    private class LoadImageFromDatabaseTask extends AsyncTask<Integer, Integer, ImageHelper>{

        private final ProgressDialog progressDialog = new ProgressDialog(EventActivity.this);

        protected void onPreExecute() {
            this.progressDialog.setMessage("Loading Image from Db...");
            this.progressDialog.show();
        }

        @Override
        protected ImageHelper doInBackground(Integer... integers) {
            Log.d("LOAd:Background", "");
            return dbAdapter.getImage("");
        }

        protected void onProgressUpdate(Integer... progress) {
        }
        protected void onPostExecute(ImageHelper imageHelper) {
            Log.d("Load imm keksi ", imageHelper.getImageId());
            if (this.progressDialog.isShowing()) {
                this.progressDialog.dismiss();
            }
            setUpImage(imageHelper.getImageByteArray());
        }
    }

    private void setUpImage(byte[] bytes) {
        Log.d("hello motor", "Decoding bytes");
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        imageView.setImageBitmap(bitmap);
    }
*/

    MenuItem registerMenuItem;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);

        registerMenuItem = menu.findItem(R.id.action_register);

        invalidateJoinOption();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_register:
                String userId = UserUtility.getUserId(this);

                if (userId != null) {
                    new AsyncLoadRegisterEventOptions().execute();
                } else {
                    new AlertDialog.Builder(this)
                            .setTitle("You have to login to register")
                            .setCancelable(false)
                            .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(EventActivity.this, LoginActivity.class));
                                }
                            })
                            .setNeutralButton("Sign Up", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(EventActivity.this, SignUpActivity.class));
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                }
                break;
            case R.id.action_reminder:
                final TimePicker timePicker = new TimePicker(this);
                timePicker.setCurrentMinute(Calendar.getInstance().get(Calendar.MINUTE) + 1);
                final DatePicker datePicker = new DatePicker(this);
                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.addView(timePicker);
                linearLayout.addView(datePicker);
                ScrollView scrollView = new ScrollView(this);
                scrollView.addView(linearLayout);

                new AlertDialog.Builder(EventActivity.this)
                        .setTitle("Set reminder for this event")
                        .setView(scrollView)
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Calendar calendar = Calendar.getInstance();
                                long current = calendar.getTimeInMillis();
                                calendar.set(Calendar.YEAR, datePicker.getYear());
                                calendar.set(Calendar.MONTH, datePicker.getMonth());
                                calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                                calendar.set(Calendar.SECOND, 0);
                                calendar.set(Calendar.MILLISECOND, 0);
                                long alarm = calendar.getTimeInMillis();

                                Intent intent = new Intent(EventActivity.this, ReminderReceiver.class);
                                intent.putExtra(ReminderReceiver.ALARM_EXTRA, downloadedEvent.getEventName());
                                PendingIntent alarmIntent = PendingIntent.getBroadcast(EventActivity.this, (int) downloadedEvent.getEventRowID(), intent, 0);

                                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                alarmManager.cancel(alarmIntent);
                                alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + (alarm - current), alarmIntent);

                                Toast.makeText(EventActivity.this, "Reminder is set for " + new Date(alarm), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();

                break;
            case R.id.action_maps:
                try {
                    // launch Google Map/Waze (if available) or browser with Google Map website
                    // with attempt to search the location provided by server for this event
                    String url = "http://maps.google.com/maps?daddr=" + downloadedEvent.getEventLocation();
//                String url = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(this, "No application available to view map", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    protected class AsyncLoadRegisterEventOptions extends AsyncTask<Void, Void, JSONObject> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(EventActivity.this);
            progressDialog.setTitle("Please wait...");
            progressDialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            try {
                JSONObject jsonObj = RestAPI.getRegisterEventOption(String.valueOf(downloadedEvent.getEventRowID()));
                return jsonObj.getJSONArray("Value").getJSONObject(0);
            } catch (Exception e) {
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            if(result != null) {
                try {
                    JSONArray jsonArray = result.getJSONArray("UserType");
                    String[] UserTypeOptions = new String[jsonArray.length()];
                    UserTypeIdOptions = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        UserTypeOptions[i] = jsonArray.getJSONObject(i).getString("Name");
                        UserTypeIdOptions[i] = jsonArray.getJSONObject(i).getString("UserTypeId");
                    }

                    jsonArray = result.getJSONArray("Fee");
                    String[] FeeOptions = new String[jsonArray.length()];
                    FeeIdOptions = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        FeeOptions[i] = jsonArray.getJSONObject(i).getString("Category");
                        FeeIdOptions[i] = jsonArray.getJSONObject(i).getString("FeeId");
                    }

                    final Spinner feeSpinner = new Spinner(EventActivity.this);
                    feeSpinner.setAdapter(new ArrayAdapter<String>(EventActivity.this, android.R.layout.simple_dropdown_item_1line, FeeOptions));
                    final Spinner userTypeSpinner = new Spinner(EventActivity.this);
                    userTypeSpinner.setAdapter(new ArrayAdapter<String>(EventActivity.this, android.R.layout.simple_dropdown_item_1line, UserTypeOptions));
                    LinearLayout linearLayout = new LinearLayout(EventActivity.this);
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    linearLayout.addView(feeSpinner);
                    linearLayout.addView(userTypeSpinner);
                    ScrollView scrollView = new ScrollView(EventActivity.this);
                    scrollView.addView(linearLayout);

                    new AlertDialog.Builder(EventActivity.this)
                            .setTitle("Select register type")
                            .setView(scrollView)
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String ConferenceId = String.valueOf(downloadedEvent.getEventRowID());
                                    String FeeId = FeeIdOptions[feeSpinner.getSelectedItemPosition()];
                                    String UserId = UserUtility.getUserId(EventActivity.this);
                                    String UserTypeId = UserTypeIdOptions[userTypeSpinner.getSelectedItemPosition()];

                                    new AsyncRegister().execute(ConferenceId, FeeId, UserId, UserTypeId);
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .show();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(EventActivity.this, "Unable to load register event options", Toast.LENGTH_SHORT).show();
                }
            }

            progressDialog.dismiss();
        }

        String[] UserTypeIdOptions, FeeIdOptions;
    }

    protected class AsyncRegister extends AsyncTask<String, JSONObject, Boolean> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(EventActivity.this);
            progressDialog.setTitle("Please wait...");
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = false;

            try {
                String ConferenceId = params[0];
                String FeeId = params[1];
                String UserId = params[2];
                String UserTypeId = params[3];

                JSONObject jsonObject = RestAPI.registerEvent(ConferenceId, FeeId, UserId, UserTypeId);
                if(jsonObject.getBoolean("Successful") == true && jsonObject.getBoolean("Value") == true) {
                    result = true;
                }
            } catch (Exception e) {

            }

            return result;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                Toast.makeText(EventActivity.this, "Register successful!", Toast.LENGTH_SHORT).show();
                dbAdapter.setEventJoin(downloadedEvent.getEventRowID(), true);
                invalidateJoinOption();
            } else {
                Toast.makeText(EventActivity.this, "Unable to register event.\nPlease try again", Toast.LENGTH_SHORT).show();
            }

            progressDialog.dismiss();
        }
    }
}
