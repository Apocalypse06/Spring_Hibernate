package tx03.dao;

import java.util.Date;

import tx03.model.OrderItemBean;

public interface OrderItemReview {
	
	// 由 OrderItemBean內的產品代號(pdid)與廣告代號(adid)到Campaign表格中取得
	// 商品價格(Campaign#productPrice)。
	int findProductPriceByAdidAndPdid(int adid, int pdid);
	
	public int findItemAmount(OrderItemBean oib);
	
	int updateUnpaidOrderAmount(OrderItemBean oib, int cmid);

	void checkCampaignPeriod(Date orderDate, int adid, int pdid);
	
	int updateProductStock(int pdid, int orderedQuantity);
	
	
}
