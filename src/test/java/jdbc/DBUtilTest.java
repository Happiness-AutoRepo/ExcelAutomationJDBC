package jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;


import utilities.DBUtility.DBType;
import utilities.DBUtility;

public class DBUtilTest {

	
	@Test
	public void testUtilityConnection() throws SQLException {
		Connection conn = DBUtility.getConnection(DBType.MYSQL);
		Statement stmt = conn.createStatement();
		ResultSet result = stmt.executeQuery("SELECT * FROM countries");
		
		List<String[]> sqlResultsList = new ArrayList<String[]>(); 
		
		ResultSetMetaData rsMetaData = result.getMetaData();
		int columnsCount = rsMetaData.getColumnCount();
		
		String[] colNames = new String[columnsCount];
		for(int colIndex = 1; colIndex <= columnsCount; colIndex++) {
			colNames[colIndex - 1] = rsMetaData.getColumnName(colIndex);
		} 
		
		sqlResultsList.add(colNames);
		
		while(result.next()) {
			String[] rowData = new String[columnsCount];
			for(int cellNum = 1; cellNum <= columnsCount; cellNum++) {
				rowData[cellNum - 1] = result.getString(cellNum);
			}
			
			sqlResultsList.add(rowData);
		}
		
		for(String[] rowData : sqlResultsList) {
			for(String cellData : rowData) {
				System.out.print(cellData + " ");
			}
			System.out.println("");
		}
		
	}
}
