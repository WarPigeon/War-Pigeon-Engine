package com.runetooncraft.warpigeon.engine.level;

import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;

public class Tile {

	public int x,y;
	public TileCollide collideMap = new TileCollide();
	boolean isCollisionLayerTile = false;
	protected Sprite sprite;
	private int TileID;
	private String name;
	protected Boolean Collide = false;
	
	public Tile(Sprite sprite, int TileID, String name) {
		this.name = name;
		this.sprite = sprite;
		this.TileID = TileID;
	}
	
	public void render(int x, int y, ScreenEngine2D screen, int Layer) {
		if(sprite != null) {
			if(isCollisionLayerTile) {
				screen.renderCollisionLayerTile(x << Level.PDR, y << Level.PDR, this);
			} else {
				screen.renderTile(x << Level.PDR, y << Level.PDR, this);
			}
		}
	}
	
	public boolean collide(int side) {
		if(Collide) {
			return collideMap.GetCollideSide(side);
		} else {
			return false;
		}
	}
	
	/**
	 * @return TileID as definied by the game code
	 */
	public int getTileID() {
		return TileID;
	}

	public Object getName() {
		return name;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	
}
