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
	JPanel inputPanel;
	JLabel label;
	JTextField textField;
	JButton addButton, editButton, deleteButton;
	JTable tagTable;
    DefaultTableModel tagTableModel;

    public TagFrame(Workbook workBook, JTable mainTable) {
    	setSize(500, 500);
        setLayout(new BorderLayout());
        
        initTable(workBook.getTagList());

        inputPanel = new JPanel();
        label = new JLabel("Tag name : ");
        textField = new JTextField(10);
        addButton = new JButton("ADD");
        editButton = new JButton("EDIT");
        deleteButton = new JButton("DELETE");
        
        inputPanel.add(label);
        inputPanel.add(textField);
        inputPanel.add(addButton);
        inputPanel.add(editButton);
        inputPanel.add(deleteButton);
        
        addButton.addActionListener(new ActionListener() {
            
        	@Override
            public void actionPerformed(ActionEvent e) {
                String tagName = textField.getText();
                if(tagName.equals("")) {
                	JOptionPane.showMessageDialog(null, "Empty names are not allowed", "CREATE ERROR", JOptionPane.ERROR_MESSAGE);
                }else if(workBook.getTagList().containsKey(tagName)){
                	JOptionPane.showMessageDialog(null, "Duplicate names are not allowed", "CREATE ERROR", JOptionPane.ERROR_MESSAGE);
                }else if(tagName.contains(" ")){
                	JOptionPane.showMessageDialog(null, "Cannot contain spaces", "CREATE ERROR", JOptionPane.ERROR_MESSAGE);
                	textField.setText("");
                }else {
                	Tag newTag = new Tag(tagName);
                	workBook.getTagList().put(newTag.getName(), newTag);
                    tagTableModel.addRow(new Object[]{newTag.getName(), newTag.getNum()});
                    textField.setText("");
                }
            }
        });
        
        editButton.addActionListener(new ActionListener() {

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
	                    	textField.setText("");
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

        deleteButton.addActionListener(new ActionListener() {
            
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

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(tagTable), BorderLayout.CENTER);
    }
    public void initTable(Map<String, Tag> tagList) {
        tagTableModel = new DefaultTableModel(new Object[]{"태그 이름", "문제 개수"}, 0);
        tagTable = new JTable(tagTableModel);
        
        if(!tagList.isEmpty()) {
        	for(String tagName : tagList.keySet()) {
        		tagTableModel.addRow(new Object[] {tagName, tagList.get(tagName).getNum()});
        	}
        }
        Filter.sortTable(tagTable);
    }
}
