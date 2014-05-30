package com.runetooncraft.warpigeon.engine.utils;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

import com.runetooncraft.warpigeon.pigionsdk.*;
import com.runetooncraft.warpigeon.pigionsdk.tilesdk.NewTile;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class SDKFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	public GridBagLayout gridBagLayout;
	public JPanel GamePanel, RightSidePanel, RightEdgePanel;
	public SDKPanel BottomPanel;
	public JFrame TileSelection = new TileSelection();
	public NewTile newtile = new NewTile();
	
	public SDKFrame() {
		gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{633, 0, 0};
		gridBagLayout.rowHeights = new int[]{457, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		GamePanel = new JPanel();
		GridBagConstraints gbc_GamePanel = new GridBagConstraints();
		gbc_GamePanel.insets = new Insets(0, 0, 5, 5);
		gbc_GamePanel.fill = GridBagConstraints.BOTH;
		gbc_GamePanel.gridx = 0;
		gbc_GamePanel.gridy = 0;
		getContentPane().add(GamePanel, gbc_GamePanel);
		
		RightSidePanel = new JPanel();
		GridBagConstraints gbc_RightSidePanel = new GridBagConstraints();
		gbc_RightSidePanel.insets = new Insets(0, 0, 5, 0);
		gbc_RightSidePanel.fill = GridBagConstraints.BOTH;
		gbc_RightSidePanel.gridx = 1;
		gbc_RightSidePanel.gridy = 0;
		getContentPane().add(RightSidePanel, gbc_RightSidePanel);
		
		BottomPanel = new SDKPanel();
		GridBagConstraints gbc_BottomPanel = new GridBagConstraints();
		gbc_BottomPanel.insets = new Insets(0, 0, 0, 5);
		gbc_BottomPanel.fill = GridBagConstraints.BOTH;
		gbc_BottomPanel.gridx = 0;
		gbc_BottomPanel.gridy = 1;
		getContentPane().add(BottomPanel, gbc_BottomPanel);
		
		RightEdgePanel = new JPanel();
		GridBagConstraints gbc_RightEdgePanel = new GridBagConstraints();
		gbc_RightEdgePanel.fill = GridBagConstraints.BOTH;
		gbc_RightEdgePanel.gridx = 1;
		gbc_RightEdgePanel.gridy = 1;
		getContentPane().add(RightEdgePanel, gbc_RightEdgePanel);
	}
	
	public void setGameSize(int widthPixels, int heightPixels) {
		int[] Widths = gridBagLayout.columnWidths;
		int[] Heights = gridBagLayout.rowHeights;
		gridBagLayout.columnWidths = new int[]{widthPixels, Widths[1], Widths[2]};
		gridBagLayout.rowHeights = new int[]{heightPixels, Heights[1], Heights[2]};
	}
}
