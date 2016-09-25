package com.example.anton.spy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.anton.spy.sql.Sql;
import com.example.anton.spy.system.Helper;
import com.example.anton.spy.system.functions.GetLocation;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
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
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("CREATED");
      new  BootCompletedReceiver().onReceive(this.getApplicationContext() , this.getIntent());
      setContentView(R.layout.activity_main);


    }

    public void onClickLocation(View view){
        GetLocation s =  new GetLocation(this);
        System.out.println("SSSSSSSSSSSSSSSSSSSSS");
        System.out.println(s.getProvider() + " prov");


    }

    public void onClickRegister(View view) {

        final TextView email = (TextView) findViewById(R.id.email);
        final TextView pass = (TextView) findViewById(R.id.password);
        Register reg = new Register();
        reg.execute(String.valueOf(email.getText()), String.valueOf(pass.getText()), this.getApplicationContext());
        System.out.println("PUSH !!!");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}

class Register extends AsyncTask<Object, String, String> {


    @Override
    protected String doInBackground(Object... params) {


        String mainurl = "http://169.254.239.179:8080/ccontrol-v0.3";

        String email = (String) params[0];
        String pass = (String) params[1];
        Context c = (Context) params[2];
        String url = mainurl+"/api/user";


        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("login", email);
            jsonObj.put("password", pass);
            jsonObj.put("role", "ROLE_USER");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Helper help = new Helper();
        HttpResponse response=
                null;
        try {
            response = help.SendJSONPost(jsonObj, c, url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String jString = EntityUtils.toString(response.getEntity());

            System.out.println(jString + " JSTRING POST RESPONSE");
            JSONObject o = new JSONObject(jString);
            help.sqlSettingsAdd((new String[]{"uid",String.valueOf(o.get("id"))}), c);
            Sql sql = new Sql(c, "ccontrol.db", null, 1);

            SQLiteDatabase SQLDb = sql.getWritableDatabase();
            System.out.println(SQLDb.toString() + " SQLDB TO STRING !!!!");
            String query =  "SELECT value FROM settings where title = 'uid'";
            System.out.print(SQLDb.rawQuery(query, null).getColumnNames().toString());
            Cursor cursor = SQLDb.rawQuery(query, null);

            cursor.moveToFirst();
                String value = cursor.getString(cursor.getColumnIndex("value"));
            String im = Settings.Secure.getString(c.getContentResolver(),
                    Settings.Secure.ANDROID_ID);

            JSONObject phone = new JSONObject("{\"id\":0,\"user\":{\"id\":\""+value+"\"},\"markers\":[],\"emei\":\""+im+"\",\"name\":\"fuckyeah\"}");
            response = help.SendJSONPut(phone, c, mainurl+"/api/phone");
            String phoneJson = EntityUtils.toString(response.getEntity());
            JSONObject phoneJObj = new JSONObject(phoneJson);
            help.sqlSettingsAdd((new String[]{"pid", String.valueOf(phoneJObj.get("id"))}), c);
            System.out.printf("PID"+phoneJObj.get("id")+ " WAS ADDED");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.printf("PID WAS ADDED");
        return null;
    }
}
 /*  HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://169.254.239.179:8080/ccontrol-v0.3/api/user");
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
        try {
            HttpResponse response = client.execute(post);
            String jString = EntityUtils.toString(response.getEntity());
            System.out.println(jString);
            JSONObject o = new JSONObject(jString);

            Sql sql = new Sql(c, "ccontrol.db", null, 1);
            SQLiteDatabase SQLDb = sql.getWritableDatabase();

            ContentValues newValues = new ContentValues();
            newValues.put(Sql.TITLE_COLUMN, "uid");
            newValues.put(Sql.VALUE_COLUMN, (Integer) o.get("id"));
            newValues.put(Sql.VALUE2_COLUMN, "0");
            newValues.put(Sql.VALUE3_COLUMN, "0");
            newValues.put(Sql.VALUE4_COLUMN, "0");
            SQLDb.insert("settings", null, newValues);
            SQLDb.close();*/
//phone
           /*  Sql sql = new Sql(c, "ccontrol.db", null, 1);
             SQLiteDatabase SQLDb = sql.getWritableDatabase();
            String query = "SELECT value FROM settings where title = 'uid'";
            Cursor cursor = SQLDb.rawQuery(query, null);
            String value = cursor.getString(cursor.getColumnIndex(Sql.VALUE_COLUMN));


        JSONObject phone = new JSONObject(new JSONObject(("{\"id\":0,\"user\":{\"id\":\""+value+"\"},\"markers\":[],\"emei\":\""+c+"\",\"name\":\"nokla2112\"}"))
        help.SendJSONPut(phone, c, "http://169.254.239.179:8080/ccontrol-v0.3/api/user");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
*/

/*
    void getEmei(Context c){
        TelephonyManager mngr = (TelephonyManager)c.getSystemService(Context.TELEPHONY_SERVICE);
        mngr.getDeviceId();

    }
}*/
