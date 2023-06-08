package com.myhash.frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.myhash.object.ImageHelper;
import com.myhash.object.Problem;
import com.myhash.object.Workbook;

public class DatailFrame extends JFrame {
	private JPanel infoP;
	private JButton solveBtn, memoBtn, historyBtn;
	private JLabel imgIcon;
	private JScrollPane imgP;
	
	int selectedRow;
	Problem selectedProblem;
	
	public DatailFrame(Workbook workBook, JTable mainTable) {
		selectedRow = mainTable.convertRowIndexToModel(mainTable.getSelectedRow());
		selectedProblem = workBook.getProblemList().get(selectedRow);
		
    	setSize(1000, 1000);
		setLayout(new BorderLayout());
		
		infoP = new JPanel();
		solveBtn = new JButton("show solve");
		memoBtn = new JButton("show memo");
		historyBtn = new JButton("history management");
		imgIcon = new JLabel();
		
		imgP = new JScrollPane(imgIcon);
		initImg(mainTable);
		
		infoP.add(solveBtn);
		infoP.add(memoBtn);
		infoP.add(historyBtn);
		
		add(imgP, BorderLayout.CENTER);
		add(infoP, BorderLayout.NORTH);
		
		historyBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame makeHistoryFrame = new HistoryFrame(workBook, mainTable);
				makeHistoryFrame.setVisible(true);
			}
			
		});
		
		solveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Solve : " + selectedProblem.getSolve(), "Solve", JOptionPane.PLAIN_MESSAGE);
			}
			
		});
		
		memoBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Memo : " + selectedProblem.getMemo(), "Memo", JOptionPane.PLAIN_MESSAGE);
			}
		});
		
	}
    public void initImg(JTable mainTable) {
    	ImageIcon icon = null;
    	try {
	       icon = ImageHelper.makeImgIcon(selectedProblem.getFileloc(), 1000);
    	}catch(NullPointerException e) {
            selectedProblem.setFileloc("");
            mainTable.getModel().setValueAt(selectedProblem.getFileloc(), selectedRow, 3);
    	}
        imgIcon.setIcon(icon);
    }
}
