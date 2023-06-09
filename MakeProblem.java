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
import javax.swing.table.DefaultTableModel;

class MakeProblemPanel extends JFrame {
	private JPanel fileLoadPanel, tagPanel;
	private JLabel titleLb, filelocLb, imgIcon, solveLb, memoLb, tagLb;
	private JButton loadImgBtn, addBtn;
    private JTextField titleTf, filelocTf, solveTf, memoTf;
    
    private Set<String> checkList = new HashSet<String>();
    
    public MakeProblemPanel(Workbook workBook, JTable mainTable) {
    	
    	setSize(1000, 1000);
        setLayout(new GridLayout(6, 1));
        
        //title
        titleLb = new JLabel("Title:");
        titleTf = new JTextField();

        //File load
        fileLoadPanel = new JPanel();
        filelocLb = new JLabel("File location:");
        loadImgBtn = new JButton("Load image");
        filelocTf = new JTextField();
        filelocTf.setEditable(false);
        imgIcon = new JLabel();
        
        //solve
        solveLb = new JLabel("Solve:");
        solveTf = new JTextField();

        //memo
        memoLb = new JLabel("Memo:");
        memoTf = new JTextField();

        //tag
        tagLb = new JLabel("Tag:");
        tagPanel = new JPanel();
        
        addBtn = new JButton("Add Problem");
        
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
        add(addBtn);
        
        loadImgBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
     	        String selectedFile = ImageHelper.loadImg();
     	        if(selectedFile != null) {
     	        	ImageIcon icon = ImageHelper.makeImgIcon(selectedFile);
     	        	imgIcon.setIcon(icon);
     	        	filelocTf.setText(selectedFile);
     	        }
			}
        });
        
        addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//add check empty field
				
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
				
				JOptionPane.showMessageDialog(null, "MESSAGE : Added successfully", "Sucess", JOptionPane.INFORMATION_MESSAGE);
				
				//init field
				titleTf.setText("");
				solveTf.setText("");
				memoTf.setText("");
				filelocTf.setText("");
				imgIcon.setIcon(null);
			}
        });
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
}
