package com.example.jobndirangu.schoolportalms.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobndirangu.schoolportalms.Interface.ItemClickListner;
import com.example.jobndirangu.schoolportalms.R;

public class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtEventName, txtEventDescription, txtEventDate;
    public ImageView imageView;
    public ItemClickListner listner;

    public EventViewHolder(View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.event_image);
        txtEventName = (TextView) itemView.findViewById(R.id.event_name);
        txtEventDescription = (TextView) itemView.findViewById(R.id.event_description);
        txtEventDate = (TextView) itemView.findViewById(R.id.event_date);

    }

    public void setItemClickListner(ItemClickListner listner) {
        this.listner = listner;
    }

    @Override
    public void onClick(View view)
    {
        listner.onClick(view,getAdapterPosition(),false);
    }
}
