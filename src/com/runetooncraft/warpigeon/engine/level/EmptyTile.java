package com.runetooncraft.warpigeon.engine.level;

import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;
import com.runetooncraft.warpigeon.engine.level.Level;
import com.runetooncraft.warpigeon.engine.level.Tile;

public class EmptyTile extends Tile {

	public EmptyTile(Sprite sprite, int id) {
		super(sprite, id, "VoidTile");
	}

	public void render(int x, int y, ScreenEngine2D screen) {
		//Do nothing because empty tile
	}

}
