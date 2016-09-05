package com.github.florent37.materialviewpager.subham.fragment;

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

/**
 * Created by florentchampigny on 24/04/15.
 */
public class RecyclerViewFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private TestRecyclerViewAdapter mAdapter;
    private static final int ITEM_COUNT = 100;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Object> mContentItems = new ArrayList<>();
  //  DatabaseHelper myDb;
    private Config config;
    GPSTracker gps;
    double latitude=0.00,longitude=0.00;
    public static RecyclerViewFragment newInstance() {
        return new RecyclerViewFragment();
    }
View v;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_recyclerview, container, false);
        Log.i("","fragment k aayaa1");
       mSwipeRefreshLayout=(SwipeRefreshLayout)v.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                getData1();
            }
        });


        mRecyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);
        //      myDb = new DatabaseHelper(getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        Log.i("", "heloo" + SplashScreen.imps);



        parseJSON(SplashScreen.imps);




        return v;
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
                    onItemsLoadComplete();
                } catch (Exception e) {
                    getData1();
                }

            }

            @Override
            protected String doInBackground(Void... params) {
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL("http://resolvefinance.co.uk/Whatsapp/getlist.php?username="+SplashScreen.username1+"&lati="+SplashScreen.latitude+"&longi="+SplashScreen.longitude);
                    Log.i("ok check it22", "http://resolvefinance.co.uk/Whatsapp/getlist.php?username="+SplashScreen.username1+"&lati="+SplashScreen.latitude+"&longi="+SplashScreen.longitude);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null) {
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    getData1();
                    return null;
                }
            }
        }
        GetData gd = new GetData();
        gd.execute();
    }


    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        //      myDb = new DatabaseHelper(getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        Log.i("", "fragment k aayaa");


    }






    public void showData(){
        mAdapter = new TestRecyclerViewAdapter(getActivity(),Config.title_friend,Config.fullname,Config.sex,Config.distance,Config.type,Config.image);
        mRecyclerView.setAdapter(mAdapter);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);


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
