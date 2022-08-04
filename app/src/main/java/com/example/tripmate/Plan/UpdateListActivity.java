package com.example.tripmate.Plan;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tripmate.R;
import com.example.tripmate.SaveSharedPreference;
import com.example.tripmate.fragmentActivity2;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class UpdateListActivity extends AppCompatActivity {
    private EditText plan_place, plan_title;
    private ImageButton close, start_date, end_date;
    private TextView text_sDate, text_eDate;
    private Button btEdit;
    Calendar calendar = Calendar.getInstance();
    private String plancode, nickname, place, title, start, end;


    //시작일
    DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDisplay();
        }
    };
    //종료일
    DatePickerDialog.OnDateSetListener datePicker2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDisplay2();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_planlist);

        Intent intent = getIntent();
        plancode = intent.getExtras().getString("code");
        nickname = SaveSharedPreference.getNickName(this);
        place = intent.getStringExtra("place");
        System.out.println("장소????????"+place);
        title = intent.getStringExtra("title");
        start = intent.getStringExtra("start");
        end = intent.getStringExtra("end");

        //여행지
        plan_place = findViewById(R.id.plan_et_place);
        plan_place.setText(place);
        plan_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PlanSearchActivity.class);
                startActivityForResult(intent, UpdateListActivity.Code.requestCode);
            }
        });

        //일정타이틀
        plan_title = findViewById(R.id.plan_et_title);
        plan_title.setText(title);

        //일정시작일
        text_sDate = findViewById(R.id.plan_tv_stardate);
        text_sDate.setText(start);
        start_date = findViewById(R.id.plan_imgbt_startdate);
        start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(UpdateListActivity.this, datePicker, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //일정종료일
        text_eDate = findViewById(R.id.plan_tv_enddate);
        text_eDate.setText(end);
        end_date = findViewById(R.id.plan_imgbt_enddate);
        end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(UpdateListActivity.this, datePicker2, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btEdit = findViewById(R.id.plan_bt_edit);
        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sendplace = plan_place.getText().toString();
                String sendtitle = plan_title.getText().toString();
                String sendstart = text_sDate.getText().toString();
                String sendend = text_eDate.getText().toString();

                HttpPlanListUpdate httpPlanListUpdate = new HttpPlanListUpdate();
                HttpPlanListUpdate.SendTask sendTask = httpPlanListUpdate.new SendTask();
                String result = null;
                try {
                    result = sendTask.execute(plancode, sendplace, sendtitle, sendstart, sendend).get();
                    if("success".equals(result)){
                        fragmentActivity2.getInstance().init();
                        onBackPressed();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        //액티비티 종료
        close = findViewById(R.id.plan_imgbt_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //시작일
    private void updateDisplay(){
        String format = "yy/MM/dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.KOREA);
        String a = simpleDateFormat.format(calendar.getTime());
        text_sDate.setText(a);
    }
    //종료일
    private void updateDisplay2(){
        String format = "yy/MM/dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.KOREA);
        String a = simpleDateFormat.format(calendar.getTime());
        text_eDate.setText(a);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent){
        if(requestCode == UpdateListActivity.Code.requestCode && resultCode == UpdateListActivity.Code.resultCode)
            plan_place.setText(resultIntent.getStringExtra("place"));
    }

    public static class Code{
        public static int requestCode = 100;
        public static int resultCode = 1;
    }
}

