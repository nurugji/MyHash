package com.myhash.object;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Workbook {
	
	private Map<String, Tag> tagList;
	private ArrayList<Problem> problemList;
	String name;
	
	public Workbook(String name) {
		this.tagList = new HashMap<String, Tag>();
		this.problemList = new ArrayList<Problem>();
		this.name = name;
	}
	 
	public Map<String, Tag> getTagList() {
		return tagList;
	}
	public void setTagList(Map<String, Tag> tagList) {
		this.tagList = tagList;
	}
	public void addTag(Tag newTag) {
		this.tagList.put(newTag.getName(), newTag);
	}
	public void removeTag(Tag prvTag) {
		this.tagList.remove(prvTag.getName());
	}
	public ArrayList<Problem> getProblemList() {
		return problemList;
	}
	public void setProblemList(ArrayList<Problem> problems) {
		this.problemList = problems;
	}
	public void addProblem(Problem newProblem) {
		this.problemList.add(newProblem);
	}
	public void removeProblem(int selectedRow) {
		this.problemList.remove(selectedRow);
	}
	public String getName() {
		return this.name;
	}
	public int getProblemNum() {
		return this.problemList.size();
	}
	public int getTagNum() {
		return this.tagList.size();
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getName()+",");
		sb.append(getProblemNum()+",");
		sb.append(getTagNum()+",");
		return sb.toString();
	}
}
