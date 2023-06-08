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
	private JPanel fileLoadP, tagP, topP, bottomP, leftP, rightP, centerP;
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

        fileLoadP = new JPanel();
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
        tagP = new JPanel();
        saveBtn = new JButton("save problem");

        initTag(workBook);
        
        fileLoadP.add(filelocLb);
        fileLoadP.add(loadImgBtn);
        fileLoadP.add(filelocTf);
        
        topP = new JPanel(new GridLayout(3, 2));
        topP.setPreferredSize(new Dimension(400, 400));
        bottomP = new JPanel(new BorderLayout());
        bottomP.setPreferredSize(new Dimension(400, 400));
        rightP = new JPanel();
        leftP = new JPanel();
      
        topP.add(titleLb);
        topP.add(titleTf);
        topP.add(solveLb);
        topP.add(solveTf);
        topP.add(memoLb);
        topP.add(memoTf);
        bottomP.add(tagLb, BorderLayout.NORTH);
        bottomP.add(tagP, BorderLayout.CENTER);
        
        leftP.add(topP);
        leftP.add(bottomP);

        rightP.add(fileLoadP);
        rightP.add(imgIcon);
        
        centerP = new JPanel(new GridLayout(0,2));
        centerP.add(leftP);
        centerP.add(rightP);
        

        add(centerP, BorderLayout.CENTER);
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
        	tagP.add(box);
        }
    }
}

