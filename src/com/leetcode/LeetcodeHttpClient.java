package com.leetcode;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.AllQuestions;
import com.leetcode.bean.AllQuestionMain;
import com.leetcode.bean.Question;
import com.leetcode.util.JsonService;

public class LeetcodeHttpClient {

	private static String excelPath = "C:\\Users\\deepak\\Google Drive (deepak1037@gmail.com)\\Leetcode\\leetcode-excel";
	static List<String> list = Arrays.asList("-75","-TopInterview","-MustDo", "-Priority", "6M20", "6M15", "6M10", "6M05", "6M01", "6MRem");
	static Map<String, String> companyMap = new HashMap<>();
	static {
		companyMap.put("Facebook", "FB");
		companyMap.put("Google", "GG");
		companyMap.put("Apple", "APL");
		companyMap.put("Microsoft", "MS");
		companyMap.put("Amazon", "AZ");
	}

	private static String csrftoken = null;

	public static void main(String[] args) throws Exception {
		start("Facebook");
	}

	public static void start(String company) throws Exception, InterruptedException {
		start(company, new HashSet<>());
	}

	public static void start(String company, Set<Integer> temp) throws Exception, InterruptedException {
		start(company, temp, new HashSet<>());
	}

	public static void start(String company, Set<Integer> excludedList, Set<Integer> priority)
			throws Exception, InterruptedException {
		Map<Integer, Integer> map = new HashMap<>();
		readExcel(company, map);
		/*
		 * Iterator<Integer> i = excludedList.iterator(); while(i.hasNext()) { Integer
		 * id = i.next(); if(map.containsKey(id)) { i.remove(); } }
		 */
		// Load Favorite List
		AllQuestionMain dlb = loadFavoriteList();
		Map<String, String> fvList = createFavoriteList(dlb);

		// Delete Company Favorite List
		deleteList(companyMap.get(company), fvList);

		// Re-create Company Favorite List
		createList(companyMap.get(company));

		// Reload Favorite List
		dlb = loadFavoriteList();
		fvList = createFavoriteList(dlb);
		int exSize = excludedList.size();
		
		Set<Integer> topInterview = loadTopInterviewQuestion(excludedList);
		Set<Integer> mustDo = loadMustDo(excludedList);
		Set<Integer> fb75 = load75(excludedList);
		// Key is list id and value is list of id of question to be added to list.
		Map<String, Set<Integer>> res = createList(companyMap.get(company), fvList, map, excludedList);

		//res.put(fvList.get("FB-75"), fb75);
		//res.put(fvList.get("FB-TopInterview"), topInterview);
		//res.put(fvList.get("FB-MustDo"), mustDo);
		//res.put(fvList.get("FB-Priority"), priority);
		int count = 0;
		for (Entry<String, Set<Integer>> entry : res.entrySet()) {
			String hashId = entry.getKey();
			for (Integer questionId : entry.getValue()) {
				addQuestionToList(hashId, questionId.toString());
				count++;
				System.out.println(count);
				Thread.sleep(100);
			}
		}
		System.out.println(exSize);
		System.out.println(count);
		System.out.println(map.size());
	}
	
	private static Set<Integer> loadTopInterviewQuestion(Set<Integer> excludedList) throws Exception {
		Set<Integer> topInterview = new HashSet<>();

		AllQuestions ex = LeetcodeHttpClient.getFeaturedQuestionList("top-interview-questions");
		ex.getStatStatusPairs().forEach(s -> {
			if (!excludedList.contains(s.getStat().getQuestionId())) {
				topInterview.add(s.getStat().getQuestionId());
			}
		});

		return topInterview;
	}
	
	private static Set<Integer> load75(Set<Integer> excludedList) throws Exception {
		Set<Integer> fb75 = new HashSet<>();


		AllQuestionMain aqm = LeetcodeHttpClient.loadFavoriteList();
		Map<String, List<Question>> map = new HashMap<>();
		aqm.getData().getFavoritesLists().getAllFavorites().stream()
				.forEach(fv -> map.put(fv.getName(), fv.getQuestions()));
		map.getOrDefault("Blind Curated 75", new ArrayList<>()).forEach(qtn -> {
			if (!excludedList.contains(qtn.getQuestionId())) {
				fb75.add(qtn.getQuestionId());
			}
		});
		
		return fb75;
	}

	private static Set<Integer> loadMustDo(Set<Integer> excludedList) throws Exception {
		Set<Integer> mustDo = new HashSet<>();


		AllQuestionMain aqm = LeetcodeHttpClient.loadFavoriteList();
		Map<String, List<Question>> map = new HashMap<>();
		aqm.getData().getFavoritesLists().getAllFavorites().stream()
				.forEach(fv -> map.put(fv.getName(), fv.getQuestions()));
		map.getOrDefault("Must Do Easy Questions", new ArrayList<>()).forEach(qtn -> {
			if (!excludedList.contains(qtn.getQuestionId())) {
				mustDo.add(qtn.getQuestionId());
			}
		});
		map.getOrDefault("Must Do Medium Questions", new ArrayList<>()).forEach(qtn -> {
			if (!excludedList.contains(qtn.getQuestionId())) {
				mustDo.add(qtn.getQuestionId());
			}
		});
		
		return mustDo;
	}

	private static Map<String, Set<Integer>> createList(String string, Map<String, String> fvList,
			Map<Integer, Integer> map, Set<Integer> temp) {
		// List<Integer> temp = new ArrayList<>();

		Set<Integer> greaterThan19 = map.entrySet().stream().filter(entry -> selectedFor20List(temp, entry))
				.map(entry -> entry.getKey()).collect(Collectors.toSet());
		temp.addAll(greaterThan19);

		Set<Integer> greaterThan14 = map.entrySet().stream().filter(entry -> selectedFor15List(temp, entry))
				.map(entry -> entry.getKey()).collect(Collectors.toSet());
		temp.addAll(greaterThan14);

		Set<Integer> greaterThan9 = map.entrySet().stream().filter(entry -> selectedFor10List(temp, entry))
				.map(entry -> entry.getKey()).collect(Collectors.toSet());
		temp.addAll(greaterThan9);

		Set<Integer> lessThan10 = map.entrySet().stream().filter(entry -> selectedFor5List(temp, entry))
				.map(entry -> entry.getKey()).collect(Collectors.toSet());
		temp.addAll(lessThan10);

		Set<Integer> lessThan5 = map.entrySet().stream().filter(entry -> selectedFor0List(temp, entry))
				.map(entry -> entry.getKey()).collect(Collectors.toSet());
		temp.addAll(lessThan5);

		Set<Integer> all = map.entrySet().stream().filter(entry -> !temp.contains(entry.getKey()))
				.map(entry -> entry.getKey()).collect(Collectors.toSet());
		Map<String, Set<Integer>> res = new HashMap<>();
		res.put(fvList.get(string + "6M20"), greaterThan19);// 20
		res.put(fvList.get(string + "6M15"), greaterThan14);// 15
		res.put(fvList.get(string + "6M10"), greaterThan9);// 10
		res.put(fvList.get(string + "6M05"), lessThan10);// 5
		res.put(fvList.get(string + "6M01"), lessThan5);// 0
		res.put(fvList.get(string + "6MRem"), all);// All
		System.out.println(map.size());
		// System.out.println(greaterThan19.size()+greaterThan9.size()+lessThan10.size()+others.size());
		return res;
	}

	private static boolean selectedFor0List(Set<Integer> temp, Entry<Integer, Integer> entry) {
		return !temp.contains(entry.getKey()) && entry.getValue() > 1 && entry.getValue() < 5;
	}

	private static boolean selectedFor5List(Set<Integer> temp, Entry<Integer, Integer> entry) {
		return !temp.contains(entry.getKey()) && entry.getValue() < 10
				&& (entry.getValue() > 4 || (entry.getKey() > 999 && entry.getValue() > 0));
	}

	private static boolean selectedFor10List(Set<Integer> temp, Entry<Integer, Integer> entry) {
		return !temp.contains(entry.getKey()) && entry.getValue() < 15
				&& (entry.getValue() > 9 || (entry.getKey() > 999 && entry.getValue() > 4)
						|| (entry.getKey() > 1099 && entry.getValue() > 2) || (entry.getKey() > 1299 &&  entry.getValue() > 0));
	}

	private static boolean selectedFor15List(Set<Integer> temp, Entry<Integer, Integer> entry) {
		return !temp.contains(entry.getKey()) && entry.getValue() < 20
				&& (entry.getValue() > 14 || (entry.getKey() > 1099 && entry.getValue() > 5) || (entry.getKey() > 1299 &&  entry.getValue() > 2));
	}

	private static boolean selectedFor20List(Set<Integer> temp, Entry<Integer, Integer> entry) {
		return !temp.contains(entry.getKey())
				&& (entry.getValue() > 19 || (entry.getKey() > 1099 && entry.getValue() > 9) || (entry.getKey() > 1299 &&  entry.getValue() > 4));
	}

	private static void readExcel(String sheetName, Map<Integer, Integer> map) throws Exception {

		try {
			FileInputStream file = new FileInputStream(new File(excelPath + "\\LeetCode" + ".xlsx"));

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheet(sheetName);

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				int rowNum = row.getRowNum();
				if (rowNum == 0) {
					continue;
				}
				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();

				Cell cell1 = row.getCell(1);
				Integer questionId = (int) cell1.getNumericCellValue();
				Cell cell2 = row.getCell(4);
				Integer frequency = (int) cell2.getNumericCellValue();
				map.put(questionId, frequency);
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void deleteList(String string, Map<String, String> fvList) throws Exception {
		for (String str : list) {
			String idhash = fvList.get(string + str);
			String url = "https://leetcode.com/list/api/" + idhash;
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			loadHeader(con);
			con.setRequestMethod("DELETE");
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
		}
	}

	private static void createList(String string) throws Exception {
		for (String str : list) {

			String url = "https://leetcode.com/list/api/";
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			loadHeader(con);
			String postJsonData = "{\"name\":\"" + string + str + "\"}";
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(postJsonData);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
		}
	}

	private static Map<String, String> createFavoriteList(AllQuestionMain dlb) {
		// AllQuestionMain dlb = JsonService.getObjectFromJson(response.toString(),
		// AllQuestionMain.class);
		Map<String, String> map = new HashMap<>();
		dlb.getData().getFavoritesLists().getAllFavorites().stream()
				.forEach(fv -> map.put(fv.getName(), fv.getIdHash()));
		return map;
	}

	public static AllQuestionMain loadSubmissions(String slug) throws Exception {
		String url = "https://leetcode.com/graphql";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		loadHeader(con);
		String postJsonData = "{\"operationName\":\"Submissions\",\"variables\":{\"limit\":20,\"lastKey\":null,\"questionSlug\":\""+slug+"\"},\"query\":\"query Submissions( $questionSlug: String!) {  submissionList(offset: 0, limit: 20, questionSlug: $questionSlug) {    lastKey    hasNext    submissions {      id      statusDisplay      lang      runtime      timestamp     url      isPending      memory      __typename    }    __typename  }}\"}";

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(postJsonData);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("nSending 'POST' request to URL : " + url);
		System.out.println("Post Data : " + postJsonData);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String output;
		StringBuffer response = new StringBuffer();
		csrftoken = con.getHeaderField("csrftoken");
		while ((output = in.readLine()) != null) {
			response.append(output);
		}
		in.close();
		AllQuestionMain dlb = JsonService.getObjectFromJson(response.toString(), AllQuestionMain.class);
		return dlb;
	
		
	}
	
	public static AllQuestionMain loadFavoriteList() throws Exception {
		String url = "https://leetcode.com/graphql";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		loadHeader(con);
		String postJsonData = "{\"operationName\":\"favoritesList\",\"variables\":{},\"query\":\"query favoritesList { favoritesLists { allFavorites {idHash name questions {questionId  questionFrontendId      status        title        titleSlug }}}}\"}";

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(postJsonData);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("nSending 'POST' request to URL : " + url);
		System.out.println("Post Data : " + postJsonData);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String output;
		StringBuffer response = new StringBuffer();
		csrftoken = con.getHeaderField("csrftoken");
		while ((output = in.readLine()) != null) {
			response.append(output);
		}
		in.close();
		AllQuestionMain dlb = JsonService.getObjectFromJson(response.toString(), AllQuestionMain.class);
		return dlb;
	}

	private static void addQuestionToList(String hashId, String questionId) throws Exception {

		String url = "https://leetcode.com/graphql";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		loadHeader(con);

		String postJsonData = "{\"operationName\":\"addQuestionToFavorite\",\"variables\":{\"favoriteIdHash\":\""
				+ hashId + "\",\"questionId\":\"" + questionId
				+ "\"},\"query\":\"mutation addQuestionToFavorite($favoriteIdHash: String!, $questionId: String!) {\\n  addQuestionToFavorite(favoriteIdHash: $favoriteIdHash, questionId: $questionId) {\\n    ok\\n    error\\n    favoriteIdHash\\n    questionId\\n    __typename\\n  }\\n}\\n\"}";

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(postJsonData);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("nSending 'POST' request to URL : " + url);
		System.out.println("Post Data : " + postJsonData);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String output;
		StringBuffer response = new StringBuffer();

		while ((output = in.readLine()) != null) {
			response.append(output);
		}
		in.close();

		// printing result from response
		// System.out.println(response.toString());
	}
	public static AllQuestions getAllQuestions() throws Exception {
		return getAllQuestions("https://leetcode.com/api/problems/all/");
	}
	
	public static AllQuestions getFeaturedQuestionList(String listName) throws Exception {
		String url = "https://leetcode.com/api/problems/favorite_lists/" + listName + "/";
		return getAllQuestions(url);
	}
	
	public static AllQuestions getAllQuestions(String url) throws Exception {
		//String url = "https://leetcode.com/api/problems/favorite_lists/" + listName + "/";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		loadHeader(con, "GET");
		// String postJsonData
		// ="{\"operationName\":\"getCompanyTag\",\"variables\":{\"slug\":\""+company.toLowerCase()+"\"},\"query\":\"query
		// getCompanyTag($slug: String!) { companyTag(slug: $slug) { name translatedName
		// frequencies questions { ...questionFields __typename } __typename }
		// favoritesLists { publicFavorites { ...favoriteFields __typename }
		// privateFavorites { ...favoriteFields __typename } __typename } } fragment
		// favoriteFields on FavoriteNode { idHash id name isPublicFavorite viewCount
		// creator isWatched questions { questionId title titleSlug __typename }
		// __typename } fragment questionFields on QuestionNode { status questionId
		// questionFrontendId title titleSlug translatedTitle stats difficulty
		// isPaidOnly topicTags { name translatedName slug __typename }
		// frequencyTimePeriod __typename } \"}";

		// Send post request
		con.setDoOutput(true);
		// DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		// wr.writeBytes(postJsonData);
		// wr.flush();
		// wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("nSending 'POST' request to URL : " + url);
		System.out.println("Get Data : ");
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String output;
		StringBuffer response = new StringBuffer();
		// csrftoken = con.getHeaderField("csrftoken");
		while ((output = in.readLine()) != null) {
			response.append(output);
		}
		in.close();
		AllQuestions dlb = JsonService.getObjectFromJson(response, AllQuestions.class);
		return dlb;
	}

	public static String getCompanyQuestion(String company) throws Exception {
		String url = "https://leetcode.com/graphql";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		loadHeader(con);
		String postJsonData = "{\"operationName\":\"getCompanyTag\",\"variables\":{\"slug\":\"" + company.toLowerCase()
				+ "\"},\"query\":\"query getCompanyTag($slug: String!) {   companyTag(slug: $slug) {     name     translatedName     frequencies     questions {       ...questionFields       __typename     }     __typename   }   favoritesLists {     publicFavorites {       ...favoriteFields       __typename     }     privateFavorites {       ...favoriteFields       __typename     }     __typename   } }  fragment favoriteFields on FavoriteNode {   idHash   id   name   isPublicFavorite   viewCount   creator   isWatched   questions {     questionId     title     titleSlug     __typename   }   __typename }  fragment questionFields on QuestionNode {   status   questionId   questionFrontendId   title   titleSlug   translatedTitle   stats   difficulty   isPaidOnly   topicTags {     name     translatedName     slug     __typename   }   frequencyTimePeriod   __typename } \"}";

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(postJsonData);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("nSending 'POST' request to URL : " + url);
		System.out.println("Post Data : " + postJsonData);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String output;
		StringBuffer response = new StringBuffer();
		// csrftoken = con.getHeaderField("csrftoken");
		while ((output = in.readLine()) != null) {
			response.append(output);
		}
		in.close();
		return response.toString();
	}

	private static void loadHeader(HttpURLConnection con) throws ProtocolException {
		loadHeader(con, "POST");
	}

	private static void loadHeader(HttpURLConnection con, String method) throws ProtocolException {
		// Setting basic post request
		con.setRequestMethod(method);
		con.setRequestProperty("authority", "leetcode.com");
		con.setRequestProperty("pragma", "no-cache");
		con.setRequestProperty("cache-control", "no-cache");
		con.setRequestProperty("accept", "*/*");
		con.setRequestProperty("x-newrelic-id", "UAQDVFVRGwEAXVlbBAg=");
		if (csrftoken == null) {
			con.setRequestProperty("x-csrftoken", "QWfcgpzQy7rN7h5UsQpWjG4YMBYMuS0wZqtTo7X8zt7eod4uj39OtGyBcvMlcryP");
		} else {
			con.setRequestProperty("x-csrftoken", csrftoken);
		}
		con.setRequestProperty("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36");
		con.setRequestProperty("content-type", "application/json");
		con.setRequestProperty("origin", "https://leetcode.com");
		con.setRequestProperty("sec-fetch-site", "same-origin");
		con.setRequestProperty("sec-fetch-mode", "cors");
		con.setRequestProperty("sec-fetch-dest", "empty");
		con.setRequestProperty("referer", "https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/");
		con.setRequestProperty("accept-language", "en-US,en;q=0.9");
		con.setRequestProperty("cookie",
				"_ga=GA1.2.2010661978.1601150685; __cfduid=d254da46c7aa00d571d32a694d847f7911603766992; __atuvc=7%7C43%2C18%7C44%2C8%7C45%2C1%7C46%2C1%7C47; __cf_bm=dd0202344dede931fdcaf1cfd39428c5142485d9-1605808188-1800-AVTRjplWi1hSxYth5CbuJFwWJm31PfG+IZ/MVrU9JJBvr4Ff/F3A/YaAC2tpSP78f8D6EOh5Myj1Zq4QbxLeioI=; _gid=GA1.2.531187170.1605808189; __extfc=1; csrftoken=QWfcgpzQy7rN7h5UsQpWjG4YMBYMuS0wZqtTo7X8zt7eod4uj39OtGyBcvMlcryP; messages=\"cb278983524fa9ef227a4e2b421850e6977cf623$[[\"__json_message\"054005425054\"Successfully signed in as deepaknetfren.\"]]\"; LEETCODE_SESSION=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJfYXV0aF91c2VyX2lkIjoiMTQ5NjczNCIsIl9hdXRoX3VzZXJfYmFja2VuZCI6ImRqYW5nby5jb250cmliLmF1dGguYmFja2VuZHMuTW9kZWxCYWNrZW5kIiwiX2F1dGhfdXNlcl9oYXNoIjoiODNkY2M5MzE3NjFiY2E4ZTk5YTcwMjUwZDlhM2YxMjAyMTFlOTY2ZCIsImlkIjoxNDk2NzM0LCJlbWFpbCI6ImRlZXBhay5rZWpyaXdhbEBnbWFpbC5jb20iLCJ1c2VybmFtZSI6ImRlZXBha25ldGZyZW4iLCJ1c2VyX3NsdWciOiJkZWVwYWtuZXRmcmVuIiwiYXZhdGFyIjoiaHR0cHM6Ly93d3cuZ3JhdmF0YXIuY29tL2F2YXRhci9kNTFlZjM0N2FmYmUwMjRkOWZhYmIyODcxMGFhYTFmMy5wbmc_cz0yMDAiLCJyZWZyZXNoZWRfYXQiOjE2MDU4MDgxOTYsImlwIjoiNzMuMjAyLjE3Ny4yMiIsImlkZW50aXR5IjoiOGQzZmVjMjU4MWQzOTYxZjMwMzc4NTFkNWNjMDAzOWMiLCJfc2Vzc2lvbl9leHBpcnkiOjEyMDk2MDAsInNlc3Npb25faWQiOjQyNzMyMDR9.WIjJ039uDSB_LgNUbhK7rj3R0eYJUYkhnWT9Gt_QcTE");
	}

}
