package com.runetooncraft.warpigeon.engine;

import java.io.File;

import com.runetooncraft.warpigeon.engine.entity.Position;
import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine3D;
import com.runetooncraft.warpigeon.engine.utils3d.KeyListener;
import com.runetooncraft.warpigeon.engine.utils3d.MouseListener;

public class WPEngine5 extends WPEngine4 {

	protected ScreenEngine3D screen3D;
	protected double Time = 0.0;
	protected Position position;
	protected KeyListener KL;
	public MouseListener mouse3d = new MouseListener();
	/**
	 * WPEngine5 will integrate 3D!?
	 * @param Height
	 * @param Width
	 * @param Scale
	 * @param PixelWidth
	 * @param PixelHeight
	 * @param ImageToPixelRatio
	 * @param DataFolder
	 * @param gametype
	 */
	public WPEngine5(int Height, int Width, int Scale, int PixelWidth, int PixelHeight, int ImageToPixelRatio, File DataFolder, GameType gametype) {
		super(Height, Width, Scale, PixelWidth, PixelHeight, ImageToPixelRatio, DataFolder, gametype);
		EngineStart();
	}

	public WPEngine5(int Height, int Width, int Scale, double FPSLimit, int PixelWidth, int PixelHeight, int ImageToPixelRatio, File DataFolder, GameType gametype) {
		super(Height, Width, Scale, FPSLimit, PixelWidth, PixelHeight, ImageToPixelRatio, DataFolder, gametype);
		EngineStart();
	}

	public WPEngine5(int Height, int Width, int Scale, double FPSLimit, boolean ShowFPSLimit, int PixelWidth, int PixelHeight, int ImageToPixelRatio, File DataFolder, GameType gametype) {		
		super(Height, Width, Scale, FPSLimit, ShowFPSLimit, PixelWidth, PixelHeight, ImageToPixelRatio, DataFolder, gametype);
		EngineStart();
	}

	public WPEngine5(int Height, int Width, int Scale, boolean ShowFPSLimit, int PixelWidth, int PixelHeight, int ImageToPixelRatio, File DataFolder, GameType gametype) {
		super(Height, Width, Scale, ShowFPSLimit, PixelWidth, PixelHeight, ImageToPixelRatio, DataFolder, gametype);
		EngineStart();
	}
	
	public void render() {
		Time++;
		bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.ClearBuffer();
		position.Update();
		KL.update();
		privateRender();
		screen3D.draw3D();
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		graphics = bs.getDrawGraphics();
		graphics.drawImage(view, 0, 0, getWidth(), getHeight(), null);
		graphics.dispose();
		bs.show();
	}
	
	public void EngineStart() {
		KL = new KeyListener();
		setEngineKeyListener(KL);
		mouse = mouse3d;
		position = new Position(this);
	}
	
	public void DefineScreen(int PixelWidth, int PixelHeight, int ImageToPixelRatio, int scale) {
		screen3D = new ScreenEngine3D(getUnscaledWidth(),getUnscaledHeight(), PixelWidth, PixelHeight, ImageToPixelRatio, scale, this);
		screen = screen3D;
	}
	
	/**
	 * @return game tick
	 */
	public double getTick() {
		return Time;
	}
	
	/**
	 * @return the engine's mouse listener
	 */
	public MouseListener getMouseListener() {
		return mouse3d;
	}
	
	/**
	 * Returns player position
	 * @return
	 */
	public Position getPosition() {
		return position;
	}
	
	public KeyListener getKeyListener() {
		return KL;
	}
	
}
