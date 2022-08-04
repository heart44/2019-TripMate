package com.example.tripmate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tripmate.Board.fragmentBoard;
import com.example.tripmate.Chat.ChatModel;
import com.example.tripmate.Location.NearLocationFragment;
import com.example.tripmate.User.HttpNicknameToEmail;
import com.example.tripmate.User.HttpUserLogin;
import com.example.tripmate.User.UserModel;
import com.example.tripmate.User.UserUpdateActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    Button open_close;
    DrawerLayout drawerLayout;
    Dialog dialog;
    ImageView profileImage;
    TextView nickNameText;
    TextView emailText;
    String uid;
    ImageView setting;
    private int fragment4count = 0;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private fragmentActivity1 fragment1 = new fragmentActivity1();
    private fragmentActivity2 fragment2 = new fragmentActivity2();
    private fragmentActivity3 fragment3 = new fragmentActivity3();
    private fragmentActivity4 fragment4 = fragmentActivity4.getInstance();
    private NearLocationFragment fragment5 = new NearLocationFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //아이디 및 닉네임 각 페이지에 전송
        Intent intent = getIntent();
        String nickname = "";
        String userid = "";

        nickname = SaveSharedPreference.getNickName(this);
        userid = SaveSharedPreference.getUserName(this);


        HttpNicknameToEmail nicknameToEmail = new HttpNicknameToEmail();
        HttpNicknameToEmail.sendTask send1 = nicknameToEmail.new sendTask();
        String email = null;
        try {
            email = send1.execute(nickname).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        //첫 화면 지정
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment1).commitAllowingStateLoss();

        //상단 툴바 생성
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        //툴바에 홈버튼을 활성화
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_appbar_menu);
        //툴바 중앙 제목
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Navigation Drawer 생성 및 초기화

        String imageurl = null;
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        System.out.println("mainactivity uid " + uid);



        drawerLayout = findViewById(R.id.drawer_layout);
        ImageView imageView = findViewById(R.id.navigation_header_image_user);

        NavigationView navigationView = findViewById(R.id.nv_main_navigation_root);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        nickNameText = headerView.findViewById(R.id.navigation_header_text_nickname);
        nickNameText.setText(nickname);
        emailText = headerView.findViewById(R.id.navigation_header_text_email);
        emailText.setText(email);
        profileImage = headerView.findViewById(R.id.navigation_header_image_user);
        setting = headerView.findViewById(R.id.navigation_header_image_setting);
        FirebaseDatabase.getInstance().getReference().child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserModel userModel = dataSnapshot.getValue(UserModel.class);
                Glide.with(profileImage.getContext())
                        .load(userModel.getProfileImageUrl())
                        .apply(new RequestOptions().circleCrop())
                        .into(profileImage);

            }

            public void onCancelled(DatabaseError databaseError) {

            }
        });


        String finalEmail = email;
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent = new Intent(getApplicationContext(), UserUpdateActivity.class);
                intent.putExtra("email", finalEmail);
                startActivity(intent);
            }
        });



        //하단 툴바 생성
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // BottomNavigationView 메뉴를 선택할 때마다 위치가 변하지 않도록
        BottomNavigationHelper.disableShiftMode(bottomNavigationView);


        // bottomNavigationView의 아이템이 선택될 때 호출될 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                FragmentTransaction transaction = fragmentManager.beginTransaction();

                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        transaction.replace(R.id.frameLayout, fragment1).commitAllowingStateLoss();
                        break;
                    case R.id.action_home2:
                        transaction.replace(R.id.frameLayout, fragment2).commitAllowingStateLoss();
                        break;
                    case R.id.action_home3:
                        transaction.replace(R.id.frameLayout, fragment3).commitAllowingStateLoss();
                        break;
                    case R.id.action_home4:
                        /* 내가 수정할 부분 */
                        if (fragment4count != 0) {
                            fragmentBoard.setRefreshCount(0);
                            fragmentBoard.getInstance().init();
                        }
                        transaction.replace(R.id.frameLayout, fragment4).commitAllowingStateLoss();
                        fragment4count++;
                        break;
                    case R.id.action_home5:
                        transaction.replace(R.id.frameLayout, fragment5).commitNowAllowingStateLoss();
                        break;
                }

                return true;
            }

        });

    } //onCreate

    //메뉴 연동
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("종료").setMessage("종료하시겠습니까?");
            builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.finishAffinity(MainActivity.this);
                    System.runFinalizersOnExit(true);
                    System.exit(0);
                }
            });
            builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            dialog = builder.create();
            dialog.show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.navigation_item_notice) {
            // Handle the camera action
        } else if (id == R.id.navigation_item_qna) {

        } else if (id == R.id.navigation_item_recommand) {

        } else if (id == R.id.navigation_item_logout) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("로그아웃 확인").setMessage("로그아웃 하시겠습니까?");
            builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(MainActivity.this, Intro1Activity.class);
                    SaveSharedPreference.clearUserName(getApplicationContext());
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            dialog = builder.create();
            dialog.show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
