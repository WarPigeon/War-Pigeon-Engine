package com.runetooncraft.warpigeon.pigionsdk;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JCheckBox;

public class SDKTopPanel extends JPanel {
	public JCheckBox overlayCheck;
	public SDKTopPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		overlayCheck = new JCheckBox("Enable Overlay");
		GridBagConstraints gbc_chckbxOverlay = new GridBagConstraints();
		gbc_chckbxOverlay.anchor = GridBagConstraints.WEST;
		gbc_chckbxOverlay.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxOverlay.gridx = 0;
		gbc_chckbxOverlay.gridy = 0;
		add(overlayCheck, gbc_chckbxOverlay);
	}

}
