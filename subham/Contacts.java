package com.github.florent37.materialviewpager.subham;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Contacts extends Activity {
    private RecyclerView mRecyclerView;
    private TestRecyclerViewAdapter3 mAdapter;
    String sb1="";
    Config config;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        mRecyclerView=(RecyclerView)findViewById(R.id.recyclerView1);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        readContacts1();
    }

    private void readContacts1(){
        class GetData1 extends AsyncTask<Void,Void,String> {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                 progressDialog = ProgressDialog.show(Contacts.this, "Fetching Data", "Please wait...",false,false);

                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
               super.onPostExecute(s);
                    progressDialog.dismiss();

                try {
                    getData1();
                } catch (Exception e) {
                    readContacts1();
                    e.printStackTrace();
                }


            }

            @Override
            protected String doInBackground(Void... params) {
                ContentResolver cr = getContentResolver();

                Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
                String phone = null;
                if (cur.getCount() > 0) {
                    while (cur.moveToNext()) {
                        String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                        if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                            Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                            while (pCur.moveToNext()) {
                                phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                sb1=sb1.concat("-" + phone);
                                sb1=sb1.replace("*", "");
                                sb1=sb1.replace("+","");
                                sb1=sb1.replace(" ","");


                                Log.i("Phoneno",""+sb1);
                            }
                            pCur.close();

                        }
                    }
                }
                return null;
            }

        }
        GetData1 gd = new GetData1();
            gd.execute();

    }




    private void getData1() {

        class LoginAsync extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected String doInBackground(String... params) {
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                try { nameValuePairs.add(new BasicNameValuePair("phoneno",sb1.toString()));


                }
                catch (Exception e){
                    Toast.makeText(getBaseContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                }

                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://resolvefinance.co.uk/Whatsapp/contactlist.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (ClientProtocolException e){
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }
            @Override
            public void onPostExecute(String result){

                try {
                    parseJSON(result);
                } catch (Exception e) {
                    getData1();
                    e.printStackTrace();
                }
            }
        }
        LoginAsync la = new LoginAsync();
        la.execute();

    }





    public void showData(){
        mAdapter = new TestRecyclerViewAdapter3(Contacts.this,Config.title_friend,Config.fullname,Config.sex,Config.image);
        mRecyclerView.setAdapter(mAdapter);



    }

    private void parseJSON(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            Log.i("","array imp"+array);

            config = new Config(array.length());

            for(int i=0; i<array.length(); i++){
                JSONObject j = array.getJSONObject(i);
                Config.title_friend[i] = getTitle_Friend(j);
                Config.fullname[i] = getFull_Name(j);
                Config.sex[i] = getSex(j);
                Config.image[i]=getImage(j);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        showData();

    }

    private String getTitle_Friend(JSONObject j){
        String name = null;
        try {
            name = j.getString(Config.TAG_USER_NAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return name;
    }

    private String getImage(JSONObject j){
        String name = null;
        try {
            name = j.getString(Config.TAG_IMAGE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return name;
    }

    private String getSex(JSONObject j){
        String sex = null;
        try {
            sex = j.getString(Config.TAG_SEX);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sex;
    }
    private String getFull_Name(JSONObject j){
        String url = null;
        try {
            url = j.getString(Config.TAG_FULL_NAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return url;
    }



}