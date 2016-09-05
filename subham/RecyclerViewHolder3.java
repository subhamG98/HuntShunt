package com.github.florent37.materialviewpager.subham;
 
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by SONU on 25/09/15.
 */
public class RecyclerViewHolder3 extends RecyclerView.ViewHolder  {
    // View holder for gridview recycler view as we used in listview
 //   public TextView title;
    public ImageView imageview;
 
 
 
 
    public RecyclerViewHolder3(View view) {
        super(view);
        // Find all views ids
 
   //     this.title = (TextView) view
     //           .findViewById(R.id.title);
        this.imageview = (ImageView) view
                .findViewById(R.id.image);
 
 
    }
 
 
 
}