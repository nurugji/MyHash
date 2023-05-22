package com.myhash.object;

import java.util.ArrayList;
import java.util.Set;

import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class Filter {
	
	public static String createRegularExpression(Set<String> selectTagtoSort) {
		StringBuilder sb = new StringBuilder();
		ArrayList<String> temp = Sort.tagSort(selectTagtoSort);
		for(String tagName : temp) {
			sb.append(".*" + tagName + ".*");
		}
		return sb.toString();
	}
	
    public static int tableFilter(String query, JTable mainTable) {
    	TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>((DefaultTableModel) mainTable.getModel());
    	mainTable.setRowSorter(tr);
    	
    	tr.setRowFilter(RowFilter.regexFilter(query));
    	return mainTable.getRowCount();
    }
    
    public static void sortTable(JTable table) {
 		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>((DefaultTableModel) table.getModel());
 		sorter.setComparator(0, new Sort.TitleCmp());
 		table.setRowSorter(sorter); 
     }
}
