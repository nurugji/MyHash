import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

class EditProblem extends JFrame {
	private JPanel fileLoadPanel, tagPanel;
	private JLabel titleLb, filelocLb, imgIcon, solveLb, memoLb, tagLb;
	private JButton loadImgBtn, saveBtn;
    private JTextField titleTf, filelocTf, solveTf, memoTf;
    
    private Set<String> checkList = new HashSet<String>();
    
    int selectedRow;
    Problem selectedProblem;

    public EditProblem(Workbook workBook, JTable mainTable){
    	
    	setSize(1000, 1000);
    	setLayout(new GridLayout(6, 1));
    	
    	selectedRow = mainTable.getSelectedRow();
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
        saveBtn = new JButton();
        initTag(workBook);
        
        fileLoadPanel.add(filelocLb);
        fileLoadPanel.add(loadImgBtn);
        fileLoadPanel.add(filelocTf);

        add(titleLb);
        add(titleTf);
        add(fileLoadPanel);
        add(imgIcon);
        add(solveLb);
        add(solveTf);
        add(memoLb);
        add(memoTf);
        add(tagLb);
        add(tagPanel);
        add(saveBtn);
        
        loadImgBtn.addActionListener(new ActionListener() {
     	   public void actionPerformed(ActionEvent e) {
     	        String selectedFile = ImageHelper.loadImg();
     	        if(selectedFile != null) {
     	        	ImageIcon icon = ImageHelper.makeImgIcon(selectedFile);
     	        	imgIcon.setIcon(icon);
     	        	filelocTf.setText(selectedFile);
     	        }
     	    }
        });

        saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//data change
				selectedProblem.setTitle(titleTf.getText());
				selectedProblem.setFileloc(filelocTf.getText());
				selectedProblem.setSolve(solveTf.getText());
				selectedProblem.setMemo(memoTf.getText());
		    	selectedProblem.setTag(checkList);
		    	
		    	//table change
		    	mainTable.getModel().setValueAt(selectedProblem.getTitle(), selectedRow, 0);
		    	mainTable.getModel().setValueAt(selectedProblem.getTagtoString(), selectedRow, 1);
		    	mainTable.getModel().setValueAt(selectedProblem.getFileloc(), selectedRow, 3);
			}
        });
        

    }
    public void initImg(JTable mainTable) {
        ImageIcon icon = ImageHelper.makeImgIcon(selectedProblem.getFileloc());
        if(icon == null) {
            filelocTf.setText("");
            selectedProblem.setFileloc("");
            
            mainTable.getModel().setValueAt(selectedProblem.getFileloc(), selectedRow, 2);
        }
        imgIcon.setIcon(icon);
    }
    
    public void initTag(Workbook workBook) {
        ArrayList<String> temp = new ArrayList<String>(workBook.getTagList().keySet());
        temp.sort(new StrCmp());

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
					else {
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

