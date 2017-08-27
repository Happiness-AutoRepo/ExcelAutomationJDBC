package excelreadwrite;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelRead {
	public static void main(String[] args) throws IOException {
		
		String excelPath = "C:/Users/Marat Metoff/Desktop/EmpData.xlsx";
		FileInputStream in = new FileInputStream(excelPath);											// open the file to read
		
		XSSFWorkbook workbook = new XSSFWorkbook(in);													// let the Apache handle all the data
		XSSFSheet worksheet = workbook.getSheet("Employees");											// going to a specific sheet
		
		int rowsCount = worksheet.getPhysicalNumberOfRows();											// getting the number of rows
		int colCount = worksheet.getRow(0).getPhysicalNumberOfCells();									// getting the number of cells in the first row(or number of columns)
		System.out.println(colCount);
		System.out.println(rowsCount);
		
		System.out.println(worksheet.getRow(0).getCell(0));						                        // getting the cell Value	
		String cellValue = worksheet.getRow(1).getCell(0).getStringCellValue();
		System.out.println(cellValue);
		
		
		for (int row = 1; row <= rowsCount; row++) {													// printing all the names + departments
			String name = worksheet.getRow(row).getCell(1).getStringCellValue();
			String dept = worksheet.getRow(row).getCell(2).getStringCellValue();
			System.out.println("| " + name + " --> " + dept + " |");
		}
		
		
		in.close();
	}
}
