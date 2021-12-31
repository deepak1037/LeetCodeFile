package com.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.AllQuestions;
import com.leetcode.bean.AllQuestionMain;
import com.leetcode.bean.Question;

public class LeetcodeMain {
	private static List<String> companyList = new ArrayList<>();
	static {
		companyList.add("Google");
		//companyList.add("Microsoft");
		companyList.add("Amazon");
		//companyList.add("Goldman-Sachs");
		//companyList.add("Facebook");
	}
	public static void main(String[] args) throws Exception{
		Map<String, String> companyMap = new HashMap<>();// Key is companyName and value is list of question in json format
		for(String company:companyList) {
			companyMap.put(company, LeetcodeHttpClient.getCompanyQuestion(company));
		}	
		LeetCodeAllQuestion.startAll(companyMap);
		for(String company:companyList) {
			Set<Integer> priority = new HashSet<>();
			Set<Integer> excludedList = createExcludedList(company, priority);
			LeetcodeHttpClient.start(company, excludedList, priority);
		}

	}
	private static Set<Integer> createExcludedList(String company,  Set<Integer> priority) throws Exception {
		
		Set<Integer> excludedList = new HashSet<>();
		
		if(company.toLowerCase().equals("facebook")) {
			
		
			AllQuestions ex = LeetcodeHttpClient.getFeaturedQuestionList("top-facebook-questions");
			ex.getStatStatusPairs().forEach(s->excludedList.add(s.getStat().getQuestionId()));
			
			
			AllQuestionMain aqm =LeetcodeHttpClient.loadFavoriteList();
			Map<String, List<Question>> map = new HashMap<>();
			aqm.getData().getFavoritesLists().getAllFavorites().stream()
					.forEach(fv -> map.put(fv.getName(), fv.getQuestions()));
			map.getOrDefault("Facebook-Interviews", new ArrayList<>()).forEach(qtn-> {
				if(!excludedList.contains(qtn.getQuestionId())) {
					priority.add(qtn.getQuestionId());
				}
				});
			map.getOrDefault("Facebook-Phone", new ArrayList<>()).forEach(qtn-> {
				if(!excludedList.contains(qtn.getQuestionId())) {
					priority.add(qtn.getQuestionId());
				}
				});
			
			

			excludedList.addAll(priority);

		}
		return excludedList;
	}

}
