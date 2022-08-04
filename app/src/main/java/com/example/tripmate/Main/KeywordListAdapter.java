/* 대구 -> 관광지리스트 보여주는 어뎁터 */

package com.example.tripmate.Main;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tripmate.Plan.PlanTripActivity;
import com.example.tripmate.R;
import com.example.tripmate.TourAPI.TripDataInfo;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class KeywordListAdapter extends BaseAdapter  {
    public ArrayList<TripDataInfo> listViewItemList = new ArrayList<TripDataInfo>();

    public KeywordListAdapter() {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();

        if(convertView ==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_keyword_search_item, parent, false);
        }

        //화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView titleImage = (ImageView) convertView.findViewById(R.id.titleImage);
        TextView textTitle = (TextView) convertView.findViewById(R.id.textTitle);
        TextView text_adress = (TextView) convertView.findViewById(R.id.text_adress);
        TextView content_title = (TextView) convertView.findViewById(R.id.content_title);

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

}
