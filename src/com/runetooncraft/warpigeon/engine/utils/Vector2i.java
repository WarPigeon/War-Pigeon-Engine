package com.runetooncraft.warpigeon.engine.utils;


public class Vector2i {

	private int x, y;
	private int TileX, TileY;
	public static int TILE_SIZEX;
	public static int TILE_SIZEY;
	private Vector2Type type = null;

	public Vector2i(Vector2Type type) {
		this.type = type;
		this.TileX = 0;
		this.TileY = 0;
		this.x = 0;
		this.y = 0;
	}

	public Vector2i(Vector2i vector) {
		this.type = vector.type;
		set(vector.getX(), vector.getY());
	}

	/**
	 * Creates a vector with Vecto2Type.BY_PIXEL
	 */
	public Vector2i() {
		this.type = Vector2Type.BY_PIXEL;
		this.x = 0;
		this.y = 0;

	}

	public Vector2i(int x, int y, Vector2Type type) {
		this.type = type;
		if (type.equals(Vector2Type.BY_TILE)) {
			this.TileX = x;
			this.TileY = y;
			this.x = x * TILE_SIZEX;
			this.y = y * TILE_SIZEY;
		} else if (type.equals(Vector2Type.BY_PIXEL)) {
			this.x = x;
			this.y = y;
			this.TileX = x / TILE_SIZEX;
			this.TileY = y / TILE_SIZEY;
		}
	}

	public Vector2i update(int x, int y) {
		if(type.equals(Vector2Type.BY_TILE)) {
			this.TileX = x;
			this.TileY = y;
			this.x = x * TILE_SIZEX;
			this.y = y * TILE_SIZEY;
		} else {
			this.x = x;
			this.y = y;
			this.TileX = x / TILE_SIZEX;
			this.TileY = y / TILE_SIZEY;
		}
		return this;
	}

	public Vector2i setX(int x) {
		if (type.equals(Vector2Type.BY_TILE)) {
			this.TileX = x;
		} else if (type.equals(Vector2Type.BY_PIXEL)) {
			this.x = x;
			this.TileX = x / TILE_SIZEX;
		}
		return this;
	}

	public Vector2i set(int x, int y) {
		setX(x);
		setY(y);
		return this;
	}
	
	public Vector2i setPixelToTile(int x, int y) {
		this.x = x;
		this.y = y;
		this.TileX = x / TILE_SIZEX;
		this.TileY = y / TILE_SIZEY;
		return this;
	}
	
	public Vector2i setTileToPixel(int TileX, int TileY) {
		this.TileX = TileX;
		this.TileY = TileY;
		this.x = TileX * TILE_SIZEX;
		this.y = TileY * TILE_SIZEY;
		return this;
	}

	public Vector2i setY(int y) {
		if (type.equals(Vector2Type.BY_TILE)) {
			this.TileY = y;
		} else if (type.equals(Vector2Type.BY_PIXEL)) {
			this.y = y;
			this.TileY = y / TILE_SIZEY;
		}
		return this;
	}

	public int getX() {
		if (type.equals(Vector2Type.BY_TILE)) {
			return TileX;
		} else { // UPDATE THIS IF YOU ADD MORE VECTOR2TYPES
			return x;
		}
	}
	
	/**
	 * Returns the pixel coordinate of X no matter what the vector type.
	 * @return
	 */
	public int getPixelX() {
		return x;
	}
	
	/**
	 * Returns the pixel coordinate of Y no matter what the vector type.
	 * @return
	 */
	public int getPixelY() {
		return y;
	}

	public int getY() {
		if (type.equals(Vector2Type.BY_TILE)) {
			return TileY;
		} else { // UPDATE THIS IF YOU ADD MORE VECTOR2TYPES
			return y;
		}
	}

	public Vector2i add(Vector2i vector) {
		this.x += vector.x;
		this.y += vector.y;
		this.TileX += vector.TileX;
		this.TileY += vector.TileY;
		return this;
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

	public static double getDistance(Vector2i tile, Vector2i goal) {
		double dx = tile.getX() - goal.getX();
		double dy = tile.getY() - goal.getY();
		return Math.sqrt((dx * dx) + (dy * dy));
	}
	
	public boolean equals(Object object) {
		if(!(object instanceof Vector2i)) return false;
		Vector2i vector = (Vector2i) object;
		if(getX() == vector.getX() && getY() == vector.getY() && type.equals(vector.type)) return true;
		return false;
	}
}
