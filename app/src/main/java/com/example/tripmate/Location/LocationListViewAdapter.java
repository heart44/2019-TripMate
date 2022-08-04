package com.example.tripmate.Location;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tripmate.R;

import java.util.ArrayList;
import java.util.Collections;

public class LocationListViewAdapter extends BaseAdapter  {
    private ArrayList<LocationListViewItem> items = new ArrayList<>();

    Bitmap bitmap;

    public LocationListViewAdapter(){

    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context= parent.getContext();

        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.location_listview, parent, false);
        }

        TextView title = convertView.findViewById(R.id.list_title);
        TextView addr = convertView.findViewById(R.id.list_addr);
        TextView tel = convertView.findViewById(R.id.list_tel);
        TextView dist = convertView.findViewById(R.id.list_dist);

        // Button nav_button = (Button)convertView.findViewById(R.id.nav_button);

        ImageView firstimage = convertView.findViewById(R.id.list_firstimage);

        final LocationListViewItem item = items.get(position);


        title.setText(item.getTitle());
        addr.setText(item.getAddress1());
        tel.setText(item.getTel());
        dist.setText(item.getDist() / 1000 + "km");

/*
        Thread mThread = new Thread(){
            @Override
            public void run(){
                try{
                    URL url = new URL(item.getFirstimage());

                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                }catch (IOException ex){

                }
            }
        };
        mThread.start();

        try{
            mThread.join();
            firstimage.setColorFilter(Color.parseColor("#c4b9b9"), PorterDuff.Mode.MULTIPLY);

            firstimage.setImageBitmap(bitmap);



        }catch (InterruptedException e){
        }

*/


        if (item.getFirstimage() == "이미지가 없음") {
            firstimage.setImageResource(R.drawable.no_image);
        } else {
            Glide.with(context).load(item.getFirstimage()).into(firstimage);
        }


        return convertView;
    }

    public void addItem(String title, String address1, String firstimage, String tel, int dist, double mapx , double mapy) {
        LocationListViewItem item = new LocationListViewItem();
        item.setTitle(title);
        item.setAddress1(address1);
        item.setFirstimage(firstimage);
        item.setTel(tel);
        item.setDist(dist);
        item.setMapx(mapx);
        item.setMapy(mapy);
        items.add(item);
    }

    public void sort() {
        Collections.sort(items);
    }

    public void clear() {
        items.clear();
    }

}