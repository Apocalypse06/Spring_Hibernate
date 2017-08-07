package tx03.facade;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tx03.dao.OrderDao;
import tx03.dao.OrderItemReview;
import tx03.model.OrderBean;
import tx03.model.OrderItemBean;


@Service("orderService")
public class OrderServiceImpl implements OrderService {
	@Autowired
	OrderItemService ois ;
	@Autowired
	OrderItemReview oir;
	@Autowired
	OrderDao  odao; 

	@Override
	public void processOrder(OrderBean ob) {
		processDetails(ob);
		
		persistOrder(ob);		
	}	
	public void processDetails(OrderBean ob){
		Set<OrderItemBean> items = ob.getItems();
		int cmid = ob.getCmid();
		Date orderDate = ob.getOrderDate();
		for(OrderItemBean oib: items){
			ois.processOrderItem(oib, cmid, orderDate);
		}		
	}
	//計算訂單總金額
	public void persistOrder(OrderBean ob){
			double totalAmount = findTotalOrderAmount(ob);
			ob.setAmount(totalAmount);
			odao.saveOrder(ob);
			
	}
	
	/** **
	 * 計算一張訂單的總金額。
	 * 由OrderBean的getItems()取出List<OrderItemBean>物件，經由迴圈計算每項商品的金額，
	 * 加總這些金額後得到總金額。
	 */
	@Override
	public int findTotalOrderAmount(OrderBean ob) {
		int total = 0 ;
		Set<OrderItemBean> items = ob.getItems();
		for (OrderItemBean oib :items){
			if (oib.getApproved()) {//訂單被批准
				int p = oir.findProductPriceByAdidAndPdid(oib.getAdid(), oib.getPdid());
				total += p * oib.getQuantity();
			}
		}				
		return total;
	}
	public OrderItemService getOis() {
		return ois;
	}
	public void setOis(OrderItemService ois) {
		this.ois = ois;
	}
	public OrderItemReview getOir() {
		return oir;
	}
	public void setOir(OrderItemReview oir) {
		this.oir = oir;
	}
	public OrderDao getOdao() {
		return odao;
	}
	public void setOdao(OrderDao odao) {
		this.odao = odao;
	}
	
}
