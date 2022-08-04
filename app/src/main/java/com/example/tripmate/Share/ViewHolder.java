package com.example.tripmate.Share;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tripmate.R;

public class ViewHolder extends RecyclerView.ViewHolder{

    protected TextView share_title;
    protected Button share_route_get_btn;
    protected TextView share_location_title;
    protected ImageView share_title_img;
    protected TextView share_content;

    protected ImageButton share_like_btn;
    protected TextView share_like_number;
    protected ImageButton share_view_btn ;
    protected TextView share_view_number;

    ViewHolder(View itemView)
    {
        super(itemView);

        share_title = itemView.findViewById(R.id.share_title);
        share_location_title = itemView.findViewById(R.id.share_location_title);
        share_content = itemView.findViewById(R.id.share_content);
    }
}
