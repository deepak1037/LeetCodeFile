package com.leetcode;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.AllQuestions;
import com.example.StatStatusPair;
import com.leetcode.bean.AllQuestionMain;
import com.leetcode.bean.Question;
import com.leetcode.util.JsonService;

public class LeetCodeAllQuestion {
	private static String dirPath = "C:\\Users\\deepak\\Google Drive (deepak1037@gmail.com)\\Leetcode\\leetcode-old";
	private static String excelPath = "C:\\Users\\deepak\\Google Drive (deepak1037@gmail.com)\\Leetcode\\leetcode-excel";
	private static Map<Integer, String> difficulty = new HashMap<>();
	static {
		difficulty.put(1, "Easy");
		difficulty.put(2, "Medium");
		difficulty.put(3, "Hard");
	}

	public static void main(String[] args) throws Exception {
		Map<String, Map<Integer, List<Integer>>> all = new HashMap<>();
		LeetCodeAllQuestion lc = new LeetCodeAllQuestion();
		

		
		Map<String, List<LeetCodeRow>> map=new HashMap<>();
		File dir = new File(dirPath);
		for (File file : dir.listFiles()) {
			if(file.isFile())
			lc.processFiles(all,map,file);
		}
		lc.writeToExcel(map);
		//startAll();
		//lc.writeToExcel(allMap);
	}
	
	public static void startAll() throws Exception{
		LeetCodeAllQuestion lc = new LeetCodeAllQuestion();
		Map<String, List<LeetCodeRow>> allMap=new HashMap<>();
		Map<String, Map<Integer, List<Integer>>> all = new HashMap<>();
		lc.loadAllFiles(allMap,all);
		lc.updateToExcel(allMap);
	}
	
	public static void startAll(String fileName, String jsonString) throws Exception{
		LeetCodeAllQuestion lc = new LeetCodeAllQuestion();
		Map<String, List<LeetCodeRow>> allMap=new HashMap<>();
		Map<String, Map<Integer, List<Integer>>> all = new HashMap<>();
		lc.processAllFiles(allMap,all, fileName, jsonString);
		lc.updateToExcel(allMap);
	}
	
	public static void startAll(Map<String, String> companyMap) throws Exception{
		LeetCodeAllQuestion lc = new LeetCodeAllQuestion();
		Map<String, List<LeetCodeRow>> allMap=new HashMap<>();
		Map<String, Map<Integer, List<Integer>>> all = new HashMap<>();
		for(Entry<String, String> entry : companyMap.entrySet()) {			
			lc.processAllFiles(allMap,all, entry.getKey(), entry.getValue());
		}
		
		lc.updateToExcel(allMap);
	}


	private void processFiles(Map<String, Map<Integer, List<Integer>>> all,Map<String, List<LeetCodeRow>> map, File file) throws Exception {
		String jsonString = new String(Files.readAllBytes(file.toPath()));
		AllQuestions dlb = JsonService.getObjectFromJson(jsonString, AllQuestions.class);
		String fileName = FilenameUtils.getBaseName(file.getName());
		List<LeetCodeRow> data = loadData(fileName,all,dlb);		
		map.put(fileName, data);

	}
	Map<Object,Object> paidOnlyMap=new HashMap<>();

	private List<LeetCodeRow> loadData(String fileName, Map<String, Map<Integer, List<Integer>>> all,AllQuestions dlb) {
		List<LeetCodeRow> list = new ArrayList<>();
		LeetCodeRow leetCodeT=new LeetCodeRow("FrontendId","QuestionId","Question", "Difficulty", "Frequency");
		leetCodeT.setAmazonFrequency("Amazon");
		leetCodeT.setAppleFrequency("Apple");
		leetCodeT.setGoogleFrequency("Google");
		leetCodeT.setLinkedinFrequency("LinkedIn");
		leetCodeT.setMicrosoftFrequency("Microsoft");
		leetCodeT.setFacebookFrequency("Facebook");
		leetCodeT.setPaidOnly("PaidOnly");
		list.add(leetCodeT);
		for (StatStatusPair statStatusPair : dlb.getStatStatusPairs()) {
			LeetCodeRow leetCodeRow=new LeetCodeRow(statStatusPair.getStat().getFrontendQuestionId(),statStatusPair.getStat().getQuestionId(),statStatusPair.getStat().getQuestionTitle(),difficulty.get(statStatusPair.getDifficulty().getLevel()),statStatusPair.getFrequency());
			leetCodeRow.setAmazonFrequency(Optional.ofNullable(all.get("Amazon-All")).map(x->x.get(statStatusPair.getStat().getQuestionId())).map(y->y.get(0)).orElse(0));
			leetCodeRow.setAppleFrequency(Optional.ofNullable(all.get("Apple-All")).map(x->x.get(statStatusPair.getStat().getQuestionId())).map(y->y.get(0)).orElse(0));
			leetCodeRow.setFacebookFrequency(Optional.ofNullable(all.get("Facebook-All")).map(x->x.get(statStatusPair.getStat().getQuestionId())).map(y->y.get(0)).orElse(0));
			leetCodeRow.setGoogleFrequency(Optional.ofNullable(all.get("Google-All")).map(x->x.get(statStatusPair.getStat().getQuestionId())).map(y->y.get(0)).orElse(0));
			leetCodeRow.setLinkedinFrequency(Optional.ofNullable(all.get("LinkedIn-All")).map(x->x.get(statStatusPair.getStat().getQuestionId())).map(y->y.get(0)).orElse(0));
			leetCodeRow.setMicrosoftFrequency(Optional.ofNullable(all.get("Microsoft-All")).map(x->x.get(statStatusPair.getStat().getQuestionId())).map(y->y.get(0)).orElse(0));
			leetCodeRow.setPaidOnly(statStatusPair.getPaidOnly());
			if(fileName.equalsIgnoreCase("AllQuestions")) {
				paidOnlyMap.put(leetCodeRow.getQuestionId(), leetCodeRow.getPaidOnly());
			}
			list.add(leetCodeRow);
		}
		return list;
	}

	private void loadAllFiles(Map<String, List<LeetCodeRow>> allMap,Map<String, Map<Integer, List<Integer>>> all) throws Exception {
		String dirPathAll = dirPath+"\\All";
		File dir = new File(dirPathAll);
		for (File file : dir.listFiles()) {
			if (file.isFile())
				processAllFiles(allMap,all, file);
		}
	}
	
	
	
	private void processAllFiles(Map<String, List<LeetCodeRow>> allMap,Map<String, Map<Integer, List<Integer>>> all, File file) throws Exception {
		String jsonString = new String(Files.readAllBytes(file.toPath()));
		String fileName = FilenameUtils.getBaseName(file.getName());
		processAllFiles(allMap,all, fileName, jsonString);
	}

	private void processAllFiles(Map<String, List<LeetCodeRow>> allMap,Map<String, Map<Integer, List<Integer>>> all, String fileName, String jsonString) throws Exception {
		List<LeetCodeRow> list = new ArrayList<>();
		list.add(new LeetCodeRow("FrontendId","QuestionId","Question", "Difficulty", "Frequency 6 month","Frequency 1 Year","Frequency 2 Year","Frequency All","PaidOnly"));
		AllQuestionMain dlb = JsonService.getObjectFromJson(jsonString, AllQuestionMain.class);
		all.put(fileName, dlb.getData().getCompanyTag().getFrequencies());
		processAllData(dlb,list);
		allMap.put(fileName, list);
	}
	
	

	private void processAllData(AllQuestionMain dlb,List<LeetCodeRow> list) {
		List<Question> questions =dlb.getData().getCompanyTag().getQuestions();
		Map<Integer,List<Integer>> frequencies= dlb.getData().getCompanyTag().getFrequencies();
		for(Question ques:questions) {
			List<Integer> freq=frequencies.get(ques.getQuestionId());
			if(freq==null) {
				System.out.println("");
			}
			LeetCodeRow leetCodeRow=new LeetCodeRow(ques.getQuestionFrontendId(),ques.getQuestionId(),ques.getTitle(),ques.getDifficulty(),freq.get(0),freq.get(1),freq.get(2),freq.get(3),null);
			list.add(leetCodeRow);
		}
	
	}
	
	private void updateToExcel(Map<String, List<LeetCodeRow>> data) throws Exception {
		  //FileInputStream inputStream = new FileInputStream(new File(excelPath + "\\LeetCode" + ".xlsx"));
		// Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		for (Map.Entry<String, List<LeetCodeRow>> entry : data.entrySet()) {
			addNewSheet(workbook, entry.getValue(), entry.getKey());
		}

		try {
			// Write the workbook in file system
			FileOutputStream out = new FileOutputStream(new File(excelPath + "\\LeetCode" + ".xlsx"));
			workbook.write(out);
			out.close();
			System.out.println("LeetCode.xlsx written successfully on disk.");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void addNewSheet(XSSFWorkbook workbook, List<LeetCodeRow> data, String sheetName) {
		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet(sheetName);
		XSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);

		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		Row row = null;
		Cell cell0 = null;
		Cell cell1 = null;
		Cell cell2 = null;
		Cell cell3 = null;
		Cell cell4 = null;
		Cell cell5 = null;
		Cell cell6 = null;
		Cell cell7 = null;
		Cell cell8 = null;
		Cell cell9 = null;
		Cell cell10 = null;
		int rownum = 0;
		LeetCodeRow rowData1=data.remove(0);
		row = sheet.createRow(rownum++);
		
		cell0 = row.createCell(0);
		cell0.setCellType(Cell.CELL_TYPE_STRING);
		cell0.setCellValue((String)rowData1.getFrontendId());
		
		cell1 = row.createCell(1);
		cell1.setCellType(Cell.CELL_TYPE_STRING);
		cell1.setCellValue((String)rowData1.getQuestionId());	
		
		cell2 = row.createCell(2);
		cell2.setCellValue(rowData1.getQuestion());
		
		cell3 = row.createCell(3);
		cell3.setCellValue((String)rowData1.getDifficulty());
		
		cell4 = row.createCell(4);
		cell4.setCellValue((String)rowData1.getFrequency6m());
		
		cell5 = row.createCell(5);
		cell5.setCellValue((String)rowData1.getFrequency1y());
		
		cell6 = row.createCell(6);
		cell6.setCellValue((String)rowData1.getFrequency2y());
		
		cell7 = row.createCell(7);
		cell7.setCellValue((String)rowData1.getFrequencyAll());
		
		cell8 = row.createCell(8);
		cell8.setCellValue((String)rowData1.getPaidOnly());

		for (LeetCodeRow rowData : data) {
			row = sheet.createRow(rownum++);
			
			cell0 = row.createCell(0);
			cell0.setCellType(Cell.CELL_TYPE_STRING);
			cell0.setCellValue((Integer)rowData.getFrontendId());
			
			cell1 = row.createCell(1);
			cell1.setCellType(Cell.CELL_TYPE_STRING);
			cell1.setCellValue((Integer)rowData.getQuestionId());	
			
			cell2 = row.createCell(2);
			cell2.setCellValue(rowData.getQuestion());
			
			cell3 = row.createCell(3);
			cell3.setCellValue(rowData.getDifficulty());
			
			cell4 = row.createCell(4);
			cell4.setCellValue((Integer)rowData.getFrequency6m());
			
			cell5 = row.createCell(5);
			cell5.setCellValue((Integer)rowData.getFrequency1y());
			
			cell6 = row.createCell(6);
			cell6.setCellValue((Integer)rowData.getFrequency2y());
			
			cell7 = row.createCell(7);
			cell7.setCellValue((Integer)rowData.getFrequencyAll());
			
			if(paidOnlyMap.get(rowData.getQuestionId())!=null) {
				cell8 = row.createCell(8);
				cell8.setCellValue((Boolean)paidOnlyMap.get(rowData.getQuestionId()));
			}			
		}
		
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(5);
		sheet.autoSizeColumn(6);

	}

	private void writeToExcel(Map<String, List<LeetCodeRow>> data) throws Exception {

		// Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		for (Map.Entry<String, List<LeetCodeRow>> entry : data.entrySet()) {
			addSheet(workbook, entry.getValue(), entry.getKey());
		}

		try {
			// Write the workbook in file system
			FileOutputStream out = new FileOutputStream(new File(excelPath + "\\LeetCode" + ".xlsx"));
			workbook.write(out);
			out.close();
			System.out.println("LeetCode.xlsx written successfully on disk.");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void addSheet(XSSFWorkbook workbook, List<LeetCodeRow> data, String sheetName) throws Exception {
		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet(sheetName);
		XSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);

		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		Row row = null;
		Cell cell1 = null;
		Cell cell2 = null;
		Cell cell3 = null;
		Cell cell4 = null;
		Cell cell5 = null;
		Cell cell6 = null;
		Cell cell7 = null;
		Cell cell8 = null;
		Cell cell9 = null;
		Cell cell10 = null;
		Cell cell11 = null;
		int rownum = 0;
		LeetCodeRow rowData1=data.remove(0);
		row = sheet.createRow(rownum++);
		cell1 = row.createCell(0);
		cell1.setCellValue((String)rowData1.getQuestionId());			
		cell2 = row.createCell(1);
		cell2.setCellValue(rowData1.getQuestion());
		cell3 = row.createCell(2);
		cell3.setCellValue((String)rowData1.getDifficulty());
		cell4 = row.createCell(3);
		cell4.setCellValue((String)rowData1.getFrequency());
		
		cell5 = row.createCell(4);
		cell5.setCellValue((String)rowData1.getAmazonFrequency());
		cell6 = row.createCell(5);
		cell6.setCellValue((String)rowData1.getGoogleFrequency());
		cell7 = row.createCell(6);
		cell7.setCellValue((String)rowData1.getMicrosoftFrequency());
		cell8 = row.createCell(7);
		cell8.setCellValue((String)rowData1.getAppleFrequency());
		
		cell9 = row.createCell(8);
		cell9.setCellValue((String)rowData1.getFacebookFrequency());
		cell10 = row.createCell(9);
		cell10.setCellValue((String)rowData1.getLinkedinFrequency());
		cell11 = row.createCell(10);
		cell11.setCellValue((String)rowData1.getPaidOnly());
		for (LeetCodeRow rowData : data) {
			row = sheet.createRow(rownum++);
			cell1 = row.createCell(0);
			cell1.setCellValue((Integer)rowData.getQuestionId());			
			cell2 = row.createCell(1);
			cell2.setCellValue(rowData.getQuestion());
			cell3 = row.createCell(2);
			cell3.setCellValue(rowData.getDifficulty());
			cell4 = row.createCell(3);
			cell4.setCellValue((Double)rowData.getFrequency());
			
			cell5 = row.createCell(4);
			cell5.setCellValue((Integer)rowData.getAmazonFrequency());
			cell6 = row.createCell(5);
			cell6.setCellValue((Integer)rowData.getGoogleFrequency());
			cell7 = row.createCell(6);
			cell7.setCellValue((Integer)rowData.getMicrosoftFrequency());
			cell8 = row.createCell(7);
			cell8.setCellValue((Integer)rowData.getAppleFrequency());
			
			cell9 = row.createCell(8);
			cell9.setCellValue((Integer)rowData.getFacebookFrequency());
			cell10 = row.createCell(9);
			cell10.setCellValue((Integer)rowData.getLinkedinFrequency());

			cell11 = row.createCell(10);
			cell11.setCellValue((Boolean)rowData.getPaidOnly());
		}
		
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(5);
		sheet.autoSizeColumn(6);
		sheet.autoSizeColumn(7);
		sheet.autoSizeColumn(8);
		sheet.autoSizeColumn(9);
	}

	class LeetCodeRow {
		private Object frontendId;
		private Object questionId;
		private String question;
		private String difficulty;
		private Object frequency;
		private Object amazonFrequency;
		private Object appleFrequency;
		private Object facebookFrequency;
		private Object googleFrequency;
		private Object linkedinFrequency;
		private Object microsoftFrequency;
		private Object frequency6m;
		private Object frequency1y;
		private Object frequency2y;
		private Object frequencyAll;
		private Object paidOnly;

		public LeetCodeRow(Object frontendId, Object questionId, String question, String difficulty, Object frequency) {
			this.frontendId = frontendId;
			this.questionId = questionId;
			this.question = question;
			this.difficulty = difficulty;
			this.frequency = frequency;
		}

		public LeetCodeRow(Object frontendId,Object questionId, String question, String difficulty, Object frequency,
				Object amazonFrequency, Object googleFrequency,Object microsoftFrequency,Object appleFrequency, Object facebookFrequency, 
				Object linkedinFrequency) {
			super();
			this.frontendId = frontendId;
			this.questionId = questionId;
			this.question = question;
			this.difficulty = difficulty;
			this.frequency = frequency;
			this.amazonFrequency = amazonFrequency;
			this.appleFrequency = appleFrequency;
			this.facebookFrequency = facebookFrequency;
			this.googleFrequency = googleFrequency;
			this.linkedinFrequency = linkedinFrequency;
			this.microsoftFrequency = microsoftFrequency;
		}

		public LeetCodeRow(Object frontendId,Object questionId, String question, String difficulty,Object frequency6m, Object frequency1y,
				Object frequency2y, Object frequencyAll,Object paidOnly) {
			super();
			this.frontendId = frontendId;
			this.questionId = questionId;
			this.question = question;
			this.difficulty = difficulty;
			this.frequency6m = frequency6m;
			this.frequency1y = frequency1y;
			this.frequency2y = frequency2y;
			this.frequencyAll = frequencyAll;
			this.paidOnly=paidOnly;
		}

		
		public Object getFrontendId() {
			return frontendId;
		}

		public void setFrontendId(Object frontendId) {
			this.frontendId = frontendId;
		}

		public Object getQuestionId() {
			return questionId;
		}

		public void setQuestionId(Object questionId) {
			this.questionId = questionId;
		}

		public String getQuestion() {
			return question;
		}

		public void setQuestion(String question) {
			this.question = question;
		}

		public String getDifficulty() {
			return difficulty;
		}

		public void setDifficulty(String difficulty) {
			this.difficulty = difficulty;
		}

		public Object getFrequency() {
			return frequency;
		}

		public void setFrequency(Object frequency) {
			this.frequency = frequency;
		}

		public Object getAmazonFrequency() {
			return amazonFrequency;
		}

		public void setAmazonFrequency(Object amazonFrequency) {
			this.amazonFrequency = amazonFrequency;
		}

		public Object getAppleFrequency() {
			return appleFrequency;
		}

		public void setAppleFrequency(Object appleFrequency) {
			this.appleFrequency = appleFrequency;
		}

		public Object getFacebookFrequency() {
			return facebookFrequency;
		}

		public void setFacebookFrequency(Object facebookFrequency) {
			this.facebookFrequency = facebookFrequency;
		}

		public Object getGoogleFrequency() {
			return googleFrequency;
		}

		public void setGoogleFrequency(Object googleFrequency) {
			this.googleFrequency = googleFrequency;
		}

		public Object getLinkedinFrequency() {
			return linkedinFrequency;
		}

		public void setLinkedinFrequency(Object linkedinFrequency) {
			this.linkedinFrequency = linkedinFrequency;
		}

		public Object getMicrosoftFrequency() {
			return microsoftFrequency;
		}

		public void setMicrosoftFrequency(Object microsoftFrequency) {
			this.microsoftFrequency = microsoftFrequency;
		}

		public Object getFrequency6m() {
			return frequency6m;
		}

		public void setFrequency6m(Object frequency6m) {
			this.frequency6m = frequency6m;
		}

		public Object getFrequency1y() {
			return frequency1y;
		}

		public void setFrequency1y(Object frequency1y) {
			this.frequency1y = frequency1y;
		}

		public Object getFrequency2y() {
			return frequency2y;
		}

		public void setFrequency2y(Object frequency2y) {
			this.frequency2y = frequency2y;
		}

		public Object getFrequencyAll() {
			return frequencyAll;
		}

		public void setFrequencyAll(Object frequencyAll) {
			this.frequencyAll = frequencyAll;
		}

		public Object getPaidOnly() {
			return paidOnly;
		}

		public void setPaidOnly(Object paidOnly) {
			this.paidOnly = paidOnly;
		}



	}
}
