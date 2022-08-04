package com.example.tripmate.Chat;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tripmate.Board.BoardListAdapter;
import com.example.tripmate.Board.fragmentBoard;
import com.example.tripmate.R;


public class fragmentChatroom extends Fragment {
    private static fragmentChatroom instance;
    private static ChatroomListAdapter adapter;
    private static RecyclerView recyclerView;
    @SuppressLint("ValidFragment")

    private fragmentChatroom() { }

    public static fragmentChatroom getInstance() {
        if (instance == null) {
            instance = new fragmentChatroom();
            return instance;
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatroom, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.chatroomfragment_recyclerview);
        init();
        return view;
    }

    public void init() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new ChatroomListAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public String getTitle() {
        return "채팅리스트";
    }
}