package tx03.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="OrderItemBean")
public class OrderItemBean {
	private Integer   oiid;			// OrderItem 主鍵
	private Integer   odid;			// Order 主鍵
	private Integer   adid;			// Advertise 主鍵
	private Integer   pdid;			// Product 主鍵
	private Integer   quantity;		// 訂購數量
	private Boolean   approved;		// 合格訂單
	private OrderBean odBean;
	
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
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getOiid() {
		return oiid;
	}
	public void setOiid(Integer oiid) {
		this.oiid = oiid;
	}
	@ManyToOne  // 多對ㄧ，多中有個ㄧ，Cart類別
	// @JoinColumn: 定義多方(Items)所對應表格中的外來鍵為何。省略此註釋，
	// Hibernate會自動產生ㄧ個外來鍵，預設名稱為: 此性質名稱_外來鍵對應的主鍵名稱
	//@JoinColumn(name="od_id", nullable=false)  
	public OrderBean getOdBean() {
		return odBean;
	}

	public void setOdBean(OrderBean odBean) {
		this.odBean = odBean;
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
