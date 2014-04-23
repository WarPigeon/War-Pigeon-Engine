package com.runetooncraft.warpigeon.engine.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Sprite {

	public final int SIZE;
	public final int SIZEX;
	public final int SIZEY;
	private int x, y;
	public int[] pixels;
	private SpriteSheet sheet;
	private boolean ContainsPixels = false;
	
	/**
	 * Finds the pixels from a spritesheet created with the SpriteSheet class in a cetain position.
	 * @param size
	 * @param x
	 * @param y
	 * @param sheet
	 */
	public static Sprite DefaultGuiTop = new Sprite(128,32,0, 0, SpriteSheet.DefaultGUISheet);
	public static Sprite DefaultGuiBottom = new Sprite(128,32,0, 1, SpriteSheet.DefaultGUISheet);
	public static Sprite DefaultGuiFiller = new Sprite(128,2,0, (11*2), SpriteSheet.DefaultGUISheet);
	public static Sprite DefaultGuiComponentRIGHTARROW = new Sprite(8,8,0,0,SpriteSheet.DefaultGUIComponents);
	public static Sprite DefaultGuiComponentLEFTARROW = new Sprite(8,8,1,0,SpriteSheet.DefaultGUIComponents);
	
	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		this.SIZE = size;
		this.SIZEX = size;
		this.SIZEY = size;
		pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.x = this.x + 1 + x; //added 1 to account for pink lines to separate sprites.
		this.y = y * size; 
		this.y = this.y + 1 + y; //added 1 to account for pink lines to separate sprites.
		this.sheet = sheet;
		load(false, false);
	}
	
	public Sprite(int size, int x, int y, SpriteSheet sheet, boolean FlipVertically) {
		this.SIZE = size;
		this.SIZEX = size;
		this.SIZEY = size;
		pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.x = this.x + 1 + x; //added 1 to account for pink lines to separate sprites.
		this.y = y * size; 
		this.y = this.y + 1 + y; //added 1 to account for pink lines to separate sprites.
		this.sheet = sheet;
		load(FlipVertically, false);
	}
	
	public Sprite(int size, int x, int y, SpriteSheet sheet, boolean FlipVertically, boolean FlipHorizontally) {
		this.SIZE = size;
		this.SIZEX = size;
		this.SIZEY = size;
		pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.x = this.x + 1 + x; //added 1 to account for pink lines to separate sprites.
		this.y = y * size; 
		this.y = this.y + 1 + y; //added 1 to account for pink lines to separate sprites.
		this.sheet = sheet;
		load(FlipVertically, FlipHorizontally);
	}
	
	public Sprite(int sizeX, int sizeY, int x, int y, SpriteSheet sheet) {
		if(sizeX <= sizeY) {
			this.SIZE = sizeX;
		} else {
			this.SIZE = sizeY;
		}
		this.SIZEX = sizeX;
		this.SIZEY = sizeY;
		pixels = new int[SIZEX * SIZEY];
		this.x = x * sizeX;
		this.y = y * sizeY;
		this.x = this.x + 1 + x; //added 1 to account for pink lines to separate sprites.
		this.y = this.y + 1 + y; //added 1 to account for pink lines to separate sprites.
		this.sheet = sheet;
		load(false, false);
	}
	public Sprite(int sizeX, int sizeY, SpriteSheet sheet) {
		if(sizeX <= sizeY) {
			this.SIZE = sizeX;
		} else {
			this.SIZE = sizeY;
		}
		this.SIZEX = sizeX;
		this.SIZEY = sizeY;
		pixels = new int[SIZEX * SIZEY];
		this.sheet = sheet;
		load(false, false);
	}
	
	public Sprite(int size, int colour) {
		this.SIZE = size;
		this.SIZEX = size;
		this.SIZEY = size;
		pixels = new int[SIZE*SIZE];
		setColour(colour);
	}
	
	private void setColour(int colour) {
		for (int i = 0; i < SIZE*SIZE; i++) {
			pixels[i] = colour;
		}
	}

	public void load(Boolean FlipVertically, Boolean FlipHorizontally) {		
		ContainsPixels = true;
		if(SIZEY >= SIZEX) {
			for(int y = 0; y < SIZEY; y++) {
				for (int x = 0; x < SIZEX; x++) {
					pixels[x+y*SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZE];
				}
			}
		} else {
			for (int x = 0; x < SIZEX; x++) {
				for(int y = 0; y < SIZEY; y++) {
					pixels[x+y*SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZE];
				}
			}
		}
		
		if(FlipVertically) {
			BufferedImage image = new BufferedImage(SIZEX, SIZEY, BufferedImage.TYPE_INT_RGB);
			image.setRGB(0, 0, SIZEX, SIZEY, pixels, 0, SIZE);
			AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
			tx.translate(0, -image.getHeight(null));
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			image = op.filter(image, null);
			image.getRGB(0, 0, SIZEX, SIZEY, pixels, 0, SIZE);
		}
		
		if(FlipHorizontally) {
			BufferedImage image = new BufferedImage(SIZEX, SIZEY, BufferedImage.TYPE_INT_RGB);
			image.setRGB(0, 0, SIZEX, SIZEY, pixels, 0, SIZE);
			AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
			tx.translate(-image.getWidth(null), 0);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			image = op.filter(image, null);
			image.getRGB(0, 0, SIZEX, SIZEY, pixels, 0, SIZE);
		}
	}
	
	public int getHeight() {
		return SIZEY;
	}
	
	public int getWidth() {
		return SIZEX;
	}
	
	public BufferedImage toBufferedImage() {
		BufferedImage image = new BufferedImage(SIZEX, SIZEY, BufferedImage.TYPE_INT_RGB);
		if(ContainsPixels) {
			image.setRGB(0, 0, SIZEX, SIZEY, pixels, 0, SIZE);
		} else {
			Graphics2D g2d = image.createGraphics();
			g2d.setColor(Color.gray);
			g2d.fillRect(0, 0, SIZEX, SIZEY);
		}
		return image;
	}
}
