package com.runetooncraft.warpigeon.engine.utils;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;

import com.runetooncraft.warpigeon.pigionsdk.*;
import com.runetooncraft.warpigeon.pigionsdk.tilesdk.*;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JLabel;

public class BasicFrame extends JFrame implements ComponentListener {

	public SDKPanel sdkpanel;
	public JComboBox selectedLayer;
	public JPanel GamePanel;
	public ExpandLevel expandLevel;
	public GridBagLayout gridBagLayout;
	public Newlevel newlevel;
	public OpenLevel openlevel = new OpenLevel();
	public JMenuItem mntmSave = new JMenuItem("Save");
	public JMenuItem OpenLevel;
	public JMenuItem AddLayer;
	public JMenuItem DeleteLayer;
	public JFrame TileSelection = new TileSelection();
	public NewTile newtile = new NewTile();
	private JMenu mnTile;
	private JMenuItem mntmNewTile;
	private JMenu mnLevel;
	public JMenuItem mntmExpand;
	/**
	 * Create the frame.
	 */
	public BasicFrame() {
		newlevel = new Newlevel();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		addComponentListener(this);
		TileSelection.setVisible(true);
		gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{506, 0};
		gridBagLayout.rowHeights = new int[]{341, 116, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		expandLevel = new ExpandLevel();
		GamePanel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		getContentPane().add(GamePanel, gbc_panel);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		
		sdkpanel = new SDKPanel();
		GridBagLayout gridBagLayout_1 = (GridBagLayout) sdkpanel.getLayout();
		gridBagLayout_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0};
		GridBagConstraints gbc_panel2 = new GridBagConstraints();
		gbc_panel2.insets = new Insets(0, 0, 0, 0);
		gbc_panel2.gridx = 0;
		gbc_panel2.gridy = 1;
		getContentPane().add(sdkpanel, gbc_panel2);
		
		JLabel lblLayer = new JLabel("Layer:");
		GridBagConstraints gbc_lblLayer = new GridBagConstraints();
		gbc_lblLayer.insets = new Insets(0, 0, 5, 5);
		gbc_lblLayer.gridx = 19;
		gbc_lblLayer.gridy = 3;
		sdkpanel.add(lblLayer, gbc_lblLayer);
		
		selectedLayer = new JComboBox();
		GridBagConstraints gbc_selectedLayer = new GridBagConstraints();
		gbc_selectedLayer.gridwidth = 5;
		gbc_selectedLayer.insets = new Insets(0, 0, 5, 0);
		gbc_selectedLayer.fill = GridBagConstraints.HORIZONTAL;
		gbc_selectedLayer.gridx = 21;
		gbc_selectedLayer.gridy = 3;
		sdkpanel.add(selectedLayer, gbc_selectedLayer);
		
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
		
		AddLayer = new JMenuItem("Add Layer");
		mnLayer.add(AddLayer);
		
		DeleteLayer = new JMenuItem("Delete Layer");
		mnLayer.add(DeleteLayer);
		
		mnTile = new JMenu("Tile");
		menuBar.add(mnTile);
		
		mntmNewTile = new JMenuItem("New Tile");
		mntmNewTile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newtile.setVisible(true);
			}
		});
		mnTile.add(mntmNewTile);
		
		mnLevel = new JMenu("Level");
		menuBar.add(mnLevel);
		
		mntmExpand = new JMenuItem("Expand");
		mnLevel.add(mntmExpand);
		
		JSplitPane splitPane = new JSplitPane();
		
	}
	@Override
	public void componentHidden(ComponentEvent e) {}
	@Override
	public void componentMoved(ComponentEvent e) {
		int x = this.getX() + this.getWidth() + 5;
		int y = this.getY() - 5;
		TileSelection.setLocation(x, y);
	}
	@Override
	public void componentResized(ComponentEvent e) {}
	@Override
	public void componentShown(ComponentEvent e) {}
}
