package com.runetooncraft.warpigeon.engine.graphics;


import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import com.runetooncraft.warpigeon.engine.WPEngine5;
import com.runetooncraft.warpigeon.engine.utils3d.*;

public class ScreenEngine3D {

	private WPEngine5 engine;
	private final Bitmap frameBuffer;
	private final BufferStrategy bufferStrategy;
	private final Graphics graphics;
//	private double renderDistance = 7000;

	public ScreenEngine3D(int width, int height, WPEngine5 engine) {
		this.engine = engine;
		frameBuffer = new Bitmap(width, height);
		frameBuffer.clear((byte)0x60);
		engine.createBufferStrategy(1);
		bufferStrategy = engine.getBufferStrategy();
		graphics = bufferStrategy.getDrawGraphics();
	}
	
	public  void SwapBuffers() {
		frameBuffer.copyTobyteArray(engine.getPixels());
		graphics.drawImage(engine.getView(), 0, 0, frameBuffer.getWidth(), frameBuffer.getHeight(), null);
		bufferStrategy.show();
	}
	
	public Bitmap getFrameBuffer() {
		return frameBuffer;
	}
}
