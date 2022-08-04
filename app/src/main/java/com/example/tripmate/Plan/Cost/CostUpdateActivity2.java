package com.example.tripmate.Plan.Cost;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.example.tripmate.R;

import java.util.concurrent.ExecutionException;

public class CostUpdateActivity2 extends AppCompatActivity {
    private String costcode, title1, date, start, end;
    private int price1;
    private ImageButton cost_back, cost_delete;
    private EditText price, title;
    private RadioButton cash, card, type1, type2, type3, type4, type5, type6;
    private Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cost);

        Intent intent = getIntent();
        costcode = intent.getStringExtra("costcode");
        start = intent.getStringExtra("start");
        end = intent.getStringExtra("end");
        price1 = intent.getIntExtra("price", 1);
        title1 = intent.getStringExtra("title");
        System.out.println(title1);
        String price2 = String.valueOf(price1);
        date = intent.getStringExtra("date");

        price = (EditText)findViewById(R.id.cost_update_price);
        price.setText(price2);
        title = (EditText)findViewById(R.id.cost_update_title);
        title.setText(title1);
        cash = (RadioButton)findViewById(R.id.cost_update_cash);   //현금
        card = (RadioButton)findViewById(R.id.cost_update_card);   //카드
        type1 = (RadioButton)findViewById(R.id.cost_update_type_1); //숙박
        type2 = (RadioButton)findViewById(R.id.cost_update_type_2); //교통
        type3 = (RadioButton)findViewById(R.id.cost_update_type_3); //관광
        type4 = (RadioButton)findViewById(R.id.cost_update_type_4); //쇼핑
        type5 = (RadioButton)findViewById(R.id.cost_update_type_5); //식비
        type6 = (RadioButton)findViewById(R.id.cost_update_type_6); //기타

        edit = (Button)findViewById(R.id.cost_bt_edit);
        edit.setOnClickListener(new View.OnClickListener() {
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

                HttpCostUpdate httpCostUpdate = new HttpCostUpdate();
                HttpCostUpdate.SendTask sendTask = httpCostUpdate.new SendTask();
                String result = null;
                try {
                    result = sendTask.execute(costcode, sendtitle, sendcategory, sendtype, date, sendprice).get();
                    if("success".equals(result)){
                        PlanCostActivity.getInstance().getDate(start, end);
                        PlanCostActivity.getInstance().init2();
                        finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        });

        cost_back = (ImageButton)findViewById(R.id.cost_ibt_back);
        cost_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cost_delete = (ImageButton)findViewById(R.id.cost_ibt_delete);
        cost_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
                builder1.setTitle("삭제").setMessage("삭제하시겠습니까?");
                builder1.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                builder1.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String result = null;
                        HttpCostDelete httpCostDelete = new HttpCostDelete();
                        HttpCostDelete.SendTask sendTask = httpCostDelete.new SendTask();
                        PlanCostAdapter adapter = new PlanCostAdapter();
                        try{
                            result = sendTask.execute(costcode).get();
                            if("success".equals(result)){
                                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                builder.setTitle("삭제").setMessage("삭제 되었습니다.");
                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                        CostSectionAdapter.removeAllItems();
                                        PlanCostActivity.getInstance().init();
                                        PlanCostActivity.getInstance().getDate(start, end);
                                        PlanCostActivity.getInstance().init2();
                                        finish();
                                    }
                                });
                                builder.create().show();
                            }
                        } catch (ExecutionException | InterruptedException e) { e.printStackTrace(); }
                    }
                }); builder1.show();
            }
        });

    }
}
