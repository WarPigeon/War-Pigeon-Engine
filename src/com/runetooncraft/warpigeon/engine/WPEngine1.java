package com.runetooncraft.warpigeon.engine;

import com.runetooncraft.warpigeon.engine.utils.BasicGameWindow;

public class WPEngine1 extends BasicGameWindow implements Runnable {

	private Thread thread;
	private double FPSLimit = 60.0;
	protected boolean running = false;
	private boolean ShowFPSLimit = false;
	private String WindowTitle = "";
	public static String Version = "v0.1.16";
	/**
	 * War-Pigion Engine 1 is the most basic engine that all of the other engines base themselves off of.
	 * 
	 * @param Height
	 * @param Width
	 * @param Scale
	 * @param FPSLimit
	 */
	public WPEngine1(int Height, int Width, int Scale, double FPSLimit, GameType gametype) {
		super(Height, Width, Scale, gametype);
		this.FPSLimit = FPSLimit;
	}
	/**
	 * War-Pigion Engine 1 is the most basic engine that all of the other engines base themselves off of.
	 * This method does not ask for an FPSLimit variable in order to keep this class compatible with older game tests.
	 * This means that the FPSLimit is automatically set to 60.0. I would recommend setting an FPS limit for your game with the new method though.
	 * Unless of course you are fine with the limit being 60. There must be a limit in place though in order to maintain flow.
	 * @param Height
	 * @param Width
	 * @param Scale
	 */
	public WPEngine1(int Height, int Width, int Scale, GameType gametype) {
		super(Height, Width, Scale, gametype);
	}
	
	/**
	 * War-Pigion Engine 1 is the most basic engine that all of the other engines base themselves off of.
	 * 
	 * @param Height
	 * @param Width
	 * @param Scale
	 * @param FPSLimit
	 */
	public WPEngine1(int Height, int Width, int Scale, boolean ShowFPSLimit, GameType gametype) {
		super(Height, Width, Scale, gametype);
		this.ShowFPSLimit = ShowFPSLimit;
	}
	
	/**
	 * War-Pigion Engine 1 is the most basic engine that all of the other engines base themselves off of.
	 * 
	 * @param Height
	 * @param Width
	 * @param Scale
	 * @param FPSLimit
	 */
	public WPEngine1(int Height, int Width, int Scale, double FPSLimit, boolean ShowFPSLimit, GameType gametype) {
		super(Height, Width, Scale, gametype);
		this.FPSLimit = FPSLimit;
		this.ShowFPSLimit = ShowFPSLimit;
	}
	
	
	/**
	 * Start a thread
	 */
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	/**
	 * Stop a thread
	 */
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / FPSLimit;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		WindowTitle = GetWindowTitle();
		requestFocus();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				EngineUpdate();
				updates++;
				delta--;
			}
			render();
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
	
	/**
	 * Don't override. Used for default engine functions. Override update();
	 */
	public void EngineUpdate() {
		
	}
	/**
	 * Called while running before render();
	 */
	public void update() {
		
	}
	
	/**
	 * Called while running after update(); Please override privateRender();
	 */
	public void render() {
		privateRender();
	}
	
	/**
	 * Runs the same time as render(); Used for privatized code.
	 */
	public void privateRender() {
		
	}
	
	public String getWarPigionVersion() {
		return Version;
	}
}
