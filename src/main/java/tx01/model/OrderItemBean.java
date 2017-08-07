package tx01.model;

public class OrderItemBean {
	private Integer   oiid;			// OrderItem 主鍵
	private Integer   odid;			// Order 主鍵
	private Integer   adid;			// Advertise 主鍵
	private Integer   pdid;			// Product 主鍵
	private Integer   quantity;		// 訂購數量
	private Boolean   approved;		// 合格訂單
	
	//@Transient 	
	private Integer   price;		// 價格
	
	public OrderItemBean(Integer oiid, Integer odid, Integer adid, Integer pdid, Integer quantity) {
		this.oiid = oiid;
		this.odid = odid;
		this.adid = adid;
		this.pdid = pdid;
		this.quantity = quantity;
		this.approved = false;
	}

	public OrderItemBean() { 
		
	}
	
	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public Integer getOiid() {
		return oiid;
	}
	public void setOiid(Integer oiid) {
		this.oiid = oiid;
	}
	public Integer getOdid() {
		return odid;
	}
	public void setOdid(Integer odid) {
		this.odid = odid;
	}
	public Integer getAdid() {
		return adid;
	}
	public void setAdid(Integer adid) {
		this.adid = adid;
	}
	public Integer getPdid() {
		return pdid;
	}
	public void setPdid(Integer pdid) {
		this.pdid = pdid;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

}
