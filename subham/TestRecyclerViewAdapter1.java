package com.github.florent37.materialviewpager.subham;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.session.MediaController;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class TestRecyclerViewAdapter1 extends RecyclerView.Adapter<TestRecyclerViewAdapter1.ViewHolder> {

    private final Activity context;

    String videoPath="";
    static final int TYPE_CELL = 0;
    static final int TYPE_CELL1= 1;

    static final int TYPE_CELL2 = 2;
    static final int TYPE_CELL3= 3;

    static final int TYPE_CELL4 = 4;
    static final int TYPE_CELL5= 5;

    List<ListClass1> items;
    private int flag = 1;

    FrameLayout vimp;
    FrameLayout vimp1;
    static String video1="";

    public static String clicked_on_username = "";
    public static String clicked_on_name = "";
    RelativeLayout def;

    private RecyclerView mRecyclerView1;
    private RecyclerView.Adapter mAdapter1;

    // Progress Dialog
//    private ProgressDialog pDialog;
    // Progress dialog type (0 - for Horizontal progress bar)
  //  public static final int progress_bar_type = 0;

    DisplayMetrics dm;
    SurfaceView sur_View;
    MediaController media_Controller;



    public TestRecyclerViewAdapter1(Activity context, String[] username, String[] message,String[] type,String[] link) {
        super();
        this.context = context;
        items = new ArrayList<ListClass1>();
        for (int i = 0; i < username.length; i++) {
            ListClass1 item = new ListClass1();
            item.setUsername(username[i]);
            item.setMessage(message[i]);
            item.setType(type[i]);
            item.setLink(link[i]);

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
        public TextView username;
        public TextView message;
        public ImageView image;
        VideoView video_player_view;
        public ImageView thumbnail_micro;
        public RecyclerView recyclerview;


        public ViewHolder(View itemView) {
            super(itemView);

//            username = (TextView) itemView.findViewById(R.id.username);
            message=(TextView)itemView.findViewById(R.id.message);
            image=(ImageView)itemView.findViewById(R.id.image);
          //  video_player_view=(VideoView)itemView.findViewById(R.id.video_player_view);
             thumbnail_micro = (ImageView)itemView.findViewById(R.id.thumbnail_micro);

        }
    }








    @Override
    public int getItemViewType(int position) {
        ListClass1 list =  items.get(position);

        Log.i("", "last i think"+list.getUsername());

        if(list.getUsername().equals(SplashScreen.username1))
        {
          if(list.getType().equals("image"))
          {
              return TYPE_CELL3;
          }
          else if(list.getType().equals("video"))
          {
              return TYPE_CELL4;
          }
            else {
              return TYPE_CELL1;
          }
        }
        else
        {

            if(list.getType().equals("image"))
            {
                return TYPE_CELL2;
            }
            else if(list.getType().equals("video"))
            {
                return TYPE_CELL5;
            }
            else {
                return TYPE_CELL;
            }

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
            case TYPE_CELL: {
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_small2, parent, false);
                trimCache(context);

                ViewHolder viewHolder = new ViewHolder(v);
                return viewHolder;
            }
            case TYPE_CELL1: {
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_small3, parent, false);
                trimCache(context);
//3
                ViewHolder viewHolder = new ViewHolder(v);
                return viewHolder;
            }

            case TYPE_CELL2: {
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_big4, parent, false);
                trimCache(context);

                vimp=(FrameLayout)v.findViewById(R.id.vimp);
                ViewHolder viewHolder = new ViewHolder(v);
                return viewHolder;
            }
            case TYPE_CELL3: {
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_big3, parent, false);

                trimCache(context);
                vimp=(FrameLayout)v.findViewById(R.id.vimp);

                ViewHolder viewHolder = new ViewHolder(v);
                return viewHolder;
            }


            case TYPE_CELL4: {
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_big5, parent, false);

                trimCache(context);
                vimp1=(FrameLayout)v.findViewById(R.id.vimp1);

                ViewHolder viewHolder = new ViewHolder(v);
                return viewHolder;
            }


            case TYPE_CELL5: {
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_big6, parent, false);

                trimCache(context);
                vimp1=(FrameLayout)v.findViewById(R.id.vimp1);

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
    public void onBindViewHolder(final TestRecyclerViewAdapter1.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {


            case TYPE_CELL:
                final ListClass1 list =  items.get(position);
  //              holder.username.setText(list.getUsername());
                holder.message.setText(list.getMessage());


                break;

            case TYPE_CELL1:
                final ListClass1 list1 =  items.get(position);
    //            holder.username.setText(list1.getUsername());
                holder.message.setText(list1.getMessage());

                break;


            case TYPE_CELL2:
                final ListClass1 list2 =  items.get(position);
                Picasso.with(context).load("" +list2.getLink()).resize(100, 100).into(holder.image);
                vimp.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                             //   new DownloadFileFromURL().execute(list2.getLink());

                            }
                        }
                );


                break;

            case TYPE_CELL3:
                final ListClass1 list3 =  items.get(position);
                Picasso.with(context).load("" +list3.getLink() ).resize(100, 100).into(holder.image);
                vimp.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                           //     new DownloadFileFromURL().execute(list3.getLink());

                            }
                        }
                );

                break;

            case TYPE_CELL4:
                final ListClass1 list4 =  items.get(position);
                vimp1.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                video1=list4.getLink();

                                Intent myIntent = new Intent("com.github.florent37.materialviewpager.subham.VideoViewActivity");
                                context.startActivity(myIntent);                          }
                        }
                );

                break;
            case TYPE_CELL5:
                final ListClass1 list5 =  items.get(position);
                vimp1.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                video1=list5.getLink();


                                Intent myIntent = new Intent("com.github.florent37.materialviewpager.subham.VideoViewActivity");
                                context.startActivity(myIntent);                          }

                        }
                );

                break;



        }

    }
}