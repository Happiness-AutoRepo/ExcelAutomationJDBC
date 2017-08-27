package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import utilities.ConfigurationReader;

public class JDBCNavigations {
  
	String URL = "jdbc:mysql://localhost:3306/hr";
	String DBUserName = "root";
	String DBPassword = ConfigurationReader.getProperty("password");
	
	Connection conn;
	Statement stmt;
	ResultSet result;
	
	String query = "SELECT department_id, COUNT(*) FROM employees GROUP BY department_id";
	
	@BeforeClass
	public void setUp() throws SQLException {
		
		conn = DriverManager.getConnection(URL, DBUserName, DBPassword);
		
	}
	
	@Test
	public void getRowsCount() throws SQLException {
		
		stmt = conn.createStatement();
		result = stmt.executeQuery("SELECT * FROM employees WHERE job_id = 'IT_PROG' ORDER BY salary");
		
		result.last();
		int rowsCount = result.getRow();
		
		System.out.println("Number of rows = " + rowsCount);
	}
	
	@Test(dependsOnMethods = {"getRowsCount"}) 
	public void goReverse() throws SQLException {
		result.afterLast();
		
		while(result.previous()) {
			System.out.println(result.getString("last_name") + " earns " + result.getDouble("salary"));
		}
	}
	
	@Test(dependsOnMethods = {"goReverse"})
	public void goToSpecificRow() throws SQLException {
		System.out.println("========================");
		result.absolute(2);
		System.out.println("2nd ROW: " + result.getString("last_name") + " earns " + result.getDouble("salary"));
		result.absolute(1);
		System.out.println("1st ROW: " + result.getString("last_name") + " earns " + result.getDouble("salary"));
	}
	
	@Test(dependsOnMethods = {"goToSpecificRow"})
	public void printAll() throws SQLException {
		
		System.out.println("==========================");
		result = stmt.executeQuery(query);
		while(result.next()) {
			
			int currentRowNum = result.getRow();
			int currentDeptID = result.getInt("department_id");
			int currentCount = result.getInt("count(*)");
			
			System.out.println(currentRowNum + ": Department Number - " + currentDeptID + ". Number of people: " + currentCount);
		}
		
	}
	
	@AfterTest
	public void closeConnections() throws SQLException {
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
