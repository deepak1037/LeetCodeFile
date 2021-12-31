package com.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Stack;

public class Test {

	static Map<String, String> mapper = new HashMap<>();
	static {
		//mapper.put("A", "realm_id#deleted#isPrimary");
		mapper.put("B", "suggested_qbo_txn_id_match");
		mapper.put("C", "status");
		mapper.put("D", "transaction_source");
		mapper.put("E", "fi_amount");
		mapper.put("F", "contact_display_name");
		mapper.put("G", "resolved_entities_id");
		mapper.put("H", "receipt_processing_state");
		mapper.put("I", "download_uri");
		mapper.put("J", "connection_account_id");
		mapper.put("K", "transaction_date");
		mapper.put("L", "is_posted");
		mapper.put("M", "user_marked_duplicate");
		mapper.put("N", "item_name");
	}

	static Map<String, List<String>> indexmap = new HashMap<>();
	static {
		indexmap.put("1", Arrays.asList("B"));
		indexmap.put("2", Arrays.asList("C","D","E"));
		indexmap.put("3", Arrays.asList("D"));
		indexmap.put("4", Arrays.asList("C","D","F"));
		indexmap.put("5", Arrays.asList("C","G"));
		indexmap.put("6", Arrays.asList("C","D","H"));
		//indexmap.put("7", Arrays.asList("D","H","I"));
		indexmap.put("8", Arrays.asList("C","D","J","K","L","M"));
		indexmap.put("9", Arrays.asList("C","J"));
		indexmap.put("10", Arrays.asList("D","I"));
		indexmap.put("11", Arrays.asList("C","D","N"));
	}


	
    public static List<String> permute(List<String> list) {
        if(list==null) return null;
        boolean[] isIncluded=new boolean[list.size()];
        Stack<String> aux=new Stack<>();
        List<String> result=new ArrayList<>();
        helper(result,aux,isIncluded,list,list.size());
        return result;
    }
    
    private static void helper(List<String> result, Stack<String> aux, boolean[] isIncluded, List<String> list, int n){
        
        if(aux.size()==n){
        	List<String> lista = new ArrayList<>(aux);
        	String str = String.join(":",lista);
        	if(str.startsWith("C")||str.startsWith("D")||str.startsWith("B")||str.startsWith("J"))
            result.add(str);
        }

        for(int i=0;i<n;++i){
            if(!isIncluded[i]){
                aux.push(list.get(i));
                isIncluded[i]=true;
                helper(result,aux,isIncluded,list,n);
                isIncluded[i]=false;
                aux.pop();
            }
        }
    }
    static Map<String, List<String>> mapOut = new HashMap<>();
    static int icnt = Integer.MAX_VALUE;
    public static void main1(String[] args) {
    	
    	PriorityQueue pq;
    	List<List<String>> list = new ArrayList<>();
    	int cnt = 1;
    	for(List<String> index:indexmap.values()) {
    		 List<String> result = permute(index);
    		 cnt*=result.size();
    		 System.out.println(result);
    		 list.add(result);
    		
    	}
    	List<List<String>> out = new ArrayList<>();
    	out.add(new ArrayList<>());
    	for(List<String> l1:list) {
    		out=permuteList(out, l1);
    	}
    	System.out.println(cnt);
    	System.out.println(out.size());
    	for(List<String> l1:out) {
    		l1.sort((a, b) -> Integer.compare(b.length(), a.length()));
    		Map<String, List<String>> map = best(l1);
    		if(map.size()<icnt) {
    			icnt = map.size();
    			mapOut = map;
    		}
    	}
    	System.out.println(icnt);
    	decodeMap(mapOut);
    }
    
    private static void decodeMap(Map<String, List<String>> map) {
    	for(Entry<String, List<String>> entry:map.entrySet()) {
    		String key = entry.getKey();
    		List<String> value = entry.getValue();
    		List<String> out = new ArrayList<>();
    		key = decode(key);
    		for(String str: value) {
    			out.add(decode(str));
    		}
    		System.out.println(key+"="+out);
    	}
    }
    
    private static String decode(String key) {
    	//String out = new String();
    	String[] strs=key.split(":");
    	List<String> out = new ArrayList<>();
    	for(String str:strs) {
    		out.add(mapper.get(str));
    	}
    	return String.join("#", out);
    }
    
    public static List<List<String>> permuteList(List<List<String>> list1, List<String> list2) {
    	List<List<String>> out = new ArrayList<>();
    	for(int i=0; i<list1.size();i++) {
    		
        	for(int j=0; j<list2.size();j++) {
        		List<String> tmp = new ArrayList<>(list1.get(i));
        		tmp.add(list2.get(j));
        		out.add(tmp);
        	}
    	}
    	return out;
    }
	public static void main(String[] args) {
		String searchString = "<p class=\"tx-medium mg-b-2\">";
		String str = "<p class=\"tx-medium mg-b-2\">Monal Daxini</p>";
		if(str.contains(searchString)) {
			int fi = str.indexOf(">");
			int li = str.lastIndexOf("<");
			String tt = str.substring(fi+1, li);
			str = str.substring(searchString.length(), str.length() - 4);
			System.out.println("");
		}
		List<String> list = new ArrayList<>();
		list.add("a:b:c:d");
		list.add("b:c:d");
		list.add("a:b:c:f");
		list.add("a:b:c:d:e:f");
		list.sort((a, b) -> Integer.compare(b.length(), a.length()));
		Iterator<String> itr = list.iterator();
		String key = itr.next();
		System.out.println(key);
		while (itr.hasNext()) {
			String val = itr.next();
			System.out.println(val);
		}
		best(list);
	}

	private static Map<String, List<String>> best(List<String> list) {
		Map<String, List<String>> map = new HashMap<>();
		while (list.size() > 0) {
			Iterator<String> itr = list.iterator();
			String key = itr.next();

			map.putIfAbsent(key, new ArrayList<>());
			while (itr.hasNext()) {
				String val = itr.next();
				if (key.startsWith(val)) {
					map.get(key).add(val);
				}
			}
			list.remove(key);
			for (String val : map.get(key)) {
				list.remove(val);
			}
		}
		System.out.println(map);
		return map;
	}

}
