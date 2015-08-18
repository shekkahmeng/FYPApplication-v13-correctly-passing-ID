package com.shekkahmeng.fypapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;


import com.shekkahmeng.fypapplication.rest.OnlineEvent;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by shekkahmeng on 7/13/2015.
 */
public class DBAdapter {

    public static final String EVENT_ROWID = "id";
    public static final String EVENT_NAME = "name";
    public static final String EVENT_WEBSITE = "website";
    public static final String EVENT_DATE = "date";
    public static final String EVENT_CONTACT_NAME = "contactName";
    public static final String EVENT_CONTACT = "contact";
    public static final String SHORT_NAME = "shortName";
    public static final String CHAIRMAN_NAME = "chairmanName";
    public static final String CHAIRMAN_EMAIL = "chairmanEmail";
    public static final String CONFERENCE_PHONE = "conferencePhone";
    public static final String SYSTEM_EMAIL = "systemEmail";
    public static final String SECRETARIST_ADDRESS = "secretaristAddress";
    public static final String EVENT_LOCATION = "location";

    public static final String EVENT_JOINED = "joined";
    public static final String EVENT_REMINDER = "reminder";

    public static final String IMAGE_ID = "imageid";
    public static final String IMAGE_BITMAP = "imagebitmap";
    private static final String TAG = "DBAdapter";


    private static final String DATABASE_NAME = "AssignmentsDB";
    private static final String DATABASE_TABLE = "assignments";
    private static final int DATABASE_VERSION = 4;

    private static final String DATABASE_CREATE =
            "create table if not exists assignments (id integer primary key autoincrement,"
                    + "name VARCHAR not null, website VARCHAR, date date, contactName VARCHAR, contact VARCHAR, shortName VARCHAR, chairmanName VARCHAR, "
                    + "chairmanEmail VARCHAR, conferencePhone VARCHAR, systemEmail VARCHAR, secretaristAddress VARCHAR, location VARCHAR, imageid VARCHAR, imagebitmap VARCHAR, joined integer, reminder integer );";

    private final Context context;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);

    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DATABASE_CREATE);
                Log.e("DATABASE OPERATION", "Table created....");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version" + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS assignments");
            onCreate(db);
        }
    }

    //open database
    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //close
    public void close() {
        DBHelper.close();
    }

    //insert
    public long insertRecord(long rowId, String name, String website, String date, String contactName, String contact, String shortName, String chairmanName, String chairmanEmail, String conferencePhone, String systemEmail, String secretaristAddress, String location, String imageId, Drawable dbDrawable) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(EVENT_ROWID, rowId);
        initialValues.put(EVENT_NAME, name);
        initialValues.put(EVENT_WEBSITE, website);
        initialValues.put(EVENT_DATE, date);
        initialValues.put(EVENT_CONTACT_NAME, contactName);
        initialValues.put(EVENT_CONTACT, contact);
        initialValues.put(SHORT_NAME, shortName);
        initialValues.put(CHAIRMAN_NAME, chairmanName);
        initialValues.put(CHAIRMAN_EMAIL, chairmanEmail);
        initialValues.put(CONFERENCE_PHONE, conferencePhone);
        initialValues.put(SYSTEM_EMAIL, systemEmail);
        initialValues.put(SECRETARIST_ADDRESS, secretaristAddress);
        initialValues.put(EVENT_LOCATION, location);
        initialValues.put(IMAGE_ID, imageId);
        Bitmap bitmap = ((BitmapDrawable) dbDrawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        initialValues.put(IMAGE_BITMAP, stream.toByteArray());
        initialValues.put(EVENT_JOINED, 0);
        initialValues.put(EVENT_REMINDER, 0);
        Log.e("DATABASE OPERATION", "oNE ROW INSERTEd....");
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //delete
    public boolean deleteRecord(long rowId) {
        return db.delete(DATABASE_TABLE, EVENT_ROWID + " = " + rowId, null) > 0;
    }

    //get All
    public Cursor getAllRecords() {
        return db.query(DATABASE_TABLE, new String[]{EVENT_ROWID, EVENT_NAME, EVENT_WEBSITE, EVENT_DATE, EVENT_CONTACT_NAME, EVENT_CONTACT, SHORT_NAME, CHAIRMAN_NAME, CHAIRMAN_EMAIL, CONFERENCE_PHONE, SYSTEM_EMAIL, SECRETARIST_ADDRESS, EVENT_LOCATION, IMAGE_ID, IMAGE_BITMAP, EVENT_JOINED, EVENT_REMINDER}, null, null, null, null, null);
    }

    //get ONe
    public Cursor getRecord(long rowId) throws SQLException {
//        ImageHelper imageHelper = new ImageHelper();
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[]{EVENT_ROWID, EVENT_NAME, EVENT_WEBSITE, EVENT_DATE, EVENT_CONTACT_NAME, EVENT_CONTACT, SHORT_NAME, CHAIRMAN_NAME, CHAIRMAN_EMAIL, CONFERENCE_PHONE, SYSTEM_EMAIL, SECRETARIST_ADDRESS, EVENT_LOCATION, IMAGE_ID, IMAGE_BITMAP, EVENT_JOINED, EVENT_REMINDER},
                        EVENT_ROWID + "=" + rowId, null, null, null, null, null);
//        if (mCursor != null) {
//            if (mCursor.moveToFirst()) {
//                imageHelper.setImageId(mCursor.getString(7));
//                imageHelper.setImageByteArray(mCursor.getBlob(8));
//            }
//            while (mCursor.moveToNext()) ;
//
//
//        }
        return mCursor;
    }
/*
    public ImageHelper getImage(String imageId){
        Cursor cursor2 = db.query(DATABASE_TABLE, new String[]{EVENT_ROWID, IMAGE_ID, IMAGE_BITMAP}, IMAGE_ID
                + " LIKE '"+imageId+"%'", null, null, null, null);
        ImageHelper imgHelper = new ImageHelper();

        if (cursor2.moveToFirst()) {
            do {
                imgHelper.setImageId(cursor2.getString(1));
                imgHelper.setImageByteArray(cursor2.getBlob(2));
            } while (cursor2.moveToNext());
        }
        cursor2.close();
        return imgHelper;
    }
    */

    //update reCord
    public long insertRecord(OnlineEvent onlineEvent) {
        ContentValues initialValues = new ContentValues();

        initialValues.put(EVENT_ROWID, onlineEvent.getEventId());
        initialValues.put(EVENT_NAME, onlineEvent.getEventName());
        initialValues.put(EVENT_WEBSITE, onlineEvent.getEventWebsite());
        initialValues.put(EVENT_DATE, onlineEvent.getEventDate());
        initialValues.put(EVENT_CONTACT_NAME, onlineEvent.getEventContactName());
        initialValues.put(EVENT_CONTACT, onlineEvent.getEventContact());
        initialValues.put(SHORT_NAME, onlineEvent.getEventShortName());
        initialValues.put(CHAIRMAN_NAME, onlineEvent.getChairmanName());
        initialValues.put(CHAIRMAN_EMAIL, onlineEvent.getChairmanEmail());
        initialValues.put(CONFERENCE_PHONE, onlineEvent.getConferencePhone());
        initialValues.put(SYSTEM_EMAIL, onlineEvent.getSystemEmail());
        initialValues.put(SECRETARIST_ADDRESS, onlineEvent.getSecretaristAddress());
        initialValues.put(EVENT_LOCATION, onlineEvent.getEventLocation());
        initialValues.put(IMAGE_BITMAP, onlineEvent.getEventImage());

        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean updateRecord(long rowId, String name, String website, String date, String contactName, String contact, String shortName, String chairmanName, String chairmanEmail, String conferencePhone, String systemEmail, String secretaristAddress, String location, String imageId, Drawable dbDrawable) {

        ContentValues args = new ContentValues();
        args.put(EVENT_NAME, name);
        args.put(EVENT_WEBSITE, website);
        args.put(EVENT_DATE, date);
        args.put(EVENT_CONTACT_NAME, contactName);
        args.put(EVENT_CONTACT, contact);
        args.put(SHORT_NAME, shortName);
        args.put(CHAIRMAN_NAME, chairmanName);
        args.put(CHAIRMAN_EMAIL, chairmanEmail);
        args.put(CONFERENCE_PHONE, conferencePhone);
        args.put(SYSTEM_EMAIL, systemEmail);
        args.put(SECRETARIST_ADDRESS, secretaristAddress);
        args.put(EVENT_LOCATION, location);
        args.put(IMAGE_ID, imageId);
        Bitmap bitmap = ((BitmapDrawable) dbDrawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        args.put(IMAGE_BITMAP,stream.toByteArray());
        args.put(EVENT_JOINED, 0);
        args.put(EVENT_REMINDER, 0);
        return db.update(DATABASE_TABLE, args, EVENT_ROWID + "=" + rowId, null) > 0;
    }


    public boolean setEventReminder(long rowId, long time) {
        ContentValues args = new ContentValues();
        args.put(EVENT_REMINDER, time);
        return db.update(DATABASE_TABLE, args, EVENT_ROWID + "=" + rowId, null) > 0;
    }

    public boolean setEventJoin(long rowId, boolean join) {
        ContentValues args = new ContentValues();
        args.put(EVENT_JOINED, join ? 1 : 0);
        return db.update(DATABASE_TABLE, args, EVENT_ROWID + "=" + rowId, null) > 0;
    }

    public Set<Long> getDownloadedEventIDs() {
        Set<Long> downloadedEventIDs = new HashSet<Long>();

        Cursor mCursor = db.query(true, DATABASE_TABLE, new String[]{EVENT_ROWID}, null, null, null, null, null, null);
        if (mCursor != null) {
            while (mCursor.moveToNext()) {
                downloadedEventIDs.add(mCursor.getLong(mCursor.getColumnIndex(EVENT_ROWID)));
            }
            mCursor.close();
        }

        return downloadedEventIDs;
    }

    public List<DownloadedEvent> getDownloadedEvents() {
        List<DownloadedEvent> downloadedEvents = new ArrayList<DownloadedEvent>();

        Cursor cursor = db.query(true, DATABASE_TABLE, new String[]{EVENT_ROWID, EVENT_NAME, EVENT_WEBSITE, EVENT_DATE, EVENT_CONTACT_NAME, EVENT_CONTACT, SHORT_NAME, CHAIRMAN_NAME, CHAIRMAN_EMAIL, CONFERENCE_PHONE, SYSTEM_EMAIL, SECRETARIST_ADDRESS, EVENT_LOCATION, IMAGE_ID, IMAGE_BITMAP, EVENT_JOINED, EVENT_REMINDER}, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                downloadedEvents.add(parseDownloadedEvent(cursor));
            }

            cursor.close();
        }

        return downloadedEvents;
    }

    public DownloadedEvent getDownloadedEvent(long rowID) {
        DownloadedEvent downloadedEvent = null;

        Cursor cursor = db.query(true, DATABASE_TABLE, new String[]{EVENT_ROWID, EVENT_NAME, EVENT_WEBSITE, EVENT_DATE, EVENT_CONTACT_NAME, EVENT_CONTACT, SHORT_NAME, CHAIRMAN_NAME, CHAIRMAN_EMAIL, CONFERENCE_PHONE, SYSTEM_EMAIL, SECRETARIST_ADDRESS, EVENT_LOCATION, IMAGE_ID, IMAGE_BITMAP, EVENT_JOINED, EVENT_REMINDER}, EVENT_ROWID + "=?", new String[] { String.valueOf(rowID)}, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                downloadedEvent = parseDownloadedEvent(cursor);
            }

            cursor.close();
        }

        return downloadedEvent;
    }


    private DownloadedEvent parseDownloadedEvent(Cursor cursor) {
        DownloadedEvent downloadedEvent = new DownloadedEvent();
        downloadedEvent.setEventRowID(cursor.getLong(cursor.getColumnIndex(EVENT_ROWID)));
        downloadedEvent.setEventName(cursor.getString(cursor.getColumnIndex(EVENT_NAME)));
        downloadedEvent.setEventWebsite(cursor.getString(cursor.getColumnIndex(EVENT_WEBSITE)));
        downloadedEvent.setEventDate(cursor.getString(cursor.getColumnIndex(EVENT_DATE)));
        downloadedEvent.setEventContactName(cursor.getString(cursor.getColumnIndex(EVENT_CONTACT_NAME)));
        downloadedEvent.setEventContact(cursor.getString(cursor.getColumnIndex(EVENT_CONTACT)));
        downloadedEvent.setShortName(cursor.getString(cursor.getColumnIndex(SHORT_NAME)));
        downloadedEvent.setChairmanName(cursor.getString(cursor.getColumnIndex(CHAIRMAN_NAME)));
        downloadedEvent.setChairmanEmail(cursor.getString(cursor.getColumnIndex(CHAIRMAN_EMAIL)));
        downloadedEvent.setConferencePhone(cursor.getString(cursor.getColumnIndex(CONFERENCE_PHONE)));
        downloadedEvent.setSystemEmail(cursor.getString(cursor.getColumnIndex(SYSTEM_EMAIL)));
        downloadedEvent.setSecretaristAddress(cursor.getString(cursor.getColumnIndex(SECRETARIST_ADDRESS)));
        downloadedEvent.setEventLocation(cursor.getString(cursor.getColumnIndex(EVENT_LOCATION)));
        downloadedEvent.setEventImage(cursor.getString(cursor.getColumnIndex(IMAGE_BITMAP)));
//        byte[] img = cursor.getBlob(cursor.getColumnIndex(IMAGE_BITMAP));
//        downloadedEvent.setEventImage(BitmapFactory.decodeByteArray(img, 0, img.length));
        downloadedEvent.setEventJoin(cursor.getInt(cursor.getColumnIndex(EVENT_JOINED)) == 1 ? true : false);
        downloadedEvent.setEventReminder(cursor.getLong(cursor.getColumnIndex(EVENT_REMINDER)));

        return downloadedEvent;
    }
}






