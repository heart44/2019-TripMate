package com.example.tripmate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

//가장 처음에 접근하게 되는 인트로 페이지

public class Intro1Activity extends Activity {
    Handler handler = new Handler();
    Intent intent;

    Runnable r = new Runnable() {
        @Override
        public void run() {
            //3초뒤에 다음화면(MainActivity)으로 넘어가기 Handler 사용
            Intent intent =  new Intent(getApplicationContext(), Intro2Activity.class);
            startActivity(intent); // 다음화면으로 넘어가기
            finish(); // Activity 화면 제거
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro1); // xml과 java소스를 연결

        if(SaveSharedPreference.getUserName(Intro1Activity.this).length() == 0) {
            // call Login Activity
            intent =  new Intent(getApplicationContext(), Intro2Activity.class);
            startActivity(intent); // 다음화면으로 넘어가기
            finish(); // Activity 화면 제거

        } else {
            // Call Next Activity
            intent =  new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("STD_NUM", SaveSharedPreference.getUserName(this).toString());
            startActivity(intent);
            this.finish();
        }

    } // end of onCreate

    @Override
    protected void onResume() {
        super.onResume();
        // 다시 화면에 들어왔을 때 예약 걸어주기
        handler.postDelayed(r, 1000); // 3초 뒤에 Runnable 객체 수행
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 화면을 벗어나면, handler 에 예약해놓은 작업을 취소하자
        handler.removeCallbacks(r); // 예약 취소
    }

}