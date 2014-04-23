package com.runetooncraft.warpigeon.engine.level;

import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;

public class TileCoordinate {

	private int x, y;
	private int TileX, TileY;
	private final int TILE_SIZE = ScreenEngine2D.ImageToPixelRatio;
	
	public TileCoordinate(int x, int y) {
		TileX = x;
		TileY = y;
		this.x = x * TILE_SIZE;
		this.y = y * TILE_SIZE;
	}
	
	public void update(int x, int y) {
		TileX = x;
		TileY = y;
		this.x = x * TILE_SIZE;
		this.y = y * TILE_SIZE;
	}
	
	public int x() {
		return x;
	}
	
	public int y() {
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
		TileX = x / TILE_SIZE;
		TileY = y / TILE_SIZE;
		this.x = x;
		this.y = y;
	}
}
