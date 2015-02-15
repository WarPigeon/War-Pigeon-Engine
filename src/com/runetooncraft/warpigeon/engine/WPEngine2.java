package com.runetooncraft.warpigeon.engine;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.utils.KeyBoardEvents;

public class WPEngine2 extends WPEngine1 {


	private static final long serialVersionUID = 1L;
	public Graphics graphics = null;
	public BufferedImage view = new BufferedImage(getUnscaledWidth(), getUnscaledHeight(), BufferedImage.TYPE_INT_RGB);
	protected int[] pixels = ((DataBufferInt)view.getRaster().getDataBuffer()).getData();
	protected ScreenEngine2D screen;
	protected KeyBoardEvents KeyEvents;
	protected int x = 0;
	protected int y = 0;
	
	/**
	 * WPEngine2 works more with buffering and graphics for 2 dimensional games. This engine allows you to render sprites, etc
	 * This method does not ask for an FPSLimit variable in order to keep this class compatible with older game tests.
	 * This means that the FPSLimit is automatically set to 60.0. I would recommend setting an FPS limit for your game with the new method though.
	 * Unless of course you are fine with the limit being 60. There must be a limit in place though in order to maintain flow.
	 * @param Height
	 * @param Width
	 * @param Scale
	 */
	public WPEngine2(int Height, int Width, int Scale, int PixelWidth, int PixelHeight, int ImageToPixelRatio, GameType gametype) {
		super(Height, Width, Scale, gametype);
		EngineStart();
		DefineScreen(PixelWidth, PixelHeight, ImageToPixelRatio, Scale);
	}
	
	/**
	 * WPEngine2 works more with buffering and graphics for 2 dimensional games. This engine allows you to render sprites, etc
	 * @param Height
	 * @param Width
	 * @param Scale
	 * @param ShowFPSLimit
	 */
	public WPEngine2(int Height, int Width, int Scale, boolean ShowFPSLimit, int PixelWidth, int PixelHeight, int ImageToPixelRatio, GameType gametype) {
		super(Height, Width, Scale, ShowFPSLimit, gametype);
		EngineStart();
		DefineScreen(PixelWidth, PixelHeight, ImageToPixelRatio, Scale);
	}
	
	/**
	 * WPEngine2 works more with buffering and graphics for 2 dimensional games. This engine allows you to render sprites, etc
	 * @param Height
	 * @param Width
	 * @param Scale
	 * @param FPSLimit
	 */
	public WPEngine2(int Height, int Width, int Scale, double FPSLimit, int PixelWidth, int PixelHeight, int ImageToPixelRatio, GameType gametype) {
		super(Height, Width, Scale, FPSLimit, gametype);
		EngineStart();
		DefineScreen(PixelWidth, PixelHeight, ImageToPixelRatio, Scale);
	}
	
	/**
	 * WPEngine2 works more with buffering and graphics for 2 dimensional games. This engine allows you to render sprites, etc
	 * 
	 * @param Height
	 * @param Width
	 * @param Scale
	 * @param FPSLimit
	 * @param ShowFPSLimit
	 */
	public WPEngine2(int Height, int Width, int Scale, double FPSLimit, boolean ShowFPSLimit, int PixelWidth, int PixelHeight, int ImageToPixelRatio, GameType gametype) {
		super(Height, Width, Scale, FPSLimit, ShowFPSLimit, gametype);
		EngineStart();
		DefineScreen(PixelWidth, PixelHeight, ImageToPixelRatio, Scale);
	}
	
	public void DefineScreen(int PixelWidth, int PixelHeight, int ImageToPixelRatio, int scale) {
		screen = new ScreenEngine2D(getUnscaledWidth(), getUnscaledHeight(), PixelWidth, PixelHeight, ImageToPixelRatio, scale);
	}

	/**
	 * After the class constructor from above is initialized, this takes the variables from each constructor and starts this portion of the WP Engine
	 * @param TileByTileRatio
	 */
	private void EngineStart() {
		addKeyListener(KeyEvents);
	}
	
	/**
	 * Override this method and use it to have events happen on every program tick, AKA - alot!
	 */
	public void update() {
		
	}
	
	public void EngineUpdate() {
		
	}
	
	@Override
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.ClearBuffer();
		screen.render(x,y);
		privateRender();
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		graphics = bs.getDrawGraphics();
		graphics.drawImage(view, 0, 0, getWidth(), getHeight(), null);
		graphics.dispose();
		bs.show();
	}
	
	public Graphics getGraphics() {
		return graphics;
	}
	
	public BufferedImage getBufferedView() {
		return view;	
	}
	
	public int[] getPixels() {
		return pixels;
	}

	public ScreenEngine2D getScreenEngine2D() {
		return screen;
	}
	
	public KeyBoardEvents getKeyListener() {
		return KeyEvents;
	}
	
	/**
	 * This sets the key listener for the engine. Use this! Not addkeylistener.
	 * @param KeyEvents
	 */
	public void setEngineKeyListener(KeyBoardEvents KeyEvents) {
		this.KeyEvents = KeyEvents;
		addKeyListener(KeyEvents);
		addFocusListener(KeyEvents);
	}
}
