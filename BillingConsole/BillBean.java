package BillingConsole;

public class BillBean {
	
	String name,sd,ed;
	float cq,bq,amnt;
	int s;
	
	public BillBean(){}
	
	public BillBean(String name, String sd, String ed, float cq, float bq, float amnt, int s) {
		super();
		this.name = name;
		this.sd = sd;
		this.ed = ed;
		this.cq = cq;
		this.bq = bq;
		this.amnt = amnt;
		this.s = s;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSd() {
		return sd;
	}

	public void setSd(String sd) {
		this.sd = sd;
	}

	public String getEd() {
		return ed;
	}

	public void setEd(String ed) {
		this.ed = ed;
	}

	public float getCq() {
		return cq;
	}

	public void setCq(float cq) {
		this.cq = cq;
	}

	public float getBq() {
		return bq;
	}

	public void setBq(float bq) {
		this.bq = bq;
	}

	public float getAmnt() {
		return amnt;
	}

	public void setAmnt(float amnt) {
		this.amnt = amnt;
	}

	public int getS() {
		return s;
	}

	public void setS(int s) {
		this.s = s;
	}
	
	
}
