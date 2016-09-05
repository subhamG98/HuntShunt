package com.github.florent37.materialviewpager.subham;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.NavigationView;

import com.crashlytics.android.Crashlytics;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.github.florent37.materialviewpager.subham.fragment.CarpaccioRecyclerViewFragment;
import com.github.florent37.materialviewpager.subham.fragment.RecyclerView3;
import com.github.florent37.materialviewpager.subham.fragment.RecyclerViewFragment;
import com.github.florent37.materialviewpager.subham.fragment.RecyclerViewFragment1;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    private MaterialViewPager mViewPager;

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    SharedPreferences pref1;
    ImageView profile_image;
    TextView username;
    RelativeLayout relativeLayout;

    private Config4 config;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       

        if (!BuildConfig.DEBUG)
            Fabric.with(this, new Crashlytics());

        setTitle("");

        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

        toolbar = mViewPager.getToolbar();
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (toolbar != null) {
            setSupportActionBar(toolbar);

            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
            }
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
        mDrawer.setDrawerListener(mDrawerToggle);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);



        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {

                    case R.id.home:
                        Intent i21 = new Intent("com.github.florent37.materialviewpager.subham.MainActivity");
                        startActivity(i21);
                        return true;

                    case R.id.logout:
                        pref1 = getSharedPreferences("ActivityPREF1", Context.MODE_PRIVATE);
                        SharedPreferences.Editor ed = pref1.edit();
                        ed.putBoolean("activity_executed", false);
                        ed.commit();
                        Intent i22 = new Intent("com.github.florent37.materialviewpager.subham.MainActivity2");
                        startActivity(i22);
                        return true;


                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.inbox:
                        Intent i2 = new Intent("com.github.florent37.materialviewpager.subham.UserProfile1");
                        startActivity(i2);

                        return true;

                    // For rest of the options we just show a toast on click

                    case R.id.starred:
                        Intent i = new Intent("com.github.florent37.materialviewpager.subham.Explore");
                        startActivity(i);
                        //Toast.makeText(getApplicationContext(),"Stared Selected",Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.drafts:
                        Intent i1 = new Intent("com.github.florent37.materialviewpager.subham.Contacts");
                        startActivity(i1);
                       // Toast.makeText(getApplicationContext(), "Drafts Selected", Toast.LENGTH_SHORT).show();
                        return true;

                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }






            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();



        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position % 3) {

                    case 0:
                        return RecyclerViewFragment.newInstance();
                    case 1:
                        return RecyclerView3.newInstance();
                    case 2:
                        return RecyclerViewFragment1.newInstance();

                    default:
                        return CarpaccioRecyclerViewFragment.newInstance();
                }
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 3) {
                    case 0:
                        return "NEARBY PEOPLE";
                    case 1:
                        return "WHATS NEW";
                    case 2:
                        return "FRIENDS";

                }
                return "";
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                "http://resolvefinance.co.uk/Whatsapp/uploads/love.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.red,
                                "http://resolvefinance.co.uk/Whatsapp/uploads/love2.jpg");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.green,
                                "http://resolvefinance.co.uk/Whatsapp/uploads/love1.jpg");



                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        View logo = findViewById(R.id.logo_white);

        if (logo != null)
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();
                    Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
                }
            });



        relativeLayout = (RelativeLayout)findViewById(R.id.drawerheader);

        username= (TextView)relativeLayout.findViewById(R.id.email);
        profile_image= (ImageView)relativeLayout.findViewById(R.id.profile_image);
        username.setText((SplashScreen.username1).toUpperCase());
        getDrawer();









    }
    private void getDrawer(){
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
                    getDrawer();
                    e.printStackTrace();
                }


            }

            @Override
            protected String doInBackground(Void... params) {
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL("http://resolvefinance.co.uk/Whatsapp/getdrawer.php?username="+SplashScreen.username1);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null) {
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    getDrawer();
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
            JSONArray array = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            Log.i("","array imp"+array);

            config = new Config4(array.length());

            for(int i=0; i<array.length(); i++){
                JSONObject j = array.getJSONObject(i);

                Config4.image[i] = getImage(j);



            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Picasso.with(MainActivity.this).load("" +Config4.image[0] ).resize(150, 150).into(profile_image);

    }


    private String getImage(JSONObject j){
        String name = null;
        try {
            name = j.getString(Config4.TAG_IMAGE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return name;
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) ||
                super.onOptionsItemSelected(item);
    }








}
