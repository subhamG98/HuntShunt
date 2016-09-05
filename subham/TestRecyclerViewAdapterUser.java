package com.github.florent37.materialviewpager.subham;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class TestRecyclerViewAdapterUser extends RecyclerView.Adapter<TestRecyclerViewAdapterUser.ViewHolder> {

    private final Activity context;

    static final int TYPE_CELL_USER = 0;

    private int flag = 1;



    private ArrayList<Data_Model_User> arrayList;

    public TestRecyclerViewAdapterUser(Activity context, ArrayList<Data_Model_User> arrayList) {
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





    @Override
    public void onBindViewHolder(TestRecyclerViewAdapterUser.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case TYPE_CELL_USER:
                final Data_Model_User model = arrayList.get(position);


                Picasso.with(context).load(model.getImage() ).resize(100,100).into(holder.photos);


                break;

            }

    }
}