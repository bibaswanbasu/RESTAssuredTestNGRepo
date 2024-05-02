package bb.RESTfw.utilities;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	private static String TestDataFile = System.getProperty("user.dir") + "/TestData/UserCreation_TestData.xlsx";

	public static Object[][] getUserData(String sheetName) throws IOException {
		FileInputStream fis = new FileInputStream(TestDataFile);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);

		// Get the first sheet
		Sheet sheet = workbook.getSheet(sheetName);

		// Get the number of rows and columns
		int rowCount = sheet.getLastRowNum() + 1;
		int colCount = sheet.getRow(0).getLastCellNum();

		// Create an object array to store the data
		Object[][] data = new Object[rowCount][colCount];

		// Loop through rows and columns to get data
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				Cell cell = sheet.getRow(i).getCell(j);
				String value;

				if (cell == null) {
					value = "";
				} else {
					switch (cell.getCellType()) {
					case STRING:
						value = cell.getStringCellValue();
						break;
					case NUMERIC:
						int intValue = (int) cell.getNumericCellValue();
						value = String.valueOf(intValue);
//						value = String.valueOf(cell.getNumericCellValue());
						break;
					default:
						value = "";
					}
				}
				data[i][j] = value;
			}
		}

		fis.close();
		workbook.close();
		return data;
	}

	public static String[] getColumnData(String sheetName, String columnName) throws IOException {
		FileInputStream fis = new FileInputStream(TestDataFile);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);

		// Get the first sheet
		Sheet sheet = workbook.getSheet(sheetName);

		// Get the userName column index
		int userNameColumnIndex = -1;
		for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
			Cell cell = sheet.getRow(0).getCell(j);
			if (cell != null && cell.getStringCellValue().equalsIgnoreCase(columnName)) {
				userNameColumnIndex = j;
				break;
			}
		}

		// Check if userName column found
		if (userNameColumnIndex == -1) {
			throw new RuntimeException(columnName + "column not found in Excel sheet");
		}

		// Get the number of rows (excluding header)
		int rowCount = sheet.getLastRowNum();

		// Create an array to store usernames
		String[] usernames = new String[rowCount];

		// Loop through rows to get usernames
		for (int i = 1; i <= rowCount; i++) {
			Cell cell = sheet.getRow(i).getCell(userNameColumnIndex);
			String username;

			if (cell == null) {
				username = "";
			} else {
				username = cell.getStringCellValue();
			}
			usernames[i - 1] = username;
		}

		fis.close();
		workbook.close();
		return usernames;
	}

}
