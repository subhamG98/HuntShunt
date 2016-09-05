package com.github.florent37.materialviewpager.subham;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


public class MainActivity2 extends Activity {
    public static String username="";
    //DatabaseHelper myDb;
    public int flag=0;
    public static final String SHARED_PREF_NAME = "myloginapp";

    public static final String USERNAME_SHARED_PREF = "email";

    public static final String LOGGEDIN_SHARED_PREF = "loggedin";
     EditText usernameET;
    SharedPreferences pref1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);

         pref1 = getSharedPreferences("ActivityPREF1", Context.MODE_PRIVATE);
        if(pref1.getBoolean("activity_executed", false)){
            Intent intent = new Intent(this,SplashScreen.class);
            startActivity(intent);
            finish();
        } else {

        }

   /*     SharedPreferences pref = getSharedPreferences("Loggedin", Context.MODE_PRIVATE);
          if(pref.getBoolean("isloggedin",false))
          {

          }
        else
          {
              Intent intent=new Intent("com.github.florent37.materialviewpager.subhamdhruvintern.SplashScreen");
              startActivity(intent);

          }

*/


        Button login=(Button)findViewById(R.id.login);
        Button signup=(Button)findViewById(R.id.signup);
         usernameET=(EditText)findViewById(R.id.username);
        final EditText password=(EditText)findViewById(R.id.password);
      //  myDb = new DatabaseHelper(this);
        //Cursor res = myDb.getlogin();
        /*if (res.getCount() > 0) {
            while (res.moveToNext()) {
                username = res.getString(0);
                Intent intent = new Intent("com.github.florent37.materialviewpager.subhamdhruvintern.SplashScreen");
                startActivity(intent);
                finish();
            }
        }
        */



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameET.getText().toString();
                login(usernameET.getText().toString(), password.getText().toString());

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.github.florent37.materialviewpager.subham.signphone");
                startActivity(intent);

            }
        });
    }
    private void checkforinternet() {
        ConnectivityManager cm2 = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni2 = cm2.getActiveNetworkInfo();
        if (ni2 == null) {
            //Toast.makeText(MainActivity.this, "Not Connected to Internet!!", Toast.LENGTH_LONG).show();
            flag = 0;
            return;
        }
    }
    private void login(final String username, final String password) {

        class LoginAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loadingDialog = ProgressDialog.show(MainActivity2.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                InputStream is = null;
               // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
               // nameValuePairs.add(new BasicNameValuePair("username", username));
                //nameValuePairs.add(new BasicNameValuePair("password", password));
                String result = null;
                try{
                    HttpClient httpClient = new DefaultHttpClient();
                   Log.i("utmost imp","http://resolvefinance.co.uk/Whatsapp/login.php?username="+username+"&password="+password);
                    HttpPost httpPost = new HttpPost("http://resolvefinance.co.uk/Whatsapp/login.php?username="+username+"&password="+password);
                    //httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
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
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("checking result =",""+result);

                return result;
            }

            @Override
            public void onPostExecute(String result){


                try {
                    String s = result.trim();
                    Log.i("checking s =",""+s);
                    loadingDialog.dismiss();
                    if(s.contains("success")){

                        SharedPreferences.Editor ed = pref1.edit();
                        ed.putBoolean("activity_executed", true);
                        ed.putString("username",username);
                        ed.commit();

                        Intent intent=new Intent("com.github.florent37.materialviewpager.subham.SplashScreen");
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Invalid Username/Password!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {

                    login(usernameET.getText().toString(), password);
                }
            }
        }

        LoginAsync la = new LoginAsync();
        la.execute(username);

    }



}
