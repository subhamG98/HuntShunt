package com.github.florent37.materialviewpager.subham;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by SONU on 25/09/15.
 */
public class RecyclerView_Adapter3 extends
        RecyclerView.Adapter<RecyclerViewHolder3> {// Recyclerview will extend to
    // recyclerview adapter
    private ArrayList<Data_Model> arrayList;
    private Context context;
    LinearLayout abcd;

    public RecyclerView_Adapter3(Context context,
                                ArrayList<Data_Model> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder3 holder, int position) {
        final Data_Model model = arrayList.get(position);

        RecyclerViewHolder3 mainHolder = (RecyclerViewHolder3) holder;// holder

        Bitmap image = BitmapFactory.decodeResource(context.getResources(),
                model.getImage());// This will convert drawbale image into
        // bitmap


        // setting title
       // mainHolder.title.setText(model.getTitle());
        trimCache(context);
        mainHolder.imageview.setImageBitmap(Bitmap.createScaledBitmap(image, 80, 80, false));
        mainHolder.imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(context, "clicked on" + model.getTitle(), Toast.LENGTH_LONG).show();
                        Explore.onCall(model.getTitle());

                    }
                }
        );
        abcd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
  //              Toast.makeText(context, "clicked on" + model.getTitle(), Toast.LENGTH_LONG).show();
    //            Explore.onCall(model.getTitle());
            }
        });




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
    public RecyclerViewHolder3 onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());
        trimCache(context);
        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.item_row3, viewGroup, false);
        RecyclerViewHolder3 listHolder = new RecyclerViewHolder3(mainGroup);
        abcd=(LinearLayout)mainGroup.findViewById(R.id.abcd);

        return listHolder;

    }


}