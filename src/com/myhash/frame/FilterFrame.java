package com.myhash.frame;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.myhash.object.Filter;
import com.myhash.object.Tag;

public class FilterFrame extends JFrame{
	private JButton saveBtn;
	private JPanel selectP;
	private Set<String> selectTagtoSort = new HashSet<String>();
	
	public FilterFrame(Map<String, Tag> tagList, JTable mainTable, JTextField numTf) {
		setTitle("Select tag to filtering");
		setLayout(new BorderLayout());
		setSize(500, 500);
		
		saveBtn = new JButton("save");
		saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Integer problemNum = Filter.tableFilter(Filter.createRegularExpression(selectTagtoSort), mainTable);
				numTf.setText(problemNum.toString());
			}
		});
		
		selectP = new JPanel();
        for(String tagName : tagList.keySet()) {
        	JCheckBox checkbox = new JCheckBox(tagName);
        	checkbox.addItemListener(new ItemListener(){
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(checkbox.isSelected()) {
						selectTagtoSort.add(checkbox.getText());
					}else
						selectTagtoSort.remove(checkbox.getText());					
				}
        		
        	});
        	selectP.add(checkbox);
        }
        
        add(selectP, BorderLayout.CENTER);
        add(saveBtn, BorderLayout.NORTH);
	}
}
