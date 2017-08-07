package tx01.dao;

import java.util.Date;

import tx01.model.OrderItemBean;


public interface OrderItemReview {
	
	// 由 OrderItemBean內的產品代號(pdid)與廣告代號(adid)到Campaign表格中取得
	// 商品價格(Campaign#productPrice)。
	int findProductPriceByAdidAndPdid(int adid, int pdid);     // ok
	    
	public int findItemAmount(OrderItemBean oib);              // OK
	
	int updateUnpaidOrderAmount(OrderItemBean oib, int cmid);  		// Facade
	

	void checkCampaignPeriod(Date orderDate, int adid, int pdid);  	// Facade
	
	int updateProductStock(int pdid, int orderedQuantity);      	// Facade
	
	
}
