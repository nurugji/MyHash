package com.myhash.object;
import java.util.ArrayList;

public class Bookcase {
	
	private ArrayList<Workbook> workbookList;
	
	public Bookcase() {
		this.workbookList = new ArrayList<Workbook>();
	}
	public ArrayList<Workbook> getWorkbookList() {
		return this.workbookList;
	}
	public void addWorkbook(Workbook workbook) {
		this.workbookList.add(workbook);
	}
	public void removeWorkbook(Workbook workbook) {
		this.workbookList.remove(workbook);
	}
}