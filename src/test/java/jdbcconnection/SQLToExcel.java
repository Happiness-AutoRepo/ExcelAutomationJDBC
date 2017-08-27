package jdbcconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import utilities.ExcelUtils;

public class SQLToExcel {
  
	private static String URL = "jdbc:mysql://localhost:3306/hr";
	private static String DbUserName = "root";
	private static String DbPassword = "95088114";
	
	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;
	String sql="SELECT country_id , country_name FROM countries";
	
	@BeforeTest
	public void connect() throws SQLException{
		connection=DriverManager.getConnection(URL,DbUserName,DbPassword);
		statement=connection.createStatement();
		resultSet=statement.executeQuery(sql);
		ExcelUtils.openExcelFile("./src/test/java/jdbcconnection/Countries.xlsx", "Sheet1");
	}
	
	@Test
	public void loadDataToExcel() throws SQLException {
		ExcelUtils.setCellData("COUNTRY_ID", 0, 0);
		ExcelUtils.setCellData("COUNTRY_NAME", 0, 1);
		
		int currentRow=1;
		while(resultSet.next()){
			String countryId=resultSet.getString("country_id");
			String countryName=resultSet.getString("country_name");
			//write into excel
			ExcelUtils.setCellData(countryId, currentRow, 0);
			ExcelUtils.setCellData(countryName, currentRow, 1);
			currentRow++;		
		}
		
	}
	
	@AfterTest
	public void closeConnections() throws SQLException{
		if (resultSet != null) {

			resultSet.close();
		}
		if (statement != null) {
			statement.close();
		}
		if (connection != null) {
			connection.close();
		}
	}
}

