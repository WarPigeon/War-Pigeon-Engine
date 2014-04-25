package com.runetooncraft.warpigeon.engine.utils;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ExpandLevel extends JFrame {
	public JTextField widthField;
	public JTextField heightField;
	public JButton btnOk;
	
	public ExpandLevel() {
		this.setSize(538,317);
		setTitle("ExpandLevel");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{139, 249, 0};
		gridBagLayout.rowHeights = new int[]{69, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("Width:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		widthField = new JTextField();
		GridBagConstraints gbc_widthField = new GridBagConstraints();
		gbc_widthField.insets = new Insets(0, 0, 5, 0);
		gbc_widthField.fill = GridBagConstraints.HORIZONTAL;
		gbc_widthField.gridx = 1;
		gbc_widthField.gridy = 0;
		getContentPane().add(widthField, gbc_widthField);
		widthField.setColumns(10);
		
		JLabel lblHeight = new JLabel("Height:");
		GridBagConstraints gbc_lblHeight = new GridBagConstraints();
		gbc_lblHeight.anchor = GridBagConstraints.EAST;
		gbc_lblHeight.insets = new Insets(0, 0, 5, 5);
		gbc_lblHeight.gridx = 0;
		gbc_lblHeight.gridy = 1;
		getContentPane().add(lblHeight, gbc_lblHeight);
		
		heightField = new JTextField();
		GridBagConstraints gbc_heightField = new GridBagConstraints();
		gbc_heightField.insets = new Insets(0, 0, 5, 0);
		gbc_heightField.fill = GridBagConstraints.HORIZONTAL;
		gbc_heightField.gridx = 1;
		gbc_heightField.gridy = 1;
		getContentPane().add(heightField, gbc_heightField);
		heightField.setColumns(10);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.gridx = 0;
		gbc_btnCancel.gridy = 2;
		getContentPane().add(btnCancel, gbc_btnCancel);
		
		btnOk = new JButton("Ok");
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.gridx = 1;
		gbc_btnOk.gridy = 2;
		getContentPane().add(btnOk, gbc_btnOk);
	}
	

}
