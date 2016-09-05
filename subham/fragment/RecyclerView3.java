package com.github.florent37.materialviewpager.subham.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.subham.Config3;
import com.github.florent37.materialviewpager.subham.R;
import com.github.florent37.materialviewpager.subham.SplashScreen;
import com.github.florent37.materialviewpager.subham.TestRecyclerViewAdapter4;

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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class RecyclerView3 extends Fragment {

    private RecyclerView mRecyclerView;
    private TestRecyclerViewAdapter4 mAdapter;
    private static final int ITEM_COUNT = 100;

    private List<Object> mContentItems = new ArrayList<>();
    private Config3 config;

    public static RecyclerView3 newInstance() {
        return new RecyclerView3();
    }
    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_recyclerview4, container, false);

        final ImageButton actionB = (ImageButton)v.findViewById(R.id.removeallnoti);
       // FloatingActionButton actionC = new FloatingActionButton(getActivity());

        actionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               removeallnoti(SplashScreen.username1);
            }
        });

        return v;
    }



    private void removeallnoti(final String receiver) {

        class LoginAsync extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected String doInBackground(String... params) {
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("receiver", receiver));

                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://resolvefinance.co.uk/Whatsapp/removeallnoti.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (ClientProtocolException e) {
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
                Intent i=new Intent("com.github.florent37.materialviewpager.subham.MainActivity");
                startActivity(i);
            }
        }

        LoginAsync la = new LoginAsync();
        la.execute(receiver);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView3);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);


        getData();



        //     MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }





    private void getData(){
        class GetData extends AsyncTask<Void,Void,String>{
            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //      progressDialog = ProgressDialog.show(getActivity(), "Fetching Data", "Please wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //    progressDialog.dismiss();
                try {
                    parseJSON(s);
                } catch (Exception e) {
                    getData();
                    e.printStackTrace();

                }
            }

            @Override
            protected String doInBackground(Void... params) {
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL("http://resolvefinance.co.uk/Whatsapp/getnewstuff.php?username="+SplashScreen.username1);
                    Log.i("Noti k error","http://resolvefinance.co.uk/Whatsapp/getnewstuff.php?username="+SplashScreen.username1);

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
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
        mAdapter = new TestRecyclerViewAdapter4(getActivity(),Config3.sender,Config3.receiver,Config3.message,Config3.image,Config3.type);
        mRecyclerView.setAdapter(mAdapter);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);


    }

    private void parseJSON(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = jsonObject.getJSONArray(Config3.TAG_JSON_ARRAY);
            Log.i("","array imp"+array);

            config = new Config3(array.length());

            for(int i=0; i<array.length(); i++){
                JSONObject j = array.getJSONObject(i);
                Config3.sender[i] = getSender(j);
                Config3.receiver[i] = getReceiver(j);

                Config3.message[i] = getMessage(j);
                Config3.image[i] = getImage(j);
                Config3.type[i] = getType(j);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        showData();

    }

    private String getSender(JSONObject j){
        String name = null;
        try {
            name = j.getString(Config3.TAG_SENDER);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return name;
    }

    private String getReceiver(JSONObject j){
        String url = null;
        try {
            url = j.getString(Config3.TAG_RECEIVER);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return url;
    }

    private String getMessage(JSONObject j){
        String name = null;
        try {
            name = j.getString(Config3.TAG_MESSAGE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return name;
    }

    private String getImage(JSONObject j){
        String url = null;
        try {
            url = j.getString(Config3.TAG_IMAGE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return url;
    }
    private String getType(JSONObject j){
        String name = null;
        try {
            name = j.getString(Config3.TAG_TYPE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return name;
    }


}
