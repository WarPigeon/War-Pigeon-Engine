package com.runetooncraft.warpigeon.engine.utils;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.GridBagLayout;
import javax.swing.JPanel;

import com.runetooncraft.warpigeon.pigionsdk.*;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SDKFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	public GridBagLayout gridBagLayout;
	public JPanel GamePanel, RightSidePanel;
	public SDKBotPanel BottomPanel;
	public SDKTopRightPanel TopRightPanel;
	public JPanel TileSelection = new TileSelection();
	public Newlevel newlevel = new Newlevel();
	public OpenLevel openlevel = new OpenLevel();
	public ExpandLevel expandLevel = new ExpandLevel();
	public JMenuItem mntmSave = new JMenuItem("Save");
	public JMenuItem OpenLevel, AddLayer, DeleteLayer, mntmNewTile, mntmExpand;
	public JMenu mnLevel;
	public JMenu mnRenderLayers;
	public SDKTopPanel TopPanel;
	
	public SDKFrame() {
		gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{818, 600, 0};
		gridBagLayout.rowHeights = new int[]{100, 457, 100, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		TopPanel = new SDKTopPanel();
		GridBagConstraints gbc_TopPanel = new GridBagConstraints();
		gbc_TopPanel.insets = new Insets(0, 0, 5, 5);
		gbc_TopPanel.fill = GridBagConstraints.BOTH;
		gbc_TopPanel.gridx = 0;
		gbc_TopPanel.gridy = 0;
		getContentPane().add(TopPanel, gbc_TopPanel);
		
		TopRightPanel = new SDKTopRightPanel();
		GridBagConstraints gbc_TopRightPanel = new GridBagConstraints();
		gbc_TopRightPanel.insets = new Insets(0, 0, 5, 0);
		gbc_TopRightPanel.fill = GridBagConstraints.BOTH;
		gbc_TopRightPanel.gridx = 1;
		gbc_TopRightPanel.gridy = 0;
		getContentPane().add(TopRightPanel, gbc_TopRightPanel);
		
		GamePanel = new JPanel();
		GridBagConstraints gbc_GamePanel = new GridBagConstraints();
		gbc_GamePanel.insets = new Insets(0, 0, 5, 5);
		gbc_GamePanel.fill = GridBagConstraints.BOTH;
		gbc_GamePanel.gridx = 0;
		gbc_GamePanel.gridy = 1;
		getContentPane().add(GamePanel, gbc_GamePanel);
		
		RightSidePanel = TileSelection;
		GridBagConstraints gbc_RightSidePanel = new GridBagConstraints();
		gbc_RightSidePanel.gridheight = 2;
		gbc_RightSidePanel.fill = GridBagConstraints.BOTH;
		gbc_RightSidePanel.gridx = 1;
		gbc_RightSidePanel.gridy = 1;
		getContentPane().add(RightSidePanel, gbc_RightSidePanel);
		
		BottomPanel = new SDKBotPanel();
		GridBagConstraints gbc_BottomPanel = new GridBagConstraints();
		gbc_BottomPanel.insets = new Insets(0, 0, 0, 5);
		gbc_BottomPanel.fill = GridBagConstraints.BOTH;
		gbc_BottomPanel.gridx = 0;
		gbc_BottomPanel.gridy = 2;
		getContentPane().add(BottomPanel, gbc_BottomPanel);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNewLevel = new JMenuItem("New Level");
		mntmNewLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newlevel.setVisible(true);
			}
		});
		mnFile.add(mntmNewLevel);
		
		OpenLevel = new JMenuItem("Open Level");
		mnFile.add(OpenLevel);
		mnFile.add(mntmSave);
		
		JMenu mnLayer = new JMenu("Layer");
		menuBar.add(mnLayer);
		
		mnRenderLayers = new JMenu("Render Layers");
		mnLayer.add(mnRenderLayers);
		
		AddLayer = new JMenuItem("Add Layer");
		mnLayer.add(AddLayer);
		
		DeleteLayer = new JMenuItem("Delete Layer");
		mnLayer.add(DeleteLayer);
		
		mnLevel = new JMenu("Level");
		menuBar.add(mnLevel);
		
		mntmExpand = new JMenuItem("Expand");
		mnLevel.add(mntmExpand);
	}
	
	public void setGameSize(int widthPixels, int heightPixels, int scale) { //delete this comment
		int[] Widths = gridBagLayout.columnWidths;
		int[] Heights = gridBagLayout.rowHeights;
		int width = 0,height = 0;
		width = widthPixels + Widths[1] + Widths[2]; 
		height = 100 + heightPixels + Heights[2] + Heights[3];
		setSize(width, height);
		this.setBounds(0, 0, width, height);
		
		System.out.println("Setting columnWidths to " + widthPixels + "," + Widths[1] + "," + Widths[2]);
		System.out.println("Setting rowHeights to " + Heights[0] + "," + heightPixels + "," + Heights[2] + "," + Heights[3]);
		
		gridBagLayout.columnWidths = new int[]{widthPixels, Widths[1], Widths[2]};
		gridBagLayout.rowHeights = new int[]{Heights[0], heightPixels, Heights[2], Heights[3]};
		
		//IntStreams can be used once Java 8 is out

		TopRightPanel.HeightPane.setValue(Integer.toString(heightPixels/ (scale / 1000)));
		TopRightPanel.WidthPane.setValue(Integer.toString(widthPixels / (scale / 1000)));
		TopRightPanel.ScalePane.setValue(Integer.toString(scale));
	}
}