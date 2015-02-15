package com.runetooncraft.warpigeon.pigionsdk;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JCheckBox;
import javax.swing.JTabbedPane;

public class SDKTopPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	public JCheckBox collisionsCheck;
	public JCheckBox overlayCheck;
	public JTabbedPane tabbedPane;
	public SDKConsole consolePane = new SDKConsole();
	
	public SDKTopPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		overlayCheck = new JCheckBox("Grid Overlay");
		GridBagConstraints gbc_overlayCheck = new GridBagConstraints();
		gbc_overlayCheck.anchor = GridBagConstraints.WEST;
		gbc_overlayCheck.insets = new Insets(0, 0, 5, 5);
		gbc_overlayCheck.gridx = 0;
		gbc_overlayCheck.gridy = 0;
		add(overlayCheck, gbc_overlayCheck);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Console",consolePane);
			JLabel tab = returnFormattedTab("Console");
			tabbedPane.setTabComponentAt(0, tab);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.gridheight = 4;
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 1;
		gbc_tabbedPane.gridy = 0;
		add(tabbedPane, gbc_tabbedPane);
		
		collisionsCheck = new JCheckBox("Collisions Overlay");
		GridBagConstraints gbc_collisionsCheck = new GridBagConstraints();
		gbc_collisionsCheck.anchor = GridBagConstraints.WEST;
		gbc_collisionsCheck.insets = new Insets(0, 0, 5, 5);
		gbc_collisionsCheck.gridx = 0;
		gbc_collisionsCheck.gridy = 1;
		add(collisionsCheck, gbc_collisionsCheck);
		
		
	}
	private JLabel returnFormattedTab(String text) {
		JLabel tab = new JLabel();
		tab.setPreferredSize(new Dimension(text.length()*6, 10));
		tab.setText(text);
		return tab;
	}

}
