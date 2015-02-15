package com.runetooncraft.warpigeon.testengine.tiles;

import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;
import com.runetooncraft.warpigeon.engine.level.Level;
import com.runetooncraft.warpigeon.engine.level.Tile;

public class GrassTile3 extends Tile {

	public GrassTile3(Sprite sprite, int id) {
		super(sprite, id, "GrassTile3");
	}

	public void render(int x, int y, ScreenEngine2D screen, int Layer) {
		screen.renderTile(x << Level.PDR, y << Level.PDR, this);
	}
}
