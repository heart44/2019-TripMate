package com.example.tripmate.Chat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tripmate.R;

public class ChatroomListViewHolder extends RecyclerView.ViewHolder {


    public ImageView imageView;
    public TextView textView_title;
    public TextView textView_last_message;
    public TextView textView_timestamp;

    public ChatroomListViewHolder(View view) {
        super(view);

        imageView = (ImageView) view.findViewById(R.id.chatitem_imageview);
        textView_title = (TextView) view.findViewById(R.id.chatitem_textview_title);
        textView_last_message = (TextView) view.findViewById(R.id.chatitem_textview_lastMessage);
        textView_timestamp = (TextView) view.findViewById(R.id.chatitem_textview_timestamp);
    }
}

