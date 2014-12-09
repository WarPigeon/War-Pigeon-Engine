package com.runetooncraft.warpigeon.engine.utils;

import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;

public class Vector2i {

	private int x, y;
	private int TileX, TileY;
	private final int TILE_SIZEX = ScreenEngine2D.PixelWidth;
	private final int TILE_SIZEY = ScreenEngine2D.PixelWidth;
	
	public Vector2i(int x, int y, Vector2Type type) {
		if(type.equals(Vector2Type.BY_TILE)) {
			TileX = x;
			TileY = y;
			this.x = x * TILE_SIZEX;
			this.y = y * TILE_SIZEY;
		} else if(type.equals(Vector2Type.BY_PIXEL)) {
			this.x = x;
			this.y = y;
			TileX = x / TILE_SIZEX;
			TileY = y / TILE_SIZEY;
		}
	}
	
	public void update(int x, int y) {
		TileX = x;
		TileY = y;
		this.x = x * TILE_SIZEX;
		this.y = y * TILE_SIZEY;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int[] xy() {
		int[] r = new int[2];
		r[0] = x;
		r[1] = y;
		return r;
	}
	
	public int tileX() {
		return TileX;
	}
	
	public int tileY() {
		return TileY;
	}
	
	public int[] tileXY() {
		int[] r = new int[2];
		r[0] = TileX;
		r[1] = TileY;
		return r;
	}

	public void updatePixel(int x, int y) {
		TileX = x / TILE_SIZEX;
		TileY = y / TILE_SIZEY;
		this.x = x;
		this.y = y;
	}
}
