package com.runetooncraft.warpigeon.engine;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.File;

import com.runetooncraft.warpigeon.engine.entity.Position;
import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine3D;
import com.runetooncraft.warpigeon.engine.utils.BasicGameWindow;
import com.runetooncraft.warpigeon.engine.utils3d.KeyListener;
import com.runetooncraft.warpigeon.engine.utils3d.MouseListener;

public class WPEngine5 extends BasicGameWindow implements Runnable {

	private static final long serialVersionUID = 1L;
	protected ScreenEngine3D screen3D;
	protected KeyListener KL;
	private double FPSLimit = 60.0;
	public MouseListener mouse3d = new MouseListener();
	public BufferedImage view = new BufferedImage(getUnscaledWidth(), getUnscaledHeight(), BufferedImage.TYPE_3BYTE_BGR);
	public byte[] pixels = ((DataBufferByte)view.getRaster().getDataBuffer()).getData();
	boolean running = false;
	private Thread thread;
	protected float delta;
	
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
	public WPEngine5(int Height, int Width, int Scale, File DataFolder, GameType gametype) {
		super(Width, Height, Scale, gametype);
		GetFrame().add(this);
		screen3D = new ScreenEngine3D(getUnscaledWidth(),getUnscaledHeight(), this);
	}
	
	public void EngineUpdate() {
		
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
	
	public KeyListener getKeyListener() {
		return KL;
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 100000000000.0 / FPSLimit;
		delta = 0;
		int frames = 0;
		String WindowTitle = GetWindowTitle();
		int updates = 0;
		requestFocus();
		while (running) {
			long now = System.nanoTime();
			delta = (float) ((now - lastTime) / ns);
			lastTime = now;
			while (delta >= 1) {
				update();
				EngineUpdate();
				updates++;
				delta--;
			}
			privateRender();
			screen3D.SwapBuffers();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) { 
				timer += 1000;
				SetWindowTitle(WindowTitle + " | " + "Updates: " + updates + "\n Frames: " + frames);
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	public void privateRender() {
		
	}

	private void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void update() {
		
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	public byte[] getPixels() {
		return pixels;
	}
	
	public BufferedImage getView() {
		return view;
	}
	
}
