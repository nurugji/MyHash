package com.myhash.frame;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import com.myhash.object.Filter;
import com.myhash.object.Problem;
import com.myhash.object.Tag;
import com.myhash.object.Workbook;

//add edit button
class TagFrame extends JFrame {
	JPanel inputP;
	JLabel label;
	JTextField nameTf;
	JButton addBtn, editBtn, deleteBtn;
	JTable tagTable;
    DefaultTableModel tagTableModel;

    public TagFrame(Workbook workBook, JTable mainTable) {
    	setSize(500, 500);
        setLayout(new BorderLayout());
        
        initTable(workBook.getTagList());

        inputP = new JPanel();
        label = new JLabel("Tag name : ");
        nameTf = new JTextField(10);
        addBtn = new JButton("ADD");
        editBtn = new JButton("EDIT");
        deleteBtn = new JButton("DELETE");
        
        inputP.add(label);
        inputP.add(nameTf);
        inputP.add(addBtn);
        inputP.add(editBtn);
        inputP.add(deleteBtn);
        
        addBtn.addActionListener(new ActionListener() {
            
        	@Override
            public void actionPerformed(ActionEvent e) {
                String tagName = nameTf.getText();
                if(tagName.equals("")) {
                	JOptionPane.showMessageDialog(null, "Empty names are not allowed", "CREATE ERROR", JOptionPane.ERROR_MESSAGE);
                }else if(workBook.getTagList().containsKey(tagName)){
                	JOptionPane.showMessageDialog(null, "Duplicate names are not allowed", "CREATE ERROR", JOptionPane.ERROR_MESSAGE);
                }else if(tagName.contains(" ")){
                	JOptionPane.showMessageDialog(null, "Cannot contain spaces", "CREATE ERROR", JOptionPane.ERROR_MESSAGE);
                	nameTf.setText("");
                }else {
                	Tag newTag = new Tag(tagName);
                	workBook.getTagList().put(newTag.getName(), newTag);
                    tagTableModel.addRow(new Object[]{newTag.getName(), newTag.getNum()});
                    nameTf.setText("");
                }
            }
        });
        
        editBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				 int selectedRow = tagTable.convertRowIndexToModel(tagTable.getSelectedRow());
	                if (selectedRow != -1) {
	                	String value = tagTable.getModel().getValueAt(selectedRow, 0).toString();
	                	Tag selectedTag = workBook.getTagList().get(value);
	            		String result = JOptionPane.showInputDialog(null, "Input new tag name", value);
	            		
	            		if(result.equals("")) {
	                    	JOptionPane.showMessageDialog(null, "Empty names are not allowed", "CREATE ERROR", JOptionPane.ERROR_MESSAGE);
	                    }else if(workBook.getTagList().containsKey(result)){
	                    	JOptionPane.showMessageDialog(null, "Duplicate names are not allowed", "CREATE ERROR", JOptionPane.ERROR_MESSAGE);
	                    }else if(result.contains(" ")){
	                    	JOptionPane.showMessageDialog(null, "Cannot contain spaces", "CREATE ERROR", JOptionPane.ERROR_MESSAGE);
	                    	nameTf.setText("");
	                    }else {
	                    	Tag newTag = new Tag(result);
	                    	newTag.setNum(selectedTag.getNum());
	            			for(int i = 0; i < workBook.getProblemList().size(); i++) {
	            				//edit to tag in problem, edit main table
	            				Problem problem = workBook.getProblemList().get(i);
	                			if(problem.deleteTag(value)) {
	                				problem.addTag(result);
	                				mainTable.getModel().setValueAt(workBook.getProblemList().get(i).getTagtoString(), i, 1);
	                			}
	                		}

	                    	workBook.getTagList().put(newTag.getName(), newTag);
	                        tagTableModel.addRow(new Object[]{newTag.getName(), newTag.getNum()});
	            			
	                        workBook.getTagList().remove(value);
	                        tagTableModel.removeRow(selectedRow);
	            		}
	                }else {
	                	JOptionPane.showMessageDialog(null, "Please select a row to edit", "DELETE ERROR", JOptionPane.ERROR_MESSAGE);
	                }
			}
        	
        });

        deleteBtn.addActionListener(new ActionListener() {
            
        	@Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tagTable.convertRowIndexToModel(tagTable.getSelectedRow());
                if (selectedRow != -1) {
                	String value = tagTable.getModel().getValueAt(selectedRow, 0).toString();
                	int problemNum = workBook.getTagList().get(value).getNum();
            		int result = JOptionPane.showConfirmDialog(null, "There are " + problemNum + " problems with this tag are you sure to delete it?", "CONFIRM", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);;
            		if(result == 0) {
            			for(int i = 0; i < workBook.getProblemList().size(); i++) {
            				//delete to tag in problem, edit main table
                			if(workBook.getProblemList().get(i).deleteTag(value)) {
                				mainTable.getModel().setValueAt(workBook.getProblemList().get(i).getTagtoString(), i, 1);
                			}
                		}
            			workBook.getTagList().remove(value);
                        tagTableModel.removeRow(selectedRow);
            		}
                }else {
                	JOptionPane.showMessageDialog(null, "Please select a row to delete", "DELETE ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(inputP, BorderLayout.NORTH);
        add(new JScrollPane(tagTable), BorderLayout.CENTER);
    }
    public void initTable(Map<String, Tag> tagList) {
        tagTableModel = new DefaultTableModel(new Object[]{"tag name", "problem num"}, 0);
        tagTable = new JTable(tagTableModel);
        
        if(!tagList.isEmpty()) {
        	for(String tagName : tagList.keySet()) {
        		tagTableModel.addRow(new Object[] {tagName, tagList.get(tagName).getNum()});
        	}
        }
        Filter.sortTagTable(tagTable);
    }
}
