package tx03.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="Customer")
public class Customer {
	private Integer cmid;
	private String name;
	private Integer unpaid_order_amount;
	private boolean reject;
	public Customer(Integer cmid, String name, Integer unpaid_order_amount, boolean reject) {
		super();
		this.cmid = cmid;
		this.name = name;
		this.unpaid_order_amount = unpaid_order_amount;
		this.reject = reject;
	}
	public Customer() {
		
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getCmid() {
		return cmid;
	}
	public void setCmid(Integer cmid) {
		this.cmid = cmid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getUnpaid_order_amount() {
		return unpaid_order_amount;
	}
	public void setUnpaid_order_amount(Integer unpaid_order_amount) {
		this.unpaid_order_amount = unpaid_order_amount;
	}
	public boolean isReject() {
		return reject;
	}
	public void setReject(boolean reject) {
		this.reject = reject;
	}
	
}
