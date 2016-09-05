package com.github.florent37.materialviewpager.subham;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class UserProfile1 extends ActionBarActivity {

    ImageView img;

    private RecyclerView mRecyclerView;
    private TestRecyclerViewAdapterUser mAdapter;
    ArrayList<Data_Model_User> arrayList;

    TextView username;
    EditText status;
    EditText age;
    EditText location;
    EditText hobbies;
    EditText about;

    ConfigUser config;
    ConfigUser1 config1;
    Button updateuser;

    String clickedon="";
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";


    private static final String TAG = MainActivity.class.getSimpleName();


    // Camera activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private Uri fileUri; // file url to store image/video

    ProgressBar marker_progress2;
    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile1);
        marker_progress2=(ProgressBar)findViewById(R.id.marker_progress1);

        imageView2=(ImageView)findViewById(R.id.imageView2);
        imageView2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );

        final ImageButton actionA = (ImageButton)findViewById(R.id.addimages);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent("com.androidbelieve.drawerwithswipetabs.new_scapp_post");
                //startActivity(intent);
                captureImage();
            }
        });

        // Checking camera availability
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            finish();
        }
        img=(ImageView)findViewById(R.id.img);
        username=(TextView)findViewById(R.id.namedp);
        status=(EditText)findViewById(R.id.status);
        age=(EditText)findViewById(R.id.age);
        location=(EditText)findViewById(R.id.location);
        hobbies=(EditText)findViewById(R.id.hobbies);
        about=(EditText)findViewById(R.id.about);
         updateuser=(Button)findViewById(R.id.updateuser);
        updateuser.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateuser();
                    }
                }
        );
        getData2();

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerViewUser);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager1);
        mRecyclerView.setHasFixedSize(true);


        getData21();



    }



    /**
     * Checking device has camera hardware or not
     * */
    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /**
     * Launching camera app to capture image
     */
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }



    /**
     * Receiving activity result method will be called after closing the camera
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // successfully captured the image
                // launching upload activity
                launchUploadActivity(true);


            } else if (resultCode == RESULT_CANCELED) {

                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();

            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }

        } else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // video successfully recorded
                // launching upload activity
                launchUploadActivity(false);

            } else if (resultCode == RESULT_CANCELED) {

                // user cancelled recording
                Toast.makeText(getApplicationContext(),
                        "User cancelled video recording", Toast.LENGTH_SHORT)
                        .show();

            } else {
                // failed to record video
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to record video", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void launchUploadActivity(boolean isImage){
        Intent i = new Intent(UserProfile1.this, UploadActivity2.class);
        i.putExtra("filePath", fileUri.getPath());
        i.putExtra("isImage", isImage);
        startActivity(i);
    }

    /**
     * ------------ Helper Methods ----------------------
     * */

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }





    public  void updateuser() {

        class LoginAsync extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected String doInBackground(String... params) {
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("age",age.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("location", location.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("hobbies", hobbies.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("aboutyou",about.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("status", status.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("username", SplashScreen.username1));

                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://resolvefinance.co.uk/Whatsapp/updateuser.php");
                    Log.i("update a",""+age.getText().toString());

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
                Log.i("signin1 a", "result=" + result + " " + username.getText().toString());

                startActivity(i);

            }
        }

        LoginAsync la = new LoginAsync();
        la.execute();

    }


    private void getData21(){
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
                    parseJSON1(s);
                } catch (Exception e) {
                    getData21();
                    e.printStackTrace();
                }

            }

            @Override
            protected String doInBackground(Void... params) {
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL("http://resolvefinance.co.uk/Whatsapp/productuser.php?username="+SplashScreen.username1);
                    //Log.i("ok check it22", "http://resolvefinance.co.uk/Whatsapp/getlist.php?username="+SplashScreen.username1+"&lati="+SplashScreen.latitude+"&longi="+SplashScreen.longitude);
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





    public void showData1(){


        TestRecyclerViewAdapterUser1 adapter = new TestRecyclerViewAdapterUser1(this, arrayList);
        mRecyclerView.setAdapter(adapter);// set adapter on recyclerview
        adapter.notifyDataSetChanged();// Notify the adapter




    }

    private void parseJSON1(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            Log.i("", "array imp" + array);

            config1 = new ConfigUser1(array.length());
            arrayList = new ArrayList<>();

            for(int i=0; i<array.length(); i++){
                JSONObject j = array.getJSONObject(i);
                ConfigUser1.image[i]=getImage1(j);
                arrayList.add(new Data_Model_User(getImage1(j)));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        showData1();

    }

    private String getImage1(JSONObject j){
        String url = null;
        try {
            url = j.getString(ConfigUser1.TAG_IMAGE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return url;
    }














    private void getData2(){
        class GetData extends AsyncTask<Void,Void,String> {
            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                marker_progress2.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                marker_progress2.setVisibility(View.INVISIBLE);
                try {
                    parseJSON(s);
                } catch (Exception e) {
                    getData2();
                    e.printStackTrace();
                }


            }

            @Override
            protected String doInBackground(Void... params) {
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL("http://resolvefinance.co.uk/Whatsapp/aboutuser.php?fname="+SplashScreen.username1);
                    Log.i("ok check it211", "http://resolvefinance.co.uk/Whatsapp/getlist.php?username="+SplashScreen.username1);
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
        username.setText(ConfigUser.username[0].toString());
        status.setText(ConfigUser.status[0].toString());
        age.setText(ConfigUser.age[0].toString());
        location.setText(ConfigUser.location[0].toString());
        about.setText(ConfigUser.about[0].toString());
        hobbies.setText(ConfigUser.hobbies[0].toString());
        Picasso.with(this).load("" + ConfigUser.image[0]).resize(800, 800).into(img);

    }






    private void parseJSON(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = jsonObject.getJSONArray(ConfigUser.TAG_JSON_ARRAY);
            Log.i("","array imp profile"+array);

            config = new ConfigUser(array.length());

            for(int i=0; i<array.length(); i++){
                JSONObject j = array.getJSONObject(i);
                ConfigUser.username[i] = getUsername(j);
                ConfigUser.status[i] = getStatus(j);
                ConfigUser.image[i] = getImage(j);
                ConfigUser.age[i] = getAge(j);
                ConfigUser.location[i] = getLocation(j);
                ConfigUser.hobbies[i] = getHobbies(j);
                ConfigUser.checkfriend[i] = getCheckFriend(j);
                ConfigUser.about[i] = getAbout(j);


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        showData();

    }

    private String getUsername(JSONObject j){
        String name = null;
        try {
            name = j.getString(ConfigUser.TAG_USER_NAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return name;
    }

    private String getStatus(JSONObject j){
        String sex = null;
        try {
            sex = j.getString(ConfigUser.TAG_STATUS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sex;
    }
    private String getImage(JSONObject j){
        String distance = null;
        try {
            distance = j.getString(ConfigUser.TAG_IMAGE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return distance;
    }
    private String getAge(JSONObject j){
        String url = null;
        try {
            url = j.getString(ConfigUser.TAG_AGE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return url;
    }
    private String getLocation(JSONObject j){
        String url = null;
        try {
            url = j.getString(ConfigUser.TAG_LOCATION);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return url;
    }

    private String getHobbies(JSONObject j){
        String url = null;
        try {
            url = j.getString(ConfigUser.TAG_HOBBIES);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return url;
    }

    private String getAbout(JSONObject j){
        String url = null;
        try {
            url = j.getString(ConfigUser.TAG_ABOUT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return url;
    }

    private String getCheckFriend(JSONObject j){
        String url = null;
        try {
            url = j.getString(ConfigUser.TAG_CHECKFRIEND);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return url;
    }








    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
