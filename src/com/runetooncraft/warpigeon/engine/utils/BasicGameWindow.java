package com.runetooncraft.warpigeon.engine.utils;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

import com.runetooncraft.warpigeon.engine.GameType;

public class BasicGameWindow extends Canvas implements Window {
	private static final long serialVersionUID = 1L;
	int width = 300;
	int height = width / 16 * 9;
	int scale = 3;
	JFrame frame;
	BasicFrame BFrame;
	Dimension size = null;
	/**
	 * for PigionSDK
	 */
	
	public BasicGameWindow(int Width, int Height, int Scale, GameType gametype) {
		if(gametype.equals(GameType.PIGION_SDK)) {
			this.width = Width;
			this.height = Height;
			this.scale = Scale;
			size = new Dimension(width * (scale / 1000), height * (scale / 1000));
			BFrame = new BasicFrame();
			setPreferredSize(size);
			frame = BFrame;
			System.out.println(size.width + "," + size.height);
			Dimension WholeFrame = new Dimension(size.width,size.height);
			frame.setSize(WholeFrame);
			BFrame.gridBagLayout.columnWidths = new int[]{size.width, 0};
			BFrame.gridBagLayout.rowHeights = new int[]{size.height, 116, 0};
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		} else if(gametype.equals(GameType.FREE_ROAM_TILE_BASED)) {
			this.width = Width;
			this.height = Height;
			this.scale = Scale;
			size = new Dimension(width * (scale / 1000), height * (scale / 1000));
			setPreferredSize(size);
			frame = new JFrame();
			frame.setSize(size);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		}
	}
	@Override
	public void SetWidth(int width) {
		this.width = width;
		size.setSize(this.width * scale, this.height * scale);
	}

	@Override
	public void SetHeight(int height) {
		this.height = height;
		size.setSize(this.width * scale, this.height * scale);
	}

	@Override
	public int getWidth() {
		return frame.getWidth(); //because scaling is not included
	}

	@Override
	public int getHeight() {
		return frame.getHeight();
	}
	
	/**
	 * @return JFrame
	 */
	public JFrame GetFrame() {
		return frame;
	}
	
	/**
	 * Set if you want the JFrame to be resizable or not
	 * @param bool
	 */
	public void SetWindowResizable(boolean bool) {
		frame.setResizable(bool);
	}

	/**
	 * Set the game window title
	 * @param title
	 */
	public void SetWindowTitle(String title) {
		frame.setTitle(title);
	}
	
	/**
	 * for JFrame purposes, please use this to set your class as the JFrame's class instantiation
	 * @param extendedclass
	 */
	public void SetClassInstance(Canvas extendedclass, Boolean PigionSDK) {
		if(PigionSDK) {
			BFrame.GamePanel.add(extendedclass);
		} else { 
			frame.add(extendedclass);
		}
	}
	
	/**
	 * Sets the Frame size the same as the component
	 */
	public void PackFrame() {
//		frame.pack();
	}
	
	/**
	 * By default true 
	 * Sets the window visibility
	 * @param visibile
	 */
	public void SetVisible(boolean visibile) {
		frame.setVisible(visibile);
	}
	@Override
	public int getUnscaledWidth() {
		return width;
	}
	@Override
	public int getUnscaledHeight() {
		return height;
	}
	
	/**
	 * Gets window title
	 * @return
	 */
	public String GetWindowTitle() {
		return frame.getTitle();
	}
	
	/**
	 * Used by PigionSDK
	 */
	public void SetViewedFrame(JFrame frame) {
		this.frame = frame;
	}
	
	/**
	 * Returns basicframe instance
	 */
	public BasicFrame getBasicFrame() {
		return BFrame;
	}
}
