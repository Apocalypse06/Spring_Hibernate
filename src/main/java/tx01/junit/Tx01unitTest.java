package tx01.junit;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import tx01.dao.OrderItemReview;
import tx01.model.OrderItemBean;

public class Tx01unitTest {

	ApplicationContext ctx = null;
	OrderItemReview oir = null;
	OrderItemBean oi1 = null;
	OrderItemBean oi2 = null;
	OrderItemBean oi3 = null;
	OrderItemBean oi4 = null;
	List<OrderItemBean> items = null;
	{
		ctx = new ClassPathXmlApplicationContext("tx01/applicationContext.xml");// 起始區塊，每次NEW就會做一次
		oir = ctx.getBean(OrderItemReview.class);

		// OrderItemBean建構子的參數說明
		// oiid : OrderItem 主鍵
		// odid : Order 主鍵
		// adid : Advertise 主鍵
		// pdid : Product 主鍵
		// quantity : 訂購數量
		// oi1物件中，
		// OrderItem之主鍵: 1；
		// 外鍵(對應Order表格之主鍵): 2；
		// 廣告代號為: 1
		// 產品代號為: 1 (單價32元)
		// 購買數量: 20
		oi1 = new OrderItemBean(1, 2, 1, 1, 20); // $ 640, $160 oiid, odid,
													// adid, pdid, amount)
		// oi2物件中，
		// OrderItem之主鍵: 2；
		// 外鍵(對應Order表格之主鍵): 2；
		// 廣告代號為: 1
		// 產品代號為: 2 (單價50元)
		// 購買數量: 20
		oi2 = new OrderItemBean(2, 2, 1, 2, 20); // $1000 / $250
		// oi3物件中，
		// OrderItem之主鍵: 3；
		// 外鍵(對應Order表格之主鍵): 2；
		// 廣告代號為: 1
		// 產品代號為: 3 (單價30元)
		// 購買數量: 20
		oi3 = new OrderItemBean(3, 2, 1, 3, 20); // $ 600/ $150
		// oi4物件中，
		// OrderItem之主鍵: 4；
		// 外鍵(對應Order表格之主鍵): 2；
		// 廣告代號為: 1
		// 產品代號為: 4 (單價10元)
		// 購買數量: 20
		oi4 = new OrderItemBean(4, 2, 1, 4, 20); // $ 200 / $50

		items = Arrays.asList(oi1, oi2, oi3, oi4);
	}

	@Test
	public void findItemAmount() throws SQLException {
		int price = oir.findItemAmount(oi1);
		System.out.println(price);
		price = oir.findItemAmount(oi2);
		System.out.println(price);
	}

	@Test
	public void checkCampaignPeriod() {
		Date orderDate = java.sql.Timestamp.valueOf("2017-08-10 23:0:0");
		int adid = 1;
		int pdid = 1;
		oir.checkCampaignPeriod(orderDate, adid, pdid);
	}

	@Test
	public void findProductPriceByAdidAndPdid() throws SQLException {
		int price = oir.findProductPriceByAdidAndPdid(1, 3);
		System.out.println(price);
		price = oir.findProductPriceByAdidAndPdid(2, 4);
		System.out.println(price);
	}

	@Test
	public void test() throws SQLException {
		DataSource dataSource = ctx.getBean(DataSource.class);
		System.out.println(dataSource.getConnection());
	}

}
