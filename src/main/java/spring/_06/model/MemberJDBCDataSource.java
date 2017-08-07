package spring._06.model;

import java.sql.*;
import java.util.*;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("memberJDBCDataSource")
public class MemberJDBCDataSource implements MemberDAO {
	
	DataSource dataSource;
	@Autowired
	public MemberJDBCDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public int save(Member member) {
		int count = 0 ;
		String SQL = "insert into SpringMember "
				+ " (pk, userId, password, name, email, birthday, registerDate, experience) "
				+ " values (null, ?, ?, ?, ?, ?, ?, ?)";
				
		try (
		   //Connection conn = DriverManager.getConnection(url, user, password);
           Connection conn = dataSource.getConnection();
		   PreparedStatement stmt = conn.prepareStatement(SQL);
		) {
			stmt.setString(1, member.getUserId());
			stmt.setString(2, member.getPassword());
			stmt.setString(3, member.getName());
			stmt.setString(4, member.getEmail());
			stmt.setDate(5, member.getBirthday());
			stmt.setTimestamp(6, member.getRegDate());
			stmt.setInt(7, member.getExperience());
		   	stmt.executeUpdate();
			count++;		   	
		  	System.out.println("新建會員資料成功");
		} catch(SQLException ex){
		   	System.out.println("新建會員資料失敗");
		  	ex.printStackTrace();
		}
		return count;
	}

	public Member get(Integer pk) {
		String SQL = "select * from SpringMember where pk = ?";
		Member member = null ; 
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(SQL);
		) {
			stmt.setInt(1, pk);
		   	ResultSet rs = stmt.executeQuery();
		   	if (rs.next()){
		   		member = new Member(rs.getInt(1), rs.getString(2), 
		 				rs.getString(3), rs.getString(4),
		   				rs.getString(5), rs.getDate(6),rs.getTimestamp(7),  
		   				rs.getInt(8) );
		   	}
		} catch(SQLException ex){
		  	ex.printStackTrace();
		}
		return member;
	}

	public int delete(Integer pk) {
		int count = 0;
		String SQL = "delete from SpringMember where pk = ?";
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(SQL);
		) {
			stmt.setInt(1, pk);
		   	count = stmt.executeUpdate();
		} catch(SQLException ex){
		  	ex.printStackTrace();
		}
		return count;
	}

	public int update(Member member) {
		int count = 0;
		String SQL = "update SpringMember set  password = ?, "
				+ "name = ?, email = ?, birthday =?,  registerDate =?, experience = ? where userId = ?";
		try (
			Connection conn = dataSource.getConnection();				
			PreparedStatement stmt = conn.prepareStatement(SQL);
		) {
			stmt.setString(1, member.getPassword());
			stmt.setString(2, member.getName());
			stmt.setString(3, member.getEmail());
			stmt.setDate(4, member.getBirthday());
			stmt.setTimestamp(5, member.getRegDate());
			stmt.setInt(6, member.getExperience());
			stmt.setString(7, member.getUserId());
			count = stmt.executeUpdate();
		  	System.out.println("更新會員資料成功, count=" + count);
		} catch(SQLException ex){
		   	System.out.println("更新會員資料失敗");
		  	ex.printStackTrace();
		}
		return count;
	}

	@Override
	public List<Member> getAll() {
		String SQL = "select * from SpringMember";
		List<Member>  members = new ArrayList<>();
		Member member;
		try (
			Connection conn = dataSource.getConnection();				
			PreparedStatement stmt = conn.prepareStatement(SQL);
		) {
		   	ResultSet rs = stmt.executeQuery();
		   	while (rs.next()){
		   		member = new Member(rs.getInt(1), rs.getString(2), 
		 				rs.getString(3), rs.getString(4),
		   				rs.getString(5), rs.getDate(6),rs.getTimestamp(7),  
		   				rs.getInt(8) );
			   	members.add(member);
		   	}
		   	
		} catch(SQLException ex){
		  	ex.printStackTrace();
		}
		return members;
	}
}
