package com.shekkahmeng.fypapplication.user;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.shekkahmeng.fypapplication.R;
import com.shekkahmeng.fypapplication.rest.JSONParser;
import com.shekkahmeng.fypapplication.rest.RestAPI;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = (EditText) findViewById(R.id.login_username);
        passwordEditText = (EditText) findViewById(R.id.login_password);

        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                new AsyncLogin().execute(username, password);
            }
        });
    }

    protected class AsyncLogin extends AsyncTask<String, JSONObject, Boolean> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setTitle("Please wait...");
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = false;

            try {
                // Call the User Authentication Method in API
                JSONObject jsonObj = RestAPI.login(params[0], params[1]);

                String userID = jsonObj.getString("Value");
                if(!TextUtils.isEmpty(userID) && !userID.equals("-1")) {
                    UserUtility.setUserId(LoginActivity.this, userID);
                    result = true;
                }
            } catch (Exception e) {
            }

            return result;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            //Check user validity
            if (aBoolean) {
                Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Not valid username/password ", Toast.LENGTH_SHORT).show();
            }

            progressDialog.dismiss();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_login, menu);
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
