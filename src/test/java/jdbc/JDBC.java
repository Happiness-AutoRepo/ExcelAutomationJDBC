package jdbc;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

import utilities.ConfigurationReader;

public class JDBC {
	
		public static String URL = "jdbc:mysql://localhost:3306/hr";
		public static String DBUserName = "root";
		public static String DBPassword = ConfigurationReader.getProperty("password");
		
		
	public static void main(String[] args) throws IOException, SQLException {
		
		Connection conn = DriverManager.getConnection(URL, DBUserName, DBPassword);
		System.out.println("Connection successful");
		
		if(conn != null) {
			conn.close();
		}
		
//		Runtime.getRuntime().exec("C:/Program Files/MySQL/MySQL Workbench 6.3 CE/MySQLWorkbench.exe");
		
	}
	
}
