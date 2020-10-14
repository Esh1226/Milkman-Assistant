package VariationLog;

public class RegularBean {
	String Name;
	String DOV;
	float CQty;
	float BQty;
	
	public RegularBean(){}
	
	public RegularBean(String name, String dOV, float cQty, float bQty) {
		super();
		Name = name;
		DOV = dOV;
		CQty = cQty;
		BQty = bQty;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getDOV() {
		return DOV;
	}
	public void setDOV(String dOV) {
		DOV = dOV;
	}
	public float getCQty() {
		return CQty;
	}
	public void setCQty(float cQty) {
		CQty = cQty;
	}
	public float getBQty() {
		return BQty;
	}
	public void setBQty(float bQty) {
		BQty = bQty;
	}
	

}
