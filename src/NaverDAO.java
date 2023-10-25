
import java.sql.*;
import java.util.*;

public class NaverDAO {
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	
	String url, user, pass;
	
	public NaverDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("����̹� �˻� ���� �߻�!!");
			System.exit(0);
		}
		url = "jdbc:oracle:thin:@localhost:1521:xe";
		user = "aws01";
		pass = "aws01";
	}
	
	
	public List<NaverMember> listNaver() {
		// order by tot asc : �������� ��������, default ��
		// order by tot desc : �������� ��������
		String sql = "select * from naver2";
		try {
			System.out.println("����Ʈ�Ǵ�3324");
			con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			java.util.List<NaverMember> list = new ArrayList<>();
			while(rs.next()) {
				String id = rs.getString("ID");
				String name = rs.getString("NAME");
				
				NaverMember nm = new NaverMember(id, name);
				list.add(nm);
				
			}
			System.out.println("����Ʈ�Ǵ�");
			return list;
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (con != null) con.close();
			} catch (SQLException e) {}
		}
		return null;
	}
	
}
