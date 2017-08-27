package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import utilities.ConfigurationReader;


// Sequel Pro - for Database
// Driver Download - https://dev.mysql.com/downloads/connector/odbc/
public class DatabaseTesting {
	
	static Connection conn;      											// Connection object
	private static Statement stmt;      									// Statement object
	private static ResultSet results;    									// Result Set
	public static String DB_URL = "jdbc:mysql://localhost:3306/world";      // Constant for Database URL. For Oracle - "jdbc:oracle:thin:@localhost:1521/sid"

	
	public static String DB_USER = "root";     								// Constant for Database Username
	public static String DB_PASSWORD = ConfigurationReader.getProperty("password");// Constant for Database Password
	public static String driver = "com.mysql.jdbc.Driver";         			// Driver.  Oracle - "oracle.jdbc.driver.OracleDriver"
	
	@BeforeClass
	public void beforeClass() {
		// Intialize WebDriver
		// driver = new FirefoxDriver();
		
		Properties props = new Properties();								// Properties for creating connection to database
		props.setProperty("user", "root");
		props.setProperty("password", "95088114");
	    
		try {
			
			Class.forName(driver).newInstance();							// STEP 1: Register JDBC driver
			
			System.out.println("Connecting to a selected database...");     // STEP 2: Get connection to DB
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			// conn = DriverManager.getConnection(DB_URL, props);		
			System.out.println("Connected database successfully...");
			
			System.out.println("Creating statement...");					// STEP 3: Statement object to send the SQL statement to the Database
			stmt = conn.createStatement();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test() throws SQLException {
        String query = "select * from city where countrycode = 'USA'";
        try {
        	
        	results = stmt.executeQuery(query);									// STEP 4: Extract data from result set
        	while (results.next()) {
        		int id = results.getInt("ID");
        		String last = results.getString("Name");
        		String first = results.getString("CountryCode");
        		String city = results.getString("District");
        		String population = results.getString("Population");
        		System.out.println("---------------");
        		
        		System.out.println("ID: " + id);								// Display Values
        		System.out.println("Last Name: " + last);
        		System.out.println("First Name: " + first);
        		System.out.println("City: " + city);
        		System.out.println("Population: " + population);
        		
// 				From GUI
//        		WebElement element = dv.findElement(By.id("uname"));
//        		String actualUserName = element.getText();
//        		Assert.assertEquals(actualUserName, first);
            }
        	results.close();
		} catch (SQLException se) {
			se.printStackTrace();												// Handle errors for JDBC
		} catch (Exception e) {
			e.printStackTrace();												// Handle errors for Class.forName
		}
	}

	@AfterClass
	public void afterClass() {
		try {
			if (results != null)
				results.close();
			if (stmt != null)
				conn.close();
			if (conn != null)
				conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}
}