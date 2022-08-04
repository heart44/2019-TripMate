/* 내가 수정할 부분 */

package com.example.tripmate.Board;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tripmate.R;
import com.example.tripmate.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class BoardListAdapter extends RecyclerView.Adapter<BoardListViewHolder> {

    private ArrayList<BoardModel> boardList;
    private int[] gender = new int[]{
            R.drawable.img_board_man,
            R.drawable.img_board_woman,
            R.drawable.img_board_all};
    private static String nickname;

    public BoardListAdapter(ArrayList<BoardModel> dataList)
    {
        this.boardList = dataList;
    }

    @NonNull
    @Override
    public BoardListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_boardlist, parent, false);
        BoardListViewHolder viewHolder = new BoardListViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BoardListViewHolder viewHolder, int position) {

        final String boardCode = boardList.get(position).getBoardCode();

        if (boardList.get(position).getGender() == 0) {
            viewHolder.gender.setImageResource(gender[0]);
        } else if (boardList.get(position).getGender() == 1) {
            viewHolder.gender.setImageResource(gender[1]);
        } else {
            viewHolder.gender.setImageResource(gender[2]);
        }
        viewHolder.textnickname.setText(boardList.get(position).getNickname());
        viewHolder.date.setText(boardList.get(position).getMatchdate());
        viewHolder.destination.setText(boardList.get(position).getDestination());
        viewHolder.purpose.setText(boardList.get(position).getPurpose());
        viewHolder.minage.setText(String.valueOf(boardList.get(position).getMinage()));
        viewHolder.maxage.setText(String.valueOf(boardList.get(position).getMaxage()));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), BoardViewActivity.class);
                //fragmentBoard fragment = new fragmentBoard();
                //nickname = fragment.getNickname();
                nickname= fragmentBoard.getInstance().getNickname();
                System.out.println("click listener : " + nickname);
                intent.putExtra("nickname", nickname);
                intent.putExtra("boardcode", boardCode);
                view.getContext().startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return boardList.size();
    }

}
