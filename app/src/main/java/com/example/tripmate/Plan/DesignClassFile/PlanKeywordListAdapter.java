package com.example.tripmate.Plan.DesignClassFile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tripmate.R;
import com.example.tripmate.TourAPI.TripDataInfo;

import java.util.ArrayList;

public class PlanKeywordListAdapter extends BaseAdapter {
    public ArrayList<TripDataInfo> listViewItemList = new ArrayList<TripDataInfo>();
    private ItemClickListener mItemClickListener;

    public PlanKeywordListAdapter() {
    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void addItemClickListener(ItemClickListener listener) {
        mItemClickListener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();

        if(convertView ==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_keyword_search_item, parent, false);
        }

        //화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView titleImage = convertView.findViewById(R.id.titleImage);
        TextView textTitle = convertView.findViewById(R.id.textTitle);
        TextView text_adress = convertView.findViewById(R.id.text_adress);
        TextView content_title = convertView.findViewById(R.id.content_title);
        Button placeAddBtn = convertView.findViewById(R.id.trip_add_btn);

        //Data Seet(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final TripDataInfo listViewItem = listViewItemList.get(position);

        /* 해당 그릇에 담긴 정보들을 커스텀 리스트뷰 xml의 각 TextView에 뿌려줌 */
        if(listViewItem.getImgURL() == "이미지가 없음") {
            titleImage.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(context).load(listViewItem.getImgURL()).into(titleImage);
        }

        if(listViewItem.getTitle() == "지정관광지명 없음") {
            textTitle.setText("장소명 불명");
        } else {
            textTitle.setText(listViewItem.getTitle());
        }

        text_adress.setText(listViewItem.getAddress1());
        content_title.setText("관광명소");

        Button button1 = convertView.findViewById(R.id.trip_add_btn);
        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(pos);
                }
            }
        });

        return convertView;
    }

    public void addItem(String title, String address1, String address2, String url, String location) {

        TripDataInfo mItem = new TripDataInfo();

        mItem.setImgURL(url);
        mItem.setTitle(title);
        mItem.setAddress1(address1);
        mItem.setAddress2(address2);
        mItem.setTitle(location);

        /* 데이터그릇 mItem에 담음 */
        listViewItemList.add(mItem);

    }

    //리스너를 위한 인터페이스
    public interface ItemClickListener {
        void onItemClick(int position);
    }
}
