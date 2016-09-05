package com.github.florent37.materialviewpager.subham;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class TestRecyclerViewAdapter11 extends RecyclerView.Adapter<TestRecyclerViewAdapter11.ViewHolder> {

    private final Activity context;

    static final int TYPE_HEADER = 0;
    static final int TYPE_HEADER1 = 1;

    static final int TYPE_CELL = 2;
    static final int TYPE_CELL1 = 3;

    List<ListClass> items;
    private int flag = 1;

    ProgressBar marker_progress2;


    public static String clicked_on_username = "";
    public static String clicked_on_name = "";
    RelativeLayout def;

    private RecyclerView mRecyclerView1;
    private RecyclerView.Adapter mAdapter1;

    private static final int ITEM_COUNT = 100;

    private List<Object> mContentItems = new ArrayList<>();
    public static final String[] TITLES= {"Hood","Full Sleeve Shirt","Shirt","Jean Jacket","Jacket"};
    public static final Integer[] IMAGES= {R.drawable.scraap,R.drawable.mobile,R.drawable.mobile,R.drawable.mobile,R.drawable.mobile,};

  public static String headerphoto="";

    public TestRecyclerViewAdapter11(Activity context, String[] title_friend, String[] fullname, String[] sex, String[] distance, String[] type, String[] image) {
        super();
        this.context = context;
        items = new ArrayList<ListClass>();
        for (int i = 0; i < title_friend.length; i++) {
            ListClass item = new ListClass();
            item.setTitle_friend(title_friend[i]);
            item.setFullname(fullname[i]);
            item.setSex(sex[i]);
            item.setDistance(distance[i]);
            item.setType(type[i]);
            item.setImage(image[i]);

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
        public OvalImageView dpImage_scapp_friend;
        public TextView title_friend;
        public TextView distance;
        public ImageView sex;
        public ImageView image;
        public TextView name;

        public RecyclerView recyclerview;


        public ViewHolder(View itemView) {
            super(itemView);

            dpImage_scapp_friend = (OvalImageView) itemView.findViewById(R.id.dpImage_scapp_friend);
            title_friend = (TextView) itemView.findViewById(R.id.username);
            distance=(TextView)itemView.findViewById(R.id.distance);
            sex=(ImageView)itemView.findViewById(R.id.sex);
            image=(ImageView)itemView.findViewById(R.id.image);

            name=(TextView)itemView.findViewById(R.id.name);

            recyclerview=(RecyclerView)itemView.findViewById(R.id.recyclerView3);
        }
    }








    @Override
    public int getItemViewType(int position) {
        final ListClass list =  items.get(position);
        switch (position) {
            default:
                Log.i("promotohai", "" + list.getType());
                if(list.getType().equals("promochat")){
                    Log.i("promotohai", "" + list.getType());
                    return TYPE_CELL1;
                }else{
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
                        .inflate(R.layout.list_item_card_big1, parent, false);
                mRecyclerView1 = (RecyclerView) v.findViewById(R.id.recyclerView3);
                RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false);
                mRecyclerView1.setLayoutManager(layoutManager1);
                mRecyclerView1.setHasFixedSize(true);

                ArrayList<Data_Model> arrayList = new ArrayList<>();
                for (int i = 0; i < TITLES.length; i++) {
                    arrayList.add(new Data_Model(TITLES[i],IMAGES[i]));
                }
                RecyclerView_Adapter3 adapter = new RecyclerView_Adapter3(context, arrayList);
                mRecyclerView1.setAdapter(adapter);// set adapter on recyclerview
                adapter.notifyDataSetChanged();// Notify the adapter

                 MaterialViewPagerHelper.registerRecyclerView(context, mRecyclerView1, null);

                ViewHolder viewHolder = new ViewHolder(v);
                return viewHolder;


            }

            case TYPE_CELL: {
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_small, parent, false);
                def = (RelativeLayout) v.findViewById(R.id.grgg);
                trimCache(context);

                ViewHolder viewHolder = new ViewHolder(v);
                return viewHolder;
            }

            case TYPE_CELL1: {
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_big2, parent, false);
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





    @Override
    public void onBindViewHolder(TestRecyclerViewAdapter11.ViewHolder holder, final int position) {

        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                break;
            case TYPE_HEADER1:
                break;


            case TYPE_CELL:
                final ListClass list =  items.get(position);
                Picasso.with(context).load("http://resolvefinance.co.uk/Whatsapp/uploads/" +list.getSex() + ".png").resize(50,50).into(holder.sex);

                    Picasso.with(context).load(list.getImage()).resize(50, 50).into(holder.dpImage_scapp_friend);

                    holder.title_friend.setText(list.getTitle_friend());
                    holder.distance.setText(list.getDistance());


                    def.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ListClass list =  items.get(position);
                            clicked_on_username = list.getTitle_friend();
                            clicked_on_name = list.getFullname();
                            Toast.makeText(context, "clicked on" + clicked_on_name + " " + clicked_on_username, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent("com.github.florent37.materialviewpager.subham.UserProfile");
                            intent.putExtra("clickedon", clicked_on_username);
                            context.startActivity(intent);


                        }
                    });
                     trimCache(context);
                break;

            case TYPE_CELL1:
                final ListClass list1 =  items.get(position);
            //    marker_progress2.setVisibility(View.VISIBLE);
                Picasso.with(context).load("" + list1.getImage()).resize(150, 150).into(holder.image);

                holder.name.setText(list1.getTitle_friend());
                trimCache(context);



                break;
        }

    }
}