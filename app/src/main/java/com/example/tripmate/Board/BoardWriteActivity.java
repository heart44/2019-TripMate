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
import com.example.tripmate.SaveSharedPreference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class BoardWriteActivity extends AppCompatActivity {
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
    private Button write;
    private RadioButton man;
    private RadioButton woman;
    private RadioButton all;
    private RadioButton carfull;
    private RadioButton food;
    private RadioButton tour;
    private RadioButton picture;
    private ArrayList<String> detinationList;
    private Date now;
    private Dialog dialog;
    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);
      /*  Intent intent = getIntent();
        nickname = intent.getExtras().getString("nickname");
        System.out.println("boardwriteactivity : " + nickname);*/
        nickname = SaveSharedPreference.getNickName(this);

        destination = findViewById(R.id.BoardWriteActivity_text_where);
        destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), PlaceSearchActivity.class);
                //v.getContext().startActivity(intent);
                startActivityForResult(intent, Code.requestCode);

            }
        });
        content = findViewById(R.id.BoardWriteActivity_text_content);
        age_start = findViewById(R.id.BoardWriteActivity_edittext_age_start);
        age_end = findViewById(R.id.BoardWriteActivity_edittext_age_end);



        final Calendar startDate = Calendar.getInstance();
        date_start = findViewById(R.id.BoardWriteActivity_edittext_date_start);
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
                        BoardWriteActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                int printmonth = month + 1;
                                date_start.setText(year + "???" + printmonth + "???" + dayOfMonth + "???");
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

        time_start = findViewById(R.id.BoardWriteActivity_edittext_time_start);
        time_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(BoardWriteActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time_start.setText(selectedHour + "???" + selectedMinute + "???");
                        time_start.setTextSize(14);
                        startDate.set(startyear, startmonth, startday, selectedHour, selectedMinute);
                        // EditText??? ????????? ?????? ??????
                    }
                }, hour, minute, false); // true??? ?????? 24?????? ????????? TimePicker ??????
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        time_end = findViewById(R.id.BoardWriteActivity_edittext_time_end);
        time_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(BoardWriteActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time_end.setText(selectedHour + "???" + selectedMinute + "???");
                        time_end.setTextSize(14);

                        // EditText??? ????????? ?????? ??????
                    }
                }, hour, minute, false); // true??? ?????? 24?????? ????????? TimePicker ??????
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        write = findViewById(R.id.BoardWriteActivity_button_write);
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(destination.getText()) ||
                        TextUtils.isEmpty(content.getText()) ||
                        TextUtils.isEmpty(age_start.getText()) ||
                        TextUtils.isEmpty(age_end.getText()) ||
                        TextUtils.isEmpty(date_start.getText()) ||
                        TextUtils.isEmpty(time_start.getText()) ||
                        TextUtils.isEmpty(time_end.getText())) {
                    alert("??? ??????", "???????????? ??? ???????????????");
                } else {
                    final int minage = Integer.parseInt(age_start.getText().toString());
                    final int maxage = Integer.parseInt(age_end.getText().toString());
                    if (minage > maxage) {
                        alert("??? ??????", "??????????????? ?????????????????? ??? ??? ????????????");
                    } else {

                        man = findViewById(R.id.BoardWriteActivity_radio_man);
                        woman = findViewById(R.id.BoardWriteActivity_radio_woman);
                        all = findViewById(R.id.BoardWriteActivity_radio_all);
                        tour = findViewById(R.id.BoardWriteActivity_radio_tour);
                        carfull = findViewById(R.id.BoardWriteActivity_radio_carfull);
                        picture = findViewById(R.id.BoardWriteActivity_radio_picture);
                        food = findViewById(R.id.BoardWriteActivity_radio_food);
                        final String senddestination = destination.getText().toString();

                        final String sendcontent = content.getText().toString();
                        String sendgender1 = "2";
                        String sendpurpose = "??????";

                        if (man.isChecked())
                            sendgender1 = "0";
                        else if (woman.isChecked())
                            sendgender1 = "1";
                        else if (all.isChecked())
                            sendgender1 = "2";

                        if (food.isChecked())
                            sendpurpose = "??????";
                        else if (carfull.isChecked())
                            sendpurpose = "??????";
                        else if (picture.isChecked())
                            sendpurpose = "??????";
                        else if (tour.isChecked())
                            sendpurpose = "??????";

                        final String sendminage = age_start.getText().toString();

                        final String sendmaxage = age_end.getText().toString();

                        final String senddate = date_start.getText().toString();
                        final String sendstarttime = time_start.getText().toString();
                        final String sendendtime = time_end.getText().toString();

                        final String date;
                        String date1;
                        date1 = senddate.replace("???", "-");
                        date1 = date1.replace("???", "-");
                        date1 = date1.replace("???", "");
                        date = date1;

                        String result = null;
                        HttpBoardWrite httpBoardWriteActivity = new HttpBoardWrite();
                        HttpBoardWrite.sendTask send = httpBoardWriteActivity.new sendTask();
                        try {
                            result = send.execute(nickname, senddestination, sendcontent, sendgender1, sendminage, sendmaxage, senddate, sendstarttime, sendendtime, sendpurpose).get();
                            if ("success".equals(result)) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(BoardWriteActivity.this);
                                builder.setTitle("?????????").setMessage("????????? ????????? ?????????????????????");
                                final String finalDate = date;
                                builder.setPositiveButton("???", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        fragmentBoard.getInstance().removeAllItems();
                                        fragmentBoard.getInstance().setRefreshCount(1);
                                        fragmentBoard.getInstance().init();
                                        onBackPressed();
                                    }
                                });
                                dialog = builder.create();
                                dialog.show();

                            } else {
                                alert("?????????", "?????? ????????? ?????????");
                            }

                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }

                    }


                }


            }
        });


    }

    private void alert(String title, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(BoardWriteActivity.this);
        builder.setTitle(title).setMessage(content);
        builder.setPositiveButton("???", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent){
        if(requestCode == Code.requestCode && resultCode == Code.resultCode)
            destination.setText(resultIntent.getStringExtra("place"));
    }
    public static class Code{
        public static int requestCode = 100;
        public static int resultCode = 1;
    }

}
