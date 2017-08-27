package excelpracticeultimate;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelPracticeUltimate {

	public static void main(String[] args) throws Exception {

		String excelFilePath = "./src/test/resources/TestData/Book1.xlsx";
		FileInputStream fis = new FileInputStream(excelFilePath);

		// Workbook
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sh = wb.getSheetAt(0);

		int numberOfRows = sh.getPhysicalNumberOfRows();

		Row row = sh.getRow(0);
		int numberOfCells = row.getPhysicalNumberOfCells();

		Cell cell = row.getCell(0);
		System.out.println("The cell value is " + cell.toString());

		for (int i = 0; i < numberOfRows; i++) {
			if (sh.getRow(i).getCell(0).getStringCellValue().equals("Y")) {
				for (int j = 0; j < numberOfCells; j++) {
					System.out.println(sh.getRow(i).getCell(j));
				}
			} else {
				System.out.println("-------------");
				System.out.println("Skipped");
				System.out.println("-------------");
			}
		}
		
		fis.close();
		
		cell = sh.getRow(8).getCell(8);
		if(cell == null) {
			cell = sh.getRow(8).createCell(8);
			cell.setCellValue("Hahahaha");
		}else {
			cell.setCellValue("Hahahaha");
		}
		
		FileOutputStream fos = new FileOutputStream(excelFilePath);
		wb.write(fos);
		fos.close();
		wb.close();
	}

}
