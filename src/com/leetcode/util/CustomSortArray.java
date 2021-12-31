package com.leetcode.util;

import java.util.Arrays;
import java.util.List;

public class CustomSortArray {

	public static void main(String[] args) {
		Integer[] numbers1 = { 6, 3, 4, 5 };
		Integer[] numbers2 = { 13, 10, 21, 20 };
		Integer[] numbers = { 8, 5, 11, 4, 6 };
		System.out.println(moves(Arrays.asList(numbers)));
	}

	public static int moves(List<Integer> arr) {
		int i = 0;
		int j = arr.size() - 1;
		int numberOfMove = 0;
		while (i < j ) {

			while (arr.get(i) % 2 == 0) {// skip even character from beginning
				i++;
			}

			while (arr.get(j) % 2 == 1) {//skip odd character from end
				j--;
			}

			if (i < j) { // if i is after j array is already sorted, hence this check
				numberOfMove++;
				i++;
				j--;
			}

		}
		return numberOfMove;
	}

}
