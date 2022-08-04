package com.example.tripmate.Chat;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tripmate.R;
import com.example.tripmate.User.HttpUIDToNickname;
import com.example.tripmate.User.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

public class ChatroomListAdapter extends RecyclerView.Adapter<ChatroomListViewHolder> {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
    private List<ChatModel> chatModels = new ArrayList<>();
    private String uid;
    private ArrayList<String> destinationUsers = new ArrayList<>();

    public ChatroomListAdapter() {
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (uid != null) {
            FirebaseDatabase.getInstance().getReference().child("chatrooms").orderByChild("users/" + uid).equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    chatModels.clear();
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        chatModels.add(item.getValue(ChatModel.class));
                    }
                    notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


    }

    public ChatroomListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chatroom,parent,false);


        return new ChatroomListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatroomListViewHolder holder, final int position) {


        final ChatroomListViewHolder customViewHolder = (ChatroomListViewHolder)holder;
        String destinationUid = null;

        // 일일 챗방에 있는 유저를 체크
        for(String user: chatModels.get(position).users.keySet()){
            if(!user.equals(uid)){
                destinationUid = user;
                destinationUsers.add(destinationUid);

            }
        }
        FirebaseDatabase.getInstance().getReference().child("users").child(destinationUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserModel userModel =  dataSnapshot.getValue(UserModel.class);
                Glide.with(customViewHolder.itemView.getContext())
                        .load(userModel.getProfileImageUrl())
                        .apply(new RequestOptions().circleCrop())
                        .into(customViewHolder.imageView);

                customViewHolder.textView_title.setText(userModel.getUserName());



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //메시지를 내림 차순으로 정렬 후 마지막 메세지의 키값을 가져옴

        Map<String,ChatModel.Comment> commentMap = new TreeMap<>(Collections.reverseOrder());
        commentMap.putAll(chatModels.get(position).comments);
        if(commentMap.keySet().toArray().length > 0){


            String lastMessageKey = (String) commentMap.keySet().toArray()[0];
            customViewHolder.textView_last_message.setText(chatModels.get(position).comments.get(lastMessageKey).message);
            //TimeStamp
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
            long unixTime = (long) chatModels.get(position).comments.get(lastMessageKey).timestamp ;
            Date date = new Date(unixTime);
            customViewHolder.textView_timestamp.setText(simpleDateFormat.format(date));

        }

        customViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String result = null;
                HttpUIDToNickname UidToNickname = new HttpUIDToNickname();
                HttpUIDToNickname.sendTask send = UidToNickname.new sendTask();
                try {
                    result = send.execute(destinationUsers.get(position)).get();
                    System.out.println("fragmentchatroom : " + result);
                    Intent intent = new Intent(view.getContext(), MessageActivity.class);
                    intent.putExtra("destinationUid", destinationUsers.get(position));
                    intent.putExtra("destinationNickname", result);

                    view.getContext().startActivity(intent);


                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

        });




    }

    @Override
    public int getItemCount() {
        return chatModels.size();
    }
}