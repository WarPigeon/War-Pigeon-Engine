package com.runetooncraft.warpigeon.engine.level;

import java.io.File;
import java.util.Random;

public class RandomLevel extends Level {

	private static final Random random = new Random();
	
	public RandomLevel(int width, int height) {
		super(width, height);
	}
	
	public RandomLevel(int width, int height, File workingDir, String LevelName) {
		super(width, height, workingDir, LevelName);
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
