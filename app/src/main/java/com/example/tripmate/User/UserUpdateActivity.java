package com.example.tripmate.User;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.tripmate.Board.BoardViewActivity;
import com.example.tripmate.Board.HttpBoardDelete;
import com.example.tripmate.Intro1Activity;
import com.example.tripmate.MainActivity;
import com.example.tripmate.R;
import com.example.tripmate.SaveSharedPreference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.concurrent.ExecutionException;

public class UserUpdateActivity extends Activity {

    private static final int PICK_FROM_ALBUM = 10;
    private EditText currentPassword;
    private EditText nextPassword;
    private EditText nextnickname;
    private EditText age;
    private EditText email;
    private RadioButton man;
    private RadioButton woman;
    private Button button_password_correct;
    private Button button_nickname_validation;
    private Button updatebutton;
    private Button deletebutton;
    private AlertDialog dialog;
    private boolean password_correct = false;
    private boolean nickname_validate = false;
    private static final String TAG = "update Activity : ";
    private String currentNickname;
    private Dialog dialog1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update);

        Intent intent = getIntent();


        currentNickname = SaveSharedPreference.getNickName(this);
        currentPassword = findViewById(R.id.UserUpdateActivity_text_current_password);
        nextPassword = findViewById(R.id.UserUpdateActivity_text_next_password);
        nextnickname = findViewById(R.id.UserUpdateActivity_text_nickname);


        button_password_correct = findViewById(R.id.UserUpdateActivity_button_password_correct);
        button_password_correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("update", "???????????? ?????? ????????? ??????");
                if (TextUtils.isEmpty(currentPassword.getText())) {
                    alert("?????? ???????????? ??????", "?????? ??????????????? ???????????????");
                } else {
                    String sendCurrentPassword = currentPassword.getText().toString();
                    String result = null;
                    HttpUserCheckPassword httpUserCheckPassword = new HttpUserCheckPassword();
                    HttpUserCheckPassword.sendTask send = httpUserCheckPassword.new sendTask();
                    try {
                        result = send.execute(currentNickname, sendCurrentPassword).get();
                        if ("password-wrong".equals(result)) {
                            alert("????????????", "?????? ??????????????? ???????????????");
                        } else if ("error".equals(result)) {
                            alert("??????", "?????? ?????? ??????????????????");
                        } else {
                            alert("????????????", "??????????????? ????????????");
                            password_correct = true;
                        }


                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        button_nickname_validation = findViewById(R.id.UserUpdateActivity_button_nickname_validate);
        button_nickname_validation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("update", "?????? ?????? ????????? ??????");
                if (TextUtils.isEmpty(nextnickname.getText())) {
                    alert("????????? ??????", "???????????? ???????????????");
                } else if (nextnickname.getText().toString().equals(currentNickname)) {
                    alert("????????? ??????","?????? ???????????? ????????????");
                    nickname_validate = true;
                } else {
                    String result = null;
                    HttpNicknameCheck httpUserDataActivity = new HttpNicknameCheck();
                    HttpNicknameCheck.sendTask send = httpUserDataActivity.new sendTask();
                    try {
                        result = send.execute(nextnickname.getText().toString()).get();
                        if ("duplication".equals(result)) {
                            alert("?????????", "???????????? ?????????????????????");
                        } else if ("error".equals(result)) {
                            alert("?????????", "?????? ?????? ??????????????????");
                        } else {
                            alert("?????????", "?????? ????????? ????????? ?????????");
                            nickname_validate = true;
                        }

                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        deletebutton = findViewById(R.id.UserUpdateActivity_button_delete);
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UserUpdateActivity.this);
                builder.setTitle("??????").setMessage("?????? ???????????????????");
                builder.setPositiveButton("???", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String result = null;
                        HttpUserDelete httpUserDeleteActivity = new HttpUserDelete();
                        HttpUserDelete.sendTask send = httpUserDeleteActivity.new sendTask();
                        try {
                            result = send.execute(currentNickname).get();

                            if("success".equals(result)){
                                AlertDialog.Builder builder = new AlertDialog.Builder(UserUpdateActivity.this);
                                builder.setTitle("??????").setMessage("?????? ?????????????????????.");
                                builder.setPositiveButton("???", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(UserUpdateActivity.this, Intro1Activity.class);
                                        SaveSharedPreference.clearUserName(getApplicationContext());
                                        startActivity(intent);
                                    }
                                });
                                dialog1 = builder.create();
                                dialog1.show();
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(UserUpdateActivity.this);
                                builder.setTitle("??????").setMessage("?????? ?????? ??????????????????.");
                                builder.setPositiveButton("???", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                                dialog1 = builder.create();
                                dialog1.show();
                            }
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                });
                builder.setNegativeButton("?????????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog = builder.create();
                dialog.show();
            }
        });

        updatebutton = findViewById(R.id.UserUpdateActivity_button_update);
        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(nextPassword.getText())) {
                    alert("?????? ?????? ??????", "?????? ????????? ?????? ????????????");
                } else {
                    if (password_correct == false || nickname_validate == false) {
                        alert("?????? ?????? ??????", "??????????????? ??????????????? ?????????.");
                    } else {
                        if (nextPassword.getText().toString().length() < 6) {
                            alert("????????????", "??????????????? 6??? ???????????? ????????????"); // ?????????????????? ???????????? ???????????? 6????????? ????????? ????????????
                        } else {

                            String sendpassword = nextPassword.getText().toString();
                            final String sendnickname = nextnickname.getText().toString();

                            String result = null;
                            try {
                                HttpUserUpdate httpUserDataActivity = new HttpUserUpdate();
                                HttpUserUpdate.sendTask send = httpUserDataActivity.new sendTask();
                                result = send.execute(currentNickname, sendnickname, sendpassword).get();

                                if ("error".equals(result)) {
                                    alert("?????? ?????? ??????", "?????? ?????? ??????????????????");
                                } else {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(UserUpdateActivity.this);
                                    builder.setTitle("?????? ?????? ??????").setMessage("?????? ?????? ????????? ?????????????????????");
                                    builder.setPositiveButton("???", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            SaveSharedPreference.setNickName(getApplicationContext(), sendnickname);
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            UserUpdateActivity.this.finish();
                                        }
                                    });
                                    dialog = builder.create();
                                    dialog.show();

                                }
                            } catch (ExecutionException | InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            }

        });


    }

    private void alert(String title, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserUpdateActivity.this);
        builder.setTitle(title).setMessage(content);
        builder.setPositiveButton("???", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog = builder.create();
        dialog.show();
    }


}

