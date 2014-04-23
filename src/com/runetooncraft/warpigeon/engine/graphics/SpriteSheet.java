package com.runetooncraft.warpigeon.engine.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	
	private String path;
	public final int SIZE;
	public final int SIZEX;
	public final int SIZEY;
	public int[] pixels;
	
	public static SpriteSheet DefaultGUISheet = new SpriteSheet("/textures/Gui.png", 516, 132);
	public static SpriteSheet DefaultGUIComponents = new SpriteSheet("/textures/GuiComponents.png", 360);
	
	/**
	 * Use this to define sprite sheets that are attached as resources. Then use the Sprite class to identify sprites from the sprite sheet.
	 * @param path
	 * @param size
	 */
	public SpriteSheet(String path, int size) {
		this.path = path;
		this.SIZE = size;
		this.SIZEX = size;
		this.SIZEY = size;
		pixels = new int[SIZE * SIZE];
		load();
	}
	
	/**
	 * Use this to define sprite sheets that are attached as resources. Then use the Sprite class to identify sprites from the sprite sheet.
	 * @param path
	 * @param size
	 * @param sizeX
	 * @param sizeY
	 */
	public SpriteSheet(String path, int sizeX, int sizeY) {
		this.path = path;
		this.SIZE = sizeX; //...possibly
		this.SIZEX = sizeX;
		this.SIZEY = sizeY;
		pixels = new int[sizeX * sizeY];
		load();
	}
	
	public SpriteSheet(File file, BufferedImage image) {
		this.path = file.getAbsolutePath();
		this.SIZE = image.getWidth();
		this.SIZEX = image.getWidth();
		this.SIZEY = image.getHeight();
		pixels = new int[SIZEX * SIZEY];
		load();
		
	}	
	private void load() {
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
