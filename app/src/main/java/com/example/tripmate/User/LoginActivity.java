package com.example.tripmate.User;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tripmate.MainActivity;
import com.example.tripmate.R;
import com.example.tripmate.SaveSharedPreference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends Activity {
    private AlertDialog dialog;
    private EditText id;
    private EditText password;
    private Button login;
    private String myuid;
    private String email;
    private String nickname;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id = findViewById(R.id.LoginActivity_text_id);
        password = findViewById(R.id.LoginActivity_text_password);



        login = findViewById(R.id.LoginActivity_button_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((TextUtils.isEmpty(id.getText()) || TextUtils.isEmpty(password.getText()))) {
                    alert("로그인", "아이디와 비밀번호를 입력해주세요");
                } else {
                    String sendid = id.getText().toString();
                    String sendpassword = password.getText().toString();


                    try {
                        HttpUserLogin httpUserDataActivity = new HttpUserLogin();
                        HttpUserLogin.sendTask send = httpUserDataActivity.new sendTask();
                        String result = send.execute(sendid, sendpassword).get();
                        JSONArray jarray = null;
                        String receiveMsg = null;

                        try {
                            jarray = new JSONObject(result).getJSONArray("Login");

                            JSONObject jsonObject3 = jarray.getJSONObject(2);
                            receiveMsg = jsonObject3.getString("msg");

                            JSONObject jsonObject1 = jarray.getJSONObject(1);
                            email = jsonObject1.getString("email");
                            JSONObject jsonObject0 = jarray.getJSONObject(0);
                            nickname = jsonObject0.getString("nickname");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("email : " + email);
                        System.out.println("nickname + " + nickname);

                        if ("success".equals(receiveMsg)) {
                            firebaseAuth = FirebaseAuth.getInstance();
                            //firebaseAuth.signOut();  로그아웃하는부분;

                            loginEvent();

                            authStateListener = new FirebaseAuth.AuthStateListener() {
                                @Override
                                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                }
                            };


                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setTitle("로그인").setMessage("로그인 성공했습니다");
                            builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {


                                    SaveSharedPreference.setUserName(getApplicationContext(), id.getText().toString());
                                    SaveSharedPreference.setNickName(getApplicationContext(), nickname);


                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtra("nickname", nickname);
                                    intent.putExtra("userid", id.getText().toString());

                                    startActivity(intent);
                                    finish();

                                }
                            });
                            dialog = builder.create();
                            dialog.show();
                        } else if ("no-id".equals(receiveMsg)) {
                            alert("로그인", "해당 아이디가 없습니다");
                        } else if ("password-wrong".equals(receiveMsg)) {
                            alert("로그인", "비밀번호가 틀렸습니다");
                        } else if ("error".equals(receiveMsg)) {
                            alert("로그인", "다시 한번 시도해주세요");
                        }

                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    void loginEvent() {

        firebaseAuth.signInWithEmailAndPassword(email, password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            //로그인 실패한부분
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }


 /*   @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }*/

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    private void alert(String title, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle(title).setMessage(content);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    public void onBackPressed() {


        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("종료").setMessage("종료하시겠습니까?");
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.finishAffinity(LoginActivity.this);
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
