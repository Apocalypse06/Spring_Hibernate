package tx01.junit;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import tx01.dao.OrderItemReview;
import tx01.facade.OrderItemService;
import tx01.facade.OrderService;
import tx01.model.OrderBean;
import tx01.model.OrderItemBean;

public class TXAnnoTest {
    ApplicationContext ctx = null;
    OrderItemService ois = null;
    OrderService os = null;
    OrderItemReview oir = null;
    OrderBean ob = null;
    OrderItemBean oi1 = null;
    OrderItemBean oi2 = null;
    OrderItemBean oi3 = null;
    OrderItemBean oi4 = null;
    {
    	ctx = new ClassPathXmlApplicationContext("tx01/applicationContext.xml");
    	oir = ctx.getBean(OrderItemReview.class);
    	// OrderItemBean建構子的參數說明
    	// oiid		: OrderItem 主鍵
    	// odid		: Order 主鍵
    	// adid		: Advertise 主鍵
    	// pdid		: Product 主鍵
    	// quantity	: 訂購數量
    	// oi1物件中，
    	// OrderItem之主鍵: 1；
    	// 外鍵(對應Order表格之主鍵): 2；
    	// 廣告代號為: 1
    	// 產品代號為: 1 (單價32元)
    	// 購買數量: 20
    	oi1 = new OrderItemBean(1, 2, 1, 1, 20);   	// $ 640, $160  oiid, odid, adid, pdid, amount)
    	// oi2物件中，
    	// OrderItem之主鍵: 2；
    	// 外鍵(對應Order表格之主鍵): 2；
    	// 廣告代號為: 1
    	// 產品代號為: 2 (單價50元)
    	// 購買數量: 20
    	oi2 = new OrderItemBean(2, 2, 1, 2, 20);    // $1000 / $250
    	// oi3物件中，
    	// OrderItem之主鍵: 3；
    	// 外鍵(對應Order表格之主鍵): 2；
    	// 廣告代號為: 1
    	// 產品代號為: 3 (單價30元)
    	// 購買數量: 20
    	oi3 = new OrderItemBean(3, 2, 1, 3, 20);   	// $ 600/ $150
    	// oi4物件中，
    	// OrderItem之主鍵: 4；
    	// 外鍵(對應Order表格之主鍵): 2；
    	// 廣告代號為: 1
    	// 產品代號為: 4 (單價10元)
    	// 購買數量: 20
    	oi4 = new OrderItemBean(4, 2, 1, 4, 20);    // $ 200  / $50 
    	                                            // Total: $2440/ $610
    	ois = ctx.getBean(OrderItemService.class);
    	os = ctx.getBean(OrderService.class);
    	
    	List<OrderItemBean> items  = Arrays.asList(oi1, oi2, oi3, oi4);
    	ob = new OrderBean(1, 0, "新北市三峽區中正路100號", "08654123", "新欣美妝工業股份有限公司", java.sql.Date.valueOf("2017-05-25"), items);
    }
    
    @Test
    // 測試
    public void processOrder() {
		os.processOrder(ob);
	}
    
    @Test
    // 測試
    public void processOrderItem() {
		int cmid = 1 ;  // 客戶編號為 1 
		Date date = java.sql.Date.valueOf("2018-05-22");
		ois.processOrderItem(oi2, cmid, date);
	}
    
    // updateProductStock
    @Test
    public void updateUnpaidOrderAmount() {
		oir.updateUnpaidOrderAmount(oi4, 2);
	}
    
    // OK
    @Test
    public void updateProductStockA() {
		oir.updateProductStock(1, 10);
	}
    
    // OK，計算一張訂單的總金額
    @Test
    public void findTotalOrderAmount() {
    	OrderItemBean oi1 = new OrderItemBean(1, 3, 1, 1, 7);  // 224
    	OrderItemBean oi2 = new OrderItemBean(1, 3, 1, 2, 3);  // 150
    	OrderItemBean oi3 = new OrderItemBean(1, 3, 1, 3, 1);  //  30
    	OrderItemBean oi4 = new OrderItemBean(1, 3, 1, 4, 5);  //  50
    	List<OrderItemBean> items  = Arrays.asList(oi1, oi2, oi3, oi4);
    	OrderBean ob = new OrderBean(1, 0, "", "", "", java.sql.Date.valueOf("2017-07-26"), items);
		int price = os.findTotalOrderAmount(ob);
		System.out.println("一張訂單的總金額:" + price);
	}
    
    // OK，由廣告代號與產品代號來查產品價格
    @Test
    public void findProductPriceByAdidAndPdid() {
		int price ;
		for (int n = 1; n <= 4; n++) {
			price = oir.findProductPriceByAdidAndPdid(2, n);
			System.out.println(price);
		}
	}
    
    // OK，檢查訂購之商品是否已經超過促銷期限  
	@Test
	public void testStartDateEndDate() {
		// orderDate: 訂單日期
		Date orderDate = java.sql.Timestamp.valueOf("2017-08-10 22:30:00");
		int adid = 2;	// 廣告代號
		int pdid = 1;	// 產品代號
		oir.checkCampaignPeriod(orderDate, adid, pdid);
	}
}
