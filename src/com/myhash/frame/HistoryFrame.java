package com.myhash.frame;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.myhash.object.History;
import com.myhash.object.Problem;
import com.myhash.object.Workbook;

//add Maximum historyNum = 5
public class HistoryFrame extends JFrame{
	private JPanel inputPanel;
	private JButton addBtn, deleteBtn;
	private JRadioButton correctBtn, incorrectBtn;
	private JTextField memoTf;
	private ButtonGroup btnGroup;
	
	private DefaultTableModel historyModel;
	private JTable historyTable;
	private JScrollPane tablePane;
	private int selectedRow;
	private Problem selectedProblem;
	
	public HistoryFrame(Workbook workBook, JTable mainTable) {
		setSize(500, 500);
		setLayout(new BorderLayout());
		
		selectedRow = mainTable.convertRowIndexToModel(mainTable.getSelectedRow());
		selectedProblem = workBook.getProblemList().get(selectedRow);
		
		inputPanel = new JPanel();
		memoTf = new JTextField(10);
		correctBtn = new JRadioButton("correct");
		correctBtn.setSelected(true);
		incorrectBtn = new JRadioButton("incorrect");
		addBtn = new JButton("add");
		deleteBtn = new JButton("delete");
		btnGroup = new ButtonGroup();
		btnGroup.add(correctBtn);
		btnGroup.add(incorrectBtn);
		
		initProblemHistoryTable(selectedProblem);
		
		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//add history to problem object
		        boolean correct = correctBtn.isSelected() ? true : false;
		        History newHistory = new History(correct, memoTf.getText());
		        selectedProblem.addHistory(newHistory);
		        System.out.println(newHistory);
		        
		        //add history to table
		        Object[] row = {newHistory.getCorrecttoString(), newHistory.getDate().toString(), newHistory.getMemo()};
		        historyModel.addRow(row);
		        mainTable.getModel().setValueAt(selectedProblem.getPercent(), selectedRow, 2);
			}
		});
		
		deleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRowHistory = historyTable.getSelectedRow();
				if(selectedRowHistory != -1) {
					//remove history to problem object
					selectedProblem.removeHistory(selectedRowHistory);
					
					//remove history to table
					historyModel.removeRow(selectedRowHistory);
					mainTable.getModel().setValueAt(selectedProblem.getPercent(), selectedRow, 2);
				}
				
			}
			
		});
		
		inputPanel.add(memoTf);
		inputPanel.add(correctBtn);
		inputPanel.add(incorrectBtn);
		inputPanel.add(addBtn);
		inputPanel.add(deleteBtn);
		add(inputPanel, BorderLayout.NORTH);
		add(tablePane, BorderLayout.CENTER);
	}
	
	private void initProblemHistoryTable(Problem problem) {
		historyModel = new DefaultTableModel(new Object[] {"record", "date", "memo"}, 0);
		historyTable = new JTable(historyModel);
		tablePane = new JScrollPane(historyTable);
		
		for(History history : problem.getHistory()) {
	        Object[] row = {history.getCorrecttoString(), history.getDate(), history.getMemo()};
	        historyModel.addRow(row);
		}
	}
}
