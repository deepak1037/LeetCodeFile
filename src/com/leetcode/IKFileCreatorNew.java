package com.leetcode;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IKFileCreatorNew {



	public static void main(String[] args) throws Exception {
		List<String> lines = Files.readAllLines(
				Paths.get("C:\\\\deepakEduDrive\\\\new00.txt"));
		FileWriter fileWriter1 = new FileWriter("C:\\deepakEduDrive\\new-mock.txt", true);
		BufferedWriter printWriter1 = new BufferedWriter(fileWriter1);
		FileWriter fileWriter2 = new FileWriter("C:\\deepakEduDrive\\new-nonmock.txt", true);
		BufferedWriter printWriter2 = new BufferedWriter(fileWriter2);
		for (String line : lines) {
			if (line.contains("Mock")) {
				printWriter1.append(line);
				printWriter1.append("\n");
			}else {
				printWriter2.append(line);
				printWriter2.append("\n");
			}
		}

		printWriter1.close();
		printWriter2.close();
	}

}
