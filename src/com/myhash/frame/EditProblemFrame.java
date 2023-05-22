package com.myhash.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.myhash.object.ImageHelper;
import com.myhash.object.Problem;
import com.myhash.object.Sort;
import com.myhash.object.UserFolder;
import com.myhash.object.Workbook;

class EditProblemFrame extends JFrame {
	private JPanel fileLoadPanel, tagPanel;
	private JLabel titleLb, filelocLb, imgIcon, solveLb, memoLb, tagLb;
	private JButton loadImgBtn, saveBtn;
    private JTextField titleTf, filelocTf, solveTf, memoTf;
    
    private Set<String> checkList = new HashSet<String>();
    
    int selectedRow;
    Problem selectedProblem;

    public EditProblemFrame(Workbook workBook, JTable mainTable){
    	
    	setSize(1000, 1000);
    	setLayout(new BorderLayout());
    	
    	selectedRow = mainTable.convertRowIndexToModel(mainTable.getSelectedRow());
    	selectedProblem = workBook.getProblemList().get(selectedRow);
    	
    	if(!selectedProblem.getTag().isEmpty()) {
        	for(String str : selectedProblem.getTag()) {
        		checkList.add(str);
        	}
    	}
    	
    	
        titleLb = new JLabel("Title:");
        titleTf = new JTextField(selectedProblem.getTitle());

        fileLoadPanel = new JPanel();
        filelocLb = new JLabel("File location:");
        loadImgBtn = new JButton("Load image");
        filelocTf = new JTextField(selectedProblem.getFileloc());
        filelocTf.setEditable(false);
        imgIcon = new JLabel();
        initImg(mainTable);

        solveLb = new JLabel("Solve:");
        solveTf = new JTextField(selectedProblem.getSolve());
        memoLb = new JLabel("Memo:");
        memoTf = new JTextField(selectedProblem.getMemo());
        tagLb = new JLabel("Tag:");
        tagPanel = new JPanel();
        saveBtn = new JButton("save problem");

        initTag(workBook);
        
        fileLoadPanel.add(filelocLb);
        fileLoadPanel.add(loadImgBtn);
        fileLoadPanel.add(filelocTf);
        
        JPanel top = new JPanel(new GridLayout(3, 2));
        top.setPreferredSize(new Dimension(400, 400));
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setPreferredSize(new Dimension(400, 400));
        JPanel right = new JPanel();
        
        JPanel left = new JPanel();
        
        top.add(titleLb);
        top.add(titleTf);
        top.add(solveLb);
        top.add(solveTf);
        top.add(memoLb);
        top.add(memoTf);
        bottom.add(tagLb, BorderLayout.NORTH);
        bottom.add(tagPanel, BorderLayout.CENTER);
        
        left.add(top);
        left.add(bottom);

        right.add(fileLoadPanel);
        right.add(imgIcon);
        
        JPanel center = new JPanel(new GridLayout(0,2));
        center.add(left);
        center.add(right);
        

        add(center, BorderLayout.CENTER);
        add(saveBtn, BorderLayout.NORTH);
        
        loadImgBtn.addActionListener(new ActionListener() {
     	   public void actionPerformed(ActionEvent e) {
     	        String selectedFile = UserFolder.loadImg();
     	        if(selectedFile != null) {
     	        	ImageIcon icon = ImageHelper.makeImgIcon(selectedFile, 600);
     	        	imgIcon.setIcon(icon);
     	        	filelocTf.setText(selectedFile);
     	        }
     	    }
        });
        
        saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//check empty field
				if(titleTf.getText().equals("") || filelocTf.getText().equals(""))
					JOptionPane.showMessageDialog(null, "Empty title or image file are not allowed", "CREATE ERROR", JOptionPane.INFORMATION_MESSAGE);
				else {
					//edit problem to workbook
					selectedProblem.setTitle(titleTf.getText());
					selectedProblem.setFileloc(filelocTf.getText());
					selectedProblem.setSolve(solveTf.getText());
					selectedProblem.setMemo(memoTf.getText());
			    	selectedProblem.setTag(checkList);
		    	
			    	//edit problem to table
			    	mainTable.getModel().setValueAt(selectedProblem.getTitle(), selectedRow, 0);
			    	mainTable.getModel().setValueAt(selectedProblem.getTagtoString(), selectedRow, 1);
			    	mainTable.getModel().setValueAt(selectedProblem.getFileloc(), selectedRow, 3);
			    	
					JOptionPane.showMessageDialog(null, "edited successfully", "Sucess", JOptionPane.INFORMATION_MESSAGE);
				}
			}
        });
        

    }
    public void initImg(JTable mainTable) {
    	ImageIcon icon = null;
    	try {
	       icon = ImageHelper.makeImgIcon(selectedProblem.getFileloc(), 500);
    	}catch(NullPointerException e) {
        	filelocTf.setText("");
            selectedProblem.setFileloc("");
            mainTable.getModel().setValueAt(selectedProblem.getFileloc(), selectedRow, 3);
    	}
        imgIcon.setIcon(icon);
    }
    
    public void initTag(Workbook workBook) {
    	ArrayList<String> temp = Sort.tagSort(workBook.getTagList().keySet());
    	
        for(String tagName : temp) {
        	JCheckBox box = new JCheckBox(tagName);
        	box.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange() == ItemEvent.SELECTED) {
						JCheckBox checkbox = (JCheckBox) e.getSource();
						if(!checkList.contains(checkbox.getText())) {
							checkList.add(checkbox.getText());
							workBook.getTagList().get(checkbox.getText()).increaseN();
						}
					    
					}
					else if((e.getStateChange() == ItemEvent.DESELECTED)) {
						JCheckBox checkbox = (JCheckBox) e.getSource();
						checkList.remove(checkbox.getText());
						workBook.getTagList().get(checkbox.getText()).decreaseN();
					}					
					
				}
        		
        	});
        	if(checkList.contains(tagName)) {
        		box.setSelected(true);
        	}
        	tagPanel.add(box);
        }
    }
}

