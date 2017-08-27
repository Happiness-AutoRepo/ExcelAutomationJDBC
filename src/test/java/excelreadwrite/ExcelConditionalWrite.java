package excelreadwrite;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelConditionalWrite {

	public static void main(String[] args) throws IOException {
		
		String path = "C:/Users/Marat Metoff/Desktop/EmpData.xlsx";
		FileInputStream input = new FileInputStream(path);
		XSSFWorkbook workbook = new XSSFWorkbook(input);
		XSSFSheet worksheet = workbook.getSheet("Sheet2");
		
		int rowsCount = worksheet.getPhysicalNumberOfRows();
		
		for (int row = 1; row < rowsCount; row++) {
			if(worksheet.getRow(row).getCell(1).getStringCellValue().equals("Cucumber BDD")) {
				XSSFCell cell = worksheet.getRow(row).getCell(2);
				if(cell == null) {
					cell = worksheet.getRow(row).createCell(2);
				}
				cell.setCellValue("Pass");
				break;
			}
		}
		
		
		FileOutputStream output = new FileOutputStream(path);
		workbook.write(output);
		input.close();
		output.close();
		workbook.close();
	}

}
