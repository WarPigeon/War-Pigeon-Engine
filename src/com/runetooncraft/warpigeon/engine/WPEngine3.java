package com.runetooncraft.warpigeon.engine;

import java.awt.image.BufferStrategy;
import java.io.File;

import com.runetooncraft.warpigeon.engine.entity.mob.Player;
import com.runetooncraft.warpigeon.engine.level.Level;
import com.runetooncraft.warpigeon.engine.utils.MouseEvents;

public class WPEngine3 extends WPEngine2 {

	private static final long serialVersionUID = 1L;
	protected Level level;
	protected Player player;
	protected File DataFolder;
	protected MouseEvents mouse = new MouseEvents();
	/**
	 * WpEngine3 Uses all of the data created before by WPEngine2 and adds a unique level system that reads from file or generates at random.
	 * @param Height
	 * @param Width
	 * @param Scale
	 * @param ShowFPSLimit
	 * @param PixelWidth
	 * @param PixelHeight
	 * @param ImageToPixelRatio
	 * @param DataFolder
	 */
	public WPEngine3(int Height, int Width, int Scale, double FPSLimit, boolean ShowFPSLimit, int PixelWidth, int PixelHeight, int ImageToPixelRatio, File DataFolder, GameType gametype) {
		super(Height, Width, Scale, FPSLimit, ShowFPSLimit, PixelWidth, PixelHeight, ImageToPixelRatio, gametype);
		this.DataFolder = DataFolder;
		EngineStart();
	}
	
	/**
	 * WpEngine3 Uses all of the data created before by WPEngine2 and adds a unique level system that reads from file or generates at random.
	 * @param Height
	 * @param Width
	 * @param Scale
	 * @param FPSLimit
	 * @param PixelWidth
	 * @param PixelHeight
	 * @param ImageToPixelRatio
	 * @param DataFolder
	 */
	public WPEngine3(int Height, int Width, int Scale, double FPSLimit, int PixelWidth, int PixelHeight, int ImageToPixelRatio, File DataFolder, GameType gametype) {
		super(Height,Width,Scale,FPSLimit,PixelWidth,PixelHeight,ImageToPixelRatio, gametype);
		this.DataFolder = DataFolder;
		EngineStart();
	}
	
	/**
	 * WpEngine3 Uses all of the data created before by WPEngine2 and adds a unique level system that reads from file or generates at random.
	 * @param Height
	 * @param Width
	 * @param Scale
	 * @param PixelWidth
	 * @param PixelHeight
	 * @param ImageToPixelRatio
	 * @param DataFolder
	 */
	public WPEngine3(int Height, int Width, int Scale, int PixelWidth, int PixelHeight, int ImageToPixelRatio, File DataFolder, GameType gametype) {
		super(Height,Width,Scale,PixelWidth,PixelHeight,ImageToPixelRatio, gametype);
		this.DataFolder = DataFolder;
		EngineStart();
	}
	
	/**
	 * WpEngine3 Uses all of the data created before by WPEngine2 and adds a unique level system that reads from file or generates at random.
	 * @param Height
	 * @param Width
	 * @param Scale
	 * @param ShowFPSLimit
	 * @param PixelWidth
	 * @param PixelHeight
	 * @param ImageToPixelRatio
	 * @param DataFolder
	 */
	public WPEngine3(int Height, int Width, int Scale, boolean ShowFPSLimit, int PixelWidth, int PixelHeight, int ImageToPixelRatio, File DataFolder, GameType gametype) {
		super(Height,Width,Scale,ShowFPSLimit,PixelWidth,PixelHeight,ImageToPixelRatio, gametype);
		this.DataFolder = DataFolder;
		EngineStart();
	}
	
	private void EngineStart() {
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}
	
	public void EngineUpdate() {
		
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.ClearBuffer();
		level.render(player.x, player.y, screen);
		privateRender();
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		graphics = bs.getDrawGraphics();
		graphics.drawImage(view, 0, 0, getWidth(), getHeight(), null);
		graphics.dispose();
		bs.show();
		if(mouse.getButton() == 1) {
			MouseLeftClicked();
		}
		if(mouse.getButton() == 3) {
			MouseRightClicked();
		}
	}

	public void MouseRightClicked() {
		
	}

	/**
	 * called when mouse is left clicked
	 * @param mouse
	 */
	public void MouseLeftClicked() {
//		System.out.println("X: " + mouse.getX() + ". Y: " + mouse.getY());
	}
	
	/**
	 * @return current loaded level instance.
	 */
	public Level getLevel() {
		return level;
	}
	
	/**
	 * Returns the data storage directory for the engine
	 */
	public File getWorkingDir() {
		return DataFolder;
	}
}
