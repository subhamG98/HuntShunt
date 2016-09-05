package com.github.florent37.materialviewpager.subham;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.florent37.materialviewpager.subham.R;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.subham.Config;
import com.github.florent37.materialviewpager.subham.GPSTracker;
import com.github.florent37.materialviewpager.subham.R;
import com.github.florent37.materialviewpager.subham.SplashScreen;
import com.github.florent37.materialviewpager.subham.TestRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class chatbased extends ActionBarActivity {

    private RecyclerView mRecyclerView;
    private TestRecyclerViewAdapter11 mAdapter;
    private Config config;
    GPSTracker gps;
    double latitude=0.00,longitude=0.00;
    ImageView imageView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbased);
        getData1();
        imageView2=(ImageView)findViewById(R.id.imageView2);
        imageView2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerViewchat);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        Log.i("", "heloo" + SplashScreen.imps);

    }



    private void getData1(){
        class GetData extends AsyncTask<Void,Void,String> {
            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //      progressDialog = ProgressDialog.show(getApplicationContext(), "Fetching Data", "Please wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //    progressDialog.dismiss();
                try {
                    parseJSON(s);
                } catch (Exception e) {
                    getData1();
                    e.printStackTrace();
                }

            }

            @Override
            protected String doInBackground(Void... params) {
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL("http://resolvefinance.co.uk/Whatsapp/getlistchat.php?username="+SplashScreen.username1+"&lati="+SplashScreen.latitude+"&longi="+SplashScreen.longitude);
                    Log.i("ok check it2211", "http://resolvefinance.co.uk/Whatsapp/getlistchat.php?username="+SplashScreen.username1+"&lati="+SplashScreen.latitude+"&longi="+SplashScreen.longitude);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null) {
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }
            }
        }
        GetData gd = new GetData();
        gd.execute();
    }









    public void showData(){
        mAdapter = new TestRecyclerViewAdapter11(chatbased.this,Config.title_friend,Config.fullname,Config.sex,Config.distance,Config.type,Config.image);
        mRecyclerView.setAdapter(mAdapter);



    }

    private void parseJSON(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            Log.i("","array imp11"+array);

            config = new Config(array.length());

            for(int i=0; i<array.length(); i++){
                JSONObject j = array.getJSONObject(i);
                Config.title_friend[i] = getTitle_Friend(j);
                Config.fullname[i] = getFull_Name(j);
                Config.sex[i] = getSex(j);
                Config.distance[i] = getDistance(j);
                Config.type[i] = getType(j);
                Config.image[i] = getImage(j);



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
    private String getDistance(JSONObject j){
        String distance = null;
        try {
            distance = j.getString(Config.TAG_DISTANCE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return distance;
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
    private String getType(JSONObject j){
        String url = null;
        try {
            url = j.getString(Config.TAG_TYPE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return url;
    }


}
