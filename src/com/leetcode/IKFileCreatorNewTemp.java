package com.leetcode;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IKFileCreatorNewTemp {



	public static void main(String[] args) throws Exception {
		
		List<String> lines = Files.readAllLines(Paths.get("C:\\\\\\\\deepakEduDrive\\\\\\\\new1author.txt"),Charset.forName("ISO-8859-1"));
		FileWriter fileWriter1 = new FileWriter("C:\\deepakEduDrive\\newauthoronly.txt", true);
		BufferedWriter printWriter1 = new BufferedWriter(fileWriter1);
		FileWriter fileWriter2 = new FileWriter("C:\\deepakEduDrive\\newidonly.txt", true);
		BufferedWriter printWriter2 = new BufferedWriter(fileWriter2);
		FileWriter fileWriter3 = new FileWriter("C:\\deepakEduDrive\\newtitleonly.txt", true);
		BufferedWriter printWriter3 = new BufferedWriter(fileWriter3);
		for(String line:lines) {
			String[] str = line.split("=");
			printWriter2.append(str[0]);
			printWriter2.append("\n");
			printWriter1.append(str[1]);
			printWriter1.append("\n");
			printWriter3.append(str[2]);
			printWriter3.append("\n");
		}
		printWriter1.close();
		printWriter2.close();
		printWriter3.close();
		
	}

}
