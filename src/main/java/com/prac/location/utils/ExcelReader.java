package com.prac.location.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	private static String TestDataFile = System.getProperty("user.dir") + "/TestData/UserCreation_TestData.xlsx";

	public static void main(String[] args) throws IOException {
//		List<String> data = readExcelByColumnName("CreateUser", "phone");
//		System.out.println(data);

//		readExcelData("CreateUser");
		readExcelDataByColName("CreateUser", "phone");

	}

	public static List<String> readExcelByColumnName(String sheetName, String columnName) throws IOException {
		List<String> dataList = new ArrayList<String>();

		FileInputStream inputStream = new FileInputStream(TestDataFile);
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

		// get sheet
		Sheet sheet = workbook.getSheet(sheetName);

		// get row including the header [ +1 ]
		int rowNum = sheet.getLastRowNum() + 1;
		Row headerRow = sheet.getRow(0);
		// get column number
		int cellNum = headerRow.getLastCellNum();

		System.out.println("ROW- " + rowNum + " Column- " + cellNum);

		int columnIndex = -1;
		for (int i = 0; i < headerRow.getLastCellNum(); i++) {
			if (headerRow.getCell(i).getStringCellValue().equals(columnName)) {
				columnIndex = i;
				break;
			}
		}

		if (columnIndex == -1) {
			throw new IllegalArgumentException("Column name not found: " + columnName);
		}

//		System.out.println("Last Row number is: " + sheet.getLastRowNum());
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			Cell cell = row.getCell(columnIndex);
			String data = "";
			if (cell != null) {
				switch (cell.getCellType()) {
				case STRING:
					data = cell.getStringCellValue();
					break;
				case NUMERIC:
					int intValue = (int) cell.getNumericCellValue();
					data = String.valueOf(intValue);
					break;
				case BOOLEAN:
					data = String.valueOf(cell.getBooleanCellValue());
					break;

				}
			}
			dataList.add(data);
		}
		return dataList;

	}

	public static void readExcelData(String sheetName) throws IOException {

		FileInputStream inputStream = new FileInputStream(TestDataFile);
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

		// get sheet
		Sheet sheet = workbook.getSheet(sheetName);

		// get row including the header [ +1 ]
		int rowCount = sheet.getLastRowNum() + 1;
		Row headerRow = sheet.getRow(0);
		// get column number
		int colCount = headerRow.getLastCellNum();

		System.out.println("ROW- " + rowCount + " & Column- " + colCount);

		for (int rowNum = 1; rowNum < rowCount; rowNum++) {

			for (int colNum = 0; colNum < colCount; colNum++) {
				Row row = sheet.getRow(rowNum);
				Cell cell = row.getCell(colNum);
				System.out.println(cell.getStringCellValue());
			}
			System.out.println(" ");
		}

		inputStream.close();
		workbook.close();

	}

	public static void readExcelDataByColName(String sheetName, String colName) throws IOException {

		FileInputStream inputStream = new FileInputStream(TestDataFile);
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

		// Get sheet
		Sheet sheet = workbook.getSheet(sheetName);

		// Get row including the header [+1]
		int rowCount = sheet.getLastRowNum() + 1;
		Row headerRow = sheet.getRow(0);
		// get column number
		int colCount = headerRow.getLastCellNum();

		// Find column index by name (assuming unique column names)
		int colIndex = -1;
		for (int col = 0; col < colCount; col++) {
			Cell cell = headerRow.getCell(col);
			if (cell != null && cell.getStringCellValue().equalsIgnoreCase(colName)) {
				colIndex = col;
				break;
			}
		}

		// Check if column found
		if (colIndex == -1) {
			System.out.println("Column '" + colName + "' not found in sheet: " + sheetName);
			return;
		}

		System.out.println("Data for column: " + colName);

		for (int rowNum = 1; rowNum < rowCount; rowNum++) {
			Row row = sheet.getRow(rowNum);
			if (row != null) {
				Cell cell = row.getCell(colIndex);
				if (cell != null) {
					System.out.println(cell.getStringCellValue());
				} else {
					System.out.println("Cell empty in row: " + rowNum);
				}
			} else {
				System.out.println("Empty row: " + rowNum);
			}
		}

		inputStream.close();
		workbook.close();

	}

}
