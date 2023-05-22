package com.myhash.object;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;

public class Sort {
	public static class TagCmp implements Comparator<String>{
		@Override
		public int compare(String s1, String s2) {
			return s1.compareTo(s2);
		}
	}
	
	public static class TitleCmp implements Comparator<String>{
			public int compare(String s1, String s2) {
			return Integer.parseInt(s1) - Integer.parseInt(s2);
		}
	}
	
	
	public static ArrayList<String> tagSort(Set tagList){
		ArrayList<String> temp = new ArrayList<String>(tagList);
		temp.sort(new TagCmp());
		return temp;
	}
}


