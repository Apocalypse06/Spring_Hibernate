package tx03.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tx03.model.OrderBean;
import tx03.model.OrderItemBean;

@Repository
public class OrderDaoImpl implements OrderDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	/**
	 * 
	 */
	public int saveOrder(OrderBean ob) {
		int n = 0;
		Session session = getSession();

		Set<OrderItemBean> items = ob.getItems();
		ob.setItems(new HashSet<OrderItemBean>());
		session.save(ob);
		int id = ob.getOdid();
		for (OrderItemBean oid : items) {
			
		}

		System.out.println("購物車ID=" + ob.getOdid());

		return n;
	}

	public int saveOrder1(OrderBean ob) {

		// // GeneratedKeyHolder: 存放寫入訂單主檔之主鍵值
		// GeneratedKeyHolder holder = new GeneratedKeyHolder();

		String hql1 = "INSERT INTO OrderBean " + " (odid, cmid, orderDate, amount, shippingAddress, bno, invoiceTitle) "
				+ "  values  (null, ?, ?, ?, ?, ?, ? )";
		Query query1 = getSession().createQuery(hql1);
		// 利用Query來寫入訂單。
		query1.setParameter(0, ob.getCmid()).setParameter(1, new java.sql.Date(ob.getOrderDate().getTime()))
				.setParameter(2, ob.getAmount()).setParameter(3, ob.getShippingAddress()).setParameter(4, ob.getBno())
				.setParameter(5, ob.getInvoiceTitle()).executeUpdate();

		//
		//
		//
		//
		//
		// jdbcTemplate.update(new PreparedStatementCreator() {
		// @Override
		// public PreparedStatement createPreparedStatement(Connection con)
		// throws SQLException {
		// PreparedStatement statement = con.prepareStatement(sql1,
		// Statement.RETURN_GENERATED_KEYS);
		// statement.setInt(1, ob.getCmid());
		// statement.setDate(2, new java.sql.Date(ob.getOrderDate().getTime()));
		// statement.setDouble(3, ob.getAmount());
		// statement.setString(4, ob.getShippingAddress());
		// statement.setString(5, ob.getBno());
		// statement.setString(6, ob.getInvoiceTitle());
		// return statement;
		// }
		// }, holder);
		// 準備寫入訂單明細檔
		// 取出訂單主檔之主鍵值
		BigInteger lastId = ((BigInteger) getSession().createQuery("SELECT LAST_INSERT_ID()").uniqueResult());
		Integer odid = lastId.intValue();
		String hql2 = "INSERT INTO OrderItemBean " + "       (oiid, odid, adid, pdid, quantity, price)"
				+ " VALUES (null,  ?,     ?,    ?,     ?,       ? )";
		// 取出OrderBean內的訂單明細
		Set<OrderItemBean> items = ob.getItems();

		int cmid = ob.getCmid(); // 客戶編號，位於OrderBean內
		Date orderDate = ob.getOrderDate(); // 訂單日期，位於OrderBean內
		// int i = 0;
		for (OrderItemBean oib : items) { // 此迴圈會寫入每筆訂單明細
			if (oib.getApproved()) {
				// if (++i > 1)
				// throw new RuntimeException("Error");
				Integer adid = oib.getAdid(); // 廣告編號
				Integer pdid = oib.getPdid(); // 產品編號
				Integer quantity = oib.getQuantity(); // 產品數量
				Integer price = oib.getPrice(); // 產品價格
				Query query2 = getSession().createQuery(hql2);
				query2.setParameter(0, odid).setParameter(1, adid).setParameter(2, pdid).setParameter(3, quantity)
						.setParameter(4, price).executeUpdate();

				// jdbcTemplate.update(sql2, odid, adid, pdid, quantity, price);
			}
		}
		return 1;
	}

}
