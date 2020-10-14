package CustomerHistory;

public class CustomerBean {

	String name;
	String mobile;
	String address;
	String dos;
	float cqty;
	float bqty;
	float cprice;
	float bprice;
	int status;
	
	public CustomerBean() {
	}
	
	public CustomerBean(String name, String mobile, String address, String dos, float cqty, float bqty, float cprice,
			float bprice, int status) {
		super();
		this.name = name;
		this.mobile = mobile;
		this.address = address;
		this.dos = dos;
		this.cqty = cqty;
		this.bqty = bqty;
		this.cprice = cprice;
		this.bprice = bprice;
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDos() {
		return dos;
	}

	public void setDos(String dos) {
		this.dos = dos;
	}

	public float getCqty() {
		return cqty;
	}

	public void setCqty(float cqty) {
		this.cqty = cqty;
	}

	public float getBqty() {
		return bqty;
	}

	public void setBqty(float bqty) {
		this.bqty = bqty;
	}

	public float getCprice() {
		return cprice;
	}

	public void setCprice(float cprice) {
		this.cprice = cprice;
	}

	public float getBprice() {
		return bprice;
	}

	public void setBprice(float bprice) {
		this.bprice = bprice;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
