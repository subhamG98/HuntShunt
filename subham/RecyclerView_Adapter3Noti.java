package com.github.florent37.materialviewpager.subham;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by SONU on 25/09/15.
 */
public class RecyclerView_Adapter3Noti extends
        RecyclerView.Adapter<RecyclerView_Adapter3Noti.ViewHolder> {// Recyclerview will extend to
    // recyclerview adapter
    private ArrayList<Data_Model1> arrayList;
    private Context context;

    public RecyclerView_Adapter3Noti(Context context,
                                ArrayList<Data_Model1> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imagenoti;
        public TextView sender;

        public ViewHolder(View itemView) {
            super(itemView);
            imagenoti = (ImageView) itemView.findViewById(R.id.imagenoti);
        sender=(TextView)itemView.findViewById(R.id.sender);
        }
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Data_Model1 model = arrayList.get(position);
        holder.sender.setText(model.getSender());

        Picasso.with(context).load("" + model.getImage()).resize(200, 200).into(holder.imagenoti);




    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = null;

        // This method will inflate the custom layout and return as viewholder
        v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_row3noti, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;

    }


}