package com.runetooncraft.warpigeon.pigionsdk;

import javax.swing.JPanel;

import java.awt.GridBagLayout;

import javax.swing.JComboBox;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;

@SuppressWarnings("rawtypes")
public class SDKBotPanel extends JPanel {

	private static final long serialVersionUID = 1L;
		public JComboBox selectedtile;
		public JComboBox selectedtile2;
		public JComboBox selectedLayer;
		
	public SDKBotPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		selectedtile = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 5;
		gbc_comboBox.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 4;
		gbc_comboBox.gridy = 0;
		add(selectedtile, gbc_comboBox);
		
		JLabel lblMouse = new JLabel("Mouse 1:");
		GridBagConstraints gbc_lblMouse = new GridBagConstraints();
		gbc_lblMouse.gridwidth = 4;
		gbc_lblMouse.insets = new Insets(0, 0, 0, 5);
		gbc_lblMouse.gridx = 0;
		gbc_lblMouse.gridy = 0;
		add(lblMouse, gbc_lblMouse);
		
		JLabel lblMouse_1 = new JLabel("Mouse 2:");
		GridBagConstraints gbc_lblMouse_1 = new GridBagConstraints();
		gbc_lblMouse_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblMouse_1.gridx = 10;
		gbc_lblMouse_1.gridy = 0;
		add(lblMouse_1, gbc_lblMouse_1);
		
		selectedtile2 = new JComboBox();
		GridBagConstraints gbc_comboBox2 = new GridBagConstraints();
		gbc_comboBox2.gridwidth = 6;
		gbc_comboBox2.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox2.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox2.gridx = 12;
		gbc_comboBox2.gridy = 0;
		add(selectedtile2, gbc_comboBox2);
	
		JLabel lblLayer = new JLabel("Layer:");
		GridBagConstraints gbc_lblLayer = new GridBagConstraints();
		gbc_lblLayer.insets = new Insets(0, 0, 0, 5);
		gbc_lblLayer.gridx = 19;
		gbc_lblLayer.gridy = 0;
		add(lblLayer, gbc_lblLayer);
		
		selectedLayer = new JComboBox();
		GridBagConstraints gbc_selectedLayer = new GridBagConstraints();
		gbc_selectedLayer.gridwidth = 5;
		gbc_selectedLayer.fill = GridBagConstraints.HORIZONTAL;
		gbc_selectedLayer.gridx = 21;
		gbc_selectedLayer.gridy = 0;
		add(selectedLayer, gbc_selectedLayer);
	}

}
