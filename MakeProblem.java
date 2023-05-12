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

//추가하고 창이 바로 안꺼지고 비슷한 태그를 가지는 애들을 중복생성할 수 있을 듯
class MakeProblemPanel extends JFrame {
    private JTextField titleField, filelocField, solveField, memoField;
    private JButton loadImageButton;
    private JLabel titleLabel, filelocLabel, imgIcon, solveLabel, memoLabel, tagLabel;
    
    private Set<String> checkList = new HashSet<String>();

    
    private JTable table;
    
    public MakeProblemPanel(Workbook workBook, JTable table) {
//    	this.table = table;
    	DefaultTableModel model = (DefaultTableModel) table.getModel();
    	setSize(1000, 1000);
        setLayout(new GridLayout(6, 1));
        
        //title
        titleLabel = new JLabel("Title:");
        titleField = new JTextField();
        add(titleLabel);
        add(titleField);
        
        //File load
        JPanel fileLoadPanel = new JPanel();
        filelocLabel = new JLabel("File location:");
        loadImageButton = new JButton("Load image");
        loadImageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File selectedFile = loadImg();
				try {
					BufferedImage image = ImageIO.read(selectedFile);
					Image scaledImage = ImageFile.resizeImg(image, 1000);
					ImageIcon icon = new ImageIcon(scaledImage);
					imgIcon.setIcon(icon);
					filelocField.setText(selectedFile.getAbsolutePath());
					
				} catch (IOException e1) {
					System.out.println("no Img");
				} 
			}
        });
        
        filelocField = new JTextField();
        filelocField.setEditable(false);
        imgIcon = new JLabel();
        
        fileLoadPanel.add(filelocLabel);
        fileLoadPanel.add(loadImageButton);
        fileLoadPanel.add(filelocField);
        
        add(fileLoadPanel);
        add(imgIcon);

        //solve
        solveLabel = new JLabel("Solve:");
        solveField = new JTextField();
        add(solveLabel);
        add(solveField);
        
        //memo
        memoLabel = new JLabel("Memo:");
        memoField = new JTextField();
        add(memoLabel);
        add(memoField);
        
        //prev
        tagLabel = new JLabel("Tag:");
        JPanel tagPanel = new JPanel();
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

        
        add(tagLabel);
        add(tagPanel);
        
        JButton addButton = new JButton();
        addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//add problem to workbook
				String title = titleField.getText();
				String solve = solveField.getText();
				String memo = memoField.getText();
				String fileloc = filelocField.getText();
				Problem newProblem = new Problem(title, fileloc, solve, memo, checkList);
				
				for(String tagName : checkList) {
					workBook.getTagList().get(tagName).increaseN();
				}
				workBook.addProblem(newProblem);
				
				//add problem to table
				Object[] row = {newProblem.getTitle(), newProblem.getTagtoString(), newProblem.getPercent(), newProblem.getFileloc()};
				model.addRow(row);
			}
        	
        });
        add(addButton);
        
    }
    
    public File loadImg() {
      	 JFileChooser fileChooser = new JFileChooser();// 파일 탐색기 출력
           int result = fileChooser.showOpenDialog(this);//img 파인인지 검사
           
           if (result == JFileChooser.APPROVE_OPTION) {
               File selectedFile = fileChooser.getSelectedFile();
               String filename = selectedFile.getName();
               if(ImageFile.imgFileCheck(filename)) {
               	return selectedFile;
               }else {
              	 JOptionPane.showMessageDialog(this, "Error: Only JPG, JPEG, and PNG files are supported.", "Error", JOptionPane.ERROR_MESSAGE);
               }
           }
           return null;
      }
}
