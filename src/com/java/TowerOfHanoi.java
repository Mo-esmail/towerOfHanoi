package com.java;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Shehata.Ibrahim
 */
public class TowerOfHanoi extends javax.swing.JFrame {

	private static Pattern numberPattern = Pattern.compile("[0-9]+");
	ArrayList<TowerDisk> generatedDisks;

	public TowerOfHanoi() {
		initComponents();
		pack();
		this.setSize(600, 400);
		this.setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((dim.width - this.getWidth()) / 2,
				(dim.height - this.getHeight()) / 2);
		this.setTitle("Tower Of Hanoi");
	}

	boolean initializedBefore = false;

	public void generateDisks(ActionEvent e) {
		if (!numberPattern.matcher(txtNumeberOfDisks.getText()).matches()) {
			JOptionPane.showMessageDialog(btnGenerate,
					"Text you entered is not numeric", "Number Of Disks Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		int numberOfDisks = Integer.parseInt(txtNumeberOfDisks.getText());
		if (numberOfDisks > 10) {
			JOptionPane.showMessageDialog(btnGenerate,
					"Number of disks can't exceed 10 to be visible on UI",
					"Number Of Disks Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (initializedBefore) {
			reloadUI();
			txtNumeberOfDisks.setText(String.valueOf(numberOfDisks));
		} else
			initializedBefore = true;
		// btnGenerate.setEnabled(false);
		generatedDisks = new ArrayList<TowerDisk>();
		for (int i = 0; i < numberOfDisks; i++) {
			TowerDisk disk = new TowerDisk();
			disk.init(TowerDisk.DEFAULT_WIDTH - i * 10, lblSrc.getX(),
					lblSrc.getWidth(), lblSrc.getCurrentY());
			getContentPane().add(disk);
			lblSrc.addDisk();
			SwingUtilities.updateComponentTreeUI(getContentPane());
			generatedDisks.add(disk);

		}
	}

	Thread solutionThread = null;

	public void solve(ActionEvent e) {
		solutionThread = new Thread() {
			@Override
			public void run() {
				btnSolve.setEnabled(false);
				solve(getDisksCount(), lblSrc, lblInter, lblDestination);
				btnSolve.setEnabled(true);
				JOptionPane.showMessageDialog(lblInter, "Solution completed in "+counter+" moves", "Completed", JOptionPane.INFORMATION_MESSAGE);
				counter=0;

			}
		};
		solutionThread.start();
	}

	public int getDisksCount() {
		return generatedDisks.size() - 1;
	}
int counter=0;
	private void solve(int topN, Tower lblSrc, Tower lblInter,
			Tower lblDestination) {
		
		
		if (topN == 0) {
			lblCounter.setText(String.valueOf(++counter));
			generatedDisks.get(getDisksCount() - topN).moveToTower(lblSrc,
					lblDestination);
		} else {
			solve(topN - 1, lblSrc, lblDestination, lblInter);
			lblCounter.setText(String.valueOf(++counter));
			generatedDisks.get(getDisksCount() - topN).moveToTower(lblSrc,
					lblDestination);
			solve(topN - 1, lblInter, lblSrc, lblDestination);
		}
	}
JLabel lblCounter;
	private void initComponents() {

		lblSrc = new Tower();
		lblInter = new Tower();
		lblDestination = new Tower();
		lblCounter=new JLabel();
		txtNumeberOfDisks = new javax.swing.JTextField();
		jLabel1 = new javax.swing.JLabel();
		btnGenerate = new javax.swing.JButton();
		btnSolve = new javax.swing.JButton();
		btnRefresh = new javax.swing.JButton();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		lblSrc.setBackground(new java.awt.Color(153, 153, 153));
		lblSrc.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		lblSrc.setOpaque(true);
		getContentPane().add(lblSrc);
		lblSrc.setBounds(470, 100, 14, 300);

		lblInter.setBackground(new java.awt.Color(153, 153, 153));
		lblInter.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		lblInter.setOpaque(true);
		getContentPane().add(lblInter);
		lblInter.setBounds(291, 100, 14, 300);

		lblDestination.setBackground(new java.awt.Color(153, 153, 153));
		lblDestination
				.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		lblDestination.setOpaque(true);
		getContentPane().add(lblDestination);
		lblDestination.setBounds(120, 100, 14, 300);
		getContentPane().add(txtNumeberOfDisks);
		txtNumeberOfDisks.setBounds(130, 10, 70, 23);

		jLabel1.setText("Number Of Disks");
		getContentPane().add(jLabel1);
		jLabel1.setBounds(20, 10, 100, 23);

		btnGenerate.setText("Generate Disks");
		getContentPane().add(btnGenerate);
		btnGenerate.setBounds(210, 10, 120, 23);

		btnSolve.setText("Start Solution");
		getContentPane().add(btnSolve);
		btnSolve.setBounds(340, 10, 110, 23);
		btnRefresh.setText("Reload UI");
		getContentPane().add(btnRefresh);
		btnRefresh.setBounds(460, 10, 110, 23);
		lblCounter.setBounds(575, 10, 25, 23);
		getContentPane().add(lblCounter);
		btnSolve.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				solve(e);
			}
		});
		btnGenerate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				generateDisks(e);
			}
		});
		btnRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reloadUI();
			}
		});
	}

	public void reloadUI() {
		if (solutionThread != null) {
			try {

				solutionThread.stop();
				solutionThread=null;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		getContentPane().removeAll();
		initComponents();
		/*
		 * SwingUtilities.invokeLater(new Runnable() {
		 * 
		 * @Override public void run() {
		 * 
		 * } });
		 */
		SwingUtilities.updateComponentTreeUI(getContentPane());
	}

	public static void main(String args[]) {

		try {
			javax.swing.UIManager.setLookAndFeel(UIManager
					.getSystemLookAndFeelClassName());

		} catch (Exception ex) {
			java.util.logging.Logger.getLogger(TowerOfHanoi.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		}

		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new TowerOfHanoi().setVisible(true);
			}
		});
	}

	private javax.swing.JButton btnGenerate;
	private javax.swing.JButton btnSolve;
	private javax.swing.JButton btnRefresh;
	private javax.swing.JLabel jLabel1;
	private Tower lblDestination;
	private Tower lblInter;
	private Tower lblSrc;
	private javax.swing.JTextField txtNumeberOfDisks;
}
