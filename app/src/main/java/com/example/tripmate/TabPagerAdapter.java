/* 내가 수정할 부분 */

package com.example.tripmate;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.tripmate.Board.fragmentBoard;
import com.example.tripmate.Chat.fragmentChatroom;


//FragmentStatePagerAdapter
public class TabPagerAdapter extends FragmentPagerAdapter {
    //  private final ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private int count;
    public TabPagerAdapter(FragmentManager fm, int count) {
        super(fm);
        this.count = count;
    }


    @Override
    public Fragment getItem(int position) {

       /* String nickname = fragmentActivity4.getNickname();
        Bundle bundle = new Bundle();
        bundle.putString("nickname",nickname);*/

        //fragmentFriend tabFragment1 = new fragmentFriend();
        fragmentBoard tabFragment2 = fragmentBoard.getInstance();
        fragmentChatroom tabFragment3 = fragmentChatroom.getInstance();

        switch (position) {
          /*  case 0:
                return tabFragment1;*/

            case 0:
                //tabFragment2.setArguments(bundle);
                return tabFragment2;

            case 1:
                return tabFragment3;

            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return count;
    }
}
