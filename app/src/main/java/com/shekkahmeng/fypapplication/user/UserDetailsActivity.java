package com.shekkahmeng.fypapplication.user;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shekkahmeng.fypapplication.R;
import com.shekkahmeng.fypapplication.rest.JSONParser;
import com.shekkahmeng.fypapplication.rest.RestAPI;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserDetailsActivity extends AppCompatActivity {

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
    private TextView regDateTextView;
    private View progressBarView, errorView;

    private static final int REQ_CODE_LOGIN_SIGNUP = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        progressBarView = findViewById(R.id.user_detail_progressbar);
        errorView = findViewById(R.id.user_detail_error);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncLoadUserDetails().execute();
            }
        });

        emailEditText = (EditText) findViewById(R.id.user_detail_email);
        usernameEditText = (EditText) findViewById(R.id.user_detail_username);
        titleSpinner = (Spinner) findViewById(R.id.user_detail_title);
        fullNameEditText = (EditText) findViewById(R.id.user_detail_fullName);
        genderSpinner = (Spinner) findViewById(R.id.user_detail_gender);
        institutionEditText = (EditText) findViewById(R.id.user_detail_institution);
        facultyEditText = (EditText) findViewById(R.id.user_detail_faculty);
        departmentEditText = (EditText) findViewById(R.id.user_detail_department);
        researchFieldEditText = (EditText) findViewById(R.id.user_detail_researchField);
        addressEditText = (EditText) findViewById(R.id.user_detail_address);
        stateEditText = (EditText) findViewById(R.id.user_detail_state);
        postalCodeEditText = (EditText) findViewById(R.id.user_detail_postalCode);
        countrySpinner = (Spinner) findViewById(R.id.user_detail_country);
        phoneNumberEditText = (EditText) findViewById(R.id.user_detail_phoneNumber);
        faxNumberEditText = (EditText) findViewById(R.id.user_detail_faxNumber);
        passwordEditText = (EditText) findViewById(R.id.user_detail_password);
        regDateTextView = (TextView) findViewById(R.id.user_detail_regDate);

        findViewById(R.id.user_detail_button).setOnClickListener(new View.OnClickListener() {
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

                new AsyncChangeUserDetails().execute(UserUtility.getUserId(UserDetailsActivity.this), Email, Username, TitleId, FullName, GenderId, Instituition, Faculty, Department, ResearchField, Address, State, PostalCode, CountryId, PhoneNumber, FaxNumber, encryptedPassword);
            }
        });

        if (!setLoggedInInformation()) {
            findViewById(R.id.user_detail_register).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(UserDetailsActivity.this, SignUpActivity.class), REQ_CODE_LOGIN_SIGNUP);
                }
            });

            findViewById(R.id.user_detail_login).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(UserDetailsActivity.this, LoginActivity.class), REQ_CODE_LOGIN_SIGNUP);
                }
            });
        }

//        String nama = "AHAHA";
//
//        new AsyncUserDetails().execute(nama);
    }

    private boolean setLoggedInInformation() {
        String userId = UserUtility.getUserId(this);

        if (userId != null) {
            findViewById(R.id.user_detail_register_layout).setVisibility(View.GONE);
            new AsyncLoadUserDetails().execute(userId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_LOGIN_SIGNUP) {
            if (!setLoggedInInformation()) {
                finish();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    protected class AsyncLoadUserDetails extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected void onPreExecute() {
            progressBarView.setVisibility(View.VISIBLE);
            errorView.setVisibility(View.VISIBLE);
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                JSONObject jsonObj = RestAPI.getUserDetail(params[0]);
                return jsonObj.getJSONArray("Value").getJSONObject(0);
            } catch (Exception e) {
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObj) {
            progressBarView.setVisibility(View.GONE);

            if (jsonObj != null) {
                try {
                    JSONArray jsonArray = jsonObj.getJSONArray("Gender");
                    String[] genderOptions = new String[jsonArray.length()];
                    genderIdOptions = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        genderOptions[i] = jsonArray.getJSONObject(i).getString("Name");
                        genderIdOptions[i] = jsonArray.getJSONObject(i).getString("GenderId");
                    }
                    genderSpinner.setAdapter(new ArrayAdapter<String>(UserDetailsActivity.this, android.R.layout.simple_dropdown_item_1line, genderOptions));

                    jsonArray = jsonObj.getJSONArray("Title");
                    String[] titleOptions = new String[jsonArray.length()];
                    titleIdOptions = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        titleOptions[i] = jsonArray.getJSONObject(i).getString("Name");
                        titleIdOptions[i] = jsonArray.getJSONObject(i).getString("TitleId");
                    }
                    titleSpinner.setAdapter(new ArrayAdapter<String>(UserDetailsActivity.this, android.R.layout.simple_dropdown_item_1line, titleOptions));

                    jsonArray = jsonObj.getJSONArray("Country");
                    String[] countryOptions = new String[jsonArray.length()];
                    countryIdOptions = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        countryOptions[i] = jsonArray.getJSONObject(i).getString("Name");
                        countryIdOptions[i] = jsonArray.getJSONObject(i).getString("CountryId");
                    }
                    countrySpinner.setAdapter(new ArrayAdapter<String>(UserDetailsActivity.this, android.R.layout.simple_dropdown_item_1line, countryOptions));

                    UserDetailsTable userDetailsTable = JSONParser.parseUserDetails(jsonObj.getJSONArray("User").getJSONObject(0));
                    emailEditText.setText(userDetailsTable.getEmail());
                    usernameEditText.setText(userDetailsTable.getUsername());
                    fullNameEditText.setText(userDetailsTable.getFullName());
                    institutionEditText.setText(userDetailsTable.getInstituition());
                    facultyEditText.setText(userDetailsTable.getFaculty());
                    departmentEditText.setText(userDetailsTable.getDepartment());
                    researchFieldEditText.setText(userDetailsTable.getResearchField());
                    addressEditText.setText(userDetailsTable.getAddress());
                    stateEditText.setText(userDetailsTable.getState());
                    postalCodeEditText.setText(userDetailsTable.getPostalCode());
                    phoneNumberEditText.setText(userDetailsTable.getPhoneNumber());
                    faxNumberEditText.setText(userDetailsTable.getFaxNumber());
                    passwordEditText.setText(userDetailsTable.getEncryptedPassword());
                    regDateTextView.setText(userDetailsTable.getRegDate());

                    for (int i = 0; i < countryOptions.length; i++) {
                        if (userDetailsTable.getCountry().equals(countryOptions[i])) {
                            countrySpinner.setSelection(i);
                            break;
                        }
                    }

                    for (int i = 0; i < titleOptions.length; i++) {
                        if (userDetailsTable.getTitle().equals(titleOptions[i])) {
                            titleSpinner.setSelection(i);
                            break;
                        }
                    }

                    for (int i = 0; i < genderOptions.length; i++) {
                        if (userDetailsTable.getGender().equals(genderOptions[i])) {
                            genderSpinner.setSelection(i);
                            break;
                        }
                    }

                    errorView.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    String[] genderIdOptions, titleIdOptions, countryIdOptions;

    protected class AsyncChangeUserDetails extends AsyncTask<String, JSONObject, Boolean> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(UserDetailsActivity.this);
            progressDialog.setTitle("Please wait...");
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = false;

            try {
                int i = 0;
                String UserId = params[i++];
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

                JSONObject jsonObj = RestAPI.changeUserDetail(UserId, Email, Username, TitleId, FullName, GenderId, Instituition, Faculty, Department, ResearchField, Address, State, PostalCode, CountryId, PhoneNumber, FaxNumber, encryptedPassword);

                if (jsonObj.getBoolean("Successful") == true && jsonObj.getBoolean("Value") == true) {
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
                Toast.makeText(UserDetailsActivity.this, "Save profile successful!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(UserDetailsActivity.this, "Not valid information ", Toast.LENGTH_SHORT).show();
            }

            progressDialog.dismiss();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_user_details, menu);
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
