package com.runetooncraft.warpigeon.engine.level;

import java.io.File;
import java.util.Random;

import com.runetooncraft.warpigeon.engine.WPEngine4;
import com.runetooncraft.warpigeon.engine.entity.mob.Mob;
import com.runetooncraft.warpigeon.engine.entity.mob.Player;

public class SideScrollingLevel extends Level {
	
	private static final Random random = new Random();
	private int[] GroundIDS;
	private int GroundLevel,GroundSize;
	private Player player;

	public SideScrollingLevel(int width, int height, int GroundLevel, int GroundSize, int[] GroundIDS, Player player, WPEngine4 engine) {
		super(width, height, engine);
		this.GroundIDS = GroundIDS;
		this.GroundLevel = GroundLevel;
		this.GroundSize = GroundSize;
		this.player = player;
		Setup();
	}
	
	public SideScrollingLevel(int width, int height, File workingDir, String LevelName, int GroundLevel, int GroundSize, int[] GroundIDS, Player player, WPEngine4 engine) {
		super(width, height, workingDir, LevelName, engine);
		this.GroundIDS = GroundIDS;
		this.GroundLevel = GroundLevel;
		this.GroundSize = GroundSize;
		this.player = player;
		Setup();
	}

	public void generateLevel() {
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

	private Tile RandomFloorTile() {
		return TileIDS.get(GroundIDS[random.nextInt(GroundIDS.length)]);
	}
	
	private void Setup() {
		for(int layer = 1; layer <= (LayerList.size() + 1); layer++) {
			for(int m = 0; m <= (GroundSize - 1); m++) {
				int SetLevel = GroundLevel + m;
				for(int i = 0; i < width; i++) {
					TileCoordinate tc = new TileCoordinate(i,SetLevel);
					this.setTile(tc, RandomFloorTile(), layer);
				}
			}
		}
		SaveLevel();
		
		Mob[] mobBoard = new Mob[1];
		mobBoard[0] = player;
		gravity = new Gravity(2, mobBoard);
		GravityEnabled = true;
	}
}
