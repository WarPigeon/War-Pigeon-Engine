package com.runetooncraft.warpigeon.pigionsdk;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Canvas;

public class SDKPanel extends JPanel {

		public JComboBox selectedtile;
		public JComboBox selectedtile2;
		
	public SDKPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		selectedtile = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridheight = 6;
		gbc_comboBox.gridwidth = 5;
		gbc_comboBox.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 4;
		gbc_comboBox.gridy = 0;
		add(selectedtile, gbc_comboBox);
		
		JLabel lblMouse = new JLabel("Mouse 1:");
		GridBagConstraints gbc_lblMouse = new GridBagConstraints();
		gbc_lblMouse.gridheight = 4;
		gbc_lblMouse.gridwidth = 4;
		gbc_lblMouse.insets = new Insets(0, 0, 5, 5);
		gbc_lblMouse.gridx = 0;
		gbc_lblMouse.gridy = 1;
		add(lblMouse, gbc_lblMouse);
		
		JLabel lblMouse_1 = new JLabel("Mouse 2:");
		GridBagConstraints gbc_lblMouse_1 = new GridBagConstraints();
		gbc_lblMouse_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblMouse_1.gridx = 10;
		gbc_lblMouse_1.gridy = 3;
		add(lblMouse_1, gbc_lblMouse_1);
		
		selectedtile2 = new JComboBox();
		GridBagConstraints gbc_comboBox2 = new GridBagConstraints();
		gbc_comboBox2.gridwidth = 5;
		gbc_comboBox2.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox2.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox2.gridx = 13;
		gbc_comboBox2.gridy = 3;
		add(selectedtile2, gbc_comboBox2);

	}

}
