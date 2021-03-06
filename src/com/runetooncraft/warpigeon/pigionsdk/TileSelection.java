package com.runetooncraft.warpigeon.pigionsdk;


import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class TileSelection extends JPanel {
	private static final long serialVersionUID = 1L;
	public JTable table;
	public JScrollPane pane;
	private JLabel lblFilterText;
	public JTextField FilterText;

	/**
	 * Create the application.
	 */
	public TileSelection() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		setBounds(100, 100, 450, 511);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{86, 0, 86, 124, 0};
		gridBagLayout.rowHeights = new int[]{0, 316, 31, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		table = new JTable();
		pane = new JScrollPane(table);
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.insets = new Insets(0, 0, 5, 0);
		gbc_table.gridheight = 2;
		gbc_table.gridwidth = 4;
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 0;
		add(pane, gbc_table);
		
		lblFilterText = new JLabel("Filter Tile name:");
		GridBagConstraints gbc_lblFilterText = new GridBagConstraints();
		gbc_lblFilterText.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblFilterText.insets = new Insets(0, 0, 0, 5);
		gbc_lblFilterText.gridx = 0;
		gbc_lblFilterText.gridy = 2;
		add(lblFilterText, gbc_lblFilterText);
		
		FilterText = new JTextField();
		GridBagConstraints gbc_FilterText = new GridBagConstraints();
		gbc_FilterText.anchor = GridBagConstraints.NORTH;
		gbc_FilterText.gridwidth = 3;
		gbc_FilterText.fill = GridBagConstraints.HORIZONTAL;
		gbc_FilterText.gridx = 1;
		gbc_FilterText.gridy = 2;
		add(FilterText, gbc_FilterText);
		FilterText.setColumns(10);
	}

}
