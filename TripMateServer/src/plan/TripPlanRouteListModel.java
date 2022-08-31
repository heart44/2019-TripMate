package plan;

public class TripPlanRouteListModel {

	String user_code;
	String user_id;
	String plan_code;
	String plan_date;
	String title;
	double locationx;
	double locationy;
	String memo;
	int index;
	int state;

	

	public TripPlanRouteListModel() {
	}
	
	public TripPlanRouteListModel(String user_code, String plan_code, String plan_date, String title, double locationx,
			double locationy, String memo, int index,  int state) {

		this.user_code = user_code;
		this.plan_code = plan_code;
		this.plan_date = plan_date;
		this.title = title;
		this.locationx = locationx;
		this.locationy = locationy;
		this.memo = memo;
		this.index = index;
		this.state = state;
	}

	public String getUser_code() {
		return user_code;
	}

	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getPlan_code() {
		return plan_code;
	}

	public void setPlan_code(String plan_code) {
		this.plan_code = plan_code;
	}

	public String getPlan_date() {
		return plan_date;
	}

	public void setPlan_date(String plan_date) {
		this.plan_date = plan_date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getLocationx() {
		return locationx;
	}

	public void setLocationx(double locationx) {
		this.locationx = locationx;
	}

	public double getLocationy() {
		return locationy;
	}

	public void setLocationy(double locationy) {
		this.locationy = locationy;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
