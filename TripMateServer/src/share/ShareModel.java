package share;

public class ShareModel {
	
	String shareCode;
	String userCode;
	String planCode;
	String contents;
	String locationTitle;
	String shareTitle;
	
	int deleteState;

	public ShareModel() {
		
	}
	public ShareModel(String shareCode, String userCode, String planCode, String contents, String locationTitle,String shareTitle) {
		this.shareCode = shareCode; this.userCode = userCode;
		this.planCode = planCode; this.contents = contents;
		this.locationTitle = locationTitle; this.shareTitle = shareTitle;
	}
	
	public String getShareCode() {
		return shareCode;
	}
	public void setShareCode(String shareCode) {
		this.shareCode = shareCode;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getPlanCode() {
		return planCode;
	}
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public int getDeleteState() {
		return deleteState;
	}
	public void setDeleteState(int deleteState) {
		this.deleteState = deleteState;
	}
	public String getLocationTitle() {
		return locationTitle;
	}
	public void setLocationTitle(String locationTitle) {
		this.locationTitle = locationTitle;
	}
	public String getShareTitle() {
		return shareTitle;
	}
	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}
	
}
