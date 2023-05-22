package com.myhash.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.swing.table.DefaultTableModel;

import com.myhash.object.UserClipboard;
import com.myhash.object.ImageHelper;
import com.myhash.object.Problem;
import com.myhash.object.Sort;
import com.myhash.object.UserFolder;
import com.myhash.object.Workbook;

class AddProblemFrame implements ClipboardOwner{
	private JPanel fileLoadPanel, tagPanel;
	private JLabel titleLb, filelocLb, imgIcon, solveLb, memoLb, tagLb;
	private JButton loadImgBtn, addBtn;
    private JTextField titleTf, filelocTf, solveTf, memoTf;
    
    private Set<String> checkList = new HashSet<String>();
    
    public AddProblemFrame(Workbook workBook, JTable mainTable) {
    	
    	JFrame frame = new JFrame("Add problem view");
    	
    	frame.setSize(1000, 800);
        frame.setLayout(new BorderLayout());
        
        //title
        titleLb = new JLabel("Title:");
        titleTf = new JTextField(10);

        //File load
        fileLoadPanel = new JPanel();
        filelocLb = new JLabel("File location:");
        loadImgBtn = new JButton("Load image");
        filelocTf = new JTextField();
        filelocTf.setEditable(false);
        imgIcon = new JLabel();
        
        //solve
        solveLb = new JLabel("Solve:");
        solveTf = new JTextField(10);

        //memo
        memoLb = new JLabel("Memo:");
        memoTf = new JTextField(10);

        //tag
        tagLb = new JLabel("Tag:");
        tagPanel = new JPanel();
        
        addBtn = new JButton("Add Problem");
        
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
        

        frame.add(center, BorderLayout.CENTER);
        frame.add(addBtn, BorderLayout.NORTH);

        loadImgBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
     	        String selectedFile = UserFolder.loadImg();
     	        if(selectedFile != null) {
     	        	ImageIcon icon = ImageHelper.makeImgIcon(selectedFile, 500);
     	        	imgIcon.setIcon(icon);
     	        	filelocTf.setText(selectedFile);
     	        }
			}
        });
        
        addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//add check empty field
				if(titleTf.getText().equals("") || filelocTf.getText().equals(""))
					JOptionPane.showMessageDialog(null, "Empty title or image file are not allowed", "CREATE ERROR", JOptionPane.INFORMATION_MESSAGE);
				else {
					//add problem to workbook
					String title = titleTf.getText();
					String solve = solveTf.getText();
					String memo = memoTf.getText();
					String fileloc = filelocTf.getText();
					Problem newProblem = new Problem(title, fileloc, solve, memo, checkList);
					
					for(String tagName : checkList) {
						workBook.getTagList().get(tagName).increaseN();
					}
					workBook.addProblem(newProblem);
				
					//add problem to table
					Object[] row = {newProblem.getTitle(), newProblem.getTagtoString(), newProblem.getPercent(), newProblem.getFileloc()};
					DefaultTableModel model = (DefaultTableModel) mainTable.getModel();
					model.addRow(row);
					
					JOptionPane.showMessageDialog(null, "Added successfully", "Sucess", JOptionPane.INFORMATION_MESSAGE);
					
					//init field
					titleTf.setText("");
					solveTf.setText("");
					memoTf.setText("");
					filelocTf.setText("");
					imgIcon.setIcon(null);
				}
			}
        });
	  	System.out.println("world");
	  	
        frame.addKeyListener(new KeyAdapter() {
	      @Override
	      public void keyPressed(KeyEvent e) {
	          if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V) {
	              // 컨트롤 + V 눌렀을 때 클립보드에서 이미지 가져오기
	        	  int result = JOptionPane.showConfirmDialog(null, "Do you want to use the image on the clipboard?", "Confirm", JOptionPane.OK_CANCEL_OPTION);
	        	  if(result == 0) {
		          		String path = UserClipboard.clipboardSaveAsImgFile();
		          		ImageIcon icon = ImageHelper.makeImgIcon(path, 500);
		          		imgIcon.setIcon(icon);
		          		filelocTf.setText(path);
	        	  }
	          }
	             
	      	}
        });

        frame.setFocusable(true);
        frame.setVisible(true);
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
            			checkList.add(checkbox.getText());
            		}
            		else {
            			JCheckBox checkbox = (JCheckBox) e.getSource();
            			checkList.remove(checkbox.getText());
            		}						
				}
        	});
        	tagPanel.add(box);
        }
    }
    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
    	// TODO Auto-generated method stub
	
    }
}
