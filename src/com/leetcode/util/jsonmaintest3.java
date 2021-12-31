package com.leetcode.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class jsonmaintest3 {

	public static void main(String[] args) throws IOException,ParseException {

		System.out.println(totalGoalCO("UEFA Champions League", 2011));
	}

	private static int totalGoalCO(String competition, int year) throws IOException, ParseException{
		String url = "https://jsonmock.hackerrank.com/api/football_competitions";
		String team = getWinningteam(getFormatterUrl(url,competition, year));
		return totalGoals(competition,team, year);
	}
	
	private static String getWinningteam(String url) throws IOException, ParseException {
		String rsp = getResponse(url, 0);
		JSONParser parser = new JSONParser();

		JSONObject jsonResponse = (JSONObject) parser.parse(rsp);
		
		JSONArray jarray = (JSONArray) jsonResponse.get("data");
		if(jarray.size()==1) {
			JSONObject dataObject = (JSONObject) jarray.get(0);
			return dataObject.get("winner").toString();
		}

		return "";
	}

	private static int totalGoals(String competition, String team, int year) throws IOException, ParseException {
		String url = "https://jsonmock.hackerrank.com/api/football_matches";
		int numberOfGoalAsHomeTeam = getTotalGoals(getFormatterUrl(url, competition, "team1", team, year), "team1goals");
		int numberOfGoalAsVisitingTeam = getTotalGoals(getFormatterUrl(url, competition, "team2", team, year), "team2goals");
		return numberOfGoalAsHomeTeam + numberOfGoalAsVisitingTeam;
	}

	private static String getFormatterUrl(String endpoint,  String competition, int year) throws IOException {
		return String.format(endpoint + "?name=%s&year=%d",  URLEncoder.encode(competition,"UTF-8"), year);
	}
	private static String getFormatterUrl(String endpoint, String competition, String teamNumber, String team, int year) throws IOException {
		return String.format(endpoint + "?competition=%s&%s=%s&year=%d",  URLEncoder.encode(competition,"UTF-8"),teamNumber, URLEncoder.encode(team,"UTF-8"), year);
	}

	private static int getTotalGoals(String url, String teamType) throws IOException, ParseException {
		int sum = 0;
		int page = 0;
		int totalPage = 0;
		do {
			++page;
			String rsp = getResponse(url, page);
			JSONParser parser = new JSONParser();

			JSONObject jsonResponse = (JSONObject) parser.parse(rsp);

			page = Integer.parseInt(jsonResponse.get("page").toString());
			totalPage = Integer.parseInt(jsonResponse.get("total_pages").toString());
			sum += getTeamGoals(teamType, jsonResponse);
		} while (page < totalPage);

		return sum;
	}

	private static int getTeamGoals(String teamType, JSONObject jsonObject) throws IOException {
		int sum = 0;

		JSONArray jarray = (JSONArray) jsonObject.get("data");
		for (int i = 0; i < jarray.size(); i++) {
			JSONObject dataObject = (JSONObject) jarray.get(i);
			sum += Integer.parseInt(dataObject.get(teamType).toString());
		}

		return sum;
	}

	private static String getResponse(String url, int page) throws IOException {
		if(page!=0) {
			url = url+"&page="+page;
		}
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.addRequestProperty("Content-Type", "application/json");
		int responseCode = con.getResponseCode();
		StringBuilder response = new StringBuilder();

		if (responseCode == 200) {

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String output;

			while ((output = in.readLine()) != null) {
				System.out.println(output);
				response.append(output);

			}
		}

		return response.toString();
	}

	/*
	 * public static void main1(String[] args) throws Exception { String req =
	 * "{\"page\":1,\"per_page\":10,\"total\":6,\"total_pages\":1,\"data\":[{\"competition\":\"UEFA Champions League\",\"year\":2011,\"round\":\"GroupH\",\"team1\":\"Barcelona\",\"team2\":\"AC Milan\",\"team1goals\":\"2\",\"team2goals\":\"2\"},{\"competition\":\"UEFA Champions League\",\"year\":2011,\"round\":\"GroupH\",\"team1\":\"Barcelona\",\"team2\":\"Viktoria Plzen\",\"team1goals\":\"2\",\"team2goals\":\"0\"},{\"competition\":\"UEFA Champions League\",\"year\":2011,\"round\":\"GroupH\",\"team1\":\"Barcelona\",\"team2\":\"BATE Borisov\",\"team1goals\":\"4\",\"team2goals\":\"0\"},{\"competition\":\"UEFA Champions League\",\"year\":2011,\"round\":\"R16\",\"team1\":\"Barcelona\",\"team2\":\"Bayer Leverkusen\",\"team1goals\":\"7\",\"team2goals\":\"1\"},{\"competition\":\"UEFA Champions League\",\"year\":2011,\"round\":\"QF\",\"team1\":\"Barcelona\",\"team2\":\"AC Milan\",\"team1goals\":\"3\",\"team2goals\":\"1\"},{\"competition\":\"UEFA Champions League\",\"year\":2011,\"round\":\"SF\",\"team1\":\"Barcelona\",\"team2\":\"Chelsea\",\"team1goals\":\"2\",\"team2goals\":\"2\"}]}"
	 * ; JSONParser parser = new JSONParser(); JSONObject jsonObject = (JSONObject)
	 * parser.parse(req); JSONArray jarray = (JSONArray) jsonObject.get("data");
	 * Iterator<JSONObject> iterator = jarray.iterator(); int sum = 0; while
	 * (iterator.hasNext()) { JSONObject dataObject = iterator.next(); sum+=
	 * Integer.parseInt(dataObject.get("team1goals").toString()); }
	 * System.out.println(sum);
	 * 
	 * }
	 */

}
