package com.leetcode.util;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



class Result2 {

    /*
     * Complete the 'getWinnerTotalGoals' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. STRING competition
     *  2. INTEGER year
     */

    public static int getWinnerTotalGoals(String competition, int year) throws IOException {
        try {
        String url = "https://jsonmock.hackerrank.com/api/football_competitions";
        String team = getWinningteam(getFormatterUrl(url,competition, year));
        return getTotalGoals(competition, team, year);
        }catch (ParseException ex) {

        }
        return 0;

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
        private static String getFormatterUrl(String endpoint,  String competition, int year) throws IOException {
        return String.format(endpoint + "?name=%s&year=%d",  URLEncoder.encode(competition,"UTF-8"), year);
    }
        public static int getTotalGoals(String competition, String team, int year) throws IOException {

        try {

            String url = "https://jsonmock.hackerrank.com/api/football_matches";
            int numberOfGoalAsHomeTeam = getTotalGoals(getFormatterUrl(url, competition, "team1", team, year), "team1goals");
            int numberOfGoalAsVisitingTeam = getTotalGoals(getFormatterUrl(url,competition, "team2", team, year), "team2goals");
            return numberOfGoalAsHomeTeam + numberOfGoalAsVisitingTeam;
        } catch (ParseException ex) {

        }
        return 0;
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
        URL obj = new URL(url + "&page=" + page);
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
}

public class HackerRankQ2test2 {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String competition = bufferedReader.readLine();

        int year = Integer.parseInt(bufferedReader.readLine().trim());

        int result = Result2.getWinnerTotalGoals(competition, year);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
