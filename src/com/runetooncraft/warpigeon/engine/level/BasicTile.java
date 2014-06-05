package com.runetooncraft.warpigeon.engine.level;

import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;

public class BasicTile extends Tile  {

	
	public BasicTile(Sprite sprite, int TileID, String name, boolean collide) {
		super(sprite, TileID, name);
		this.Collide = collide;
	}
	
	public void render(int x, int y, ScreenEngine2D screen, int Layer) {
		screen.renderTile(x << Level.PDR, y << Level.PDR, this);
	}

}

