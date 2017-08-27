package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import utilities.ConfigurationReader;

public class JDBCSQLStatement {
	public static String URL = "jdbc:mysql://localhost:3306/hr";
	public static String DBUserName = "root";
	public static String DBPassword = ConfigurationReader.getProperty("password");
	
	static Connection conn;
	static Statement stmt;
	static ResultSet result;

	public static void main(String[] args) throws SQLException {
		try {
			
			conn = DriverManager.getConnection(URL, DBUserName, DBPassword);
			System.out.println("Successfully connected");
			
			stmt = conn.createStatement();
			
			result = stmt.executeQuery("SELECT * FROM countries");
//			result.next();
			
			while(result.next()) {
				System.out.println(result.getString("country_id"));
				System.out.println(result.getString("country_name"));
				System.out.println(result.getString("region_id"));
				System.out.println("----------------------------");
			}
			System.out.println("================================");
			
			result = stmt.executeQuery("SELECT last_name, department_name "
									+  "FROM employees e JOIN departments d "
									+  "ON e.department_id = d.department_id");
			
			while(result.next()) {
				System.out.println(result.getString("last_name") + " works in the " + result.getString("department_name") + " department");
			}
			
			
		} catch (SQLException e) {
			System.out.println("Something went wrong");
			e.printStackTrace();
		} finally {
			if(result != null) {
				result.close();
			}
			if(stmt != null) {
				stmt.close();
			}
			if(conn != null) {
				conn.close();
			}
		}
		
	}

}
