package com.example.tripmate.Board;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tripmate.Chat.MessageActivity;
import com.example.tripmate.R;
import com.example.tripmate.User.HttpNicknameToUID;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class BoardViewActivity extends AppCompatActivity {
    private String myNickname;
    private String boardCode;
    private TextView writerGenderAge;
    private TextView purpose;
    private TextView destination;
    private TextView mathcingGenderAge;
    private TextView writerNickname;
    private TextView matchingDate;
    private TextView writingDate;
    private TextView content;
    private Button update;
    private Button delete;
    private Button chat;
    private Dialog dialog;
    private Dialog dialog1;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_view);

        Intent intent = getIntent();
        myNickname = intent.getExtras().getString("nickname");
        System.out.println("boardviewActivity" + myNickname);
        boardCode = intent.getExtras().getString("boardcode");
        System.out.println("boardviewActivity" + boardCode);


        writerGenderAge = findViewById(R.id.boardview_text_writergenderage);
        purpose = findViewById(R.id.boardview_text_purpose);

        destination = findViewById(R.id.boardview_text_destination);
        mathcingGenderAge = findViewById(R.id.boardview_text_matchinggenderage);
        writerNickname = findViewById(R.id.boardview_text_writer);
        matchingDate = findViewById(R.id.boardview_text_tripdatetime);
        writingDate = findViewById(R.id.boardview_text_writingdate);
        content = findViewById(R.id.boardview_text_content);
        update = findViewById(R.id.boardview_button_update);
        delete=findViewById(R.id.boardview_button_delete);
        chat=findViewById(R.id.boardview_button_chat);

        HttpBoardView httpBoardViewActivity = new HttpBoardView();
        HttpBoardView.sendTask send = httpBoardViewActivity.new sendTask();
        String result = null;
        try {
            result = send.execute(boardCode).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(result);

        JSONArray jarray = null;



        try {
            jarray = new JSONObject(result).getJSONArray("view");
            JSONObject jsonObject = jarray.getJSONObject(0);
            final String swriterGenderAge = jsonObject.getString("writer-gender-age");
            final String spurpose = jsonObject.getString("purpose");

            final String sdestination = jsonObject.getString("destination");
            final String smatchingGenderAge = jsonObject.getString("matching-gender-age");
            final String swriter = jsonObject.getString("writer");
            final String smatchingDate = jsonObject.getString("matching-date");
            final String swritingDate = jsonObject.getString("writing-date");
            final String scontent = jsonObject.getString("content");

            writerGenderAge.setText(swriterGenderAge);
            purpose.setText(spurpose);
            destination.setText(sdestination);
            mathcingGenderAge.setText(smatchingGenderAge);
            writerNickname.setText(swriter);
            matchingDate.setText(smatchingDate);
            writingDate.setText(swritingDate);
            content.setText(scontent);

            if(myNickname.equals(swriter)){
                update.setVisibility(View.VISIBLE);
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), BoardUpdateActivity.class);
                        intent.putExtra("myNickname",myNickname);
                        intent.putExtra("boardCode",boardCode);
                        intent.putExtra("writerGenderAge",swriterGenderAge);
                        intent.putExtra("purpose",spurpose);
                        intent.putExtra("destination",sdestination);
                        intent.putExtra("matchingGenderAge",smatchingGenderAge);
                        intent.putExtra("writerNickname",swriter);
                        intent.putExtra("matchingDate",smatchingDate);
                        intent.putExtra("writingDate",swritingDate);
                        intent.putExtra("content",scontent);
                        startActivity(intent);

                    }
                });
                delete.setVisibility(View.VISIBLE);
                delete.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(BoardViewActivity.this);
                        builder.setTitle("삭제").setMessage("삭제하시겠습니까?");
                        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String result = null;
                                HttpBoardDelete httpBoardDeleteActivity = new HttpBoardDelete();
                                HttpBoardDelete.sendTask send = httpBoardDeleteActivity.new sendTask();
                                try {
                                    result = send.execute(boardCode).get();

                                    if("success".equals(result)){
                                        AlertDialog.Builder builder = new AlertDialog.Builder(BoardViewActivity.this);
                                        builder.setTitle("삭제").setMessage("삭제 완료되었습니다.");
                                        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                onBackPressed();
                                            }
                                        });
                                        dialog1 = builder.create();
                                        dialog1.show();
                                    }
                                    else{
                                        AlertDialog.Builder builder = new AlertDialog.Builder(BoardViewActivity.this);
                                        builder.setTitle("삭제").setMessage("다시 한번 시도해주세요.");
                                        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
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
                        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        dialog = builder.create();
                        dialog.show();
                    }
                });

            }
            else{
                chat.setVisibility(View.VISIBLE);
                chat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //상대닉네임 : swriter
                        //상대 uid를 닉네임으로 구해오기
                        String result;
                        HttpNicknameToUID nicknameToUID = new HttpNicknameToUID();
                        HttpNicknameToUID.sendTask send = nicknameToUID.new sendTask();
                        try {
                            result = send.execute(swriter).get();
                            System.out.println("boardviewactivity result " + result);
                            Intent intent = new Intent(view.getContext(), MessageActivity.class);
                            intent.putExtra("destinationUid",result);
                            intent.putExtra("destinationNickname",swriter);
                            startActivity(intent);

                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }


                    }
                });
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onBackPressed(){

        // final BoardListAdapter adapter = BoardListAdapter.getInstance();
        fragmentBoard.getInstance().removeAllItems();
        fragmentBoard.getInstance().setRefreshCount(1);
        fragmentBoard.getInstance().init();
        super.onBackPressed();
    }

    public void onRestart() {
        super.onRestart();
        onBackPressed();
    }
}