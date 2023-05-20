package com.myhash.object;

import java.util.Comparator;

public class StrCmp implements Comparator<String>{

	public int compare(String o1, String o2) {
		return o1.compareTo(o2);
	}
}
