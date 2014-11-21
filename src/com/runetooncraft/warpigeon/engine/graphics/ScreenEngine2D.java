package com.runetooncraft.warpigeon.engine.graphics;

import java.util.Random;

import com.runetooncraft.warpigeon.engine.entity.mob.Player;
import com.runetooncraft.warpigeon.engine.level.Level;
import com.runetooncraft.warpigeon.engine.level.Tile;

public class ScreenEngine2D {

	public int width, height, PixelWidth, PixelHeight, scale;
	public int[] pixels;
	public int[] tiles;
	public static int ImageToPixelRatio;
	private Random random = new Random();
	private boolean RenderWallSprite = false;
	private Sprite wall;
	public int xOffset, yOffset;
	public int XMid,YMid;
	/**
	 * ScreenEngine2D handles everything to do with 2-Dimensional pixel rendering and handling
	 * @param width
	 * @param height
	 */
	public ScreenEngine2D(int width, int height, int PixelWidth, int PixelHeight, int ImageToPixelRatio, int scale) {
		this.width = width;
		this.height = height;
		this.PixelWidth = PixelWidth;
		this.PixelHeight = PixelHeight;
		this.pixels = new int[ width * height ];
		this.tiles = new int[PixelWidth * PixelHeight];
		this.ImageToPixelRatio = ImageToPixelRatio;
		Level.PDR = (ImageToPixelRatio / 16) + 3;
		this.scale = scale;
		XMid = width / 2;
		YMid = height / 2;
	}
	
	public void RandomAllPixelColors() {
		for (int i = 0; i < PixelWidth * PixelHeight; i++) {
			tiles[i] = random.nextInt(0xffffff);
		}
	}
	
	public void SetSpriteWall(Sprite sprite) {
		this.wall = sprite;
		RenderWallSprite = true;
	}
	
	public void TwoHorizontalLineAllPixelColors(int Color1, int Color2) {
		boolean Color1bool = true;
		for (int i = 0; i < PixelWidth * PixelHeight; i++) {
			if(Color1bool) {
				tiles[i] = Color1;
				Color1bool = false;
			}else{
				tiles[i] = Color2;
				Color1bool = true;
			}
		}
	}
	/**
	 * Renders pixels
	 */
	public void render(int xMovement, int yMovement) {
		if(RenderWallSprite) {
			for (int y = 0; y < height; y++) {
				int yp = y + yMovement;
				if (yp < 0 || yp >= height) continue;
				for (int x = 0; x < width; x++) {
					int xp = x + xMovement;
					if (xp < 0 || xp >= width) continue;
					int PR = wall.SIZE - 1;
					pixels[xp + yp * width] = wall.pixels[(x & PR) + (y & PR) * (PR + 1)];
				}
			}
		} else {
			for (int y = 0; y < height; y++) {
				int yy = y + yMovement;
//				if (yy < 0 || yy >= height) break;
				for (int x = 0; x < width; x++) {
					int xx = x + xMovement;
//					if(xx < 0 || xx >= width) break;
					int tileIndex = ((xx >> 4) & PixelWidth - 1) + ((yy >> 4) & PixelHeight - 1) * PixelWidth;
						pixels[x + y * width] = tiles[tileIndex];
				}
			}
		}
	}
	
	public void renderTile(int xp, int yp, Tile tile) {
		xp -= xOffset;
		yp -= yOffset;
		Sprite sprite = tile.getSprite();
		for (int y = 0; y < sprite.SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.SIZE; x++) {
				int xa = x + xp;
				if (xa < -sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
//				pixels[xa+ya*width] = tile.sprite.pixels[x+y*tile.sprite.SIZE];
				int col = sprite.pixels[x+y*sprite.SIZE];
				if (col != 0xFFFF00D0) pixels[xa + ya * width] = col;
//				} else {
//					pixels[xa + ya * width] = pixels[(xa + ya * width) - 32];
//				}
			}
		}
	}
	
	public void renderPlayer(int xp, int yp, Sprite sprite) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < sprite.SIZEY; y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.SIZEX; x++) {
				int xa = x + xp;
				if (xa < - sprite.SIZEX || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
//				pixels[xa+ya*width] = sprite.pixels[x+y*sprite.SIZE];
				try {
					int col = sprite.pixels[x+y*sprite.SIZE];
					if (col != 0xFFFF00D0) pixels[xa + ya * width] = col;
				} catch(Exception e) {
					
				}
			}
		}
	}
	
	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0;x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
//				pixels[xa+ya*width] = sprite.pixels[x + y * sprite.getWidth()];
				int col = sprite.pixels[x + y * sprite.getWidth()];
				if (col != 0xFFFF00D0) pixels[xa+ya*width] = col;
			}
		}
	}
	
	public void renderImage(int xp, int yp, int[] imagePixels, int Width, int Height, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < Height; y++) {
			int ya = y + yp;
			for (int x = 0;x < Width; x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				int col = imagePixels[x + y * Width];
				pixels[xa+ya*width] = col;
			}
		}
	}
	
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	/**
	 * Clears screen
	 */
	public void ClearBuffer() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
}
