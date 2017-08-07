package spring._06.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class JdbcTemplateTest {
    private ApplicationContext ctx = null;
    private JdbcTemplate jdbcTemplate = null;
    {
    	ctx = new ClassPathXmlApplicationContext("spring/_06/model/applicationContext.xml");
    	jdbcTemplate = ctx.getBean(JdbcTemplate.class);
    }
    @Test
    public void connect() throws SQLException {
       DataSource  dataSource = ctx.getBean(DataSource.class);
       Connection con = dataSource.getConnection();
       System.out.println(con);
    }
    
	@Test
	public void save() {
		String sql = "insert into SpringMember "
				+ " (pk, userId, password, name, email, birthday, registerDate, experience) "
				+ " values (null, ?, ?, ?, ?, ?, ?, ?)";
		Object[] oa = new Object[]{"kitty001", "123456", "張凱蒂", 
				 "kitty123@gmail.com", Date.valueOf("1980-05-20"), 
				 Timestamp.valueOf("2017-06-27 11:48:50"), 2};
		int[] ia = new int[]{java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, 
				java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, 
				java.sql.Types.DATE, java.sql.Types.TIMESTAMP, 
				java.sql.Types.INTEGER};
		jdbcTemplate.update(sql, oa, ia);
	}
	
	@Test
	public void delete() {
		String sql = "DELETE FROM SpringMember WHERE pk = ? ";
		jdbcTemplate.update(sql, new Integer(2));
	}
	
	@Test
	public void get() {
		String sql = "SELECT pk, userId, password, name, email, birthday, registerDate regDate, experience  FROM SpringMember WHERE pk = ? ";
// 如果確定有該筆記錄: 		
		RowMapper<Member> rowMapper = 
				new BeanPropertyRowMapper<>(Member.class);
		Member mem = jdbcTemplate.queryForObject(sql, rowMapper, 4);

		System.out.println(mem);	
	}
	@Test
	public void getAll() {
		String sql = "SELECT pk, userId, password, name, email, birthday, registerDate regDate, experience  FROM SpringMember";
// 如果確定有該筆記錄: 		
		RowMapper<Member> rowMapper = 
				new BeanPropertyRowMapper<>(Member.class);
		List<Member> list = jdbcTemplate.query(sql, rowMapper);

		System.out.println(list);	
	}

}

