package tx03.dao;

import java.util.Date;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import tx03.model.OrderItemBean;
import tx03.ude.EndOfPromotionException;
import tx03.ude.NotYetPromotedException;
import tx03.ude.ProductStockException;
import tx03.ude.UnpaidOrderAmountExceedingException;

@Repository
public class OrderItemReviewImpl implements OrderItemReview {
	public static final int ORDER_AMOUNT_LIMIT = 5000;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	/** **
	 * 由 OrderItemBean內的產品代號(pdid)與廣告代號(adid)到Campaign表格中取得商品價格(Campaign#productPrice)。
	 */
	@Override
	public int findProductPriceByAdidAndPdid(int adid, int pdid) {
		try {
			String hql = "SELECT c.productPrice FROM Campaign c WHERE c.campaignPK.adid = ? AND c.campaignPK.pdid = ?";
			return (int) getSession().createQuery(hql).setParameter(0, adid).setParameter(1, pdid).uniqueResult();
		} catch(EmptyResultDataAccessException ex){
			throw new RuntimeException("查無此商品的價格, 廣告代號: " + adid + ", 產品代號: " + pdid);
		} 	
	}
	/**
	 *	計算某項商品(以OrderItemBean物件來表示)的金額，計算公式為 
		findProductPriceByPdidAndAdid(adid, pdid) 乘以 quantity(該項商品的數量)。 
		adid: 廣告代號, OrderItemBean#getAdid()
		pdid: 產品代號, OrderItemBean#getPdid()
		quantity: 數量, OrderItemBean#getAmount() 
	 */
	@Override
	public int findItemAmount(OrderItemBean oib) {
		int total = 0;
		int p = findProductPriceByAdidAndPdid(oib.getAdid(), oib.getPdid());
		// 將價格存入OrderItemBean內，否則每次需要價格時，都需要存取資料庫campaign表格
		oib.setPrice(p);
		
		total = p * oib.getQuantity();
		return total;
	}
    /** **
   	      更新客戶的未付款訂購金額。
   	1. 處理一項訂單明細時，該項明細的金額 + 該客戶的未付款訂購金額不能超過限額(ORDER_AMOUNT_LIMIT)
   	2. 到Customer表格中取出Customer#unpaid_order_amount
   	3. 加上本訂單明細的金額後，檢查加總後的數值是否超過限額(ORDER_AMOUNT_LIMIT)。如果超過限額，則該
   		訂單不予處裡，丟出UnpaidOrderAmountExceedingException，否則
   		Customer#unpaid_order_amount += orderAmount;       
     */
	@Override
	public int updateUnpaidOrderAmount(OrderItemBean oib, int cmid) {
		int n = 0;
		int currentAmount = findItemAmount(oib);  // 計算該項明細的金額
		
		// 讀取該客戶的未付款金額(unpaid_order_amount)
		String hql0 = "SELECT c.unpaid_order_amount FROM Customer c WHERE c.cmid = ?";
		Query query0 = getSession().createQuery(hql0);
		
		int unpaidAmount = (int) query0.setParameter(0, cmid).getSingleResult();

		if (currentAmount + unpaidAmount > ORDER_AMOUNT_LIMIT) {
			throw new UnpaidOrderAmountExceedingException("未付款金額超過限額: " + (currentAmount + unpaidAmount));
		} else {
			System.out.println("new UnPaidAmount=" + (currentAmount+unpaidAmount));
		}
		String hql = "UPDATE Customer c SET c.unpaid_order_amount "
				+ " = c.unpaid_order_amount + ? WHERE c.cmid = ?";
		
		n = (int)getSession().createQuery(hql)
				.setParameter(0, currentAmount)
				.setParameter(1, cmid).executeUpdate();

		return n;

	}
	/** **
 		檢查訂購之商品是否已經超過促銷期限
    	void checkCampaignPeriod(Date OrderBean#orderDate)
		檢查訂單的時間是否介於促銷期限之內，
		如果尚未開始促銷(OrderBean#orderDate < Campaign#startDateTime)，
			丟出NotYetPromotedException，
		如果已經結束促銷(OrderBean#orderDate > Campaign#endDateTime)，
			丟出EndOfPromotionException
	 */
	@Override
	public void checkCampaignPeriod(Date orderDate, int adid, int pdid) {
		String hql = "SELECT c.startDateTime, c.endDateTime FROM Campaign c WHERE adid = ? AND pdid = ?";
		Query query = getSession().createQuery(hql).setParameter(0, adid).setParameter(1, pdid);
		Object[] list = (Object[])query.getSingleResult();
		try {
			Date sDate = (Date)list[0];
			Date eDate = (Date)list[1];;
			if(!sDate.after(orderDate) && !eDate.before(orderDate)) {
				System.out.println("訂單日期有效，OK");
			} else {
				if(sDate.after(orderDate) ) {
					throw new NotYetPromotedException("此行銷活動尚未開始");
				}
				if(eDate.before(orderDate) ) {
					throw new EndOfPromotionException("此行銷活動已經結束");
				}
			} 
		} catch(EmptyResultDataAccessException ex){
			throw new RuntimeException("查無此行銷活動");
		} 	 

	}
	//	
	/**	**
	    int pdid: 產品代號
	    int orderedQuantity: 訂購數量
	        更新商品的數量：必須先檢查訂購之商品的數量是否足夠
		檢查Product表格中的庫存數量(Product#stock)減去orderedQuantity
		如果小於0 
			丟出ProductStockException，
		否則
			Product#stock -= orderedQuantity;
	 */
	@Override
	public int updateProductStock(int pdid, int orderedQuantity) {
		int n = 0;
		String hql0 = "SELECT p.stock FROM Product p WHERE p.pdid = :pdid";
		Query query = getSession().createQuery(hql0);
		int stock = (int) query.setParameter("pdid", pdid).getSingleResult();
		System.out.println("stock=" + stock);
		if (stock - orderedQuantity < 0) {
			throw new ProductStockException("庫存數量不足");
		} else {
			System.out.println("new stock=" + (stock - orderedQuantity));
		}

		String hql = "UPDATE Product p SET p.stock = p.stock - :qty WHERE p.pdid = :pdid";
		n = getSession().createQuery(hql)
				.setParameter("qty", orderedQuantity)
				.setParameter("pdid", pdid).executeUpdate();
		return n;
	}
	
	
}
