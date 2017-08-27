package jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;



import utilities.ConfigurationReader;

public class MetaData {

	static String URL = "jdbc:mysql://localhost:3306/hr";
	static String DBUserName = "root";
	static String DBPassword = ConfigurationReader.getProperty("password");
	
	static Connection conn;
	static Statement stmt;
	static ResultSet result;
	
	static String query = "SELECT department_id, COUNT(*) FROM employees GROUP BY department_id";
	
	public static void main(String[] args) throws SQLException {
		 
		conn = DriverManager.getConnection(URL, DBUserName, DBPassword);
		DatabaseMetaData dbMetaData = conn.getMetaData();
		
		System.out.println(dbMetaData.getDatabaseProductName());
		System.out.println(dbMetaData.getDatabaseProductVersion());
		System.out.println(dbMetaData.getUserName());
		
		stmt = conn.createStatement();
		result = stmt.executeQuery(query);
		ResultSetMetaData rsMetaData = result.getMetaData();
		
		System.out.println(rsMetaData.getColumnCount());
		System.out.println(rsMetaData.getColumnName(1));
		System.out.println(rsMetaData.getColumnName(2));
		
		result.close();
		stmt.close();
		conn.close();
		

	}

}
