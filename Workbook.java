import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class Workbook {
	
	public Map<String, Tag> tagList;
	public ArrayList<Problem> problemList;
	String name;
	 
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
	public void removeProblem(Problem prevProblem) {
		this.problemList.remove(prevProblem);
	}
}
