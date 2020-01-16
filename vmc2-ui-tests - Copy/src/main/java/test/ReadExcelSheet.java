package test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelSheet {
	public List<ArrayList<String>> sheet; 	// List to store data from excel.
	public List<String> row; 				// List to store each row.
	public Integer numberOfSheets = 16; 	// Default no. of sheet.
	private Workbook workbook; 				// Workbook object.

	/**
	 * Returns List with loaded excel sheet values.
	 * 
	 * @param sheetName
	 * @return
	 */
	public List<ArrayList<String>> getExcelData(String sheetName) {
		sheet = new ArrayList<ArrayList<String>>();
		try {
			InputStream inputStream = Propertiesfile.getFile("INPUTSHEET");
			workbook = new XSSFWorkbook(inputStream);
			workbook.getSheet(sheetName).forEach(rowValue -> addToList(rowValue));
			numberOfSheets = workbook.getNumberOfSheets();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sheet;
	}

	/**
	 * Adds sheet rows to list.
	 * 
	 * @param nextRow
	 */
	private void addToList(Row nextRow) {
		row = new ArrayList<String>();
		nextRow.forEach(cell -> {
			switch (cell.getCellTypeEnum()) {
			case STRING:
				row.add(cell.getStringCellValue());
				break;
			case NUMERIC:
				row.add(String.valueOf(cell.getNumericCellValue() + ""));
				break;
			case BLANK:
				row.add("");
			default:
				break;
			}
		});
		sheet.add((ArrayList<String>) row);
	}
}
