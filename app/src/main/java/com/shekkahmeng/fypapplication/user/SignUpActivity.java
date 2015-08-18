package com.shekkahmeng.fypapplication.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.shekkahmeng.fypapplication.R;
import com.shekkahmeng.fypapplication.rest.RestAPI;

import org.json.JSONArray;
import org.json.JSONObject;

public class SignUpActivity extends ActionBarActivity {

    private static final int REQ_CODE_LOGIN_SIGNUP = 1;

    private EditText usernameEditText;
    private EditText fullNameEditText;
    private EditText emailEditText;
    private EditText institutionEditText;
    private EditText facultyEditText;
    private EditText departmentEditText;
    private EditText researchFieldEditText;
    private EditText addressEditText;
    private EditText stateEditText;
    private EditText postalCodeEditText;
    private EditText phoneNumberEditText;
    private EditText faxNumberEditText;
    private EditText passwordEditText;
    private Spinner genderSpinner, titleSpinner, countrySpinner;
    private View progressBarView, errorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        progressBarView = findViewById(R.id.signup_progressbar);
        errorView = findViewById(R.id.signup_error);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncLoadSignupOptions().execute();
            }
        });

        emailEditText = (EditText) findViewById(R.id.signup_email);
        usernameEditText = (EditText) findViewById(R.id.signup_username);
        titleSpinner = (Spinner) findViewById(R.id.signup_title);
        fullNameEditText = (EditText) findViewById(R.id.signup_fullName);
        genderSpinner = (Spinner) findViewById(R.id.signup_gender);
        institutionEditText = (EditText) findViewById(R.id.signup_institution);
        facultyEditText = (EditText) findViewById(R.id.signup_faculty);
        departmentEditText = (EditText) findViewById(R.id.signup_department);
        researchFieldEditText = (EditText) findViewById(R.id.signup_researchField);
        addressEditText = (EditText) findViewById(R.id.signup_address);
        stateEditText = (EditText) findViewById(R.id.signup_state);
        postalCodeEditText = (EditText) findViewById(R.id.signup_postalCode);
        countrySpinner = (Spinner) findViewById(R.id.signup_country);
        phoneNumberEditText = (EditText) findViewById(R.id.signup_phoneNumber);
        faxNumberEditText = (EditText) findViewById(R.id.signup_faxNumber);
        passwordEditText = (EditText) findViewById(R.id.signup_password);

//        String temp = "f";
//        emailEditText.setText(temp + "@email.com");
//        usernameEditText.setText(temp + temp + temp);
//        fullNameEditText.setText(temp + " fullname");
//        institutionEditText.setText(temp + " institution");
//        facultyEditText.setText(temp + " faculty");
//        departmentEditText.setText(temp + " department");
//        researchFieldEditText.setText(temp + " research field");
//        addressEditText.setText(temp + " address");
//        stateEditText.setText(temp + " state");
//        postalCodeEditText.setText("12345");
//        phoneNumberEditText.setText("1234567");
//        faxNumberEditText.setText("7654321");
//        passwordEditText.setText(temp + temp + temp);

        findViewById(R.id.signup_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String Email = emailEditText.getText().toString();
                String Username = usernameEditText.getText().toString();
                String TitleId = titleIdOptions[titleSpinner.getSelectedItemPosition()];
                String FullName = fullNameEditText.getText().toString();
                String GenderId = genderIdOptions[genderSpinner.getSelectedItemPosition()];
                String Instituition = institutionEditText.getText().toString();
                String Faculty = facultyEditText.getText().toString();
                String Department = departmentEditText.getText().toString();
                String ResearchField = researchFieldEditText.getText().toString();
                String Address = addressEditText.getText().toString();
                String State = stateEditText.getText().toString();
                String PostalCode = postalCodeEditText.getText().toString();
                String CountryId = countryIdOptions[countrySpinner.getSelectedItemPosition()];
                String PhoneNumber = phoneNumberEditText.getText().toString();
                String FaxNumber = faxNumberEditText.getText().toString();
                String encryptedPassword = passwordEditText.getText().toString();

                new AsyncSignUp().execute(Email, Username, TitleId, FullName, GenderId, Instituition, Faculty, Department, ResearchField, Address, State, PostalCode, CountryId, PhoneNumber, FaxNumber, encryptedPassword);
            }
        });

        new AsyncLoadSignupOptions().execute();
    }

    protected class AsyncLoadSignupOptions extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected void onPreExecute() {
            progressBarView.setVisibility(View.VISIBLE);
            errorView.setVisibility(View.VISIBLE);
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                JSONObject jsonObj = RestAPI.getSignupOption();
                return jsonObj.getJSONArray("Value").getJSONObject(0);
            } catch (Exception e) {
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObj) {
            progressBarView.setVisibility(View.GONE);

            if(jsonObj != null) {
                try {
                    JSONArray jsonArray = jsonObj.getJSONArray("Gender");
                    String[] genderOptions = new String[jsonArray.length()];
                    genderIdOptions = new String[jsonArray.length()];
                    for(int i=0; i<jsonArray.length(); i++) {
                        genderOptions[i] = jsonArray.getJSONObject(i).getString("Name");
                        genderIdOptions[i] = jsonArray.getJSONObject(i).getString("GenderId");
                    }
                    genderSpinner.setAdapter(new ArrayAdapter<String>(SignUpActivity.this, android.R.layout.simple_dropdown_item_1line, genderOptions));

                    jsonArray = jsonObj.getJSONArray("Title");
                    String[] titleOptions = new String[jsonArray.length()];
                    titleIdOptions = new String[jsonArray.length()];
                    for(int i=0; i<jsonArray.length(); i++) {
                        titleOptions[i] = jsonArray.getJSONObject(i).getString("Name");
                        titleIdOptions[i] = jsonArray.getJSONObject(i).getString("TitleId");
                    }
                    titleSpinner.setAdapter(new ArrayAdapter<String>(SignUpActivity.this, android.R.layout.simple_dropdown_item_1line, titleOptions));

                    jsonArray = jsonObj.getJSONArray("Country");
                    String[] countryOptions = new String[jsonArray.length()];
                    countryIdOptions = new String[jsonArray.length()];
                    for(int i=0; i<jsonArray.length(); i++) {
                        countryOptions[i] = jsonArray.getJSONObject(i).getString("Name");
                        countryIdOptions[i] = jsonArray.getJSONObject(i).getString("CountryId");
                    }
                    countrySpinner.setAdapter(new ArrayAdapter<String>(SignUpActivity.this, android.R.layout.simple_dropdown_item_1line, countryOptions));

                    errorView.setVisibility(View.GONE);
                } catch (Exception e) {
                }
            }
        }
    }

    String[] genderIdOptions, titleIdOptions, countryIdOptions;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_LOGIN_SIGNUP) {
            finish();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    protected class AsyncSignUp extends AsyncTask<String, JSONObject, Boolean> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(SignUpActivity.this);
            progressDialog.setTitle("Please wait...");
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = false;

            try {
                int i = 0;
                String Email = params[i++];
                String Username = params[i++];
                String TitleId = params[i++];
                String FullName = params[i++];
                String GenderId = params[i++];
                String Instituition = params[i++];
                String Faculty = params[i++];
                String Department = params[i++];
                String ResearchField = params[i++];
                String Address = params[i++];
                String State = params[i++];
                String PostalCode = params[i++];
                String CountryId = params[i++];
                String PhoneNumber = params[i++];
                String FaxNumber = params[i++];
                String encryptedPassword = params[i++];

                JSONObject jsonObj = RestAPI.signup(Email, Username, TitleId, FullName, GenderId, Instituition, Faculty, Department, ResearchField, Address, State, PostalCode, CountryId, PhoneNumber, FaxNumber, encryptedPassword);

                if(jsonObj.getBoolean("Successful") == true && jsonObj.getBoolean("Value") == true) {
                    result = true;
                }
            } catch (Exception e) {
            }

            return result;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (aBoolean) {
                Toast.makeText(SignUpActivity.this, "Sign Up successful!", Toast.LENGTH_SHORT).show();
                startActivityForResult(new Intent(SignUpActivity.this, LoginActivity.class), REQ_CODE_LOGIN_SIGNUP);
            } else {
                Toast.makeText(SignUpActivity.this, "Not valid information ", Toast.LENGTH_SHORT).show();
            }

            progressDialog.dismiss();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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
