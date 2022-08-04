package com.example.tripmate.Plan;

import com.example.tripmate.Plan.Cost.CostModel;
import com.example.tripmate.Plan.Cost.HttpCostList1;
import com.example.tripmate.Plan.Cost.HttpCostList2;
import com.example.tripmate.Plan.DataClassFile.PlanListModel;
import com.example.tripmate.Plan.DataClassFile.PlanRouteDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class SelectPlanList {
    private ArrayList<PlanListModel> planlist;
    private ArrayList<PlanRouteDataModel> routelist;
    private ArrayList<CostModel> costlist;

    public SelectPlanList() { }
    public ArrayList<PlanListModel> getList(String nickname){
        //removeAllItem();
        HttpPlanList httpPlanList = new HttpPlanList();
        HttpPlanList.sendTask sendTask = httpPlanList.new sendTask();
        String result = null;
        planlist = new ArrayList<>();
        try {
            result = sendTask.execute(nickname).get();
            JSONArray jarray = null;
            jarray = new JSONObject(result).getJSONArray("addlist");

            if (jarray != null) {
                for(int i = 0; i < jarray.length(); i++) {
                    JSONObject jsonObject = jarray.getJSONObject(i);
                    String code = jsonObject.getString("plancode");
                    String place = jsonObject.getString("place");
                    String title = jsonObject.getString("title");
                    String start = jsonObject.getString("start");
                    String end = jsonObject.getString("end");
                    PlanListModel model = new PlanListModel(code, place, title, start, end);
                    planlist.add(model);
                }
            }
            return planlist;
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }
        //notifyDataSetChanged();
        return planlist;
    }

    public ArrayList<PlanRouteDataModel> getRoute (String userid, String planCode){
        //removeAllItem();
        HttpPlanRouteList httpPlanRouteList = new HttpPlanRouteList();
        HttpPlanRouteList.sendTask sendTask = httpPlanRouteList.new sendTask();
        String result = null;
        routelist = new ArrayList<>();
        try {
            result = sendTask.execute(userid, planCode).get();
            JSONArray jarray = null;
            jarray = new JSONObject(result).getJSONArray("list");

            if (jarray != null) {
                for(int i = 0; i < jarray.length(); i++) {
                    JSONObject jsonObject = jarray.getJSONObject(i);
                    String uid = jsonObject.getString("userCode");
                    String plancode = jsonObject.getString("planCode");
                    String plandate = jsonObject.getString("planDate");
                    String title = jsonObject.getString("title");
                    double locationx = jsonObject.getDouble("locationx");
                    double locationy = jsonObject.getDouble("locationy");
                    String memo = jsonObject.getString("memo");
                    int index = jsonObject.getInt("index");
                    PlanRouteDataModel rModel = new PlanRouteDataModel(uid, plancode, plandate, title, locationx, locationy, memo, index);
                    routelist.add(rModel);
                }
            }
            return routelist;
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }
        //notifyDataSetChanged();
        return routelist;
    }

    public ArrayList<CostModel> getCostlist1(String plancode){
        //removeAllItem();
        HttpCostList1 httpCostList1 = new HttpCostList1();
        HttpCostList1.sendTask sendTask = httpCostList1.new sendTask();
        System.out.println("넘어와아아아아아"+plancode);
        String result = null;
        costlist = new ArrayList<>();
        try {
            result = sendTask.execute(plancode).get();
            JSONArray jarray = null;
            jarray = new JSONObject(result).getJSONArray("costlist");

            if (jarray != null) {
                for(int i = 0; i < jarray.length(); i++) {
                    JSONObject jsonObject = jarray.getJSONObject(i);
                    String costcode = jsonObject.getString("costcode");
                    String code = jsonObject.getString("plancode");
                    String title = jsonObject.getString("title");
                    int category = jsonObject.getInt("category");
                    int type = jsonObject.getInt("type");
                    String date = jsonObject.getString("date");
                    int price = jsonObject.getInt("price");
                    CostModel cModel = new CostModel(costcode, code, title, category, type, date, price);
                    costlist.add(cModel);
                }
            }
            return costlist;
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }
        //notifyDataSetChanged();
        return costlist;
    }

    public ArrayList<CostModel> getCostlist2(String plancode){
        //removeAllItem();
        HttpCostList2 httpCostList2 = new HttpCostList2();
        HttpCostList2.sendTask sendTask = httpCostList2.new sendTask();
        System.out.println("넘어와아아아아아"+plancode);
        String result = null;
        costlist = new ArrayList<>();
        try {
            result = sendTask.execute(plancode).get();
            JSONArray jarray = null;
            jarray = new JSONObject(result).getJSONArray("costlist2");

            if (jarray != null) {
                for(int i = 0; i < jarray.length(); i++) {
                    JSONObject jsonObject = jarray.getJSONObject(i);
                    String costcode = jsonObject.getString("costcode");
                    String code = jsonObject.getString("plancode");
                    String title = jsonObject.getString("title");
                    int category = jsonObject.getInt("category");
                    int type = jsonObject.getInt("type");
                    String date = jsonObject.getString("date");
                    int price = jsonObject.getInt("price");
                    CostModel cModel = new CostModel(costcode, code, title, category, type, date, price);
                    costlist.add(cModel);
                }
            }
            return costlist;
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }
        //notifyDataSetChanged();
        return costlist;
    }
}
