package com.example.anton.spy.system;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import com.example.anton.spy.sql.Sql;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * Created by Anton on 21.09.2016.
 */
public class Helper {
    public HttpResponse SendJSONPost(JSONObject jsonObj, Context c, String URL ) throws IOException {



       // InputStream inputStream = null;
        //String result = "";
        HttpClient client = new DefaultHttpClient();

        HttpPost post = new HttpPost(URL);
        StringEntity se = null;

        try {
            se = new StringEntity(jsonObj.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");
        post.setEntity(se);
        HttpResponse response = null;
        try {
            response = client.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;

    }

    public HttpResponse SendJSONPut(JSONObject jsonObj, Context c, String URL ) {



        // InputStream inputStream = null;
        //String result = "";
        HttpClient client = new DefaultHttpClient();

        HttpPut post = new HttpPut(URL);
        StringEntity se = null;

        try {
            se = new StringEntity(jsonObj.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");
        post.setEntity(se);
        HttpResponse response = null;
        try {
            response = client.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;

    }


    public void jsonObjectToDatabase(JSONObject jsonObj, Context c) {
        try {
            if (jsonObj.has("uid")) {

                String id = (String) jsonObj.get("id");
                String[] s = new String[5];
                s[0] = "uid";
                s[1] = id;
                sqlSettingsAdd(s, c);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sqlSettingsAdd(String[] s1, Context c) {
        Sql sql = new Sql(c, "ccontrol.db", null, 1);
        SQLiteDatabase SQLDb = sql.getWritableDatabase();

        ContentValues newValues = new ContentValues();
        String[] s = new String[5];
        for (int i = 0; i < 5; i++) {
            s[i] = "null:"+i;
            if (s1.length - 1 >= i) {
                s[i] = s1[i];
            }
        }

        try {
            newValues.put(Sql.TITLE_COLUMN, s[0]);
            newValues.put(Sql.VALUE_COLUMN, s[1]);
            newValues.put(Sql.VALUE2_COLUMN, s[2]);
            newValues.put(Sql.VALUE3_COLUMN, s[3]);
            newValues.put(Sql.VALUE4_COLUMN, s[4]);
            SQLDb.insert("settings", null, newValues);

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array end");
        }
        try {
            SQLDb.insert("settings", null, newValues);

        } catch (SQLiteConstraintException e) {
            System.out.println(" SQL UID ALREADY EXIST!!!");
        }
        SQLDb.close();
    }
}

