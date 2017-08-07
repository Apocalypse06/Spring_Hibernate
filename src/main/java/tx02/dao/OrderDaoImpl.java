package tx02.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tx02.model.OrderBean;
import tx02.model.OrderItemBean;

public class OrderDaoImpl implements OrderDao {

	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	/**
	 * 兩個步驟: (1)寫入訂單主檔 (2)寫入訂單明細檔
	 */
	public int saveOrder(OrderBean ob) {

		// GeneratedKeyHolder: 存放寫入訂單主檔之主鍵值
		GeneratedKeyHolder holder = new GeneratedKeyHolder();

		String sql1 = "INSERT INTO ORDERS " + " (odid, cmid, orderDate, amount, shippingAddress, BNO, InvoiceTitle)"
				+ " VALUES(null,  ?, ?, ?, ?, ?, ? )";
		// 利用JDBCTemplate來寫入訂單。
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = con.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
				statement.setInt(1, ob.getCmid());
				statement.setDate(2, new java.sql.Date(ob.getOrderDate().getTime()));
				statement.setDouble(3, ob.getAmount());
				statement.setString(4, ob.getShippingAddress());
				statement.setString(5, ob.getBno());
				statement.setString(6, ob.getInvoiceTitle());
				return statement;
			}
		}, holder);
		// 準備寫入訂單明細檔
		// 取出訂單主檔之主鍵值
		Integer odid = holder.getKey().intValue();
		String sql2 = "INSERT INTO ORDERITEM " + "       (oiid, odid, adid, pdid, quantity, price)"
				+ " VALUES(null,  ?,     ?,     ?,     ?,       ? )";
		// 取出OrderBean內的訂單明細
		List<OrderItemBean> items = ob.getItems();

		int cmid = ob.getCmid(); // 客戶編號，位於OrderBean內
		Date orderDate = ob.getOrderDate(); // 訂單日期，位於OrderBean內
		for (OrderItemBean oib : items) { // 此迴圈會寫入每筆訂單明細
			if (oib.getApproved()) {
				Integer adid = oib.getAdid(); // 廣告編號
				Integer pdid = oib.getPdid(); // 產品編號
				Integer quantity = oib.getQuantity(); // 產品數量
				Integer price = oib.getPrice(); // 產品價格
				jdbcTemplate.update(sql2, odid, adid, pdid, quantity, price);
			}
		}
		return 1;
	}
}
