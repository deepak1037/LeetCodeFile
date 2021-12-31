package com.leetcode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.AllQuestions;
import com.example.StatStatusPair;
import com.leetcode.LeetCodeAllQuestion.LeetCodeRow;
import com.leetcode.bean.AllQuestionMain;
import com.leetcode.bean.Question;
import com.leetcode.bean.Submissions;
import com.leetcode.util.JsonService;

public class EnhancedLeetcodeExcelMain {

	private static String excelPath = "C:\\Users\\deepak\\Google Drive (deepak1037@gmail.com)\\Leetcode\\leetcode-excel";
	private static Map<Integer, String> difficulty = new HashMap<>();
	static {
		difficulty.put(1, "Easy");
		difficulty.put(2, "Medium");
		difficulty.put(3, "Hard");
	}

	Map<Object, Object> paidOnlyMap = new HashMap<>();

	public static void main(String[] args)  throws Exception {
		EnhancedLeetcodeExcelMain elem = new EnhancedLeetcodeExcelMain();
		Map<String, List<LeetCodeRow>> map = new HashMap<>();
		//AllQuestions allquestions = LeetcodeHttpClient.getFeaturedQuestionList("top-facebook-questions");
		//AllQuestions allquestions = LeetcodeHttpClient.getAllQuestions();
		//List<LeetCodeRow> data = elem.loadData("FacebookQuestions", allquestions);
		String json = LeetcodeHttpClient.getCompanyQuestion("Facebook");
		List<LeetCodeRow> data = elem.processCompanyQuestions("Facebook", json);
		map.put("FacebookQuestions", data);
		elem.writeToExcel(map);
	}
	
	private List<LeetCodeRow> processCompanyQuestions(String fileName, String jsonString) throws Exception {
		List<LeetCodeRow> list = new ArrayList<>();
		list.add(new LeetCodeRow("FrontendId","QuestionId","Question", "Difficulty", "Frequency 6 month","Frequency 1 Year","Frequency 2 Year","Frequency All","PaidOnly"));
		list.get(0).setLastAttempted("LastAttempted");
		AllQuestionMain dlb = JsonService.getObjectFromJson(jsonString, AllQuestionMain.class);
		
		processAllData(dlb,list);
		return list;
	}
	
	private void processAllData(AllQuestionMain dlb,List<LeetCodeRow> list) throws Exception {
		List<Question> questions =dlb.getData().getCompanyTag().getQuestions();
		Map<Integer,List<Integer>> frequencies= dlb.getData().getCompanyTag().getFrequencies();
		for(Question ques:questions) {
			List<Integer> freq=frequencies.get(ques.getQuestionId());
			if(freq==null) {
				//System.out.println("");
			}
			LeetCodeRow leetCodeRow=new LeetCodeRow(ques.getQuestionFrontendId(),ques.getQuestionId(),ques.getTitle(),ques.getDifficulty(),freq.get(0),freq.get(1),freq.get(2),freq.get(3),null);
			leetCodeRow.setLastAttempted(getLastAttempted(ques.getTitleSlug()));
			leetCodeRow.setPaidOnly(ques.getIsPaidOnly());
			list.add(leetCodeRow);
		}
	
	}

	public void processAllQuestions(String fileName, String jsonString) throws Exception {
		Map<String, List<LeetCodeRow>> map = new HashMap<>();
		processAllQuestions(map, fileName, jsonString);
		writeToExcel(map);
	}

	private void processAllQuestions(Map<String, List<LeetCodeRow>> map, String fileName, String jsonString)
			throws Exception {
		AllQuestions dlb = JsonService.getObjectFromJson(jsonString, AllQuestions.class);
		List<LeetCodeRow> data = loadData(fileName, dlb);
		map.put(fileName, data);

	}

	private List<LeetCodeRow> loadData(String fileName, AllQuestions dlb) throws Exception {
		List<LeetCodeRow> list = new ArrayList<>();
		LeetCodeRow leetCodeT = new LeetCodeRow("FrontendId", "QuestionId", "Question", "Difficulty", "Frequency");
		leetCodeT.setPaidOnly("PaidOnly");
		leetCodeT.setLastAttempted("LastAttempted");
		list.add(leetCodeT);
		for (StatStatusPair statStatusPair : dlb.getStatStatusPairs()) {
			LeetCodeRow leetCodeRow = new LeetCodeRow(statStatusPair.getStat().getFrontendQuestionId(),
					statStatusPair.getStat().getQuestionId(), statStatusPair.getStat().getQuestionTitle(),
					difficulty.get(statStatusPair.getDifficulty().getLevel()), statStatusPair.getFrequency());
			leetCodeRow.setPaidOnly(statStatusPair.getPaidOnly());
			leetCodeRow.setLastAttempted(getLastAttempted(statStatusPair.getStat().getQuestionTitleSlug()));
			if (fileName.equalsIgnoreCase("AllQuestions")) {
				paidOnlyMap.put(leetCodeRow.getQuestionId(), leetCodeRow.getPaidOnly());
			}
			list.add(leetCodeRow);
		}
		return list;
	}

	private String getLastAttempted(String questionTitleSlug) throws Exception {
		Thread.sleep(50);
		
		AllQuestionMain aqm = LeetcodeHttpClient.loadSubmissions(questionTitleSlug);
		List<Submissions> submissions =aqm.getData().getSubmissionList().getSubmissions();
		for(Submissions sub : submissions) {
			if("Accepted".equalsIgnoreCase(sub.getStatusDisplay())){
				return sub.getTimestamp();
			}
		}
		return "";
	}

	private void writeToExcel(Map<String, List<LeetCodeRow>> data) throws Exception {

		// Blank workbook
		//FileInputStream myxls = new FileInputStream(new File(excelPath + "\\LeetCode" + ".xlsx"));
		//XSSFWorkbook workbook = new XSSFWorkbook(myxls);
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
		LeetCodeRow rowData1 = data.remove(0);
		row = sheet.createRow(rownum++);
		cell0 = row.createCell(0);
		cell0.setCellValue((String) "FrontendId");
		cell1 = row.createCell(1);
		cell1.setCellValue((String) rowData1.getQuestionId());
		cell2 = row.createCell(2);
		cell2.setCellValue(rowData1.getQuestion());
		cell3 = row.createCell(3);
		cell3.setCellValue((String) rowData1.getDifficulty());
		cell4 = row.createCell(4);
		cell4.setCellValue((String) "PaidOnly");
		cell5 = row.createCell(5);
		cell5.setCellValue((String) rowData1.getLastAttempted());
		
		if(rowData1.getFrequency6m()!=null) {
			cell6 = row.createCell(6);
			cell6.setCellValue((String)rowData1.getFrequency6m());
			
			cell7 = row.createCell(7);
			cell7.setCellValue((String)rowData1.getFrequency1y());
			
			cell8 = row.createCell(8);
			cell8.setCellValue((String)rowData1.getFrequency2y());
			
			cell9 = row.createCell(9);
			cell9.setCellValue((String)rowData1.getFrequencyAll());
		}

		
		for (LeetCodeRow rowData : data) {
			row = sheet.createRow(rownum++);
			cell0 = row.createCell(0);
			cell0.setCellValue( (Integer) rowData.getFrontendId());
			cell1 = row.createCell(1);
			cell1.setCellValue((Integer) rowData.getQuestionId());
			cell2 = row.createCell(2);
			cell2.setCellValue(rowData.getQuestion());
			cell3 = row.createCell(3);
			cell3.setCellValue(rowData.getDifficulty());
			if(rowData.getPaidOnly()!=null) {
				cell4 = row.createCell(4);
				cell4.setCellValue((Boolean) rowData.getPaidOnly());
			}
			cell5 = row.createCell(5);
			cell5.setCellValue((String) rowData.getLastAttempted());
			
			if(rowData.getFrequency6m()!=null) {
				cell6 = row.createCell(6);
				cell6.setCellValue((Integer)rowData.getFrequency6m());
				
				cell7 = row.createCell(7);
				cell7.setCellValue((Integer)rowData.getFrequency1y());
				
				cell8 = row.createCell(8);
				cell8.setCellValue((Integer)rowData.getFrequency2y());
				
				cell9 = row.createCell(9);
				cell9.setCellValue((Integer)rowData.getFrequencyAll());
			}
		}

		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(5);
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
		private String lastAttempted;

		public LeetCodeRow(Object frontendId, Object questionId, String question, String difficulty, Object frequency) {
			this.frontendId = frontendId;
			this.questionId = questionId;
			this.question = question;
			this.difficulty = difficulty;
			this.frequency = frequency;
		}

		public LeetCodeRow(Object frontendId, Object questionId, String question, String difficulty, Object frequency,
				Object amazonFrequency, Object googleFrequency, Object microsoftFrequency, Object appleFrequency,
				Object facebookFrequency, Object linkedinFrequency) {
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

		public LeetCodeRow(Object frontendId, Object questionId, String question, String difficulty, Object frequency6m,
				Object frequency1y, Object frequency2y, Object frequencyAll, Object paidOnly) {
			super();
			this.frontendId = frontendId;
			this.questionId = questionId;
			this.question = question;
			this.difficulty = difficulty;
			this.frequency6m = frequency6m;
			this.frequency1y = frequency1y;
			this.frequency2y = frequency2y;
			this.frequencyAll = frequencyAll;
			this.paidOnly = paidOnly;
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

		public String getLastAttempted() {
			return lastAttempted;
		}

		public void setLastAttempted(String lastAttempted) {
			this.lastAttempted = lastAttempted;
		}

	}

}
