package com.runetooncraft.warpigeon.pigionsdk;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JComboBox;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

public class SDKTopRightPanel extends JPanel {
	
	public JFormattedTextField HeightPane,WidthPane,ScalePane;
	public JButton SetDimension;
	
	public SDKTopRightPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{99, 70, 118, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblSetDimension = new JLabel("Set Dimension:");
		GridBagConstraints gbc_lblSetDimension = new GridBagConstraints();
		gbc_lblSetDimension.anchor = GridBagConstraints.EAST;
		gbc_lblSetDimension.gridheight = 2;
		gbc_lblSetDimension.insets = new Insets(0, 0, 5, 5);
		gbc_lblSetDimension.gridx = 0;
		gbc_lblSetDimension.gridy = 0;
		add(lblSetDimension, gbc_lblSetDimension);
		
		JLabel lblWidth = new JLabel("Width:");
		GridBagConstraints gbc_lblWidth = new GridBagConstraints();
		gbc_lblWidth.anchor = GridBagConstraints.EAST;
		gbc_lblWidth.insets = new Insets(0, 0, 5, 5);
		gbc_lblWidth.gridx = 1;
		gbc_lblWidth.gridy = 0;
		add(lblWidth, gbc_lblWidth);
		MaskFormatter numberFormatter = null;
		try {
			numberFormatter = new MaskFormatter("#####");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		WidthPane = new JFormattedTextField(numberFormatter);
		GridBagConstraints gbc_WidthPane = new GridBagConstraints();
		gbc_WidthPane.insets = new Insets(0, 0, 5, 5);
		gbc_WidthPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_WidthPane.gridx = 2;
		gbc_WidthPane.gridy = 0;
		add(WidthPane, gbc_WidthPane);
		
		JLabel lblHeight = new JLabel("Height:");
		GridBagConstraints gbc_lblHeight = new GridBagConstraints();
		gbc_lblHeight.anchor = GridBagConstraints.EAST;
		gbc_lblHeight.insets = new Insets(0, 0, 5, 5);
		gbc_lblHeight.gridx = 1;
		gbc_lblHeight.gridy = 1;
		add(lblHeight, gbc_lblHeight);
		
		HeightPane = new JFormattedTextField(numberFormatter);
		GridBagConstraints gbc_HeightPane = new GridBagConstraints();
		gbc_HeightPane.insets = new Insets(0, 0, 5, 5);
		gbc_HeightPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_HeightPane.gridx = 2;
		gbc_HeightPane.gridy = 1;
		add(HeightPane, gbc_HeightPane);
		
		SetDimension = new JButton("Set");
		GridBagConstraints gbc_SetDimension = new GridBagConstraints();
		gbc_SetDimension.insets = new Insets(0, 0, 0, 5);
		gbc_SetDimension.gridx = 0;
		gbc_SetDimension.gridy = 2;
		add(SetDimension, gbc_SetDimension);
		
		JLabel lblScale = new JLabel("Scale:");
		GridBagConstraints gbc_lblScale = new GridBagConstraints();
		gbc_lblScale.anchor = GridBagConstraints.EAST;
		gbc_lblScale.insets = new Insets(0, 0, 0, 5);
		gbc_lblScale.gridx = 1;
		gbc_lblScale.gridy = 2;
		add(lblScale, gbc_lblScale);
		
		ScalePane = new JFormattedTextField(numberFormatter);
		GridBagConstraints gbc_ScalePane = new GridBagConstraints();
		gbc_ScalePane.insets = new Insets(0, 0, 0, 5);
		gbc_ScalePane.fill = GridBagConstraints.HORIZONTAL;
		gbc_ScalePane.gridx = 2;
		gbc_ScalePane.gridy = 2;
		add(ScalePane, gbc_ScalePane);
		
		WidthPane.setFocusLostBehavior(JFormattedTextField.PERSIST);
		HeightPane.setFocusLostBehavior(JFormattedTextField.PERSIST);
		ScalePane.setFocusLostBehavior(JFormattedTextField.PERSIST);
	}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
