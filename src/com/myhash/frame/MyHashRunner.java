package com.myhash.frame;

import java.util.ArrayList;

import javax.swing.JFrame;

import com.myhash.object.Bookcase;
import com.myhash.object.Database;
import com.myhash.object.Workbook;

public class MyHashRunner {
	public static void main(String[] args) {
		Bookcase bookcase = new Bookcase();
		ArrayList<Workbook> workbookList = Database.loadWorkbookList();
		for(Workbook workbook : workbookList) {
			bookcase.addWorkbook(workbook);
		}
		JFrame frame = new WorkbookFrame(bookcase);
	}
}
