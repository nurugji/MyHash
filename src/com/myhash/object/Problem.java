package com.myhash.object;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Problem {
	final static SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM/dd");
	
    private String title;
    private String fileloc;
    private String solve;
    private String memo;
    private Set<String> tag;
    private ArrayList<History> historyList;
    
    public Problem() {}
    
	public Problem(String title, String fileloc, String solve, String memo, Set<String> tag) {
		super();
		this.title = title;
		this.fileloc = fileloc;
		this.solve = solve;
		this.memo = memo;
		this.tag = tag;
		this.historyList = new ArrayList<History>();
	}
	
	public Problem(String title, String fileloc, String solve, String memo, Set<String> tag, ArrayList<History> history) {
		super();
		this.title = title;
		this.fileloc = fileloc;
		this.solve = solve;
		this.memo = memo;
		this.tag = tag;
		this.historyList = history;
	}
	
	public String getTagtoString() {
		ArrayList<String> temp = new ArrayList<String>(tag);
		temp.sort(new StrCmp());
		StringBuilder sb = new StringBuilder();
		for(String tagName : temp) {
			sb.append(tagName);
			sb.append(" ");
		}
		return sb.toString();
	}
	
	public String getHistorytoString() {
		StringBuilder sb = new StringBuilder();

		for(History history : historyList) {
			sb.append(history.toString() + "#");
		}

		return sb.toString();
	}
	
	public void setTag(Set<String> checkList) {
		this.tag = checkList;
	}
	
	public static Set<String> stringtoTag(String checkList) {
		Set<String> tagList = new HashSet<String>();
		String[] temp = checkList.split(" ");
		for(String tagName : temp) {
			tagList.add(tagName);
		}
		return tagList;
	}
	public static ArrayList<History> stringtoHistory(String record){
		if(record.equals("")) return new ArrayList<History>();
		ArrayList<History> historyList = new ArrayList<History>();
		String[] sp = record.split("#");
		for(String element : sp) {
			History history = null;
			try {
				String[] temp = element.split(" ");
				Boolean correct = Boolean.valueOf(temp[0].replace("\'", ""));
				Date date = simpleDate.parse(temp[1].replace("\'", ""));
				String memo = temp[2].replace("\'", "");
				history = new History(correct, date, memo);
			}catch(ParseException e) {
				
			}
			historyList.add(history);
		}
		return historyList;
	}

	public Set<String> getTag() {
		return this.tag;
	}
	
	public boolean deleteTag(String value) {
		return this.tag.remove(value);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getFileloc() {
		return fileloc;
	}

	public void setFileloc(String fileloc) {
		this.fileloc = fileloc;
	}

	public String getSolve() {
		return solve;
	}

	public void setSolve(String solve) {
		this.solve = solve;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public ArrayList<History> getHistory() {
		return historyList;
	}

	public void addHistory(History newHistory) {
		this.historyList.add(newHistory);
	}
	
	public void removeHistory(int index) {
		this.historyList.remove(index);
	}
	
	public String getPercent() {
		float result = 0;
		for(History history : getHistory()) {
			if(history.getCorrect()) result++;
		}
		
		return result == 0 ? "0%" : Math.round(result / getHistory().size() * 100) + "%";
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\"" + getTitle() + "\",");
		sb.append("\"" + getFileloc() + "\",");
		sb.append("\"" + getSolve() + "\",");
		sb.append("\"" + getMemo() + "\",");
		sb.append("\"" + getTagtoString() + "\",");
		sb.append("\"" + getHistorytoString() + "\",");
		System.out.println("history : " + getHistorytoString());
		return sb.toString();
	}
}
	

