package com.leetcode;


import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

public class IKClient {


	
	private static String csrftoken = null;
	private static Map<Integer, String> course = new TreeMap<>();

	public static void main(String[] args) throws Exception {
		start();
		//System.out.println("\n");
		FileWriter fileWriter = new FileWriter("C:\\deepakEduDrive\\new.txt");
	    PrintWriter printWriter = new PrintWriter(fileWriter);
		for(Map.Entry<Integer, String> set :course.entrySet()) {
			printWriter.println(set.getKey()+"="+set.getValue());
		}
		printWriter.close();
	
	}







//String url = "https://uplevel.interviewkickstart.com/resource/rc-instruction-2284-23211-205-"+i;
	private static String searchString ="<h4 class=\"mg-b-0\">";
	private static void start() throws Exception {
		int i = 1001;
		int n = 3001;
		while (i<n) {
			int j=0;
			String url = "https://uplevel.interviewkickstart.com/resource/helpful-class-video-2319-85-"+i+"-0";
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			loadHeader(con);


			int responseCode = con.getResponseCode();
			int len = con.getContentLength();
			boolean close = true;
			if(responseCode==200 ) {
				//System.out.print(i+":");
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String output;
				StringBuffer response = new StringBuffer();
				// csrftoken = con.getHeaderField("csrftoken");
				while ((output = in.readLine()) != null) {
					j++;
					if(j>460) {
						String str= "";
					}
					if(output.contains(searchString)) {
						loadMap(i,output);
						in.close();
						break;
					}
					
					//response.append(output);
					
					
				}
				if(close)
				in.close();
				 
			}
			i++;
			Thread.sleep(5000);
		}
	}


private static void loadMap(Integer key, String value) {
	value = value.substring(searchString.length(), value.length()-5);
	course.put(key, value);
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
		con.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
			
		con.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36");
		con.setRequestProperty("sec-fetch-site", "same-origin");
		con.setRequestProperty("sec-fetch-mode", "navigate");
		con.setRequestProperty("sec-fetch-dest", "document");
		
		
		con.setRequestProperty("sec-fetch-user", "?1");

		con.setRequestProperty("referer", "https://uplevel.interviewkickstart.com/schedule/");
		
		
		con.setRequestProperty("accept-language", "en-US,en;q=0.9");
		
		
		
		con.setRequestProperty("cookie",
				"csrftoken=sij7YF5GoW6p6jTZpvno4H0np1fJxli1U2Rcl7pTA6dF7X5KrCo9GVp7z6HlQKp3; sessionid=1mj8pel82qjmg7lnxwvcrtwzltkohna8; _ga=GA1.2.1720274405.1617250434; _gid=GA1.2.1165503845.1617250434; _hjid=5ac56914-7aef-46f2-adbf-0b153ded5408; _hjTLDTest=1; _hjAbsoluteSessionInProgress=0; _hjIncludedInPageviewSample=1; last_pathname=/resource/helpful-class-video-2316-85-284-0; _gat_gtag_UA_54935903_2=1");
	
		//con.setRequestProperty("cookie", "_ga=GA1.2.1719317506.1597947189; _hjid=84cdd69a-473c-401c-98f4-d9617d96bcf4; _gid=GA1.2.860139481.1599869613; csrftoken=sQpSlWqrpDt1XhqmVGFChOOLLJaWwOKIHI7Che8CibizEHoz9T35g62L20BbOUcW; sessionid=txlj8mxk914cxb5ar97bxg8r31uldvm4; _gcl_au=1.1.2101550324.1600407638; _uetsid=cc2c3ca8f68eac23cbe459c44877b398; _uetvid=5bfb9a48dd385e29f1ce18839e6930e2; _fbp=fb.1.1600407638192.1682530356; _hjTLDTest=1; _hjAbsoluteSessionInProgress=1; __extfc=1; _hjIncludedInPageviewSample=1; last_pathname=/resource/helpful-class-video-2319-85-549-0");
	}

}
