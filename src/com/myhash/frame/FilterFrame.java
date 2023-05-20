package com.myhash.frame;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.myhash.object.Filter;
import com.myhash.object.Tag;


public class FilterFrame extends JFrame{
	Set<String> selectTagtoSort = new HashSet<String>();
	
	public FilterFrame(Map<String, Tag> tagList, JTable mainTable) {
		setTitle("Select tag to filtering");
		setLayout(new BorderLayout());
		setSize(500, 500);
		
		JButton save = new JButton("save");
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Filter.tableFilter(Filter.createRegularExpression(selectTagtoSort), mainTable);
			}
		});
		
		JPanel selectPanel = new JPanel();
        for(String tagName : tagList.keySet()) {
        	JCheckBox checkbox = new JCheckBox(tagName);
    		System.out.println(tagName);
        	checkbox.addItemListener(new ItemListener(){
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(checkbox.isSelected()) {
						selectTagtoSort.add(checkbox.getText());
					}else
						selectTagtoSort.remove(checkbox.getText());					
				}
        		
        	});
        	selectPanel.add(checkbox);
        }
        
        add(selectPanel, BorderLayout.CENTER);
        add(save, BorderLayout.NORTH);
	}
}
