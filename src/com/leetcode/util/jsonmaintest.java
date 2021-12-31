package com.leetcode.util;

import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

public class jsonmaintest {

	public static void main(String[] args) throws Exception {
		
		
		//System.out.println(totalGoals("Chelsea",2011));
	}
	private static int totalGoals(String team, String year) {
		String url = "https://jsonmock.hackerrank.com/api/football_matches";
		int numberOfGoalAsHomeTeam = getTotalGoals(getFormatterUrl(url,"team1",team, year), "team1goals");
		int numberOfGoalAsVisitingTeam = getTotalGoals(getFormatterUrl(url,"team2",team, year), "team2goals");
		return numberOfGoalAsHomeTeam + numberOfGoalAsVisitingTeam;
	}
	private static String getFormatterUrl(String endpoint, String teamNumber, String team, String year) {
		return String.format(endpoint + "?%s=%s&year=%d", teamNumber, team, year);
	}

	private static int getTotalGoals(String url, String teamType) {
		int sum = 0;
		int page = 0;
		int totalPage = 0;
		do {
			++page;
			String rsp = getResponse(url, page);
			JSONObject jsonResponse = new JSONObject(rsp);
			page = jsonResponse.getInt("page");
			totalPage = jsonResponse.getInt("total_pages");
			sum += getTeamGoals(teamType, rsp);
		} while (page < totalPage);

		return sum;
	}

	private static int getTeamGoals(String teamType, String rsp) {
		int sum = 0;
		JSONObject jsonObject = new JSONObject(rsp);
		JSONArray jarray = jsonObject.getJSONArray("data");
		for (int i = 0; i < jarray.length(); i++) {
			JSONObject dataObject = jarray.getJSONObject(i);
			sum += dataObject.getInt(teamType);
		}

		return sum;
	}

	private static String getResponse(String url, int page) {
		String rsp = "{\"page\":" + page
				+ ",\"per_page\":10,\"total\":6,\"total_pages\":2,\"data\":[{\"competition\":\"UEFA Champions League\",\"year\":2011,\"round\":\"GroupH\",\"team1\":\"Barcelona\",\"team2\":\"AC Milan\",\"team1goals\":\"2\",\"team2goals\":\"2\"},{\"competition\":\"UEFA Champions League\",\"year\":2011,\"round\":\"GroupH\",\"team1\":\"Barcelona\",\"team2\":\"Viktoria Plzen\",\"team1goals\":\"2\",\"team2goals\":\"0\"},{\"competition\":\"UEFA Champions League\",\"year\":2011,\"round\":\"GroupH\",\"team1\":\"Barcelona\",\"team2\":\"BATE Borisov\",\"team1goals\":\"4\",\"team2goals\":\"0\"},{\"competition\":\"UEFA Champions League\",\"year\":2011,\"round\":\"R16\",\"team1\":\"Barcelona\",\"team2\":\"Bayer Leverkusen\",\"team1goals\":\"7\",\"team2goals\":\"1\"},{\"competition\":\"UEFA Champions League\",\"year\":2011,\"round\":\"QF\",\"team1\":\"Barcelona\",\"team2\":\"AC Milan\",\"team1goals\":\"3\",\"team2goals\":\"1\"},{\"competition\":\"UEFA Champions League\",\"year\":2011,\"round\":\"SF\",\"team1\":\"Barcelona\",\"team2\":\"Chelsea\",\"team1goals\":\"2\",\"team2goals\":\"2\"}]}";
		return rsp;
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
