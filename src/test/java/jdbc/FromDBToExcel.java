package jdbc;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import utilities.ConfigurationReader;

public class FromDBToExcel {
	
	String URL = "jdbc:mysql://localhost:3306/hr";
	String DBUserName = "root";
	String DBPassword = ConfigurationReader.getProperty("password");
	
	Connection conn;
	Statement stmt;
	ResultSet result;
	
	String query = "SELECT country_id , country_name FROM countries;";
	
	@BeforeClass
	public void setUp() throws SQLException {
		
		conn = DriverManager.getConnection(URL, DBUserName, DBPassword);
		
	}
	
	@Test
	public void getRowsCount() throws SQLException, InvalidFormatException, IOException {
		
		stmt = conn.createStatement();
		result = stmt.executeQuery(query);
		result.first();
		
		String file = "C:/Users/Marat Metoff/Desktop/Excel.xlsx";
		FileInputStream input = new FileInputStream(file);
		
		Workbook workbook = WorkbookFactory.create(input);
		Sheet sheet = workbook.getSheetAt(0);
		
		Cell cell = sheet.getRow(0).getCell(0);
		
		if(cell == null) {
			cell = sheet.getRow(0).createCell(0);
			cell.setCellValue(result.getString("country_id"));
		} else {
			cell.setCellValue(result.getString("country_id"));
		}
		
		FileOutputStream output = new FileOutputStream(file);
		workbook.write(output);
		output.close();
	}
}
