package com.runetooncraft.warpigeon.engine.utils;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

import com.runetooncraft.warpigeon.engine.GameType;
import com.runetooncraft.warpigeon.engine.WPEngine4;

public class BasicGameWindow extends Canvas implements Window {
	private static final long serialVersionUID = 1L;
	int width = 300;
	int height = width / 16 * 9;
	public int scale = 3;
	JFrame frame;
	BasicFrame BFrame;
	SDKFrame SDK;
	Dimension size = null;
	private GameType gametype;
	public Dimension GameFrame;
	/**
	 * for PigionSDK
	 */
	
	public BasicGameWindow(int Width, int Height, int Scale, GameType gametype) {
		this.gametype = gametype;
		if(gametype.equals(GameType.PIGION_SDK)) {
			this.width = Width;
			this.height = Height;
			this.scale = Scale;
			size = new Dimension(width * (scale / 1000), height * (scale / 1000));
			//BFrame = new BasicFrame();
			SDK = new SDKFrame();
			//frame = BFrame;
			frame = SDK;
			GameFrame = new Dimension(size.width,size.height);
			setPreferredSize(GameFrame);
			SDK.GamePanel.add(this);
			SDK.GamePanel.setSize(GameFrame);
			//BFrame.gridBagLayout.columnWidths = new int[]{size.width, 0};
			//BFrame.gridBagLayout.rowHeights = new int[]{size.height, 116, 0};
			SDK.setGameSize(GameFrame.width,GameFrame.height, scale);
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
	
	@SuppressWarnings("static-access")
	public void setDimension(Dimension dimension, int scale, WPEngine4 engine) {
		engine.state = engine.state.SCREEN_RESETTING;
		this.scale = scale;
		GameFrame = dimension;
		this.width = GameFrame.width;
		this.height = GameFrame.height;
		setPreferredSize(GameFrame);
		SDK.GamePanel.setSize(GameFrame);
		SDK.setGameSize(GameFrame.width,GameFrame.height, scale);
		createBufferStrategy(3);
		engine.state = engine.state.PLAY;
	}
	
	@Override
	public void SetWidth(int width) {
		this.width = width;
		size.setSize(this.width * scale, this.height * scale);
		GameFrame = new Dimension(size.width,size.height);
		setPreferredSize(GameFrame);
		SDK.GamePanel.setSize(GameFrame);
		SDK.setGameSize(GameFrame.width,GameFrame.height, scale);
	}

	@Override
	public void SetHeight(int height) {
		this.height = height;
		size.setSize(this.width * scale, this.height * scale);
		GameFrame = new Dimension(size.width,size.height);
		setPreferredSize(GameFrame);
		SDK.GamePanel.setSize(GameFrame);
		SDK.setGameSize(GameFrame.width,GameFrame.height, scale);
	}

	@Override
	public int getWidth() {
		if(gametype.equals(GameType.PIGION_SDK)) {
			return GameFrame.width;
		}
		return frame.getWidth(); //because scaling is not included
	}

	@Override
	public int getHeight() {
		if(gametype.equals(GameType.PIGION_SDK)) {
			return GameFrame.height;
		}
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
			//BFrame.GamePanel.add(extendedclass);
			//SDK.GamePanel.add(extendedclass);
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
	
	/**
	 * Returns SKKFrame instanse
	 */
	public SDKFrame getSDKFrame() {
		return SDK;
	}
}
