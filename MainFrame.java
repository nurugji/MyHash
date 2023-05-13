import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

//delete confirm message


//fix comparator
class StrCmp implements Comparator<String>{

	public int compare(String o1, String o2) {
		return o1.compareTo(o2);
	}
}

public class MainFrame extends JFrame{
	
	private Workbook workBook = new Workbook();
	
	public DefaultTableModel mainModel;
	public JTable mainTable;
	
	JTextField searchField;
	JScrollPane tablePane;
	
	
    public MainFrame() {
    	//init data
    	initWorkbook();
        initTable();
        
        JFrame frame = new JFrame("Problem Manager");
        
        JPanel managementPanel = new JPanel();
        JPanel toolPanel = new JPanel();
        
        JButton addBtn = new JButton("Add Problem");
        JButton editBtn = new JButton("Edit Problem");
        JButton deleteBtn = new JButton("Delete Problem");
        JTextField searchTf = new JTextField(20);
        JButton initBtn = new JButton("Init");
        
        JButton tagBtn = new JButton("Tag management");
        JButton detailBtn = new JButton("Deatil");
        JButton filterBtn = new JButton("Filter");
        JButton saveBtn = new JButton("Save");
        
        managementPanel.add(addBtn);
        managementPanel.add(editBtn);
        managementPanel.add(deleteBtn);
        managementPanel.add(detailBtn);
        managementPanel.add(tagBtn);
        
        toolPanel.add(searchTf);
        toolPanel.add(initBtn);
        toolPanel.add(filterBtn);
        toolPanel.add(saveBtn);

        frame.add(toolPanel, BorderLayout.NORTH);
        frame.add(tablePane, BorderLayout.CENTER);
        frame.add(managementPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	JFrame makeProblemPanel = new MakeProblemPanel(workBook, mainTable);
            	makeProblemPanel.setVisible(true);
                show();
            }
        });
        
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = mainTable.getSelectedRow();
                if (selectedRow != -1) {
                	JFrame editProblemPanel = new EditProblem(workBook, mainTable);
                	editProblemPanel.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a row to edit.");
                }
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	//add confirm message 
                int selectedRow = mainTable.getSelectedRow();
                if (selectedRow != -1) {
                	workBook.removeProblem(selectedRow);
                    mainModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a row to delete.");
                }
            }
        });

        tagBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame makeTagFrame = new TagManagemenetFrame(workBook, mainTable);
				makeTagFrame.setVisible(true);
                show();
			}
        });
        
        detailBtn.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = mainTable.getSelectedRow();
				if(selectedRow != -1) {
					JFrame detailFrame = new DetailPanel(workBook, mainTable);
					detailFrame.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(frame, "Please select a row to see detail.");
				}
			}
        });

        filterBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame selectTagPanel = new SelectTagtoFilterFrame(workBook.getTagList(), mainTable);
				selectTagPanel.setVisible(true);
			}
        });
        
        saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveProblemList(workBook.getProblemList(), "MyHashProblem.txt");
				saveTagList(workBook.getTagList(), "MyHashTag.txt");
			}
        });
        
        initBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				filter("", mainTable);
			}
        	
        });
        
        searchTf.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				filter(searchTf.getText(), mainTable);
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				filter(searchTf.getText(), mainTable);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				filter(searchTf.getText(), mainTable);
			}
        	
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setVisible(true);
    }
    
    
    public void show() {
    	for(Problem problem : workBook.getProblemList()) {
    		System.out.printf("title : %s, tag : %s, tileloc : %s, solve : %s, memo : %s history %s\n", problem.getTitle(), problem.getTag(), problem.getFileloc(), problem.getSolve(), problem.getMemo(), problem.getHistory().toString());
    	}
    	for(String tagName : workBook.getTagList().keySet()) {
    		System.out.printf("tag : %s / %d\n", tagName, workBook.getTagList().get(tagName).getNum());
    	}
    }
    
    public static void filter(String query, JTable mainTable) {
    	TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>((DefaultTableModel) mainTable.getModel());
    	mainTable.setRowSorter(tr);
    	
    	tr.setRowFilter(RowFilter.regexFilter(query));
    }
    
    public ArrayList<Problem> loadProblemList(String filePath) {
        ArrayList<Problem> loadProblems = new ArrayList<Problem>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 5) {
                    System.out.println("Invalid line: " + line);
                    continue;
                }
                String title = parts[0];
                String fileloc = parts[1];
                String solve = parts[2];
                String memo = parts[3];
                String tag = parts[4];
                Set<String> temp = Problem.stringtoTag(tag);
                Problem problem = new Problem(title, fileloc, solve, memo, temp);
                
                loadProblems.add(problem);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred while loading problems.");
            e.printStackTrace();
        }
        return loadProblems;
    }
    
    public Map<String, Tag> loadTagList(String filepath) {
        Map<String, Tag> loadTagList = new HashMap<String, Tag>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                int num = Integer.valueOf(parts[1]);
                Tag tag = new Tag(name, num);
                loadTagList.put(tag.getName(), tag);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred while loading problems.");
            e.printStackTrace();
        }
        return loadTagList;
    }
    
    public void saveTagList(Map<String, Tag> tagList, String filePath) {

    	try {
        	BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        	Set<String> temp = tagList.keySet();
        	
        	for(String tagName : temp) {
        		writer.write(tagName + " " + tagList.get(tagName).getNum());
        		writer.newLine();
        	}
        	writer.close();
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
    }
    
    public void saveProblemList(ArrayList<Problem> problems, String filepath) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
            for (Problem problem : problems) {
                String line = problem.getTitle() + "," +
                              problem.getFileloc() + "," +
                              problem.getSolve() + "," +
                              problem.getMemo() + "," +
                              problem.getTagtoString();
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while saving problems.");
            e.printStackTrace();
        }
    }
    
    //file name decide
    public void initWorkbook() {
    	workBook.setTagList(loadTagList("MyHashTag.txt"));
    	workBook.setProblemList(loadProblemList("MyHashProblem.txt"));
    }
    
    public void initTable() {
    	String[] columnNames = {"Title", "tag", "correct rate", "File Location"};
        mainModel = new DefaultTableModel(columnNames, 0){
        	@Override
            public boolean isCellEditable(int row, int column){
             return false;
            }
        };
        mainTable = new JTable(mainModel);
        tablePane = new JScrollPane(mainTable);
       
		for(Problem problem : workBook.getProblemList()) {
			Object[] row = {problem.getTitle(), problem.getTagtoString(), problem.getPercent(), problem.getFileloc()};
			mainModel.addRow(row);
		}
    }

    public static void main(String[] args) {
    	new MainFrame();
    }
}










           
            
