package com.github.florent37.materialviewpager.subham;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class chat_page1 extends Activity {
    private RecyclerView mRecyclerView;
    private TestRecyclerViewAdapter1 mAdapter;
    private Config1 config;
    private Timer myTimer;
    int flag=1;
    int x=0,y=0;
   String username_p="";
    public EditText input;
    public static int chat_flag=1;
    Button button;
    public  String message;
    private GestureDetectorCompat gestureDetectorCompat;
    RelativeLayout chatpage;
    // LogCat tag
    private static final String TAG = chat_page1.class.getSimpleName();


    // Camera activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private Uri fileUri; // file url to store image/video

    private ImageButton btnCapturePicture, btnRecordVideo;
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";
    int flagopen=1;









    @Override
    protected void onDestroy() {
        myTimer.cancel();
        super.onDestroy();

    }
    @Override
    protected void onPause() {
        myTimer.cancel();
        super.onDestroy();

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);
        username_p=SplashScreen.username1;
        gestureDetectorCompat = new GestureDetectorCompat(this, new My2ndGestureListener());
       chatpage=(RelativeLayout)findViewById(R.id.chatpage);
        chatpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chat_flag==1) {

                    Intent i = new Intent("com.github.florent37.materialviewpager.subham.chatpromotion");
                    startActivity(i);

                }

            }
        });

        btnCapturePicture = (ImageButton) findViewById(R.id.btnCapturePicture);
        btnRecordVideo = (ImageButton) findViewById(R.id.btnRecordVideo);

        /**
         * Capture image button click event
         */
        btnCapturePicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // capture picture
                captureImage();
            }
        });

        /**
         * Record video button click event
         */
        btnRecordVideo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // record video
                recordVideo();
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


    /**
     * Checking device has camera hardware or not
     * */

        ImageView abc = (ImageView)findViewById(R.id.onlydpinside);
        input = (EditText)findViewById(R.id.input);
        TextView defg=(TextView)findViewById(R.id.textView4);
        defg.setText(TestRecyclerViewAdapter.clicked_on_name);
        Picasso.with(getApplicationContext()).load("http://resolvefinance.co.uk/Whatsapp/uploads/" + TestRecyclerViewAdapter.clicked_on_username + ".png").into(abc);

        button=(Button)findViewById(R.id.buttonsend);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message=input.getText().toString();
                insertintochatbox(SplashScreen.username1,TestRecyclerViewAdapter.clicked_on_username,message);
            }
        });

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        //      myDb = new DatabaseHelper(getActivity());
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setReverseLayout(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (flag == 1) {
                    get_user_list(TestRecyclerViewAdapter.clicked_on_username, username_p);
                } else {

                }
            }
        }, 0, 6000);

        /*mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (chat_flag == 1) {

                    Intent i = new Intent("com.github.florent37.materialviewpager.subham.chatpromotion");
                    startActivity(i);

                }

                return false;

            }
        });

*/

    }



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


        @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class My2ndGestureListener extends GestureDetector.SimpleOnGestureListener {
        //handle 'swipe right' action only

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

         /*
         Toast.makeText(getBaseContext(),
          event1.toString() + "\n\n" +event2.toString(),
          Toast.LENGTH_SHORT).show();
         */

            if (event2.getX() > event1.getX()) {
                Toast.makeText(getBaseContext(),
                        "Swipe right - finish()",
                        Toast.LENGTH_SHORT).show();

                finish();
            }

            return true;
        }

    }

    private void insertintochatbox(final String person1, final String person2, final String message) {

        class LoginAsync extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected String doInBackground(String... params) {
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("message", message));
                nameValuePairs.add(new BasicNameValuePair("username", person1));
                nameValuePairs.add(new BasicNameValuePair("message_person", person2));

                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://resolvefinance.co.uk/Whatsapp/add_to_chat.php");
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
                //Toast.makeText(getBaseContext(),""+result,Toast.LENGTH_SHORT).show();
                input.setText("");
            }
        }

        LoginAsync la = new LoginAsync();
        la.execute(person1,person2,message);

    }

    private void get_user_list(final String fusername,final  String username) {

        class LoginAsync extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected String doInBackground(String... params) {
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                try { nameValuePairs.add(new BasicNameValuePair("fusername",fusername));
                    nameValuePairs.add(new BasicNameValuePair("username",username));

                }
                catch (Exception e){
                    Toast.makeText(getBaseContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                }

                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://resolvefinance.co.uk/Whatsapp/get_chats.php");
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
                    get_user_list(fusername,username);
                    e.printStackTrace();
                }
            }
        }
        LoginAsync la = new LoginAsync();
        la.execute();

    }









    public void showData(){
        mAdapter = new TestRecyclerViewAdapter1(chat_page1.this,Config1.username,Config1.message,Config1.type,Config1.link);
        mRecyclerView.setAdapter(mAdapter);


    }


    /**
     * Launching camera app to capture image
     */
    private void captureImage() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(chat_page1.this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to send your picture?");

        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

                        // start the image capture Intent
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

                    }
                });

        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                          Intent i1=new Intent(chat_page1.this,UploadGallery.class);
                        startActivity(i1);
                    }
                });

        myAlertDialog.show();







}

    /**
     * Launching camera app to record video
     */
    private void recordVideo() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(chat_page1.this);
        myAlertDialog.setTitle("Upload Video Option");
        myAlertDialog.setMessage("How do you want to send your video?");

        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intent = new Intent(
                                MediaStore.ACTION_VIDEO_CAPTURE);
                        fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
                        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

                        // start the image capture Intent
                        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);

                    }
                });

        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent pictureActionIntent = null;

                        pictureActionIntent = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                        fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
                        pictureActionIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);


                        startActivityForResult(
                                pictureActionIntent, 2);

                    }
                });

        myAlertDialog.show();












    }

    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
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
        else if (requestCode == 2) {
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
        Intent i = new Intent(chat_page1.this, UploadActivity.class);
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





    private void parseJSON(String json){

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = jsonObject.getJSONArray(Config1.TAG_JSON_ARRAY);
            Log.i("","array imp11"+array);

            config = new Config1(array.length());

            for(int i=0; i<array.length(); i++){
                JSONObject j = array.getJSONObject(i);
                Config1.username[i] = getUsername(j);
                Config1.message[i] = getMessage(j);

                Config1.type[i] = getType(j);
                Config1.link[i] = getLink(j);

            }
            x=array.length();
            if(y==x){


            }
            else {
                showData();
                y=x;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String getUsername(JSONObject j){
        String username = null;
        try {
            username = j.getString(Config1.TAG_USER_NAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return username;
    }


    private String getType(JSONObject j){
        String type = null;
        try {
            type = j.getString(Config1.TAG_TYPE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return type;
    }

    private String getLink(JSONObject j){
        String username = null;
        try {
            username = j.getString(Config1.TAG_LINK);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return username;
    }


    private String getMessage(JSONObject j){
        String sex = null;
        try {
            sex = j.getString(Config1.TAG_MESSAGE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sex;
    }

}
