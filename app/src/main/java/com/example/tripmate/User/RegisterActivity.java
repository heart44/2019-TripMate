package com.example.tripmate.User;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;


import com.example.tripmate.MainActivity;
import com.example.tripmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RegisterActivity extends Activity {

    private static final int PICK_FROM_ALBUM = 10;
    private EditText id;
    private EditText password;
    private EditText nickname;
    private EditText age;
    private EditText email;
    private RadioButton man;
    private RadioButton woman;
    private Button button_id_validation;
    private Button button_nickname_validation;
    private Button registerbutton;
    private AlertDialog dialog;
    private boolean id_validate = false;
    private boolean nickname_validate = false;
    private static final String TAG = "register Activity : ";

    private ImageView profile;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        id = findViewById(R.id.RegisterActivity_text_id);

        password = findViewById(R.id.RegisterActivity_text_password);
        nickname = findViewById(R.id.RegisterActivity_text_nickname);
        age = findViewById(R.id.RegisterActivity_text_age);
        email = findViewById(R.id.RegisterActivity_text_email);
        profile = findViewById(R.id.RegisterActivity_imageview_profile);

        profile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, PICK_FROM_ALBUM);
            }
        });

        button_id_validation = findViewById(R.id.RegisterActivity_button_id_validate);
        button_id_validation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("register", "?????? ?????? ????????? ??????");
                if (TextUtils.isEmpty(id.getText())) {
                    alert("????????? ??????", "???????????? ???????????????");
                } else {

                    try {
                        String sendid = id.getText().toString();
                        System.out.println(sendid);
                        HttpIdCheck httpUserDataActivity = new HttpIdCheck();
                        HttpIdCheck.sendTask send = httpUserDataActivity.new sendTask();
                        String result = send.execute(sendid).get();
                        System.out.println(result);
                        if ("success".equals(result)) {
                            id_validate = true;
                            alert("????????? ??????", "????????? ???????????? ??????");
                        } else if("error".equals(result)){
                            alert("????????? ??????", "???????????? ??????????????????.");
                        }
                        else{
                            alert("????????? ??????", "???????????? ???????????????.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        button_nickname_validation = findViewById(R.id.RegisterActivity_button_nickname_validate);
        button_nickname_validation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("register", "?????? ?????? ????????? ??????");
                if (TextUtils.isEmpty(nickname.getText())) {
                    alert("????????? ??????", "???????????? ???????????????");
                } else {

                    try {
                        String sendnickname = nickname.getText().toString();
                        System.out.println(sendnickname);
                        HttpNicknameCheck httpUserDataActivity = new HttpNicknameCheck();
                        HttpNicknameCheck.sendTask send = httpUserDataActivity.new sendTask();
                        String result = send.execute(sendnickname).get();
                        System.out.println("???????????? ?????? : " + result);
                        if ("success".equals(result)) {
                            nickname_validate = true;
                            alert("????????? ??????", "????????? ???????????? ??????");
                        } else {
                            alert("????????? ??????", "????????? ??? ?????? ??????????????????.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        registerbutton = findViewById(R.id.RegisterActivity_button_register);
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((TextUtils.isEmpty(age.getText()) || TextUtils.isEmpty(password.getText()) || TextUtils.isEmpty(email.getText())) || imageUri == null) {
                    alert("?????? ??????", "?????? ????????? ?????? ????????????");
                } else {
                    if (id_validate == false || nickname_validate == false) {
                        alert("?????? ??????", "??????????????? ??????????????? ?????????.");
                    } else {
                        if (password.getText().toString().length() < 6) {
                            alert("????????????", "??????????????? 6??? ???????????? ????????????"); // ?????????????????? ???????????? ???????????? 6????????? ????????? ????????????
                        } else {

                       /* CheckBox cb1 = findViewById(R.id.RegisterActivity_check_food);
                        CheckBox cb2 = findViewById(R.id.RegisterActivity_check_rest);
                        CheckBox cb3 = findViewById(R.id.RegisterActivity_check_nature);
                        CheckBox cb4 = findViewById(R.id.RegisterActivity_check_leisure);
                        CheckBox cb5 = findViewById(R.id.RegisterActivity_check_walk);
                        CheckBox cb6 = findViewById(R.id.RegisterActivity_check_biking);

                        int checkCount = 0;

                        if (cb1.isChecked()) {
                            checkCount++;

                        }
                        if (cb2.isChecked()) {
                            checkCount++;

                        }
                        if (cb3.isChecked()) {
                            checkCount++;

                        }
                        if (cb4.isChecked()) {
                            checkCount++;

                        }
                        if (cb5.isChecked()) {
                            checkCount++;
                        }
                        if (cb6.isChecked()) {
                            checkCount++;
                        }
                        ArrayList<String> interest = new ArrayList<>(3);
                        if (checkCount > 3 || checkCount == 0) {
                            alert("?????? ?????? ??????","1,2,3?????? ???????????????");
                        } else {*/
                            final String sendid = id.getText().toString();
                            String sendpassword = password.getText().toString();
                            final String sendnickname = nickname.getText().toString();
                            String sendage = age.getText().toString();
                            String sendemail = email.getText().toString();
                            String sendgender = null;

                            man = findViewById(R.id.RegisterActivity_radio_man);
                            woman = findViewById(R.id.RegisterActivity_radio_woman);
                            if (man.isChecked())
                                sendgender = "0";
                            else if (woman.isChecked())
                                sendgender = "1";


                           /* if (cb1.isChecked() == true) interest.add(cb1.getText().toString());
                            if (cb2.isChecked() == true) interest.add(cb2.getText().toString());
                            if (cb3.isChecked() == true) interest.add(cb3.getText().toString());
                            if (cb4.isChecked() == true) interest.add(cb4.getText().toString());
                            if (cb5.isChecked() == true) interest.add(cb5.getText().toString());
                            if (cb6.isChecked() == true) interest.add(cb6.getText().toString());*/


                            String result = null;
                            try {
                                HttpUserRegister httpUserDataActivity = new HttpUserRegister();
                                HttpUserRegister.sendTask send = httpUserDataActivity.new sendTask();
                                result = send.execute(sendid, sendpassword, sendnickname, sendage, sendgender, sendemail).get();

                                if ("success".equals(result)) {
                                    //???????????? ?????????????????? user ?????? ????????? ??????

                                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(sendemail, sendpassword)
                                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    Log.d(TAG,"?????????????????? ?????? ??????");
                                                    String result1;
                                                    final String uid = task.getResult().getUser().getUid();
                                                    HttpUserUID httpUID = new HttpUserUID();
                                                    HttpUserUID.sendTask send = httpUID.new sendTask();
                                                    try {
                                                        result1 = send.execute(uid,sendnickname).get();
                                                        if("success".equals(result1)){
                                                            final StorageReference profileImageRef = FirebaseStorage.getInstance().getReference().child("userImages").child(uid);
                                                            profileImageRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                                                    Task<Uri> uriTask = profileImageRef.getDownloadUrl();
                                                                    while(!uriTask.isSuccessful());
                                                                    Uri downloadUrl = uriTask.getResult();
                                                                    String imageUrl = String.valueOf(downloadUrl);
                                                                    UserModel userModel = new UserModel();
                                                                    userModel.setUserName(sendnickname);
                                                                    userModel.setProfileImageUrl(imageUrl);
                                                                    userModel.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                                    Log.d(TAG,"storage??? ????????? ?????? ??????");

                                                                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {
                                                                            Log.d(TAG,"?????????????????? db ?????? ??????");
                                                                            System.out.println("?????????????????? ???????????? ??????");

                                                                        }
                                                                    });
                                                                }
                                                            });
                                                        }
                                                    } catch (ExecutionException | InterruptedException e) {
                                                        e.printStackTrace();
                                                    }



                                                }
                                            });


                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setTitle("????????????").setMessage("??????????????? ?????????????????????");
                                    builder.setPositiveButton("???", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            intent.putExtra("nickname",sendnickname);
                                            startActivity(intent);
                                            RegisterActivity.this.finish();
                                        }
                                    });
                                    dialog = builder.create();
                                    dialog.show();

                                } else {
                                    alert("?????? ??????", "?????? ????????? ?????????");
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
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle(title).setMessage(content);
        builder.setPositiveButton("???", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_FROM_ALBUM && resultCode == RESULT_OK) {
            profile.setImageURI(data.getData()); // ????????? ?????? ??????
            imageUri = data.getData();// ????????? ?????? ??????
        }
    }
}

