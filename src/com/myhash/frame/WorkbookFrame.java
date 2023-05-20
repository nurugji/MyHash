package com.myhash.frame;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.myhash.object.Bookcase;
import com.myhash.object.Database;
import com.myhash.object.Problem;
import com.myhash.object.Tag;
import com.myhash.object.Workbook;

class WorkbookFrame extends JFrame{
	JPanel inputPanel;
	JLabel label;
	JTextField textField;
	JButton addButton, deleteButton, loadBtn, saveBtn;
	JTable workbookTable;
    DefaultTableModel workbookModel;
    Bookcase bookcase;

    public WorkbookFrame(Bookcase bookcase) {
    	JFrame frame = new JFrame("Problem Manager");
    	frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());
        
        
        setSize(800, 500);
        setLayout(new BorderLayout());
        this.bookcase = bookcase;
        
        initTable(bookcase.getWorkbookList());

        inputPanel = new JPanel();
        label = new JLabel("workbook name : ");
        textField = new JTextField(10);
        addButton = new JButton("ADD");
        deleteButton = new JButton("DELETE");
        loadBtn = new JButton("load");
        saveBtn = new JButton("save");
        
        inputPanel.add(label);
        inputPanel.add(textField);
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);
        inputPanel.add(loadBtn);
        inputPanel.add(saveBtn);
        saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Database.saveWorkbookList(bookcase.getWorkbookList());
			}
        	
        });
        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String workbookName = textField.getText();
                if(workbookName.equals("")) {
                	JOptionPane.showMessageDialog(null, "Empty names are not allowed", "CREATE ERROR", JOptionPane.ERROR_MESSAGE);
                }else if(bookcase.getWorkbookList().contains(workbookName)){
                	JOptionPane.showMessageDialog(null, "Duplicate names are not allowed", "CREATE ERROR", JOptionPane.ERROR_MESSAGE);
                }else if(workbookName.contains(" ")){
                	JOptionPane.showMessageDialog(null, "Cannot contain spaces", "CREATE ERROR", JOptionPane.ERROR_MESSAGE);
                	textField.setText("");
                }else {
                	//add workbook to bookcase
                	Workbook newWorkbook = new Workbook(workbookName);
                	bookcase.addWorkbook(newWorkbook);
                	
                	//add workbook to database
                	Database.makeFile(workbookName);
                    
                    //add workbook to table
                	workbookModel.addRow(new Object[]{newWorkbook.getName(), String.valueOf(newWorkbook.getProblemNum()), String.valueOf(newWorkbook.getTagNum())});
                    textField.setText("");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = workbookTable.convertRowIndexToModel(workbookTable.getSelectedRow());
                if (selectedRow != -1) {
                	//String value = workbookTable.getModel().getValueAt(selectedRow, 0).toString();
                	Workbook selectedWorkbook = bookcase.getWorkbookList().get(selectedRow);
                	int problemNum = selectedWorkbook.getProblemNum();
                	int tagNum = selectedWorkbook.getTagNum();
                	
            		int result = JOptionPane.showConfirmDialog(null, "There are " + problemNum + " problems and " + tagNum + " tags with this workbook are you sure to delete it?", "CONFIRM", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            		if(result == 0) {
            			//delete workbook to bookcase
            			bookcase.removeWorkbook(selectedWorkbook);
            			
            			//delete workbook to database
            			Database.removeFile(selectedWorkbook.getName());
            			
            			//delete workbook to table
                        workbookModel.removeRow(selectedRow);
            		}
                }else {
                	JOptionPane.showMessageDialog(null, "Please select a row to delete", "DELETE ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loadBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = workbookTable.convertRowIndexToModel(workbookTable.getSelectedRow());
				if (selectedRow != -1) {
					//save bookcase
					Database.saveWorkbookList(bookcase.getWorkbookList());
					
					Workbook workbook = bookcase.getWorkbookList().get(selectedRow);
					new MainFrame(bookcase.getWorkbookList(), workbookTable, frame);
					frame.setVisible(false);
                }else {
                	JOptionPane.showMessageDialog(null, "Please select a row to load", "LOAD ERROR", JOptionPane.ERROR_MESSAGE);
                }
			}
        	
        });
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	int result = JOptionPane.showConfirmDialog(null, "Do you want to save your changes", "ERROR", JOptionPane.ERROR_MESSAGE);
            	if(result == 0) {
            		Database.saveWorkbookList(bookcase.getWorkbookList());
            	}
            }
        });
        
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(workbookTable), BorderLayout.CENTER);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void initTable(ArrayList<Workbook> workbookList) {
        workbookModel = new DefaultTableModel(new Object[]{"workbook name", "problem num", "tag num"}, 0);
        workbookTable = new JTable(workbookModel);
        
        if(!workbookList.isEmpty()) {
        	for(Workbook workbook : workbookList) {
        		ArrayList<Problem> loadProblem = Database.loadProblemList(workbook.getName()+"Problem.txt");
        		Map<String, Tag> loadTag = Database.loadTagList(workbook.getName()+"Tag.txt");
        		workbook.setProblemList(loadProblem);
        		workbook.setTagList(loadTag);
        		workbookModel.addRow(new Object[] {workbook.getName(), String.valueOf(workbook.getProblemNum()), String.valueOf(workbook.getTagNum())});
        	}
        }
    }
}
