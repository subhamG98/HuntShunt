package com.github.florent37.materialviewpager.subham;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.digits.sdk.android.*;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;


public class signphone extends ActionBarActivity {
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "ZQRsQbilEdjOUFe2kcyWdFPpN";
    private static final String TWITTER_SECRET = "cSX3ARRhRMu3vxbTdcq0l6CQfKZh6ZJUFsL450u2p4ex5Ftqpd";


    EditText name;
    static EditText username;
     static String user="";
    String sex="";
    EditText password;
    EditText age;
    EditText location;
    EditText aboutyou;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    Button buttonAdd;
    LinearLayout container;
    AutoCompleteTextView acTextView;
    String[] hobbies= {
            "Singing", "Dancing", "Music", "Dramatics", "Painting", "Playing","Watching Movies","Programming","Coding"};
    String hobby="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signphone);
        radioSexGroup=(RadioGroup)findViewById(R.id.radioGroup);
        name=(EditText)findViewById(R.id.name);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        age=(EditText)findViewById(R.id.age);
        location=(EditText)findViewById(R.id.location);
        acTextView=(AutoCompleteTextView)findViewById(R.id.hobbies);

        ArrayAdapter adapter= new ArrayAdapter(this,android.R.layout.select_dialog_item,hobbies);
        acTextView.setThreshold(1);
        acTextView.setAdapter(adapter);

        aboutyou=(EditText)findViewById(R.id.aboutyou);



        buttonAdd = (Button)findViewById(R.id.add);
        container = (LinearLayout)findViewById(R.id.container);
        buttonAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.row, null);
                LinearLayout ll=(LinearLayout)addView.findViewById(R.id.container1);
                final TextView textOut = (TextView) ll.findViewById(R.id.textout);
                textOut.setText(acTextView.getText().toString());
                hobby=hobby.concat(acTextView.getText().toString());
                hobby=hobby.concat(",");
                Toast.makeText(signphone.this,""+hobby,Toast.LENGTH_SHORT).show();
                acTextView.setText("");
                textOut.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        hobby=hobby.replace(textOut.getText(),"");

                        ((LinearLayout) addView.getParent()).removeView(addView);
                        Toast.makeText(signphone.this,""+hobby,Toast.LENGTH_SHORT).show();

                    }
                });
                container.addView(addView);

            }
        });





        DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);
        digitsButton.setAuthTheme(R.style.CustomDigitsTheme);

        digitsButton.setCallback(new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                // TODO: associate the session userID with your user model
                int selectedId=radioSexGroup.getCheckedRadioButtonId();
                radioSexButton=(RadioButton)findViewById(selectedId);

                sex=radioSexButton.getText().toString();
                Log.i("Sex==",""+sex);

               Log.i("Sex==", "" + sex);

                if(name.getText().toString().equals("")||username.getText().toString().equals("")||password.getText().toString().equals("")||age.getText().toString().equals("")||aboutyou.getText().toString().equals("")||sex.equals("")||location.getText().toString().equals("")||hobby.equals("")){
                    Toast.makeText(signphone.this,"Please fill all the fields",Toast.LENGTH_SHORT).show();

                }
                else {
                    insertData(phoneNumber);
                    Log.i("Phoneno=", "" + phoneNumber);
                }
            }

            @Override
            public void failure(DigitsException exception) {
                Log.d("Digits", "Sign in with Digits failure", exception);
            }
        });

    }

    private void insertData(final String phoneno) {

        class LoginAsync extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {

                if(hobby.contains(",,"))
                {
                    hobby=hobby.replace(",,",",");
                }
                hobby=hobby.trim();
                Log.i("hobby=", "" + hobby);


                super.onPreExecute();
            }
            @Override
            protected String doInBackground(String... params) {
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                user=username.getText().toString();
                nameValuePairs.add(new BasicNameValuePair("name",name.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("username", username.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("password", password.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("age",age.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("location", location.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("hobbies", hobby));
                nameValuePairs.add(new BasicNameValuePair("aboutyou",aboutyou.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("sex", radioSexButton.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("phonono", phoneno.replace("+","")));
                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://resolvefinance.co.uk/Whatsapp/signup.php");

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


                try {
                    if(result.contains("success")) {
                        Intent i = null;
                        i = new Intent("com.github.florent37.materialviewpager.subham.signUp");
                        Log.i("signin1 a", "result=" + result + " " + username.getText().toString());

                        i.putExtra("username", username.getText().toString());
                        startActivity(i);
                    }
                    else if(result.contains("Username")) {
                        Toast.makeText(getApplicationContext(), "Sorry! Username already exists",Toast.LENGTH_LONG).show();
                    }

                    else
                    {
                        Toast.makeText(getApplicationContext(), "Oops! Some error occured.",Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    insertData(phoneno);
                    e.printStackTrace();
                }


            }
        }

        LoginAsync la = new LoginAsync();
        la.execute(phoneno);

    }







}
