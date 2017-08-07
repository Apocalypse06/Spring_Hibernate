package tx01.dao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import tx01.model.OrderItemBean;
import tx01.model.StartEndDateTime;
import tx01.ude.EndOfPromotionException;
import tx01.ude.NotYetPromotedException;
import tx01.ude.ProductStockException;
import tx01.ude.UnpaidOrderAmountExceedingException;

@Repository
public class OrderItemReviewImpl implements OrderItemReview {
	public static final int ORDER_AMOUNT_LIMIT = 5000;
	@Autowired
	private JdbcTemplate  jdbcTemplate;
	
	/**
	 * 由 OrderItemBean內的產品代號(pdid)與廣告代號(adid)到Campaign表格中取得商品價格(Campaign#productPrice)。
	 */
	@Override
	public int findProductPriceByAdidAndPdid(int adid, int pdid) {
		String sql = 
				"SELECT productPrice FROM Campaign WHERE adid = ? and pdid = ?";
		int price = jdbcTemplate.queryForObject(sql, Integer.class, adid, pdid);
		return price;
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
		int price = findProductPriceByAdidAndPdid(oib.getAdid(), oib.getPdid());
		// 將價格存入OrderItemBean內，否則每次需要價格時，都需要存取資料庫campaign表格
		oib.setPrice(price);
		
		int quantity = oib.getQuantity();
		return price * quantity;
	}
	/*
	      更新客戶的未付款訂購金額。
	1. 處理一項訂單明細時，該項明細的金額 + 該客戶的未付款訂購金額不能超過限額(ORDER_AMOUNT_LIMIT)
	2. 到Customer表格中取出Customer#unpaid_order_amount
	3. 加上本訂單明細的金額後，檢查加總後的數值是否超過限額(ORDER_AMOUNT_LIMIT)。如果超過限額，則該
		訂單不予處裡，丟出UnpaidOrderAmountExceedingException，否則
		Customer#unpaid_order_amount += orderAmount;       
*/
	@Override
	public int updateUnpaidOrderAmount(OrderItemBean oib, int cmid) {
		int currentAmount = findItemAmount(oib); // 計算該項明細的金額

		// 讀取該客戶的未付款金額(unpaid_order_amount)
		String sql0 = "SELECT unpaid_order_amount FROM Customer WHERE cmid = ?";
		int unpaidAmount = jdbcTemplate.queryForObject(sql0, Integer.class, cmid);

		if (currentAmount + unpaidAmount > ORDER_AMOUNT_LIMIT) {
			throw new UnpaidOrderAmountExceedingException("未付款金額超過限額: " + (currentAmount + unpaidAmount));
		} else {
			System.out.println("new UnPaidAmount=" + (currentAmount + unpaidAmount));
		}
		String sql = "UPDATE Customer SET unpaid_order_amount " 
		           + " = unpaid_order_amount + ? WHERE cmid = ?";
		int n = jdbcTemplate.update(sql, currentAmount, cmid);
		return n;
	}
	/**
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
		String sql = "SELECT StartDateTime, endDateTime FROM campaign WHERE adid = ? AND pdid = ?";
		RowMapper<StartEndDateTime> rowMapper = new BeanPropertyRowMapper<>(StartEndDateTime.class);
		Object[] oa = {adid, pdid};
		try {
			StartEndDateTime dt = jdbcTemplate.queryForObject(sql, oa, rowMapper);
			Date sDate = dt.getStartDateTime();
			Date eDate = dt.getEndDateTime();
			if(!sDate.after(orderDate) && !eDate.before(orderDate)) {
				//	System.out.println("訂單日期有效，OK");
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
	/*	int pdid: 產品代號
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
		String sql0 = "SELECT stock FROM Product WHERE pdid = ?";
		int stock = 0;
		try {
			stock =	jdbcTemplate.queryForObject(sql0, Integer.class, pdid);
		} catch(EmptyResultDataAccessException ex){
			throw new RuntimeException("查無此筆產品資料");
		}
		if (stock - orderedQuantity < 0) {
			throw new ProductStockException("庫存數量不足");
		} else {
			System.out.println("new stock=" + (stock - orderedQuantity));
		}

		String sql = "UPDATE Product SET stock = stock - ? WHERE pdid = ?";
		int n = jdbcTemplate.update(sql, orderedQuantity, pdid);
		return n;
	}
}
