package com.leetcode;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IKFileCreator {

	private static Map<String, List<String>> map = new HashMap<>();
	static {
		map.put("Soham", new ArrayList<>());
		map.put("Omkar", new ArrayList<>());
		map.put("Niloy", new ArrayList<>());
		map.put("Ganesh", new ArrayList<>());
		map.put("Gaurav", new ArrayList<>());
		map.put("Hien", new ArrayList<>());
		map.put("Tilo", new ArrayList<>());

		map.put("Murali", new ArrayList<>());
		map.put("Sundar", new ArrayList<>());
		map.put("Nikhil", new ArrayList<>());
		map.put("Yannis", new ArrayList<>());
		map.put("Glenn", new ArrayList<>());

		map.put("Others", new ArrayList<>());
		map.put("Ankur", new ArrayList<>());
		map.put("Pavan", new ArrayList<>());
		map.put("SS1", new ArrayList<>());
		map.put("SS2", new ArrayList<>());
		map.put("Test", new ArrayList<>());
		map.put("Nick", new ArrayList<>());
		map.put("Nadan", new ArrayList<>());
		map.put("Nafi", new ArrayList<>());
		map.put("Prudvhi", new ArrayList<>());
		map.put("Chao", new ArrayList<>());
		map.put("Manoj", new ArrayList<>());
		map.put("Sanjeev", new ArrayList<>());
		map.put("Akshay", new ArrayList<>());
		map.put("Tom", new ArrayList<>());
		
		map.put("Behavioral", new ArrayList<>());
		map.put("Ankit", new ArrayList<>());
		map.put("Shashi", new ArrayList<>());
		map.put("Ryan", new ArrayList<>());
		map.put("Orientation", new ArrayList<>());
		map.put("Vivek", new ArrayList<>());
		map.put("Vineet", new ArrayList<>());
		map.put("Joseph", new ArrayList<>());
		map.put("Richi", new ArrayList<>());
		map.put("Matthew", new ArrayList<>());		
		map.put("Ladan", new ArrayList<>());
		map.put("Abdul", new ArrayList<>());
		map.put("Brian", new ArrayList<>());
		map.put("Jake", new ArrayList<>());
		map.put("Matt", new ArrayList<>());
		map.put("Christer", new ArrayList<>());
		map.put("Satyen", new ArrayList<>());
		map.put("David", new ArrayList<>());
		map.put("Suavi", new ArrayList<>());
		map.put("Reza", new ArrayList<>());
		map.put(" Li,", new ArrayList<>());
		map.put("Yuval", new ArrayList<>());
		map.put("Shailesh", new ArrayList<>());
		map.put("Afzal", new ArrayList<>());
		map.put("Monal", new ArrayList<>());
		
		
		
		
		
		
	}

	private static void addToMap(String line) {
		for (String key : map.keySet()) {
			if (line.contains(key)) {
				map.get(key).add(line);
				return;
			}
		}
		map.get("Others").add(line);
	}

	public static void main(String[] args) throws Exception {
		List<String> list =new ArrayList<>();
		List<String> lines = Files.readAllLines(Paths.get("C:\\deepakEduDrive\\new-nonmock.txt"),Charset.forName("ISO-8859-1"));
		for (String line : lines) {
			addToMap(line);
		}
		StringBuilder sb = new StringBuilder();
		List<String> values = map.get("Others");
		for (String val : values) {
			sb.append("Others").append(",").append(val).append("\n");
			list.add(val.split("=")[0]);
		}
		map.remove("Others");
		for (Map.Entry<String, List<String>> entry : map.entrySet()) {
			String key = entry.getKey();
			values = entry.getValue();
			for (String val : values) {
				sb.append(key.trim()).append(",").append(val).append("\n");
			}
		}

		Files.write(Paths.get("C:\\deepakEduDrive\\new2.txt"),
				sb.toString().getBytes());
		
		System.out.println(String.join("\",\"", list));
	}

}
