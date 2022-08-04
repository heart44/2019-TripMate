package com.example.tripmate.Share;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tripmate.R;

import java.util.ArrayList;

public class ShareRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

    private ArrayList<TripRouteDataInfo> tripRoute;
    private ItemClickListener mItemClickListener;

    public ShareRecyclerAdapter(ArrayList<TripRouteDataInfo> dataList, ItemClickListener listener) {
        this.tripRoute = dataList;
        mItemClickListener = listener;
    }

    public void addItemClickListener(ItemClickListener listener) {
        mItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.share_recycler_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.share_title.setText(tripRoute.get(position).getTripTitle());
        viewHolder.share_location_title.setText(tripRoute.get(position).getTripLocation());
        viewHolder.share_content.setText(tripRoute.get(position).getTripDescribe());

        Button btn = viewHolder.itemView.findViewById(R.id.share_route_get_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tripRoute.size();
    }

    //리스너를 위한 인터페이스
    public interface ItemClickListener {
        void onItemClick(int position);
    }
}

