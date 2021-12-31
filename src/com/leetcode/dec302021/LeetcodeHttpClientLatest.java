package com.leetcode.dec302021;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.AllQuestions;
import com.leetcode.bean.AllQuestionMain;
import com.leetcode.util.JsonService;

public class LeetcodeHttpClientLatest {

	static List<String> list = Arrays.asList("6M20", "6M15", "6M10", "6M05", "6M01", "6MRem");
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
		// start("Facebook");
	}

	public static void startCreatingList(String company, Set<Integer> excludedList, Map<Integer, Integer> frequencyMap)
			throws Exception, InterruptedException {

		/*
		 * Iterator<Integer> i = excludedList.iterator(); while(i.hasNext()) { Integer
		 * id = i.next(); if(map.containsKey(id)) { i.remove(); } }
		 */
		// Load Favorite List
		AllQuestionMain dlb = loadFavoriteList();
		Map<String, String> fvList = getFavoriteList(dlb);

		// Delete Company Favorite List
		deleteList(companyMap.get(company), fvList);

		// Re-create Company Favorite List
		createList(companyMap.get(company));

		// Reload Favorite List
		dlb = loadFavoriteList();
		fvList = getFavoriteList(dlb);
		int exSize = excludedList.size();

		// Key is list id and value is list of id of question to be added to list.
		Map<String, Set<Integer>> res = createList(companyMap.get(company), fvList, frequencyMap, excludedList);

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
		System.out.println(frequencyMap.size());
	}

	private static Map<String, Set<Integer>> createList(String string, Map<String, String> fvList,
			Map<Integer, Integer> frequencyMap, Set<Integer> temp) {
		// List<Integer> temp = new ArrayList<>();

		Set<Integer> greaterThan19 = frequencyMap.entrySet().stream().filter(entry -> selectedFor20List(temp, entry))
				.map(entry -> entry.getKey()).collect(Collectors.toSet());
		temp.addAll(greaterThan19);

		Set<Integer> greaterThan14 = frequencyMap.entrySet().stream().filter(entry -> selectedFor15List(temp, entry))
				.map(entry -> entry.getKey()).collect(Collectors.toSet());
		temp.addAll(greaterThan14);

		Set<Integer> greaterThan9 = frequencyMap.entrySet().stream().filter(entry -> selectedFor10List(temp, entry))
				.map(entry -> entry.getKey()).collect(Collectors.toSet());
		temp.addAll(greaterThan9);

		Set<Integer> lessThan10 = frequencyMap.entrySet().stream().filter(entry -> selectedFor5List(temp, entry))
				.map(entry -> entry.getKey()).collect(Collectors.toSet());
		temp.addAll(lessThan10);

		Set<Integer> lessThan5 = frequencyMap.entrySet().stream().filter(entry -> selectedFor0List(temp, entry))
				.map(entry -> entry.getKey()).collect(Collectors.toSet());
		temp.addAll(lessThan5);

		Set<Integer> all = frequencyMap.entrySet().stream().filter(entry -> !temp.contains(entry.getKey()))
				.map(entry -> entry.getKey()).collect(Collectors.toSet());
		Map<String, Set<Integer>> res = new HashMap<>();
		res.put(fvList.get(string + "6M20"), greaterThan19);// 20
		res.put(fvList.get(string + "6M15"), greaterThan14);// 15
		res.put(fvList.get(string + "6M10"), greaterThan9);// 10
		res.put(fvList.get(string + "6M05"), lessThan10);// 5
		res.put(fvList.get(string + "6M01"), lessThan5);// 0
		res.put(fvList.get(string + "6MRem"), all);// All
		System.out.println(frequencyMap.size());
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
						|| (entry.getKey() > 1099 && entry.getValue() > 2)
						|| (entry.getKey() > 1299 && entry.getValue() > 0));
	}

	private static boolean selectedFor15List(Set<Integer> temp, Entry<Integer, Integer> entry) {
		return !temp.contains(entry.getKey()) && entry.getValue() < 20 && (entry.getValue() > 14
				|| (entry.getKey() > 1099 && entry.getValue() > 5) || (entry.getKey() > 1299 && entry.getValue() > 2));
	}

	private static boolean selectedFor20List(Set<Integer> temp, Entry<Integer, Integer> entry) {
		return !temp.contains(entry.getKey()) && (entry.getValue() > 19
				|| (entry.getKey() > 1099 && entry.getValue() > 9) || (entry.getKey() > 1299 && entry.getValue() > 4));
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

	private static Map<String, String> getFavoriteList(AllQuestionMain dlb) {
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
		String postJsonData = "{\"operationName\":\"Submissions\",\"variables\":{\"limit\":20,\"lastKey\":null,\"questionSlug\":\""
				+ slug
				+ "\"},\"query\":\"query Submissions( $questionSlug: String!) {  submissionList(offset: 0, limit: 20, questionSlug: $questionSlug) {    lastKey    hasNext    submissions {      id      statusDisplay      lang      runtime      timestamp     url      isPending      memory      __typename    }    __typename  }}\"}";

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
		// String url = "https://leetcode.com/api/problems/favorite_lists/" + listName +
		// "/";
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
				+ "\"},\"query\":\"query getCompanyTag($slug: String!) {   companyTag(slug: $slug) {     name     translatedName     frequencies     questions {       ...questionFields       __typename     }     __typename   }   favoritesLists {     publicFavorites {       ...favoriteFields       __typename     }     privateFavorites {       ...favoriteFields       __typename     }     __typename   } }  fragment favoriteFields on FavoriteNode {   idHash   id   name   isPublicFavorite   viewCount   creator   isWatched   questions {     questionId     title     titleSlug     __typename   }   __typename }  fragment questionFields on QuestionNode {   status   questionId   questionFrontendId   title   titleSlug   translatedTitle   stats   difficulty   isPaidOnly   topicTags {     name     translatedName     slug     __typename   } } \"}";

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
			con.setRequestProperty("x-csrftoken", "QWfxlC0u43foT88CSLEJIIC8ysuCQ8DMiztEOv44YDsVWb7nUyi3ABzqJtqzRfI0");
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
				"_ga=GA1.2.125521220.1631689217; gr_user_id=49ecbc34-81a9-4411-bb7b-ccb7b9148ee2; __atuvc=2%7C40%2C0%7C41%2C3%7C42; __stripe_mid=8a8e3a06-1268-4212-94e7-a4971b9ad489889417; _gid=GA1.2.1651753232.1640924505; csrftoken=QWfxlC0u43foT88CSLEJIIC8ysuCQ8DMiztEOv44YDsVWb7nUyi3ABzqJtqzRfI0; messages=\"ff744f8de10f29270fc5464e3e28aa0c3652f84c$[[\"__json_message\"054005425054\"You have signed out.\"]054[\"__json_message\"054005425054\"Successfully signed in as user5619I.\"]]\"; LEETCODE_SESSION=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJfYXV0aF91c2VyX2lkIjoiMTUxMDcyOCIsIl9hdXRoX3VzZXJfYmFja2VuZCI6ImFsbGF1dGguYWNjb3VudC5hdXRoX2JhY2tlbmRzLkF1dGhlbnRpY2F0aW9uQmFja2VuZCIsIl9hdXRoX3VzZXJfaGFzaCI6ImM4MDE0MzkzNjk5ZDk4MTU1ZDA5M2ZjMjk3YThiZWVmM2MwYzcyNmYiLCJpZCI6MTUxMDcyOCwiZW1haWwiOiJzcmgua2Fuc2FsQGdtYWlsLmNvbSIsInVzZXJuYW1lIjoidXNlcjU2MTlJIiwidXNlcl9zbHVnIjoidXNlcjU2MTlJIiwiYXZhdGFyIjoiaHR0cHM6Ly9hc3NldHMubGVldGNvZGUuY29tL3VzZXJzL3VzZXI1NjE5aS9hdmF0YXJfMTU5NDI4NjI3MC5wbmciLCJyZWZyZXNoZWRfYXQiOjE2NDA5MjQ1NjMsImlwIjoiMjYwMTo2MDE6ODMwMDphMmE6OTQ4MjpkNzdiOmIyYjE6YzMzNCIsImlkZW50aXR5IjoiZDQ0YmZiYTdlYzJiNjdkOWViOWU2YzFlZWE1NWMyNTMiLCJzZXNzaW9uX2lkIjoxNjE4NzE2NywiX3Nlc3Npb25fZXhwaXJ5IjoxMjA5NjAwfQ.JGtGHfL3BFA_GqiK4RDo6e_skR2aQBd3clEWP0AJXAA; 87b5a3c3f1a55520_gr_last_sent_sid_with_cs1=8de71c8b-7bce-4696-a91c-5463e598fb69; 87b5a3c3f1a55520_gr_last_sent_cs1=user5619I; 87b5a3c3f1a55520_gr_session_id=8de71c8b-7bce-4696-a91c-5463e598fb69; 87b5a3c3f1a55520_gr_session_id_8de71c8b-7bce-4696-a91c-5463e598fb69=true; NEW_PROBLEMLIST_PAGE=1; 87b5a3c3f1a55520_gr_cs1=user5619I; _gat=1");
	}

}
