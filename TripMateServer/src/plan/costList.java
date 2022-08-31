package plan;

public class costList {
	private String costCode;
	private String planCode;
	private String costTitle;
	private int costCategory;
	private int costType;
	private String costDate;
	private int costPrice;
	public costList() {}
	public costList(String costCode, String planCode, String costTitle, int costCategory, int costType, String costDate, int costPrice) {
		this.costCode = costCode;
		this.planCode = planCode;
		this.costTitle = costTitle;
		this.costCategory = costCategory;
		this.costType = costType;
		this.costDate = costDate;
		this.costPrice = costPrice;
	}
	
	public String getCostCode() {
		return costCode;
	}
	public void setCostCode(String costCode) {
		this.costCode = costCode;
	}
	public String getPlanCode() {
		return planCode;
	}
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
	public String getCostTitle() {
		return costTitle;
	}
	public void setCostTitle(String costTitle) {
		this.costTitle = costTitle;
	}
	public int getCostCategory() {
		return costCategory;
	}
	public void setCostCategory(int costCategory) {
		this.costCategory = costCategory;
	}
	public int getCostType() {
		return costType;
	}
	public void setCostType(int costType) {
		this.costType = costType;
	}
	public String getCostDate() {
		return costDate;
	}
	public void setCostDate(String costDate) {
		this.costDate = costDate;
	}
	public int getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(int costPrice) {
		this.costPrice = costPrice;
	}
	
	
}
