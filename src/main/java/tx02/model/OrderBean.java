package tx02.model;

import java.util.*;

// 本類別存放訂單資料
public class OrderBean {
	Integer odid; 		// 訂單編號
	Integer cmid; 			// 客戶編號
	Date orderDate; 		// 訂單日期
	Double amount; 			// 訂購總金額
	String shippingAddress; // 送貨地址
	String bno; 			// 統一編號
	String InvoiceTitle; 	// 發票抬頭
	
	List<OrderItemBean> items = new ArrayList<OrderItemBean>();

	public OrderBean() {
		super();
	}

	public OrderBean(int cmid, double totalAmount, String shippingAddress, String bNO, String invoiceTitle,
			Date orderDate, List<OrderItemBean> items) {
		super();
		this.cmid = cmid;
		this.amount = totalAmount;
		this.shippingAddress = shippingAddress;
		bno = bNO;
		InvoiceTitle = invoiceTitle;
		this.orderDate = orderDate;
		this.items = items;
	}

//	public Integer getOrderNo() {
//		return odid;
//	}
//
//	public void setOrderNo(Integer orderNo) {
//		this.odid = orderNo;
//	}

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
		return InvoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		InvoiceTitle = invoiceTitle;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public List<OrderItemBean> getItems() {
		return items;
	}

	public void setItems(List<OrderItemBean> items) {
		this.items = items;
	}

}
