package spring._06.model;
// spring._06.model.MemberJdbcTemplate
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository("memberJdbcTemplate")
public class MemberJdbcTemplate extends JdbcDaoSupport implements MemberDAO {

//	@Autowired
//	private JdbcTemplate  jdbcTemplate;
	@Autowired
	public void setDataSourceA(DataSource dataSource){
		super.setDataSource(dataSource);
	}
	
	
	
	@Override
	public int save(Member member) {
		String sql = "insert into SpringMember "
				+ " (pk, userId, password, name, email, birthday, registerDate, experience) "
				+ " values (null, ?, ?, ?, ?, ?, ?, ?)";
		Object[] oa = new Object[]{
				member.getUserId(), 
				member.getPassword(), 
				member.getName(), 
				member.getEmail() , 
				member.getBirthday(), 
				member.getRegDate(), 
				member.getExperience()};
//		int[] ia = new int[]{java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, 
//				java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, 
//				java.sql.Types.DATE, java.sql.Types.TIMESTAMP, 
//				java.sql.Types.INTEGER};
		//jdbcTemplate.update(sql, oa, ia);
		getJdbcTemplate().update(sql, oa);
		return 1;
	}

	@Override
	public Member get(Integer pk) {
		Member mem = null;
//      如果確定有該筆記錄: 		
		String sql = "SELECT pk, userId, password, name, email, birthday, registerDate regDate, experience  FROM SpringMember WHERE pk = ? ";
//		RowMapper<Member> rowMapper = 
//				new BeanPropertyRowMapper<>(Member.class);
//		mem = jdbcTemplate.queryForObject(sql, rowMapper, pk);

//      如果不確定是否有該筆記錄: 		
		RowMapper<Member> rowMapper = 
				new BeanPropertyRowMapper<>(Member.class);		
		List<Member> list = getJdbcTemplate().query(sql,
				new Object[] { pk }, new int[]{java.sql.Types.INTEGER}, 
				rowMapper);
		if (list.size()==1) {
			mem = list.get(0);
		}						
		
		return mem;
	}

	@Override
	public List<Member> getAll() {
		String sql = "SELECT pk, userId, password, name, email, birthday, registerDate regDate, experience  FROM SpringMember";
		RowMapper<Member> rowMapper = 
					new BeanPropertyRowMapper<>(Member.class);
		List<Member> list = getJdbcTemplate().query(sql, rowMapper);
		return list;
	}

	@Override
	public int delete(Integer pk) {
		String sql = "DELETE FROM SpringMember WHERE pk = ? ";
		getJdbcTemplate().update(sql, pk);
		return 1;
	}

	@Override
	public int update(Member member) {
		String sql = "UPDATE SpringMember SET password = ?, "
				+ "name = ?, birthday=?, registerDate=?, "
				+ "experience = ? WHERE userId = ? ";
		getJdbcTemplate().update(sql, member.getPassword(), 
				member.getName(), member.getBirthday(), member.getRegDate(), 
				member.getExperience(), member.getUserId()  );
		return 0;
	}
	
	public void m1(){
		int[] ia = {1,2,3,4};
		String[] sa = {"Kitty", "Micky", "Snoopy"};
		sa = new String[]{"Plane", "Rock", "Dog"};
		
		
	}

}
