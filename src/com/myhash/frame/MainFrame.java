package com.myhash.frame;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.myhash.object.Database;
import com.myhash.object.Filter;
import com.myhash.object.Problem;
import com.myhash.object.Workbook;

//delete confirm message


//fix comparator

//add workbook table update too
class MainFrame extends JFrame{
	
	public DefaultTableModel mainModel;
	public JTable mainTable;
	private JPanel managementPanel, toolPanel;
	private JButton addBtn, editBtn, deleteBtn, initBtn,  tagBtn, detailBtn, filterBtn, saveBtn;
	JTextField searchTf;
	JScrollPane tablePane;
	Workbook selectedWorkbook;
	
	int selectedRow;
	
    public MainFrame(ArrayList<Workbook> workbookList, JTable workbookTable, JFrame bookcase) {
    	selectedRow = workbookTable.getSelectedRow();
    	selectedWorkbook = workbookList.get(selectedRow);
    	
    	JFrame frame =  new JFrame("Problem list");
    	
    	frame.setSize(1000, 1000);
    	frame.setLayout(new BorderLayout());
    	frame.setVisible(true);
    	
        initTable();
        
        managementPanel = new JPanel();
        toolPanel = new JPanel();
        
        addBtn = new JButton("Add Problem");
        editBtn = new JButton("Edit Problem");
        deleteBtn = new JButton("Delete Problem");
        searchTf = new JTextField(20);
        initBtn = new JButton("Init");
       
        tagBtn = new JButton("Tag management");
        detailBtn = new JButton("Deatil");
        filterBtn = new JButton("Filter");
        saveBtn = new JButton("Save");
        
        managementPanel.add(addBtn);
        managementPanel.add(editBtn);
        managementPanel.add(deleteBtn);
        managementPanel.add(detailBtn);
        managementPanel.add(tagBtn);
        
        toolPanel.add(searchTf);
        toolPanel.add(initBtn);
        toolPanel.add(filterBtn);
        toolPanel.add(saveBtn);

        frame.add(toolPanel, BorderLayout.NORTH);
        frame.add(tablePane, BorderLayout.CENTER);
        frame.add(managementPanel, BorderLayout.SOUTH);
        
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	JFrame addProblemFrame = new AddProblemFrame(selectedWorkbook, mainTable);
            	addProblemFrame.setVisible(true);
            }
        });
        
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = mainTable.getSelectedRow();
                if (selectedRow != -1) {
                	JFrame editProblemFrame = new EditProblemFrame(selectedWorkbook, mainTable);
                	editProblemFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to edit.");
                }
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//add confirm message 
                int selectedRow = mainTable.convertRowIndexToModel(mainTable.getSelectedRow());
                Problem selectedProblem = selectedWorkbook.getProblemList().get(selectedRow);
                if (selectedRow != -1) {
        			int result = JOptionPane.showConfirmDialog(null, "Are you sure to delete " + selectedProblem.getTitle(), "ERROR", JOptionPane.ERROR_MESSAGE);
        			if(result == 0) {
                    	selectedWorkbook.removeProblem(selectedRow);
                        mainModel.removeRow(selectedRow);
        			}
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.");
                }
            }
        });

        tagBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame tagFrame = new TagFrame(selectedWorkbook, mainTable);
				tagFrame.setVisible(true);
			}
        });
        
        detailBtn.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = mainTable.getSelectedRow();
				if(selectedRow != -1) {
					JFrame detailFrame = new DatailFrame(selectedWorkbook, mainTable);
					detailFrame.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(null, "Please select a row to see detail.");
				}
			}
        });

        filterBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame filterFrame = new FilterFrame(selectedWorkbook.getTagList(), mainTable);
				filterFrame.setVisible(true);
			}
        });
        
        saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Database.saveProblemList(selectedWorkbook.getProblemList(), selectedWorkbook.getName());
				Database.saveTagList(selectedWorkbook.getTagList(), selectedWorkbook.getName());
			}
        });
        
        initBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Filter.tableFilter("", mainTable);
			}
        	
        });
        
        searchTf.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				Filter.tableFilter(searchTf.getText(), mainTable);
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				Filter.tableFilter(searchTf.getText(), mainTable);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				Filter.tableFilter(searchTf.getText(), mainTable);
			}
        });
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	int result = JOptionPane.showConfirmDialog(null, "Do you want to save your changes", "ERROR", JOptionPane.ERROR_MESSAGE);
            	if(result == 0) {
                	saveBtn.doClick();
                	workbookTable.getModel().setValueAt(selectedWorkbook.getProblemNum(), selectedRow, 1);
                	workbookTable.getModel().setValueAt(selectedWorkbook.getTagNum(), selectedRow, 2);
            	}
            	bookcase.setVisible(true);
            }
        });

    }
    
    public void show() {
    	for(Problem problem : selectedWorkbook.getProblemList()) {
    		System.out.printf("title : %s, tag : %s, tileloc : %s, solve : %s, memo : %s history %s\n", problem.getTitle(), problem.getTag(), problem.getFileloc(), problem.getSolve(), problem.getMemo(), problem.getHistory().toString());
    	}
    	for(String tagName : selectedWorkbook.getTagList().keySet()) {
    		System.out.printf("tag : %s / %d\n", tagName, selectedWorkbook.getTagList().get(tagName).getNum());
    	}
    }
    
    public void initTable() {
    	String[] columnNames = {"Title", "tag", "correct rate", "File Location"};
        mainModel = new DefaultTableModel(columnNames, 0){
        	@Override
            public boolean isCellEditable(int row, int column){
             return false;
            }
        };
        mainTable = new JTable(mainModel);
        tablePane = new JScrollPane(mainTable);
       
		for(Problem problem : selectedWorkbook.getProblemList()) {
			Object[] row = {problem.getTitle(), problem.getTagtoString(), problem.getPercent(), problem.getFileloc()};
			mainModel.addRow(row);
		}
        Filter.sortTable(mainTable);
    }
}










           
            
