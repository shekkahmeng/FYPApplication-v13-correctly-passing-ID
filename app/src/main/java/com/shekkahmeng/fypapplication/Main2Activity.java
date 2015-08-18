package com.shekkahmeng.fypapplication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.shekkahmeng.fypapplication.db.DBAdapter;

public class Main2Activity extends AppCompatActivity {

    public static final String IMAGE_ID = "IMG_ID";
    DBAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent= getIntent();
        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        //Drawable dd = getResources().getDrawable(R.drawable.images);
        //Drawable dd2 = getResources().getDrawable(R.drawable.mqdefault);
        String thirdString = "A poet is a person who writes poetry. Poets may describe themselves as such or be described as poets by others. A poet may simply be a writer of poetry, or may perform their art to an audience.\n" +
                "\n" +
                "The Italian Giacomo Leopardi, one of the most radical and challenging thinkers of the 19th century.[1]\n" +
                "The work of a poet is essentially one of communication, eith a poet is essentially one of communication, eit a poet is essentially one of communication, eit a poet is essentially one of communication, eit a poet is essentially one of communication, eit a poet is essentially one of comm" +
                "unication, eit a poet is essentiall a poet is essentially one of communication, eit a poet is essentially one of communication, eit a poet is essentially one of communication, eit a poet is essentially one of communication, eit a poet is essentially one of communication, eit" +
                " a poet is essentially one of communication, eit a poet is essentially one of communication, eit a poet is essentially one of communication, eit a poet is essentially one of communication, eit" +
                "y one of communication, eiter expressing ideas in a literal sense, such as writing about a specific event or place, or metaphorically. Poets have existed since antiquity, in nearly all languages, and have produced works that vary greatly in different cultures and time periods.[2] Throughout each civilization and language, poets have used various styles that have changed through the course of literary history, resulting in a history of poets as diverse as the literature they have produced.";

        //long a = dbAdapter.insertRecord("boyboy", "2/4/1992", thirdString, "bimetable", "please remind me", "kuala lumpur", "image id", dd2);

        dbAdapter.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
