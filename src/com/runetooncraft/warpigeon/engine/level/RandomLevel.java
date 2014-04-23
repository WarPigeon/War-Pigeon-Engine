package com.runetooncraft.warpigeon.engine.level;

import java.io.File;
import java.util.Random;

import com.runetooncraft.warpigeon.engine.WPEngine4;

public class RandomLevel extends Level {

	private static final Random random = new Random();
	
	public RandomLevel(int width, int height, WPEngine4 engine) {
		super(width, height, engine);
	}
	
	public RandomLevel(int width, int height, File workingDir, String LevelName, WPEngine4 engine) {
		super(width, height, workingDir, LevelName, engine);
	}
	
	protected void generateLevel() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tiles[x+y*width] = random.nextInt(3); //Very basic
				for(int[] Layer: LayerList) {
					Layer[x+y*width] = EmptyTile.getTileID();
				}
			}
		}
		SaveLevel();
	}
}
