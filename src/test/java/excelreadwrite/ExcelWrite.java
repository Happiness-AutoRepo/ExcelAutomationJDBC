package excelreadwrite;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWrite {

	public static void main(String[] args) throws IOException {
		
		String path = "C:/Users/Marat Metoff/Desktop/EmpData.xlsx";
		FileInputStream input = new FileInputStream(path);
		XSSFWorkbook workbook = new XSSFWorkbook(input);
		XSSFSheet worksheet = workbook.getSheet("Sheet2");
		
		int rowsCount = worksheet.getPhysicalNumberOfRows();
		
		input.close();
		
		XSSFCell cell = worksheet.getRow(1).getCell(2);
		if(cell == null) {
			cell = worksheet.getRow(1).createCell(2);
		}
		cell.setCellValue("Pass");
		
		cell = worksheet.getRow(5).getCell(2);
		if(cell == null) {
			cell = worksheet.getRow(5).createCell(2);
		}
		cell.setCellValue("Pass");
		
		
		
		
		FileOutputStream output = new FileOutputStream(path);
		workbook.write(output);
		output.close();
		workbook.close();
	}

}
