import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

class Problem {
    private String title;
    private String fileloc;
    private String solve;
    private String memo;
    private Set<String> tag;
    
    public Problem() {}
    
	public Problem(String title, String fileloc, String solve, String memo, Set<String> tag) {
		super();
		this.title = title;
		this.fileloc = fileloc;
		this.solve = solve;
		this.memo = memo;
		this.tag = tag;
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
}
