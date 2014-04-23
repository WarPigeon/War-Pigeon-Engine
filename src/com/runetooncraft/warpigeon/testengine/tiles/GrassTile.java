package com.runetooncraft.warpigeon.testengine.tiles;

import java.util.Random;

import com.runetooncraft.warpigeon.engine.graphics.ScreenEngine2D;
import com.runetooncraft.warpigeon.engine.graphics.Sprite;
import com.runetooncraft.warpigeon.engine.level.Level;
import com.runetooncraft.warpigeon.engine.level.Tile;

public class GrassTile extends Tile {
	public GrassTile(Sprite sprite, int id) {
		super(sprite, id, "GrassTile");
	}

	public void render(int x, int y, ScreenEngine2D screen, int Layer) {
		int PDR = screen.ImageToPixelRatio;
		screen.renderTile(x << Level.PDR, y << Level.PDR, this);
	}

}
