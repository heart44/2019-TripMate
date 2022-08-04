package com.example.tripmate.Plan.Cost;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.example.tripmate.Plan.SelectPlanList;
import com.example.tripmate.R;

import java.util.concurrent.ExecutionException;

public class CostAddActivity2 extends AppCompatActivity {
    private String code, date, start, end;
    private ImageButton cost_close;
    private EditText price, title;
    private RadioButton cash, card, type1, type2, type3, type4, type5, type6;
    private Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cost);

        Intent intent = getIntent();
        code = intent.getStringExtra("plancode");
        start = intent.getStringExtra("start");
        end = intent.getStringExtra("end");
        date = intent.getStringExtra("date");
        System.out.println("날짜아아아ㅏ아 "+date);

        price = (EditText)findViewById(R.id.cost_add_price);
        title = (EditText)findViewById(R.id.cost_add_title);
        cash = (RadioButton)findViewById(R.id.cost_cash);   //현금
        card = (RadioButton)findViewById(R.id.cost_card);   //카드
        type1 = (RadioButton)findViewById(R.id.cost_type_1); //숙박
        type2 = (RadioButton)findViewById(R.id.cost_type_2); //교통
        type3 = (RadioButton)findViewById(R.id.cost_type_3); //관광
        type4 = (RadioButton)findViewById(R.id.cost_type_4); //쇼핑
        type5 = (RadioButton)findViewById(R.id.cost_type_5); //식비
        type6 = (RadioButton)findViewById(R.id.cost_type_6); //기타

        done = (Button)findViewById(R.id.cost_bt_done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sendprice = price.getText().toString();
                String sendtitle = title.getText().toString();
                String sendtype = "0";
                String sendcategory = "0";

                if(cash.isChecked())
                    sendtype = "0";
                else if(card.isChecked())
                    sendtype = "1";

                if(type1.isChecked())
                    sendcategory = "0";
                else if(type2.isChecked())
                    sendcategory = "1";
                else if(type3.isChecked())
                    sendcategory = "2";
                else if(type4.isChecked())
                    sendcategory = "3";
                else if(type5.isChecked())
                    sendcategory = "4";
                else if(type6.isChecked())
                    sendcategory = "5";

                HttpCostAdd httpCostAdd = new HttpCostAdd();
                HttpCostAdd.SendTask sendTask = httpCostAdd.new SendTask();
                String result = null;
                SelectPlanList data = new SelectPlanList();
                try {
                    result = sendTask.execute(code, sendtitle, sendcategory, sendtype, date, sendprice).get();
                    if("success".equals(result)){
                        CostSectionAdapter.removeAllItems();
                        PlanCostActivity.getInstance().getDate(start, end);
                        PlanCostActivity.getInstance().init2();
                        //CostSectionAdapter.addItem(data.getCostlist2(code));
                        finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        });

        cost_close = (ImageButton)findViewById(R.id.cost_imgbt_close);
        cost_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
