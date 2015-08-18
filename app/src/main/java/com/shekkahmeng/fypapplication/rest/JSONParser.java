package com.shekkahmeng.fypapplication.rest;

import android.util.Log;

import com.shekkahmeng.fypapplication.rest.OnlineEvent;
import com.shekkahmeng.fypapplication.user.UserDetailsTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by shekkahmeng on 7/31/2015.
 */
public class JSONParser {

    public JSONParser() {
        super();
    }

    public static ArrayList<OnlineEvent> parseEvents(JSONObject object) {
        ArrayList<OnlineEvent> arrayList = new ArrayList<OnlineEvent>();
        try {
            JSONArray jsonArray = object.getJSONArray("Value");
            JSONObject jsonObj = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObj = jsonArray.getJSONObject(i);

                long ConferenceId = jsonObj.has("ConferenceId") ? jsonObj.getLong("ConferenceId") : 0;
                String Event_Name = jsonObj.has("OrganizerName") ? jsonObj.getString("OrganizerName") : "";
                String Website = jsonObj.has("Website") ? jsonObj.getString("Website") : "";
                String Date = jsonObj.has("Date") ? jsonObj.getString("Date") : "";
                String ContactName = jsonObj.has("ContactName") ? jsonObj.getString("ContactName") : "";
                String Contact = jsonObj.has("Contact") ? jsonObj.getString("Contact") : "";
                String Logo = jsonObj.has("Logo") ? jsonObj.getString("Logo") : "";
                String Short_Name = jsonObj.has("Short_Name") ? jsonObj.getString("Short_Name") : "";
                String ChairmanName = jsonObj.has("ChairmanName") ? jsonObj.getString("ChairmanName") : "";
                String ChairmanEmail = jsonObj.has("ChairmanEmail") ? jsonObj.getString("ChairmanEmail") : "";
                String ConferencePhone = jsonObj.has("ConferencePhone") ? jsonObj.getString("ConferencePhone") : "";
                String SystemEmail = jsonObj.has("SystemEmail") ? jsonObj.getString("SystemEmail") : "";
                String SecretaristAddress = jsonObj.has("SecretariatAddress") ? jsonObj.getString("SecretariatAddress") : "";
                String ConferenceVenue = jsonObj.has("ConferenceVenue") ? jsonObj.getString("ConferenceVenue") : "";
                String WelcomeText = jsonObj.has("WelcomeText") ? jsonObj.getString("WelcomeText") : "";

                arrayList.add(new OnlineEvent(ConferenceId, Event_Name, Website, Date, ContactName, Contact, Logo, Short_Name, ChairmanName, ChairmanEmail, ConferencePhone, SystemEmail, SecretaristAddress, ConferenceVenue, WelcomeText));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser=> parseEvent", e.getMessage());
        }
        return arrayList;
    }

    public static UserDetailsTable parseUserDetails(JSONObject jsonObject) {
        try {
            UserDetailsTable userDetail = new UserDetailsTable();
            userDetail.setEmail(jsonObject.has("Email") ? jsonObject.getString("Email") : null);
            userDetail.setUsername(jsonObject.has("Username") ? jsonObject.getString("Username") : null);
            userDetail.setTitle(jsonObject.has("Title") ? jsonObject.getString("Title") : null);
            userDetail.setFullName(jsonObject.has("FullName") ? jsonObject.getString("FullName") : null);
            userDetail.setGender(jsonObject.has("Gender") ? jsonObject.getString("Gender") : null);
            userDetail.setInstituition(jsonObject.has("Instituition") ? jsonObject.getString("Instituition") : null);
            userDetail.setFaculty(jsonObject.has("Faculty") ? jsonObject.getString("Faculty") : null);
            userDetail.setDepartment(jsonObject.has("Department") ? jsonObject.getString("Department") : null);
            userDetail.setResearchField(jsonObject.has("ResearchField") ? jsonObject.getString("ResearchField") : null);
            userDetail.setAddress(jsonObject.has("Address") ? jsonObject.getString("Address") : null);
            userDetail.setState(jsonObject.has("State") ? jsonObject.getString("State") : null);
            userDetail.setPostalCode(jsonObject.has("PostalCode") ? jsonObject.getString("PostalCode") : null);
            userDetail.setCountry(jsonObject.has("Country") ? jsonObject.getString("Country") : null);
            userDetail.setPhoneNumber(jsonObject.has("PhoneNumber") ? jsonObject.getString("PhoneNumber") : null);
            userDetail.setFaxNumber(jsonObject.has("FaxNumber") ? jsonObject.getString("FaxNumber") : null);
            userDetail.setRegDate(jsonObject.has("RegDate") ? jsonObject.getString("RegDate") : null);
            userDetail.setEncryptedPassword(jsonObject.has("encryptedPassword") ? jsonObject.getString("encryptedPassword") : null);

            return userDetail;
        } catch (JSONException e) {
            return null;
        }
    }


}
