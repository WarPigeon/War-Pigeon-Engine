package com.runetooncraft.warpigeon.engine.level.specialtiles;

import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;
import com.runetooncraft.warpigeon.engine.level.Tile;

public class removeCollisionTile extends Tile {

	public removeCollisionTile(Sprite sprite, int TileID, String name) {
		super(sprite, TileID, name);
		Collide = false;
	}
	
	public void render(int x, int y, ScreenEngine2D screen, int Layer) {
		
	}

}
