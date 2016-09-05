package com.github.florent37.materialviewpager.subham;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class TestRecyclerViewAdapter4 extends RecyclerView.Adapter<TestRecyclerViewAdapter4.ViewHolder> {

    private final Activity context;

    static final int TYPE_HEADER = 0;
    static final int TYPE_HEADER1 = 1;

    static final int TYPE_CELL = 2;

    List<ListClass3> items;
    private int flag = 1;


    public static String clicked_on_username = "";
    public static String clicked_on_name = "";
    RelativeLayout def;

    private RecyclerView mRecyclerView1;
    private RecyclerView.Adapter mAdapter1;

    private static final int ITEM_COUNT = 100;

    private List<Object> mContentItems = new ArrayList<>();

    String[] IMAGES;

    FrameLayout abnoti;

    public TestRecyclerViewAdapter4(Activity context, String[] sender, String[] receiver,String[] message,String[] image,String[] type) {
        super();
        this.context = context;
        items = new ArrayList<ListClass3>();
        for (int i = 0; i < sender.length; i++) {
            ListClass3 item = new ListClass3();
            item.setSender(sender[i]);
            item.setReceiver(receiver[i]);
            item.setMessage(message[i]);
            item.setImage(image[i]);
            item.setType(type[i]);



            items.add(item);
            flag = 1;
            checkforinternet();

        }
    }


    private void checkforinternet() {
        ConnectivityManager cm2 = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni2 = cm2.getActiveNetworkInfo();
        if (ni2 == null) {
            //Toast.makeText(MainActivity.this, "Not Connected to Internet!!", Toast.LENGTH_LONG).show();
            flag = 0;
            return;
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        public OvalImageView dpImage;
        public TextView noti;
        public RecyclerView recyclerview;


        public ViewHolder(View itemView) {
            super(itemView);

            dpImage = (OvalImageView) itemView.findViewById(R.id.dpImage);
            noti = (TextView) itemView.findViewById(R.id.noti);
            recyclerview=(RecyclerView)itemView.findViewById(R.id.recyclerViewnoti);
             abnoti=(FrameLayout)itemView.findViewById(R.id.abnoti);
        }
    }








    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                final ListClass3 list =  items.get(position);

                Log.i("promotohai11", "" + list.getType());
                if(list.getType().equals("promo")){
                     IMAGES=list.getImage().split(",");
                    return TYPE_HEADER1;
                }else{
                    return TYPE_CELL;}
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = null;


        switch (viewType) {
            case TYPE_HEADER: {
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_big, parent, false);
                def = (RelativeLayout) v.findViewById(R.id.grgg);
                ViewHolder viewHolder = new ViewHolder(v);
                return viewHolder;





            }
            case TYPE_HEADER1:
            {
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_big1noti, parent, false);

                mRecyclerView1 = (RecyclerView)v.findViewById(R.id.recyclerViewnoti);

                ViewHolder viewHolder = new ViewHolder(v);
                return viewHolder;


            }

            case TYPE_CELL: {
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_smallnoti, parent, false);
                def = (RelativeLayout) v.findViewById(R.id.grgg);
                abnoti=(FrameLayout)v.findViewById(R.id.abnoti);

                ViewHolder viewHolder = new ViewHolder(v);
                return viewHolder;
            }

        }

        return null;
    }





    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }




    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                break;
            case TYPE_HEADER1:
                final ListClass3 list1 =  items.get(position);
                RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false);
                mRecyclerView1.setLayoutManager(layoutManager1);
                mRecyclerView1.setHasFixedSize(true);


                ArrayList<Data_Model1> arrayList = new ArrayList<>();

                for (int i = 0; i < IMAGES.length; i++) {
                    arrayList.add(new Data_Model1(IMAGES[i],list1.getSender()));
                }
                RecyclerView_Adapter3Noti adapter = new RecyclerView_Adapter3Noti(context, arrayList);
                mRecyclerView1.setAdapter(adapter);// set adapter on recyclerview
                adapter.notifyDataSetChanged();// Notify the adapter

                MaterialViewPagerHelper.registerRecyclerView(context, mRecyclerView1, null);

                break;

            case TYPE_CELL:
                final ListClass3 list =  items.get(position);
                Picasso.with(context).load("" +list.getImage()).resize(50, 50).error(R.drawable.add_friend).into(holder.dpImage);

                holder.noti.setText(list.getMessage());
                abnoti.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (list.getMessage().contains("message")) {
                                    removenoti(list.getSender(), list.getMessage());
                                     Intent i=new Intent("com.github.florent37.materialviewpager.subham.UserProfile");
                                    i.putExtra("clickedon",list.getSender());
                                    context.startActivity(i);
                                } else {
                                    AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(context);
                                    myAlertDialog.setTitle("New Friend Request");
                                    myAlertDialog.setMessage("Do you want to accept this friend request?");

                                    myAlertDialog.setNegativeButton("No",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    removenoti(list.getSender(), list.getMessage());


                                                }
                                            });

                                    myAlertDialog.setPositiveButton("Yes",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface arg0, int arg1) {

                                                    accept(list.getSender());
                                                    removenoti(list.getSender(), list.getMessage());

                                                }
                                            });

                                    myAlertDialog.show();


                                }

                            }
                        }
                );

                 trimCache(context);

                break;
        }

    }



    private void accept(final String sender) {

        class LoginAsync extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected String doInBackground(String... params) {
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("sender", sender));
                nameValuePairs.add(new BasicNameValuePair("receiver",SplashScreen.username1));

                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://resolvefinance.co.uk/Whatsapp/acceptfriend.php");
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
            }
        }

        LoginAsync la = new LoginAsync();
        la.execute(sender);

    }

    private void removenoti(final String sender,final String message) {

        class LoginAsync extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected String doInBackground(String... params) {
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("sender", sender));
                nameValuePairs.add(new BasicNameValuePair("receiver",SplashScreen.username1));
                nameValuePairs.add(new BasicNameValuePair("message",message));

                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://resolvefinance.co.uk/Whatsapp/removenoti.php");
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
                context.startActivity(i);


            }
        }

        LoginAsync la = new LoginAsync();
        la.execute(sender,message);

    }





}