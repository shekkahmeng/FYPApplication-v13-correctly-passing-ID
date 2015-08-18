/* JSON API for android appliation */
package com.shekkahmeng.fypapplication.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import org.json.JSONArray;

public class RestAPI {
//    private static final String urlString = "http://shek.somee.com/Handler1.ashx";
//    private static final String urlString = "http://192.168.11.26:8080/Handler1.ashx";
    private static final String urlString = "http://conferencerest.apphb.com/Handler1.ashx";

    private static String convertStreamToUTF8String(InputStream stream) throws IOException {
	    String result = "";
	    StringBuilder sb = new StringBuilder();
	    try {
            InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[4096];
            int readedChars = 0;
            while (readedChars != -1) {
                readedChars = reader.read(buffer);
                if (readedChars > 0)
                   sb.append(buffer, 0, readedChars);
            }
            result = sb.toString();
		} catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }


    private static String load(String contents) throws IOException {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(60000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStreamWriter w = new OutputStreamWriter(conn.getOutputStream());
            w.write(contents);
            w.flush();
            InputStream istream = conn.getInputStream();
            String result = convertStreamToUTF8String(istream);

            android.util.Log.d("REST", "REST result\n" + result);
            return result;
        } catch(Exception e) {
            android.util.Log.d("REST", "REST exception\n" + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }


    private static Object mapObject(Object o) {
		Object finalValue = null;
		if (o.getClass() == String.class) {
			finalValue = o;
		}
		else if (Number.class.isInstance(o)) {
			finalValue = String.valueOf(o);
		} else if (Date.class.isInstance(o)) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss", new Locale("en", "USA"));
			finalValue = sdf.format((Date)o);
		}
		else if (Collection.class.isInstance(o)) {
			Collection<?> col = (Collection<?>) o;
			JSONArray jarray = new JSONArray();
			for (Object item : col) {
				jarray.put(mapObject(item));
			}
			finalValue = jarray;
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			Method[] methods = o.getClass().getMethods();
			for (Method method : methods) {
				if (method.getDeclaringClass() == o.getClass()
						&& method.getModifiers() == Modifier.PUBLIC
						&& method.getName().startsWith("get")) {
					String key = method.getName().substring(3);
					try {
						Object obj = method.invoke(o, null);
						Object value = mapObject(obj);
						map.put(key, value);
						finalValue = new JSONObject(map);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}
		return finalValue;
	}

//    public static JSONObject CreateNewAccount(String userName, String fullName, String email, String title, String gender, String country, String password) throws Exception {
//        JSONObject parameters = new JSONObject();
//        parameters.put("userName", mapObject(userName));
//        parameters.put("fullName", mapObject(fullName));
//        parameters.put("email", mapObject(email));
//        parameters.put("title", mapObject(title));
//        parameters.put("gender", mapObject(gender));
//        parameters.put("country", mapObject(country));
//        parameters.put("password", mapObject(password));
//
//        JSONObject o = new JSONObject();
//        o.put("interface","RestAPI");
//        o.put("method", "CreateNewAccount");
//        o.put("parameters", parameters);
//
//        return new JSONObject(load(o.toString()));
//    }
//
//    public static JSONObject GetUserDetails(String userName) throws Exception {
//        JSONObject parameters = new JSONObject();
//        parameters.put("userName", mapObject(userName));
//
//        JSONObject o = new JSONObject();
//        o.put("interface","RestAPI");
//        o.put("method", "GetUserDetails");
//        o.put("parameters", parameters);
//
//        return new JSONObject(load(o.toString()));
//    }
//
//    public static JSONObject UserAuthentication(String userName,String password) throws Exception {
//        JSONObject parameters = new JSONObject();
//        parameters.put("userName", mapObject(userName));
//        parameters.put("password", mapObject(password));
//
//        JSONObject o = new JSONObject();
//        o.put("interface","RestAPI");
//        o.put("method", "UserAuthentication");
//        o.put("parameters", parameters);
//
//        return new JSONObject(load(o.toString()));
//    }

    public static JSONObject getEvents() throws Exception {
        JSONObject parameters = new JSONObject();

        JSONObject o = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "getEvents");
        o.put("parameters", parameters);

        return new JSONObject(load(o.toString()));
    }

    public static JSONObject getSignupOption() throws Exception {
        JSONObject parameters = new JSONObject();

        JSONObject o = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "getSignupOption");
        o.put("parameters", parameters);

        return new JSONObject(load(o.toString()));
    }

    public static JSONObject signup(String Email, String Username, String TitleId, String FullName, String GenderId, String Instituition, String Faculty, String Department, String ResearchField, String Address, String State, String PostalCode, String CountryId, String PhoneNumber, String FaxNumber, String encryptedPassword) throws Exception {
        JSONObject parameters = new JSONObject();
        parameters.put("Email", Email);
        parameters.put("Username", Username);
        parameters.put("TitleId", TitleId);
        parameters.put("FullName", FullName);
        parameters.put("GenderId", GenderId);
        parameters.put("Instituition", Instituition);
        parameters.put("Faculty", Faculty);
        parameters.put("Department", Department);
        parameters.put("ResearchField", ResearchField);
        parameters.put("Address", Address);
        parameters.put("State", State);
        parameters.put("PostalCode", PostalCode);
        parameters.put("CountryId", CountryId);
        parameters.put("PhoneNumber", PhoneNumber);
        parameters.put("FaxNumber", FaxNumber);
        parameters.put("encryptedPassword", encryptedPassword);

        JSONObject o = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "signup");
        o.put("parameters", parameters);

        return new JSONObject(load(o.toString()));
    }

    public static JSONObject login(String Username, String encryptedPassword) throws Exception {
        JSONObject parameters = new JSONObject();
        parameters.put("Username", Username);
        parameters.put("encryptedPassword", encryptedPassword);

        JSONObject o = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "login");
        o.put("parameters", parameters);

        return new JSONObject(load(o.toString()));
    }

    public static JSONObject changeUserDetail(String UserId, String Email, String Username, String TitleId, String FullName, String GenderId, String Instituition, String Faculty, String Department, String ResearchField, String Address, String State, String PostalCode, String CountryId, String PhoneNumber, String FaxNumber, String encryptedPassword) throws Exception {
        JSONObject parameters = new JSONObject();
        parameters.put("UserId", UserId);
        parameters.put("Email", Email);
        parameters.put("Username", Username);
        parameters.put("TitleId", TitleId);
        parameters.put("FullName", FullName);
        parameters.put("GenderId", GenderId);
        parameters.put("Instituition", Instituition);
        parameters.put("Faculty", Faculty);
        parameters.put("Department", Department);
        parameters.put("ResearchField", ResearchField);
        parameters.put("Address", Address);
        parameters.put("State", State);
        parameters.put("PostalCode", PostalCode);
        parameters.put("CountryId", CountryId);
        parameters.put("PhoneNumber", PhoneNumber);
        parameters.put("FaxNumber", FaxNumber);
        parameters.put("encryptedPassword", encryptedPassword);

        JSONObject o = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "changeUserDetail");
        o.put("parameters", parameters);

        return new JSONObject(load(o.toString()));
    }

    public static JSONObject getUserDetail(String UserId) throws Exception {
        JSONObject parameters = new JSONObject();
        parameters.put("UserId", UserId);

        JSONObject o = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "getUserDetail");
        o.put("parameters", parameters);

        return new JSONObject(load(o.toString()));
    }

    public static JSONObject getRegisterEventOption(String ConferenceId) throws Exception {
        JSONObject parameters = new JSONObject();
        parameters.put("ConferenceId", ConferenceId);

        JSONObject o = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "getRegisterEventOption");
        o.put("parameters", parameters);

        return new JSONObject(load(o.toString()));
    }

    public static JSONObject registerEvent(String ConferenceId, String FeeId, String UserId, String UserTypeId) throws Exception {
        JSONObject parameters = new JSONObject();
        parameters.put("ConferenceId", ConferenceId);
        parameters.put("FeeId", FeeId);
        parameters.put("UserId", UserId);
        parameters.put("UserTypeId", UserTypeId);

        JSONObject o = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "registerEvent");
        o.put("parameters", parameters);

        return new JSONObject(load(o.toString()));
    }

}


