package com.vam.auto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExport {

	public static void main(String[] args) throws IOException {

		// Exporting the details to an Excel file. Retrieving the sheet
		FileInputStream fis = new FileInputStream(
				"F:\\ECLIPSE WORKSPACE\\AutoProject\\src\\main\\resources\\ShareMarketDetails.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet("ShareMarket1");

		// Entering the column headings into the sheet.
		Row row;
		Cell cell;
		String[] headings = { "COMPANY", "mcOpenPrice", "mcHighPrice", "mcLowPrice", "mcPrev.Close", "gOpenPrice",
				"gHighPrice", "gLowPrice", "gPrev.Close", "dOpenPrice", "dHighPrice", "dLowPrice", "dPrev.Close" };
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < headings.length; j++) {
				row = sheet.getRow(i);
				cell = row.createCell(j);
				cell.setCellType(CellType.STRING);
				cell.setCellValue(headings[j]);
				
				System.out.println(headings[j]);
			}
		}

		
		System.out.println("Headings entered");

		// Exporting the file.
		FileOutputStream fos = new FileOutputStream(
				"F:\\ECLIPSE WORKSPACE\\AutoProject\\src\\main\\resources\\ShareMarketDetails.xlsx");
		workbook.write(fos);

		// closing the IOStreams and workbook.
		fos.close();
		workbook.close();
		fis.close();
		System.out.println("completed");

	}

}
