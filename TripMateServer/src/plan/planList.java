package plan;

import java.sql.Date;

public class planList {
	private String planCode;
	private String userCode;
	private String planPlace;
	private String planTitle;
	private String startDate;
	private String endDate;
	public planList() {}
	
	public planList(String userCode, String planPlace, String planTitle, String startDate, String endDate) {
		this.userCode = userCode;
		this.planPlace = planPlace;
		this.planTitle = planTitle;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	public String getPlanCode() {
		return planCode;
	}
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getPlanPlace() {
		return planPlace;
	}
	public void setPlanPlace(String planPlace) {
		this.planPlace = planPlace;
	}
	public String getPlanTitle() {
		return planTitle;
	}
	public void setPlanTitle(String planTitle) {
		this.planTitle = planTitle;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
