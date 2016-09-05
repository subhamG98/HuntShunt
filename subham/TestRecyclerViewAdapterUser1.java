package com.github.florent37.materialviewpager.subham;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

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
public class TestRecyclerViewAdapterUser1 extends RecyclerView.Adapter<TestRecyclerViewAdapterUser1.ViewHolder> {

    private final Activity context;

    static final int TYPE_CELL_USER = 0;

    private int flag = 1;

    FrameLayout abcd;


    private ArrayList<Data_Model_User> arrayList;

    public TestRecyclerViewAdapterUser1(Activity context, ArrayList<Data_Model_User> arrayList) {
        super();
        this.context = context;
        this.arrayList=arrayList;
      //  items = new ArrayList<ListClassUser>();

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
        public ImageView photos;


        public ViewHolder(View itemView) {
            super(itemView);

            photos = (ImageView) itemView.findViewById(R.id.photos);
           abcd=(FrameLayout)itemView.findViewById(R.id.abcdef);
        }
    }








    @Override
    public int getItemViewType(int position) {
        switch (position) {
            default:
                return TYPE_CELL_USER;

        }

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0); }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = null;


        switch (viewType) {
            case TYPE_CELL_USER: {
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_big_user, parent, false);
                 trimCache(context);
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



    public  void deleteimage(final String image) {

        class LoginAsync extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected String doInBackground(String... params) {
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("image",image));
                nameValuePairs.add(new BasicNameValuePair("username",SplashScreen.username1));
                Log.i("deleteimage", "" + image + "-" + SplashScreen.username1);

                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://resolvefinance.co.uk/Whatsapp/deleteimage.php");

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
                Intent i1=new Intent("com.github.florent37.materialviewpager.subham.MainActivity");
                context.startActivity(i1);

            }
        }

        LoginAsync la = new LoginAsync();
        la.execute(image);

    }



    @Override
    public void onBindViewHolder(TestRecyclerViewAdapterUser1.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case TYPE_CELL_USER:
                final Data_Model_User model = arrayList.get(position);


                Picasso.with(context).load(model.getImage() ).resize(100,100).into(holder.photos);

                holder.photos.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(context);
                                myAlertDialog.setTitle("Confirm Deletion");
                                myAlertDialog.setMessage("Are you sure you want to delete this picture?");

                                myAlertDialog.setNegativeButton("No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface arg0, int arg1) {

                                            }
                                        });

                                myAlertDialog.setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface arg0, int arg1) {

                                                deleteimage(model.getImage());

                                            }
                                        });

                                myAlertDialog.show();

}
                        }
                );


                break;

            }

    }
}