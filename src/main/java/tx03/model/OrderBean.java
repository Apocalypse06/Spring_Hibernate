package tx03.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


// 本類別存放訂單資料
@Entity
@Table(name="OrderBean")
public class OrderBean {
	Integer odid; 		// 訂單編號
	Integer cmid; 			// 客戶編號
	Date orderDate; 		// 訂單日期
	Double amount; 			// 訂購總金額
	String shippingAddress; // 送貨地址
	String bno; 			// 統一編號
	String invoiceTitle; 	// 發票抬頭
	
	Set<OrderItemBean> items = new HashSet<OrderItemBean>();

	public OrderBean() {
	}

	public OrderBean(int cmid, double totalAmount, String shippingAddress, String bNO, String invoiceTitle,
			Date orderDate, Set<OrderItemBean> items) {
		this.cmid = cmid;
		this.amount = totalAmount;
		this.shippingAddress = shippingAddress;
		this.bno = bNO;
		this.invoiceTitle = invoiceTitle;
		this.orderDate = orderDate;
		this.items = items;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	//@Column(name="cart_id")
	@Column(name="odid")
	public Integer getOdid() {
		return odid;
	}

	public void setOdid(Integer odid) {
		this.odid = odid;
	}
	
	public Integer getCmid() {
		return cmid;
	}

	public void setCmid(Integer cmid) {
		this.cmid = cmid;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double totalAmount) {
		this.amount = totalAmount;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getBno() {
		return bno;
	}

	public void setBno(String bno) {
		this.bno = bno;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	// mappedBy="odBean" 說明: 
    // Items類別中，用 @ManyToOne修飾的性質(或屬性)名稱，多方必須定義外鍵(@JoinColumn)
	// 加上mappedBy屬性后，本類別就不是Owner
	// 如果省略mappedBy屬性，Hibernate會採用JoinTable來連結兩個表格
	
	// 如果省略cascade={CascadeType.ALL}，Hibernate不會代為儲存三筆Items物件，必須程式自行儲存
	// 
	@OneToMany(mappedBy="odBean", cascade={CascadeType.ALL})
	public Set<OrderItemBean> getItems() {
		return items;
	}

	public void setItems(Set<OrderItemBean> items) {
		this.items = items;
	}

}
