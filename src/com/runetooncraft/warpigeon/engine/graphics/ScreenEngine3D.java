package com.runetooncraft.warpigeon.engine.graphics;

import java.util.Random;

import com.runetooncraft.warpigeon.engine.WPEngine5;
import com.runetooncraft.warpigeon.engine.utils3d.MouseListener;

public class ScreenEngine3D extends ScreenEngine2D {

	private WPEngine5 engine;
	private double renderDistance = 7000;
	private double[] zBuffer;

	public ScreenEngine3D(int width, int height, int PixelWidth, int PixelHeight, int ImageToPixelRatio, int scale, WPEngine5 engine) {
		super(width, height, PixelWidth, PixelHeight, ImageToPixelRatio, scale);
		this.engine = engine;
		zBuffer = new double[width * height];
	}

	public void draw3D() {
		floor();
		renderDistanceLimiter();
	}

	/**
	 * Sets the distance that the level will stop generating.
	 * 
	 * @param renderDistance
	 */
	public void setRenderDistance(double renderDistance) {
		this.renderDistance = renderDistance;
	}

	/**
	 * @return current distance that the level will stop generating.
	 */
	public double getRenderDistance() {
		return renderDistance;
	}

	private void floor() {
		Random random = new Random();
		double rotation = engine.getPosition().rotation;
		double floorPosition = 6;
		double ceilingPosition = 20;
		double cosine = Math.cos(rotation);
		double sine = Math.sin(rotation);

		double zCoord = engine.getPosition().z;
		double xCoord = engine.getPosition().x;
		// System.out.println("Z: " + zCoord + ", x: " + xCoord);
		for (int y = 0; y < height; y++) {
			double ceiling = (y - height / 2.0) / height;
			double z = floorPosition / ceiling;

			if (ceiling < 0) {
				z = ceilingPosition / -ceiling;
			}

			for (int x = 0; x < width; x++) {
				double depth = (x - width / 2.0) / height;
				depth *= z;
				double xx = depth * cosine + z * sine;
				double yy = z * cosine - depth * sine;
				int xPix = (int) (xx + xCoord);
				int yPix = (int) (yy + zCoord);
				zBuffer[x + y * width] = z;
				pixels[x + y * width] = ((xPix & 15) * 16) | ((yPix & 15) * 16) << 8;
//				pixels[x + y * width] = rb | g;
//				if (z > renderDistance) {
//					pixels[x + y * width] = 0;
//				}
			}
		}
	}
	
	public void renderDistanceLimiter() {
		for (int i = 0; i < width * height; i++) {
			int col = pixels[i];
			double brightness = (renderDistance / (zBuffer[i])) / 255;
//			System.out.println(brightness);
			if (brightness < 0) {
				brightness = 0;
			}
			
			if (brightness > 1) {
				brightness = 1;
			}
			
			int rb = (int) ((col & 0xFF00FF) * brightness) & 0xFF00FF;
			int g =  (int) ((col & 0x00FF00) * brightness) & 0x00FF00;
			
			pixels[i] = rb | g;
		}
	}
}
