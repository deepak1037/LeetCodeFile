package com.leetcode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class IKClientNewfinal {

	private static String csrftoken = null;
	private static Map<Integer, String> course = new TreeMap<>();
	private static Map<Integer, String> courseTest = new TreeMap<>();
	private static Map<Integer, String> courseMock = new TreeMap<>();

	private static Map<Integer, String> author = new HashMap<>();

	public static void main(String[] args) throws Exception {
		int start = 9320;
		int end = 12149;
		int cnt = 10;
		for (int i = start; i <= end; i = i + cnt) {
			if (i + cnt > end) {
				cnt = end - i + 1;
			}
			start(i, i + cnt, end);
			// System.out.println("\n");
			FileWriter fileWriter = new FileWriter("C:\\deepakEduDrive\\newauthor10022021.txt", true);
			BufferedWriter printWriter = new BufferedWriter(fileWriter);
			for (Map.Entry<Integer, String> set : course.entrySet()) {
				printWriter.append(set.getKey() + "=");
				printWriter.append(author.getOrDefault(set.getKey(), "Unknown").trim() + "=");
				printWriter.append(set.getValue());
				printWriter.append("\n");
			}
			printWriter.close();
			course.clear();

			FileWriter fileWriter2 = new FileWriter("C:\\deepakEduDrive\\newMockAuth10022021.txt", true);
			BufferedWriter printWriter2 = new BufferedWriter(fileWriter2);
			for (Map.Entry<Integer, String> set : courseMock.entrySet()) {
				printWriter2.append(set.getKey() + "=");
				printWriter2.append(author.getOrDefault(set.getKey(), "Unknown").trim() + "=");
				printWriter2.append(set.getValue());
				printWriter2.append("\n");
			}
			printWriter2.close();
			courseMock.clear();
			/*
			 * FileWriter fileWriter3 = new FileWriter("C:\\deepakEduDrive\\new1Test.txt",
			 * true); BufferedWriter printWriter3 = new BufferedWriter(fileWriter2);
			 * for(Map.Entry<Integer, String> set :courseTest.entrySet()) {
			 * printWriter3.append(set.getKey()+"="+set.getValue());
			 * printWriter3.append("\n"); } printWriter3.close(); courseTest.clear();
			 */
		}
	}

//String url = "https://uplevel.interviewkickstart.com/resource/rc-instruction-2284-23211-205-"+i;
	private static String authorString = "<p class=\"tx-medium mg-b-2\">";
	private static String searchString = "<h4 class=\"mg-b-0\">";

	private static void start(int i, int n, int end) throws Exception {

		while (i < n && i<=end) {

			int j = 0;
			String url = "https://uplevel.interviewkickstart.com/resource/helpful-class-video-2319-85-" + i + "-0";
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			loadHeader(con);

			int responseCode = con.getResponseCode();
			int len = con.getContentLength();
			boolean close = true;
			if (responseCode == 200) {
				// System.out.print(i+":");
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String output;
				StringBuffer response = new StringBuffer();
				// csrftoken = con.getHeaderField("csrftoken");
				while ((output = in.readLine()) != null) {
					j++;
					//System.out.println(output);
					if (j > 460) {
						String str = "";
					}
					if (output.contains(searchString)) {
						loadMap(i, output);
						
					} else if (output.contains(authorString)) {
						loadAuth(i, output);
						in.close();
						break;
					}

					// response.append(output);

				}
				if (close)
					in.close();

			}
			i++;
			Thread.sleep(5000);
		}
	}

	private static void loadAuth(int i, String value) {
		value = value.trim();
		value = value.substring(authorString.length(), value.length() - 4);
		// author.putIfAbsent(value, new ArrayList<>());
		// author.get(value).add(i);

		author.put(i, value);
	}

	private static void loadMap(Integer key, String value) {
		value = value.substring(searchString.length(), value.length() - 5);
		if (value.contains("Mock")) {
			courseMock.put(key, value);
		} else if (value.contains("Test Review")) {
			courseTest.put(key, value);
		} else {
			course.put(key, value);
		}

	}

	private static void loadHeader(HttpURLConnection con) throws ProtocolException {
		loadHeader(con, "GET");
	}

	private static void loadHeader(HttpURLConnection con, String method) throws ProtocolException {
		// Setting basic post request
		con.setRequestMethod(method);
		con.setRequestProperty("authority", "uplevel.interviewkickstart.com");
		con.setRequestProperty("pragma", "no-cache");
		con.setRequestProperty("cache-control", "no-cache");
		con.setRequestProperty("accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");

		con.setRequestProperty("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36");
		con.setRequestProperty("sec-fetch-site", "same-origin");
		con.setRequestProperty("sec-fetch-mode", "navigate");
		con.setRequestProperty("sec-fetch-dest", "document");

		con.setRequestProperty("sec-fetch-user", "?1");

		con.setRequestProperty("referer", "https://uplevel.interviewkickstart.com/schedule/");

		con.setRequestProperty("accept-language", "en-US,en;q=0.9");

		con.setRequestProperty("cookie",
				"hjid=215e3324-a1c6-4ad9-8b2f-3a04c8e25c38; loadedAlready=0; _gcl_au=1.1.825398405.1632698037; _fbp=fb.1.1632698037515.1033996988; __lotr=https%3A%2F%2Fwww.google.com%2F; __hstc=124801877.72010f0704c252c692b8fd89b498a485.1632698037999.1632698037999.1632698037999.1; hubspotutk=72010f0704c252c692b8fd89b498a485; __hssrc=1; _lo_uid=296540-1632698038306-d22426e0a0fccedb; __lotl=https%3A%2F%2Fwww.interviewkickstart.com%2F; _ga=GA1.2.1698293999.1631765311; _uetvid=6ce9f8e01f1f11ec89468d89efc8500a; _lo_v=2; _ga_L4RSQLBKGB=GS1.1.1632705601.2.1.1632705601.60; csrftoken=6xnHWpEBGp0OpYgwlHjaPALUsQVp7gfE263lvbj4apj2BLl6ZTHtFvZSmyGfoSYa; sessionid=vf8lk1qod7n55lcgmncx4r7g1p9sc3wy; _gid=GA1.2.1398879456.1633229861; _hjAbsoluteSessionInProgress=0; last_pathname=/resource/helpful-class-video-2319-85-9320-0' \\\r\n"
				+ "  --compressed");

		// con.setRequestProperty("cookie", "_ga=GA1.2.1719317506.1597947189;
		// _hjid=84cdd69a-473c-401c-98f4-d9617d96bcf4; _gid=GA1.2.860139481.1599869613;
		// csrftoken=sQpSlWqrpDt1XhqmVGFChOOLLJaWwOKIHI7Che8CibizEHoz9T35g62L20BbOUcW;
		// sessionid=txlj8mxk914cxb5ar97bxg8r31uldvm4;
		// _gcl_au=1.1.2101550324.1600407638; _uetsid=cc2c3ca8f68eac23cbe459c44877b398;
		// _uetvid=5bfb9a48dd385e29f1ce18839e6930e2; _fbp=fb.1.1600407638192.1682530356;
		// _hjTLDTest=1; _hjAbsoluteSessionInProgress=1; __extfc=1;
		// _hjIncludedInPageviewSample=1;
		// last_pathname=/resource/helpful-class-video-2319-85-549-0");
	}

}
