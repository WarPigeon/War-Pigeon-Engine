package com.runetooncraft.warpigeon.engine.level;

import java.io.File;
import java.util.Random;

import com.runetooncraft.warpigeon.engine.WPEngine4;
import com.runetooncraft.warpigeon.engine.level.Layer.Layer;

public class RandomLevel extends Level {

	private static final Random random = new Random();
	
	public RandomLevel(int width, int height, WPEngine4 engine, CollisionType colltype) {
		super(width, height, engine, colltype);
	}
	
	public RandomLevel(int width, int height, File workingDir, String LevelName, WPEngine4 engine, CollisionType colltype) {
		super(width, height, workingDir, LevelName, engine, colltype);
	}
	
	protected void generateLevel() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				mainLayer.tiles[x+y*width] = random.nextInt(3); //Very basic
				for(Layer Layer: LayerList) {
					Layer.tiles[x+y*width] = EmptyTile.getTileID();
				}
			}
		}
		SaveLevel();
	}
}
