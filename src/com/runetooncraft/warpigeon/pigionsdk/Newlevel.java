package com.runetooncraft.warpigeon.pigionsdk;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JFormattedTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Newlevel extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public JFormattedTextField Name;
	public JFormattedTextField height;
	public JFormattedTextField width;
	public JButton okButton;

	public Newlevel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		setTitle("New Level");
		setBounds(100, 100, 161, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{232, 226, 0};
		gbl_contentPanel.rowHeights = new int[]{63, 100, 88, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblName = new JLabel("Name:");
			GridBagConstraints gbc_lblName = new GridBagConstraints();
			gbc_lblName.anchor = GridBagConstraints.EAST;
			gbc_lblName.insets = new Insets(0, 0, 5, 5);
			gbc_lblName.gridx = 0;
			gbc_lblName.gridy = 0;
			contentPanel.add(lblName, gbc_lblName);
		}
		{
			Name = new JFormattedTextField();
			Name.setText("Unnamed");
			GridBagConstraints gbc_Name = new GridBagConstraints();
			gbc_Name.insets = new Insets(0, 0, 5, 0);
			gbc_Name.fill = GridBagConstraints.HORIZONTAL;
			gbc_Name.gridx = 1;
			gbc_Name.gridy = 0;
			contentPanel.add(Name, gbc_Name);
		}
		{
			JLabel lblHeight = new JLabel("Height:");
			GridBagConstraints gbc_lblHeight = new GridBagConstraints();
			gbc_lblHeight.anchor = GridBagConstraints.EAST;
			gbc_lblHeight.insets = new Insets(0, 0, 5, 5);
			gbc_lblHeight.gridx = 0;
			gbc_lblHeight.gridy = 1;
			contentPanel.add(lblHeight, gbc_lblHeight);
		}
		{
			height = new JFormattedTextField();
			height.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					if(!Character.isDigit(e.getKeyChar()) || Character.isSpaceChar(e.getKeyChar()) || Character.isWhitespace(e.getKeyChar())) {
						e.consume();
					}
				}
			});
			height.setText("64");
			GridBagConstraints gbc_height = new GridBagConstraints();
			gbc_height.insets = new Insets(0, 0, 5, 0);
			gbc_height.fill = GridBagConstraints.HORIZONTAL;
			gbc_height.gridx = 1;
			gbc_height.gridy = 1;
			contentPanel.add(height, gbc_height);
		}
		{
			JLabel lblNewLabel = new JLabel("Width:");
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
			gbc_lblNewLabel.gridx = 0;
			gbc_lblNewLabel.gridy = 2;
			contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		}
		{
			width = new JFormattedTextField();
			width.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					if(!Character.isDigit(e.getKeyChar()) || Character.isSpaceChar(e.getKeyChar()) || Character.isWhitespace(e.getKeyChar())) {
						e.consume();
					}
				}
			});
			width.setText("64");
			GridBagConstraints gbc_width = new GridBagConstraints();
			gbc_width.fill = GridBagConstraints.HORIZONTAL;
			gbc_width.gridx = 1;
			gbc_width.gridy = 2;
			contentPanel.add(width, gbc_width);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				{
					okButton = new JButton("OK");
					okButton.setActionCommand("OK");
					buttonPane.add(okButton);
					getRootPane().setDefaultButton(okButton);
				}
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
