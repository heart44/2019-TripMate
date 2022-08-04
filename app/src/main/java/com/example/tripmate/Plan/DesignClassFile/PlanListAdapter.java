package com.example.tripmate.Plan.DesignClassFile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.tripmate.Plan.DataClassFile.PlanListModel;
import com.example.tripmate.Plan.HttpPlanList;
import com.example.tripmate.Plan.HttpPlanListDelete;
import com.example.tripmate.Plan.PlanTripActivity;
import com.example.tripmate.Plan.UpdateListActivity;
import com.example.tripmate.R;
import com.example.tripmate.SaveSharedPreference;
import com.example.tripmate.fragmentActivity2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PlanListAdapter extends RecyclerView.Adapter<PlanListViewHolder>  {
    private ArrayList<PlanListModel> planlist;

    private ItemClickListener ItemCheckListener;

    public PlanListAdapter() {
    }
    public PlanListAdapter(ArrayList<PlanListModel> dataList)
    {
        this.planlist = dataList;
    }

    public PlanListAdapter(ArrayList<PlanListModel> dataList, ItemClickListener listener) {
        this.planlist = dataList;
        this.ItemCheckListener = listener;
    }

    @NonNull
    @Override
    public PlanListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.plan_card, parent, false);
        PlanListViewHolder viewHolder = new PlanListViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlanListViewHolder planListViewHolder, int position) {
        final int pos = position;
        planListViewHolder.txtPlace.setText(planlist.get(pos).getPlanPlace());
        planListViewHolder.txtTitle.setText(planlist.get(pos).getPlanTitle());
        planListViewHolder.txtStart.setText(planlist.get(pos).getPlanStart());
        planListViewHolder.txtEnd.setText(planlist.get(pos).getPlanEnd());

        planListViewHolder.viewer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String code = planlist.get(pos).getPlanCode();
                final String place = planlist.get(pos).getPlanPlace();
                final String start = planlist.get(pos).getPlanStart();
                final String end = planlist.get(pos).getPlanEnd();

                Intent intent = new Intent(v.getContext(), PlanTripActivity.class);
                intent.putExtra("plancode", code);
                intent.putExtra("place", place);
                intent.putExtra("startDate", start);
                intent.putExtra("endDate", end);
                v.getContext().startActivity(intent);
            }
        });

        //수정/삭제 다이얼로그
        final String[] list = {"수정", "삭제"};
        planListViewHolder.viewer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                final String code = planlist.get(pos).getPlanCode();
                final String place = planlist.get(pos).getPlanPlace();
                final String title = planlist.get(pos).getPlanTitle();
                final String start = planlist.get(pos).getPlanStart();
                final String end = planlist.get(pos).getPlanEnd();
                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("옵션 선택");
                builder.setItems(list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0: //수정
                                Intent intent = new Intent(v.getContext(), UpdateListActivity.class);
//                                intent.putExtra("nickname", nickname);
                                intent.putExtra("code", code);
                                intent.putExtra("place", place);
                                intent.putExtra("title", title);
                                intent.putExtra("start", start);
                                intent.putExtra("end", end);
                                v.getContext().startActivity(intent);
                                break;
                            case 1: //삭제
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
                                builder1.setTitle("삭제").setMessage("삭제하시겠습니까?");
                                builder1.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                                builder1.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String result = null;
                                        HttpPlanListDelete httpPlanListDelete = new HttpPlanListDelete();
                                        HttpPlanListDelete.SendTask sendTask = httpPlanListDelete.new SendTask();
                                        try{
                                            result = sendTask.execute(code).get();
                                            if("success".equals(result)){
                                                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                                builder.setTitle("삭제").setMessage("삭제 되었습니다.");
                                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.dismiss();
                                                        fragmentActivity2.getInstance().init();
                                                    }
                                                });
                                                builder.create().show();
                                            }
                                        } catch (ExecutionException | InterruptedException e) { e.printStackTrace(); }
                                    }
                                }); builder1.show();
                        } dialog.dismiss();
                    }
                }); builder.show();
                return true;
            }
        });

        Switch swt = planListViewHolder.viewer.findViewById(R.id.plan_share_btn);
        swt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ItemCheckListener.onItemCheked(pos);
                } else {
                    ItemCheckListener.onItemUncheked(pos);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return planlist.size();
    }

    public interface ItemClickListener {
        void onItemCheked(int position);

        void onItemUncheked(int position);
    }

}