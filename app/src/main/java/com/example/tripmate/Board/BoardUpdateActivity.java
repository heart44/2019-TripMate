package com.example.tripmate.Board;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;

import com.example.tripmate.R;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class BoardUpdateActivity extends AppCompatActivity {
    private int startyear;
    private int startmonth;
    private int startday;
    private EditText date_start;
    private EditText time_start;
    private EditText time_end;
    private EditText destination;
    private EditText content;
    private EditText age_start;
    private EditText age_end;
    private Button update;
    private RadioButton man;
    private RadioButton woman;
    private RadioButton all;
    private RadioButton food;
    private RadioButton carfull;
    private RadioButton picture;
    private RadioButton tour;

    private Date now;
    private Dialog dialog;
    private String smyNickname;
    private String sboardCode;
    private String swriterGenderAge;
    private String spurpose;
    private String sdestination;
    private String smatchingGenderAge;
    private String swritingDate;
    private String smatchingDate;
    private String swriter;
    private String scontent;
    fragmentBoard fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_update);

        Intent intent = getIntent();
        smyNickname = intent.getExtras().getString("myNickname");
        sboardCode = intent.getExtras().getString("boardCode");
        swriterGenderAge= intent.getExtras().getString("writerGenderAge");
        spurpose = intent.getExtras().getString("purpose");
        sdestination = intent.getExtras().getString("destination");
        smatchingGenderAge = intent.getExtras().getString("matchingGenderAge");
        swriter = intent.getExtras().getString("writerNickname");
        smatchingDate = intent.getExtras().getString("matchingDate");
        swritingDate = intent.getExtras().getString("writingDate");
        scontent = intent.getExtras().getString("content");

        String minage = smatchingGenderAge.substring(0,2);
        String maxage = smatchingGenderAge.substring(5,7);


        destination = findViewById(R.id.BoardUpdateActivity_text_where);
        destination.setText(sdestination);

        destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), PlaceSearchActivity2.class);
                //v.getContext().startActivity(intent);
                startActivityForResult(intent, Code.requestCode);

            }
        });
        content = findViewById(R.id.BoardUpdateActivity_text_content);
        content.setText(scontent);
        age_start = findViewById(R.id.BoardUpdateActivity_edittext_age_start);
        age_start.setText(minage);
        age_end = findViewById(R.id.BoardUpdateActivity_edittext_age_end);
        age_end.setText(maxage);


        final Calendar startDate = Calendar.getInstance();
        date_start = findViewById(R.id.BoardUpdateActivity_edittext_date_start);
        date_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now1 = System.currentTimeMillis();
                now = new Date(now1);

                Calendar defaultDate = Calendar.getInstance();
                Calendar minDate = Calendar.getInstance();
                defaultDate.setTime(now);
                minDate.setTime(now);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        BoardUpdateActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                int printmonth = month + 1;
                                date_start.setText(year + "년" + printmonth + "월" + dayOfMonth + "일");
                                date_start.setTextSize(14);
                                startyear = year;
                                startmonth = month;
                                startday = dayOfMonth;
                                startDate.set(year, month, dayOfMonth);

                            }
                        },
                        defaultDate.get(Calendar.YEAR),
                        defaultDate.get(Calendar.MONTH),
                        defaultDate.get(Calendar.DATE)
                );

                datePickerDialog.getDatePicker().setMinDate(minDate.getTime().getTime());
                datePickerDialog.show();

            }
        });

        final Calendar mcurrentTime = Calendar.getInstance();

        time_start = findViewById(R.id.BoardUpdateActivity_edittext_time_start);
        time_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(BoardUpdateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time_start.setText(selectedHour + "시" + selectedMinute + "분");
                        time_start.setTextSize(14);
                        startDate.set(startyear, startmonth, startday, selectedHour, selectedMinute);
                        // EditText에 출력할 형식 지정
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        time_end = findViewById(R.id.BoardUpdateActivity_edittext_time_end);
        time_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(BoardUpdateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time_end.setText(selectedHour + "시" + selectedMinute + "분");
                        time_end.setTextSize(14);

                        // EditText에 출력할 형식 지정
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });




        update = findViewById(R.id.BoardUpdateActivity_button_update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(destination.getText()) ||
                        TextUtils.isEmpty(content.getText()) ||
                        TextUtils.isEmpty(age_start.getText()) ||
                        TextUtils.isEmpty(age_end.getText()) ||
                        TextUtils.isEmpty(date_start.getText()) ||
                        TextUtils.isEmpty(time_start.getText()) ||
                        TextUtils.isEmpty(time_end.getText())) {
                    alert("글 쓰기", "모든칸을 다 채워주세요");
                }
                else{
                    final int minage = Integer.parseInt(age_start.getText().toString());
                    final int maxage = Integer.parseInt(age_end.getText().toString());
                    if (minage > maxage){
                        alert("글 쓰기", "최소나이가 최대나이보다 클 수 없습니다");
                    }
                    else{

                        man = findViewById(R.id.BoardUpdateActivity_radio_man);
                        woman = findViewById(R.id.BoardUpdateActivity_radio_woman);
                        all = findViewById(R.id.BoardUpdateActivity_radio_all);
                        tour = findViewById(R.id.BoardUpdateActivity_radio_tour);
                        carfull = findViewById(R.id.BoardUpdateActivity_radio_carfull);
                        picture = findViewById(R.id.BoardUpdateActivity_radio_picture);
                        food = findViewById(R.id.BoardUpdateActivity_radio_food);
                        final String senddestination = destination.getText().toString();

                        final String sendcontent = content.getText().toString();
                        String sendgender1 = "2";
                        String sendpurpose = "맛집";
                        if (man.isChecked())
                            sendgender1 = "0";
                        else if (woman.isChecked())
                            sendgender1 = "1";
                        else if (all.isChecked())
                            sendgender1 = "2";

                        if (food.isChecked())
                            sendpurpose = "맛집";
                        else if (carfull.isChecked())
                            sendpurpose = "카풀";
                        else if (picture.isChecked())
                            sendpurpose = "사진";
                        else if (tour.isChecked())
                            sendpurpose = "관광";


                        final String sendminage = age_start.getText().toString();

                        final String sendmaxage = age_end.getText().toString();

                        final String senddate = date_start.getText().toString();
                        final String sendstarttime = time_start.getText().toString();
                        final String sendendtime = time_end.getText().toString();

                        final String date;
                        String date1;
                        date1 = senddate.replace("년", "-");
                        date1 = date1.replace("월", "-");
                        date1 = date1.replace("일", "");
                        date = date1;


                        String result = null;
                        HttpBoardUpdate httpBoardUpdateActivity = new HttpBoardUpdate();
                        HttpBoardUpdate.sendTask send = httpBoardUpdateActivity.new sendTask();
                        try {
                            result = send.execute(sboardCode, smyNickname, senddestination, sendcontent, sendgender1, sendminage, sendmaxage, senddate, sendstarttime, sendendtime, sendpurpose).get();

                            if ("success".equals(result)) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(BoardUpdateActivity.this);
                                builder.setTitle("게시판").setMessage("게시글 수정이 완료되었습니다");
                                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {


                                            /*fragmentBoard.getInstance().removeAllItems();
                                            fragmentBoard.getInstance().setRefreshCount(1);
                                            fragmentBoard.getInstance().init();*/


                                        onBackPressed();

                                    }
                                });
                                dialog = builder.create();
                                dialog.show();

                            } else {
                                alert("게시판", "다시 시도해 주세요");
                            }

                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }

            }


        });



    }

    protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent){
        if(requestCode == Code.requestCode && resultCode == Code.resultCode)
            destination.setText(resultIntent.getStringExtra("place"));
    }
    public static class Code{
        public static int requestCode = 100;
        public static int resultCode = 1;
    }

    private void alert(String title, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(BoardUpdateActivity.this);
        builder.setTitle(title).setMessage(content);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog = builder.create();
        dialog.show();
    }

}