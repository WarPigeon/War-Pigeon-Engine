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
				for(int i = 0; i < LayerList.size(); i++) {
					Layer layer = LayerList.get(i);
					if(i == 0) {
						layer.tiles[x+y*height] = random.nextInt(5); //Very basic
					} else {
						layer.tiles[x+y*height] = EmptyTile.getTileID();
					}
				}
			}
		}
		SaveLevel();
	}
}
