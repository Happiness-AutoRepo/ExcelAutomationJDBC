package excelreadwrite;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelConditionalRead {
	public static void main(String[] args) throws IOException {
		
		String path = "C:/Users/Marat Metoff/Desktop/EmpData.xlsx";
		FileInputStream input = new FileInputStream(path);
		XSSFWorkbook workbook = new XSSFWorkbook(input);
		XSSFSheet worksheet = workbook.getSheet("Sheet2");
		
		int rowsCount = worksheet.getPhysicalNumberOfRows();
		
		for (int row = 1; row < rowsCount; row++) {													// printing only the values with "Y" index
			if(worksheet.getRow(row).getCell(0).getStringCellValue().equals("Y")) {
				System.out.println(worksheet.getRow(row).getCell(1));
			}
		}
		input.close();
		workbook.close();
	}
}


// .xlx - HSSFWorkbook, .xlsx - XSSFWorkbook