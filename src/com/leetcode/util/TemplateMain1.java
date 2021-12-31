package com.leetcode.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TemplateMain1 {

	private static String url = "https://jsonmock.hackerrank.com/api/football_matches?%s=%s&year=%d";

	public static void main(String[] args) throws IOException {
		try {
			System.out.println(totalGoals("Barcelona", 2011));
		} catch (ParseException pex) {

		}

	}

	private static int totalGoals(String team, int year) throws IOException, ParseException {
		int numberOfGoalAsHomeTeam = getTotalGoals("team1", team, year, "team1goals");
		int numberOfGoalAsVisitingTeam = getTotalGoals("team2", team, year, "team2goals");
		return numberOfGoalAsHomeTeam + numberOfGoalAsVisitingTeam;
	}

	private static int getTotalGoals(String teamAs, String team, int year, String teamGoal) throws IOException, ParseException {
		
		String url = getUrl(teamAs, team, year);
		
		int sum = 0;
		int page = 0;
		int totalPage = 0;
		
		do {
			++page;
			String rsp = getHttpResponse(url + "&page=" + page);		
			JSONParser parser = new JSONParser();
			JSONObject jsonResponse = (JSONObject) parser.parse(rsp);
			page = Integer.parseInt(jsonResponse.get("page").toString());
			totalPage = Integer.parseInt(jsonResponse.get("total_pages").toString());
			sum += getTeamGoals(teamGoal, jsonResponse);
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

	private static String getHttpResponse(String url) throws IOException {
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
				response.append(output);
			}
		}
		return response.toString();
	}

	private static String getUrl(String teamNumber, String team, int year) throws IOException {
		return String.format(url, teamNumber, getUTF8Encoded(team), year);
	}

	private static String getUTF8Encoded(String val) throws UnsupportedEncodingException {
		return URLEncoder.encode(val, "UTF-8");
	}
}
