package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReportGenerator {

	/**
	 * Generates Output report.
	 * 
	 * @param excelData
	 */
	public static void writeReport(List<ArrayList<String>> excelData) {
		FileOutputStream outputStream = null;
		InputStream inputStream = null;
		try {
			Date d = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
			outputStream = new FileOutputStream(
					new File(Propertiesfile.getAbsoultePath("REPORTS") + File.separator+ sdf1.format(d) + ".xlsx"), true);
			inputStream = ReportGenerator.class.getClassLoader().getResourceAsStream(Propertiesfile.getProperty("REPORTTEMPLATE"));
			XSSFWorkbook report = new XSSFWorkbook(inputStream);
			XSSFSheet sheet1 = report.getSheet("Report");
			for (int j = 0; j < excelData.size(); j++) {
				List<String> rowList = excelData.get(j);
				String[] dataToWrite = { rowList.get(0), rowList.get(7), rowList.get(8), rowList.get(9),
						rowList.get(10) };
				int rowCount = sheet1.getLastRowNum() - sheet1.getFirstRowNum();

				XSSFRow newRow = sheet1.createRow(rowCount + 1);
				XSSFCellStyle style2 = report.createCellStyle();
				style2.setBorderTop(BorderStyle.THIN);
				style2.setBorderBottom(BorderStyle.THIN);
				style2.setBorderLeft(BorderStyle.THIN);
				style2.setBorderRight(BorderStyle.THIN);
				XSSFCell cell = newRow.createCell(0);
				cell.setCellValue(dataToWrite[0]);
				cell.setCellStyle(style2);
				cell = newRow.createCell(1);
				cell.setCellValue(dataToWrite[1]);
				cell.setCellStyle(style2);
				cell = newRow.createCell(2);
				cell.setCellValue(dataToWrite[2]);
				cell.setCellStyle(style2);
				cell = newRow.createCell(3);
				cell.setCellValue(dataToWrite[3]);
				cell.setCellStyle(style2);
				cell = newRow.createCell(4);
				cell.setCellValue(dataToWrite[4]);
				cell.setCellStyle(style2);
				sheet1.autoSizeColumn(0);

				XSSFSheet sheet2 = report.getSheet("Summary");
				DateFormat sdf = new SimpleDateFormat();
				Date date = new Date();
				sheet2.getRow(7).getCell(3).setCellValue(sdf.format(date));
				
				FormulaEvaluator evaluator = report.getCreationHelper().createFormulaEvaluator();
				evaluator.evaluateAll();

			}
			inputStream.close();
			report.write(outputStream);
			outputStream.close();
			report.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
