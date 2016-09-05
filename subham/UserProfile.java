package com.github.florent37.materialviewpager.subham;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class UserProfile extends ActionBarActivity {

//    private GestureDetectorCompat gestureDetectorCompat;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    ImageView img;
    ImageView friend;

    private RecyclerView mRecyclerView;
    private TestRecyclerViewAdapterUser mAdapter;
    ArrayList<Data_Model_User> arrayList;
    ProgressBar marker_progress2;

    TextView username;
    TextView status;
    TextView age;
    TextView location;
    TextView hobbies;
    TextView about;
    ImageView imageView2;

    ConfigUser config;
    ConfigUser1 config1;

    String clickedon="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        marker_progress2=(ProgressBar)findViewById(R.id.marker_progress1);
        Intent i=getIntent();
        clickedon=i.getStringExtra("clickedon");

        img=(ImageView)findViewById(R.id.img);
        friend=(ImageView)findViewById(R.id.friend);
        username=(TextView)findViewById(R.id.namedp);
        status=(TextView)findViewById(R.id.status);
        age=(TextView)findViewById(R.id.age);
        location=(TextView)findViewById(R.id.location);
        hobbies=(TextView)findViewById(R.id.hobbies);
        about=(TextView)findViewById(R.id.about);
        imageView2=(ImageView)findViewById(R.id.imageView2);
        imageView2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );

        getData2();
        gestureDetector = new GestureDetector(new MyGestureDetector());
        View mainview = (View) findViewById(R.id.mainView);
            // Set the touch listener for the main view to be our custom gesture listener
            mainview.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    if (gestureDetector.onTouchEvent(event)) {
                        return true;
                    }
                    return false;
                }
            });

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerViewUser);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager1);
        mRecyclerView.setHasFixedSize(true);


        getData21();



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
                    URL url = new URL("http://resolvefinance.co.uk/Whatsapp/productuser.php?username="+clickedon);
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


        TestRecyclerViewAdapterUser adapter = new TestRecyclerViewAdapterUser(this, arrayList);
        mRecyclerView.setAdapter(adapter);// set adapter on recyclerview
        adapter.notifyDataSetChanged();// Notify the adapter




    }

    private void parseJSON1(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            Log.i("","array imp"+array);

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
                    URL url = new URL("http://resolvefinance.co.uk/Whatsapp/aboutuser.php?username="+SplashScreen.username1+"&fname="+clickedon);
                    Log.i("ok check it23", "http://resolvefinance.co.uk/Whatsapp/getlist.php?username="+SplashScreen.username1+"&username1="+clickedon);
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
        Picasso.with(this).load("http://resolvefinance.co.uk/Whatsapp/uploads/" +ConfigUser.checkfriend[0]).resize(50, 50).into(friend);
        if(ConfigUser.checkfriend[0].equals("1"))
        {
           friend.setImageResource(R.drawable.f1);
            friend.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            remove_friend(SplashScreen.username1, clickedon, "0");
                            friend.setImageResource(R.drawable.f3);


                            //f3

                        }
                    }
            );


        }
        else if(ConfigUser.checkfriend[0].equals("0")||ConfigUser.checkfriend[0].equals("4"))
        {
            friend.setImageResource(R.drawable.f21);
            friend.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(UserProfile.this);
                            myAlertDialog.setTitle("Action for Pending Request");
                            myAlertDialog.setMessage("What do you want to do with this Friend Request");

                            myAlertDialog.setNegativeButton("Accept",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface arg0, int arg1) {

                                            add_friend(SplashScreen.username1, clickedon, "0");
                                            friend.setImageResource(R.drawable.f1);



                                        }
                                    });

                            myAlertDialog.setPositiveButton("Delete",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            remove_friend(SplashScreen.username1, clickedon, "0");
                                            friend.setImageResource(R.drawable.f3);


                                        }
                                    });

                            myAlertDialog.show();






                        }
                    }
            );


        }
        else
        {
            friend.setImageResource(R.drawable.f3);

            friend.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            add_friend(SplashScreen.username1, clickedon, "0");
                            friend.setImageResource(R.drawable.f21);


                        }
                    }
            );



        }

    }



    private void remove_friend(String username,String fname, final String no){
        class GetData extends AsyncTask<Void,Void,String> {
            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //       progressDialog = ProgressDialog.show(, "Fetching Data", "Please wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);


            }

            @Override
            protected String doInBackground(Void... params) {
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL("http://resolvefinance.co.uk/Whatsapp/removefriend.php?username="+SplashScreen.username1+"&fname="+clickedon+"&no="+no);
                    Log.i("ok check it23", "http://resolvefinance.co.uk/Whatsapp/getlist.php?username="+SplashScreen.username1+"&username1="+clickedon);
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





    private void add_friend(String username,String fname, final String no){
        class GetData extends AsyncTask<Void,Void,String> {
            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //       progressDialog = ProgressDialog.show(, "Fetching Data", "Please wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

            }

            @Override
            protected String doInBackground(Void... params) {
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL("http://resolvefinance.co.uk/Whatsapp/addfriend.php?username="+SplashScreen.username1+"&fname="+clickedon+"&no="+no);
                    Log.i("ok check it23", "http://resolvefinance.co.uk/Whatsapp/getlist.php?username="+SplashScreen.username1+"&username1="+clickedon);
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








    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
                    return false;
                }

                // right to left swipe
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Intent intent = new Intent(UserProfile.this.getBaseContext(), chat_page1.class);

                    startActivity(intent);
                    UserProfile.this.overridePendingTransition(
                            R.anim.slide_in_right,
                            R.anim.slide_out_left
                    );
                    // right to left swipe
                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//                    startActivity(intent);
                    UserProfile.this.overridePendingTransition(
                            R.anim.slide_in_left,
                            R.anim.slide_out_right
                    );
                }

                return false;
            }

            // It is necessary to return true from onDown for the onFling event to register
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }
        }

    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        //handle 'swipe left' action only

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

         /*
         Toast.makeText(getBaseContext(),
          event1.toString() + "\n\n" +event2.toString(),
          Toast.LENGTH_SHORT).show();
         */
/*
            if(event2.getX() < event1.getX()){
                Toast.makeText(getBaseContext(),
                        "Swipe left - startActivity()",
                        Toast.LENGTH_SHORT).show();

                //switch another activity
                Intent intent = new Intent(
                        UserProfile.this, chat_page1.class);
                startActivity(intent);
            }

            return true;
        }
    }


*/



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
