package com.leetcode.dec302021;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.AllQuestions;

public class LeetcodeMainLatest {
	private static List<String> companyList = new ArrayList<>();
	static {
		// companyList.add("Google");
		// companyList.add("Microsoft");
		companyList.add("Amazon");
		// companyList.add("Goldman-Sachs");
		// companyList.add("Facebook");
	}
	private static boolean excludeTopCompanyQuestionFromFavouriteList = true;

	public static void main(String[] args) throws Exception {
		Map<String, String> companyMap = new HashMap<>();// Key is companyName and value is list of question in json
															// format

		for (String company : companyList) {
			companyMap.put(company, LeetcodeHttpClientLatest.getCompanyQuestion(company));
		}
		Map<String, Map<Integer, Integer>> companyFrequencyMap = LeetCodeAllQuestionLatest.startAll(companyMap);
		for (String company : companyList) {
			Set<Integer> priority = new HashSet<>();
			Set<Integer> excludedList = new HashSet<>();
			if (excludeTopCompanyQuestionFromFavouriteList) {
				excludedList = createExcludedList(company, priority);
			}

			LeetcodeHttpClientLatest.startCreatingList(company, excludedList, companyFrequencyMap.get(company));
		}

	}

	private static Set<Integer> createExcludedList(String company, Set<Integer> priority) throws Exception {

		Set<Integer> excludedList = new HashSet<>();

		AllQuestions ex = LeetcodeHttpClientLatest
				.getFeaturedQuestionList("top-" + company.toLowerCase() + "-questions");
		ex.getStatStatusPairs().forEach(s -> excludedList.add(s.getStat().getQuestionId()));

		return excludedList;
	}

}
