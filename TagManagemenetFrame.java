import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
import javax.swing.table.TableRowSorter;

class TagManagemenetFrame extends JFrame {
	JPanel inputPanel;
	JLabel label;
	JTextField textField;
	JButton addButton, deleteButton;
	JTable tagTable;
    DefaultTableModel tagTableModel;

    public TagManagemenetFrame(Map<String, Tag> tagList, ArrayList<Problem> problems, JTable table) {
    	setSize(500, 500);
        setLayout(new BorderLayout());
        
        initTable(tagList);

        inputPanel = new JPanel();
        label = new JLabel("Tag name : ");
        textField = new JTextField(10);
        addButton = new JButton("ADD");
        deleteButton = new JButton("DELETE");
        
        inputPanel.add(label);
        inputPanel.add(textField);
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);
        
        //make tag
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tagName = textField.getText();
                if(tagName.equals("")) {
                	JOptionPane.showMessageDialog(null, "Empty names are not allowed", "CREATE ERROR", JOptionPane.ERROR_MESSAGE);
                }else if(tagList.containsKey(tagName)){
                	JOptionPane.showMessageDialog(null, "Duplicate names are not allowed", "CREATE ERROR", JOptionPane.ERROR_MESSAGE);
                }else if(tagName.contains(" ")){
                	JOptionPane.showMessageDialog(null, "Cannot contain spaces", "CREATE ERROR", JOptionPane.ERROR_MESSAGE);
                	textField.setText("");
                }else {
                	Tag newTag = new Tag(tagName);
                    tagList.put(newTag.getName(), newTag);
                    tagTableModel.addRow(new Object[]{newTag.getName(), newTag.getNum()});
                    textField.setText("");
                }
            }
        });

        //delete tag
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tagTable.convertRowIndexToModel(tagTable.getSelectedRow());
                if (selectedRow != -1) {
                	String value = tagTable.getModel().getValueAt(selectedRow, 0).toString();
                	int problemNum = tagList.get(value).getNum();
            		int result = JOptionPane.showConfirmDialog(null, "There are " + problemNum + " problems with this tag are you sure to delete it?", "CONFIRM", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);;
            		if(result == 0) {
            			for(int i = 0; i < problems.size(); i++) {
                			if(problems.get(i).deleteTag(value)) {
                				DefaultTableModel model = (DefaultTableModel) table.getModel();
                				model.setValueAt(problems.get(i).getTagtoString(), i, 1);
                			}
                		}
                        tagList.remove(value);
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
        // JTable을 사용한 태그 목록 표시
        tagTableModel = new DefaultTableModel(new Object[]{"태그 이름", "문제 개수"}, 0);
        tagTable = new JTable(tagTableModel);
        
        // 기존 tag 표시
        if(!tagList.isEmpty()) {
        	for(String tagName : tagList.keySet()) {
        		tagTableModel.addRow(new Object[] {tagName, tagList.get(tagName).getNum()});
        	}
        }
        sort();
    }
    
    public void sort() {
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(tagTableModel);
		tagTable.setRowSorter(sorter); 
    }
}