package tx03.facade;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tx03.dao.OrderItemReview;
import tx03.model.OrderItemBean;


@Service
public class OrderItemServiceImpl implements OrderItemService {

	
	@Autowired
	private OrderItemReview  oir ;
	
	@Override
	public void processOrderItem(OrderItemBean oib, int cmid, Date orderDate) {
		int adid = oib.getAdid();
		int pdid = oib.getPdid();
		int quantity = oib.getQuantity();
		// 1. 更新客戶的未付款訂購金額。
		oir.updateUnpaidOrderAmount(oib, cmid);

		// 2.更新商品的數量：必須先檢查訂購之商品的數量是否足夠
		oir.updateProductStock(pdid, quantity); 
		
		// 3. 檢查訂購之商品是否已經超過促銷期限
		oir.checkCampaignPeriod(orderDate, adid, pdid);
		
		oib.setApproved(true);
	}
}
