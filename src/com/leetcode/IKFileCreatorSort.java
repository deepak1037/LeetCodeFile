package com.leetcode;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IKFileCreatorSort {

	public static void main(String[] args) throws Exception {
		List<String> lines = Files.readAllLines(Paths.get("C:\\\\deepakEduDrive\\\\newtest.txt"));
		FileWriter fileWriter1 = new FileWriter("C:\\deepakEduDrive\\newtest-sort.txt", true);
		BufferedWriter printWriter1 = new BufferedWriter(fileWriter1);
		Collections.sort(lines,(a,b)->sort(a,b));
		List<Integer> ilist = new ArrayList<>();
		for (String line : lines) {

			printWriter1.append(line);
			printWriter1.append("\n");
			ilist.add(getNumber(line));
		}

		printWriter1.close();
		Collections.sort(ilist);
		for(int i:ilist) {
			System.out.println(i);
		}
	}

	private static Integer getNumber(String a) {
		String[] asplit = a.split(",");
		asplit = asplit[1].split("=");
		return Integer.parseInt(asplit[0]);
	}

	private static int sort(String a, String b) {
		String[] asplit = a.split(",");
		String[] bsplit = b.split(",");
		if(asplit[0].equals(bsplit[0])) {
			asplit = asplit[1].split("=");
			bsplit = bsplit[1].split("=");
			return Integer.compare(Integer.parseInt(asplit[0]), Integer.parseInt(bsplit[0]));
		}
		return a.compareTo(b);
	}

}
